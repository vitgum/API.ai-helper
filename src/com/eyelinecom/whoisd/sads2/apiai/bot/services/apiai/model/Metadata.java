package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

/**
 * Created by jeck on 13/04/16.
 */
public class Metadata {

  String intentId;
  String intentName;


  public Metadata() {
  }

  public String getIntentId() {
    return intentId;
  }

  public void setIntentId(String intentId) {
    this.intentId = intentId;
  }

  public String getIntentName() {
    return intentName;
  }

  public void setIntentName(String intentName) {
    this.intentName = intentName;
  }

  @Override
  public String toString() {
    return "Metadata{" +
        "intentId='" + intentId + '\'' +
        ", intentName='" + intentName + '\'' +
        '}';
  }

}
