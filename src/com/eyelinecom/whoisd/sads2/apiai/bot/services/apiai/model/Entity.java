package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeck on 21/04/16.
 */
public class Entity {

  String id;
  String name;
  List<Entry> entries;
  boolean isEnum;
  boolean automatedExpansion;


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

  public List<Entry> getEntries() {
    return entries;
  }

  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  public boolean isEnum() {
    return isEnum;
  }

  public void setEnum(boolean isEnum) {
    this.isEnum = isEnum;
  }

  public boolean isAutomatedExpansion() {
    return automatedExpansion;
  }

  public void setAutomatedExpansion(boolean automatedExpansion) {
    this.automatedExpansion = automatedExpansion;
  }

  @Override
  public String toString() {
    return "Entity{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", entries=" + entries +
        ", isEnum=" + isEnum +
        ", automatedExpansion=" + automatedExpansion +
        '}';
  }

  public static class Entry {
    String value;
    String[] synonyms;

    public Entry() {
    }

    @Override
    public String toString() {
      return "Entry{" +
          "value='" + value + '\'' +
          ", synonyms=" + Arrays.toString(synonyms) +
          '}';
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String[] getSynonyms() {
      return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
      this.synonyms = synonyms;
    }
  }

}
