package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

import java.util.Map;

/**
 * Created by jeck on 13/04/16.
 */
public class Context {

  String name;
  Map<String, Object> parameters;
  Integer lifespan;


  public Context() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  public Integer getLifespan() {
    return lifespan;
  }

  public void setLifespan(Integer lifespan) {
    this.lifespan = lifespan;
  }

}
