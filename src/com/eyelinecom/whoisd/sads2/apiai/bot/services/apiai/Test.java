package com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai;

import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Response;
import com.eyelinecom.whoisd.sads2.common.HttpDataLoader;
import com.eyelinecom.whoisd.sads2.common.Loader;

import java.util.UUID;

/**
 * author: Denis Enenko
 * date: 26.09.17
 */
public class Test {

  public static void main(String[] args) throws Exception {
    Loader<Loader.Entity> loader = new HttpDataLoader();
    AiApi api = new AiApi(loader, "API_AI_TOKEN");
    Response response = api.query(UUID.randomUUID().toString(), "Who are you?");
    System.out.println(response);
  }

}
