package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

/**
 * Created by jeck on 13/04/16.
 */
public class Fulfillment {

  String speech;
  String source;


  public Fulfillment() {
  }

  public String getSpeech() {
    return speech;
  }

  public void setSpeech(String speech) {
    this.speech = speech;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "Fulfillment { " +
        "speech = '" + speech + '\'' +
        ", source = '" + source + '\'' +
        " }";
  }

}
