package com.eyelinecom.whoisd.sads2.apiai.bot.web.servlets;

/**
 * author: Denis Enenko
 * date: 12.10.17
 */
class EventStatData {

  private final int recognizedCount;
  private final int unrecognizedCount;


  EventStatData(int recognizedCount, int unrecognizedCount) {
    this.recognizedCount = recognizedCount;
    this.unrecognizedCount = unrecognizedCount;
  }

  int getRecognizedCount() {
    return recognizedCount;
  }

  int getUnrecognizedCount() {
    return unrecognizedCount;
  }

  int getRecognizedPercent() {
    int total = recognizedCount + unrecognizedCount;
    return Math.round(100 * recognizedCount / total);
  }

  int getUnrecognizedPercent() {
    return 100 - getRecognizedPercent();
  }

}
