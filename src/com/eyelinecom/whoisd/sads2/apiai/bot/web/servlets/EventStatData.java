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

  int getTotalCount() {
    return recognizedCount + unrecognizedCount;
  }

  int getRecognizedCount() {
    return recognizedCount;
  }

  int getUnrecognizedCount() {
    return unrecognizedCount;
  }

  int getRecognizedPercent() {
    int totalCount = getTotalCount();

    if(totalCount == 0)
      return 0;

    return Math.round(100 * recognizedCount / totalCount);
  }

  int getUnrecognizedPercent() {
    int totalCount = getTotalCount();

    if(totalCount == 0)
      return 0;

    return 100 - getRecognizedPercent();
  }

}
