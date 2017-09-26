package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

/**
 * Created by jeck on 15/04/16.
 */
public class Request {

  private String timezone;
  private String lang;
  private String sessionId;


  public Request() {
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

}
