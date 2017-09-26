package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

/**
 * Created by jeck on 13/04/16.
 */
public class Status {

  Integer code;
  String errorType;
  String errorId;
  String errorDetails;


  public Status() {
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

  public String getErrorDetails() {
    return errorDetails;
  }

  public void setErrorDetails(String errorDetails) {
    this.errorDetails = errorDetails;
  }

  @Override
  public String toString() {
    return "Status { " +
        "code = " + code +
        ", errorType = '" + errorType + '\'' +
        ", errorId = '" + errorId + '\'' +
        ", errorDetails = '" + errorDetails + '\'' +
        " }";
  }

}
