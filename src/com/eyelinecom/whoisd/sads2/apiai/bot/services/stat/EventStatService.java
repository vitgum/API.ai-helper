package com.eyelinecom.whoisd.sads2.apiai.bot.services.stat;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * author: Denis Enenko
 * date: 11.10.17
 */
public class EventStatService {

  private final static Logger log = Logger.getLogger("API_AI_BOT_STAT");

  private final static Pattern COMMA = Pattern.compile(",");

  //<время события в ms, тип события>
  private final Map<Long, EventType> eventStat = new LinkedHashMap<Long, EventType>() {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Long, EventType> eldest) {
      Date eldestDate = new Date(eldest.getKey());
      Date boundaryDate = DateUtils.addDays(eldestDate, -11);
      return eldestDate.before(boundaryDate);
    }
  };

  private final File statFile;
  private long lastDumpedEventTime;


  public EventStatService(File statFile) throws IOException {
    this.statFile = statFile;
    init();
    ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    executor.scheduleWithFixedDelay(new StatDumper(), 1, 1, TimeUnit.MINUTES);
  }

  private void init() throws IOException {
    if(log.isInfoEnabled())
      log.info("Starting init EventStatService from dump file...");

    BufferedReader r = null;

    try {
      synchronized(eventStat) {
        r = new BufferedReader(new FileReader(statFile));
        String line;

        while((line = r.readLine()) != null) {
          try {
            String[] v = COMMA.split(line);

            long time = Long.valueOf(v[0]);
            EventType type = EventType.valueOf(v[1]);

            eventStat.put(time, type);
          }
          catch(IllegalArgumentException e) {
            log.error("Can't parse stat line: " + line);
          }
        }
      }
    }
    finally {
      if(r != null)
        r.close();
    }

    if(log.isInfoEnabled())
      log.info("Finished init EventStatService. Records size: " + eventStat.size());
  }

  public void registerEvent(EventType eventType) {
    if(log.isDebugEnabled())
      log.debug("registerEvent(" + eventType.name() + ")");

    synchronized(eventStat) {
      if(eventStat.get(System.currentTimeMillis()) != null) {
        try { Thread.sleep(1); } catch(InterruptedException ignored) {}
      }

      eventStat.put(System.currentTimeMillis(), eventType);
    }
  }

  public int getTotalNumberOfEvents(EventType eventType) {
    int count = 0;

    synchronized(eventStat) {
      for(Map.Entry<Long, EventType> e : eventStat.entrySet()) {
        if(e.getValue() == eventType)
          count++;
      }
    }

    if(log.isDebugEnabled())
      log.debug("getTotalNumberOfEvents(" + eventType.name() + ") :" + count);

    return count;
  }

  public int getNumberOfEventsForDay(EventType eventType, Date date) {
    int count = 0;

    long startOfDay = getStartOfDay(date).getTime();
    long endOfDay = getEndOfDay(date).getTime();

    synchronized(eventStat) {
      for(Map.Entry<Long, EventType> e : eventStat.entrySet()) {
        if(e.getValue() == eventType && startOfDay <= e.getKey() && e.getKey() <= endOfDay)
          count++;
      }
    }

    if(log.isDebugEnabled()) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      log.debug("getNumberOfEventsForDay(" + eventType.name() + ", " + sdf.format(new Date(startOfDay)) + "): " + count);
    }

    return count;
  }

  private static Date getStartOfDay(Date date) {
    return DateUtils.round(DateUtils.addHours(date, -12), Calendar.DAY_OF_MONTH);
  }

  private static Date getEndOfDay(Date date) {
    Date day = DateUtils.round(DateUtils.addHours(date, 12), Calendar.DAY_OF_MONTH);
    return new Date(day.getTime() - 1);
  }

  public void shutdown() {
    new StatDumper().run();
  }


  private final class StatDumper implements Runnable {

    @Override
    public void run() {
      if(log.isInfoEnabled())
        log.info("Start stat dumping to file...");

      synchronized(eventStat) {
        if(eventStat.isEmpty()) {
          if(log.isDebugEnabled())
            log.debug("There is nothing to dump.");

          return;
        }

        PrintWriter w = null;
        try {
          w = new PrintWriter(new BufferedWriter(new FileWriter(statFile, true)));
          int newRecs = 0;

          for(Map.Entry<Long, EventType> e : eventStat.entrySet()) {
            long eventTime = e.getKey();

            if(eventTime > lastDumpedEventTime) {
              w.println(eventTime + "," + e.getValue().name());
              lastDumpedEventTime = eventTime;
              newRecs++;
            }
          }

          if(log.isDebugEnabled())
            log.debug("Dumped new records: " + newRecs);
        }
        catch(Exception e) {
          log.error(e.getMessage(), e);
        }
        finally {
          if(w != null) {
            try { w.close(); } catch(Exception ignored) {}
          }
        }
      }

      if(log.isInfoEnabled())
        log.info("Finished stat dumping.");
    }

  }

}
