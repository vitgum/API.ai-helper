package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai;

import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Entities;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Entity;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Response;
import com.eyelinecom.whoisd.sads2.apiai.bot.utils.MarshalUtils;
import com.eyelinecom.whoisd.sads2.common.Loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeck on 14/04/16.
 */
public class AiApi {

  private final static String LANG = "RU";
  private final static String API_URL = "https://api.api.ai/v1/";

  private Loader<Loader.Entity> loader;
  private String token;


  public AiApi(Loader<Loader.Entity> loader, String token) {
    this.loader = loader;
    this.token = token;
  }

  public Response query(String session, String query) throws Exception {
    Map<String, String> parameters = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);

    parameters.put("query", query);
    parameters.put("v", "20150910");
    parameters.put("sessionId", session);
    parameters.put("lang", LANG);
    parameters.put("timezone", "Asia/Novosibirsk");

    Loader.Entity entity = loader.load(API_URL + "query", parameters, headers, "get");
    String json = new String(entity.getBuffer(), "utf-8");
    Response response = MarshalUtils.unmarshal(MarshalUtils.parse(json), Response.class);
    response.setRaw(json);

    return response;
  }

  private Entities.Entry findEntry(String name) throws Exception {
    Map<String, String> parameters = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    parameters.put("v", "20150910");

    Loader.Entity data = loader.load(API_URL + "entities", parameters, headers, "get");
    String json = new String(data.getBuffer(), "utf-8");
    Entities.Entry[] entities = MarshalUtils.unmarshal(MarshalUtils.parse(json), Entities.Entry[].class);
    for(Entities.Entry entry : entities) {
      if(name.equals(entry.getName()))
        return entry;
    }
    return null;
  }

  public Entity getEntity(String name) throws Exception {
    Entities.Entry entry = this.findEntry(name);
    if(entry == null)
      return null;

    Map<String, String> parameters = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer " + token);
    parameters.put("v", "20150910");

    Loader.Entity entity = loader.load(API_URL + "entities/" + entry.getId(), parameters, headers, "get");
    String json = new String(entity.getBuffer(), "utf-8");
    return MarshalUtils.unmarshal(MarshalUtils.parse(json), Entity.class);
  }

}
