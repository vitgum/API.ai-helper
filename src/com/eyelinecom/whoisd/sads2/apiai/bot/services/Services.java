package com.eyelinecom.whoisd.sads2.apiai.bot.services;

import com.eyeline.utils.config.ConfigException;
import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.stat.EventStatService;

import java.io.File;
import java.io.IOException;

/**
 * author: Denis Enenko
 * date: 11.10.17
 */
public class Services {

  private final EventStatService eventStatService;


  public Services(XmlConfigSection config) throws ServicesException {
    this.eventStatService = initEventStatService(config);
  }

  private EventStatService initEventStatService(XmlConfigSection config) throws ServicesException {
    try {
      File statFile = new File(config.getString("dump.stat.file"));

      if(!statFile.exists() && !statFile.createNewFile())
        throw new ServicesException("Can't create empty file for event stat dumping.");

      return new EventStatService(statFile);
    }
    catch(ConfigException | IOException e) {
      throw new ServicesException("Error during EventStatService initialization", e);
    }
  }

  public EventStatService getEventStatService() {
    return eventStatService;
  }

  public void shutdown() {
    eventStatService.shutdown();
  }

}