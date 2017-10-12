package com.eyelinecom.whoisd.sads2.apiai.bot;

import com.eyelinecom.whoisd.sads2.apiai.bot.services.Services;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.stat.EventStatService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * author: Denis Enenko
 * date: 11.09.17
 */
@ApplicationScoped
public class WebContext {

  private final static CountDownLatch initLatch = new CountDownLatch(1);

  private static Services services;

  private static String apiAiClientAccessToken;
  private static String botAskCommandName;
  private static String pushUrl;
  private static List<String> adminsUserIds;


  static synchronized void init(Services services,
                                String apiAiClientAccessToken,
                                String botAskCommandName,
                                String pushUrl,
                                List<String> adminsUserIds) {

    if(WebContext.services == null)
      WebContext.services = services;

    if(WebContext.apiAiClientAccessToken == null)
      WebContext.apiAiClientAccessToken = apiAiClientAccessToken;

    if(WebContext.botAskCommandName == null)
      WebContext.botAskCommandName = botAskCommandName;

    if(WebContext.pushUrl == null)
      WebContext.pushUrl = pushUrl;

    if(WebContext.adminsUserIds == null)
      WebContext.adminsUserIds = adminsUserIds;

    initLatch.countDown();
  }

  @Produces
  public EventStatService getEventStatService() {
    try {
      initLatch.await();
      return services.getEventStatService();
    }
    catch(InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String getApiAiClientAccessToken() {
    try {
      initLatch.await();
      return apiAiClientAccessToken;
    }
    catch(InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String getBotAskCommandName() {
    try {
      initLatch.await();
      return botAskCommandName;
    }
    catch(InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String getPushUrl() {
    try {
      initLatch.await();
      return pushUrl;
    }
    catch(InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static List<String> getAdminsUserIds() {
    try {
      initLatch.await();
      return adminsUserIds;
    }
    catch(InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  static void shutdown() {
    try {
      if(services != null)
        services.shutdown();
    }
    catch(Exception ignored) {}
  }

}