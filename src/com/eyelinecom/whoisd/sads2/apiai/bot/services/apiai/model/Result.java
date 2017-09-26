package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model;

import java.util.List;
import java.util.Map;

/**
 * Created by jeck on 13/04/16.
 */
public class Result {

  Boolean actionIncomplete;
  String source;
  String resolvedQuery;
  Double score;
  String action;
  Map<String, Object> parameters;
  List<Context> contexts;
  Fulfillment fulfillment;
  Metadata metadata;


  public Result() {
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getResolvedQuery() {
    return resolvedQuery;
  }

  public void setResolvedQuery(String resolvedQuery) {
    this.resolvedQuery = resolvedQuery;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  public List<Context> getContexts() {
    return contexts;
  }

  public void setContexts(List<Context> contexts) {
    this.contexts = contexts;
  }

  public Fulfillment getFulfillment() {
    return fulfillment;
  }

  public void setFulfillment(Fulfillment fulfillment) {
    this.fulfillment = fulfillment;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public Boolean getActionIncomplete() {
    return actionIncomplete;
  }

  public void setActionIncomplete(Boolean actionIncomplete) {
    this.actionIncomplete = actionIncomplete;
  }

  @Override
  public String toString() {
    return "Result { " +
        "source = '" + source + '\'' +
        ", resolvedQuery = '" + resolvedQuery + '\'' +
        ", score = " + score +
        ", action = '" + action + '\'' +
        ", parameters = " + parameters +
        ", contexts = " + contexts +
        ", fulfillment = " + fulfillment +
        ", metadata = " + metadata +
        " }";
  }

}
