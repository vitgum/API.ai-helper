package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by jeck on 13/04/16.
 */
public class Response {

  String id;

  Date timestamp;
  Result result;
  Status status;

  @JsonIgnore
  String raw;


  public Response() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getRaw() {
    return raw;
  }

  public void setRaw(String raw) {
    this.raw = raw;
  }

  @Override
  public String toString() {
    return "Response { " +
        "id = '" + id + '\'' +
        ", timestamp = " + timestamp +
        ", result = " + result +
        ", status = " + status +
        " }";
  }

}
