package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

/**
 * Created by jeck on 21/04/16.
 */
public class Entities {

  Entry[] entities;
  Status status;


  public Entities() {
  }

  public Entry[] getEntities() {
    return entities;
  }

  public void setEntities(Entry[] entities) {
    this.entities = entities;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public static class Entry {
    String id;
    String name;
    Integer count;
    String preview;


    public Entry() {
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getCount() {
      return count;
    }

    public void setCount(Integer count) {
      this.count = count;
    }

    public String getPreview() {
      return preview;
    }

    public void setPreview(String preview) {
      this.preview = preview;
    }
  }

}
