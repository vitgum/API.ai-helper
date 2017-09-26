package com.eyelinecom.whoisd.sads2.apiai.bot;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CountDownLatch;

/**
 * author: Denis Enenko
 * date: 11.09.17
 */
@ApplicationScoped
public class WebContext {

  private final static CountDownLatch initLatch = new CountDownLatch(1);

  private static String apiAiClientAccessToken;
  private static String botAskCommandName;
  private static String pushUrl;


  static synchronized void init(String apiAiClientAccessToken,
                                String botAskCommandName,
                                String pushUrl) {

    if(WebContext.apiAiClientAccessToken == null)
      WebContext.apiAiClientAccessToken = apiAiClientAccessToken;

    if(WebContext.botAskCommandName == null)
      WebContext.botAskCommandName = botAskCommandName;

    if(WebContext.pushUrl == null)
      WebContext.pushUrl = pushUrl;

    initLatch.countDown();
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

}