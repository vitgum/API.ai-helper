package com.eyelinecom.whoisd.sads2.apiai.bot.web.servlets;

import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.AiApi;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Fulfillment;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Result;
import com.eyelinecom.whoisd.sads2.common.HttpDataLoader;
import com.eyelinecom.whoisd.sads2.apiai.bot.WebContext;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.apiai.model.Response;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Denis Enenko
 * date: 11.09.17
 */
public class ApiAiBotServlet extends HttpServlet {

  private final static Logger log = Logger.getLogger("API_AI_BOT_SERVLET");

  private final static Pattern RU_LANG_PATTERN = Pattern.compile("[А-яЁё]+");

  private final static String BOT_ERROR_TEXT_EN = "Sorry, I'm very busy right now. Please try again later.";
  private final static String BOT_ERROR_TEXT_RU = "Извините, я сейчас очень занят. Спросите меня чуть позже.";

  private final static String NOT_FOUND_ANSWER_TEXT_EN = "Sorry, I've got no answer to your question. Your request has been forwarded to our team. Please await for our reply.";
  private final static String NOT_FOUND_ANSWER_TEXT_RU = "Извините, у меня нет ответа на ваш вопрос. Ваш запрос был перенаправлен нашей команде. Пожалуйста, ожидайте ответа.";

  private final static String PAGE_EMPTY = "<page version=\"2.0\"></page>";
  private final static String PAGE_PROTOCOL_NOT_SUPPORTED = "<page version=\"2.0\"><div>Sorry, your messenger is not supported.</div></page>";


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request, response);
  }

  private static void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = getUserId(request);
    String protocol = request.getParameter("protocol");
    String eventId = request.getParameter("event.id");
    String service = request.getParameter("service");
    Lang lang = null;

    if(log.isDebugEnabled())
      logRequest(request, userId);

    try {
      if(!"telegram".equals(protocol)) { //поддерживаем только telegram
        if(log.isDebugEnabled())
          log.debug("Protocol is not supported [" + userId + "]: " + protocol);

        sendResponse(response, PAGE_PROTOCOL_NOT_SUPPORTED, userId);
        return;
      }

      String question = getQuestion(request, WebContext.getBotAskCommandName(), userId);

      if(question == null || question.isEmpty()) {
        if(log.isDebugEnabled())
          log.debug("No question, return empty page [" + userId + "].");

        sendResponse(response, PAGE_EMPTY, userId);
        return;
      }

      if(log.isInfoEnabled())
        log.info("Question [" + userId + "]: \"" + question + "\"");

      AiApi api = new AiApi(new HttpDataLoader(), WebContext.getApiAiClientAccessToken());
      Response aiResponse = api.query(userId, question);

      if(log.isDebugEnabled())
        log.debug("API.AI raw Response [" + userId + "]: " + aiResponse);

      Result result = aiResponse.getResult();

      if(log.isDebugEnabled())
        log.debug("API.AI Result [" + userId + "]: " + result);

      String answer = null;

      if(result != null && !"input.unknown".equals(result.getAction())) {
        Fulfillment fulfillment = result.getFulfillment();

        if(log.isDebugEnabled())
          log.debug("API.AI Fulfillment [" + userId + "]: " + fulfillment);

        if(fulfillment != null)
          answer = fulfillment.getSpeech();
      }

      lang = determineLanguage(question);
      processAnswer(response, answer, lang, service, userId, eventId, protocol);
    }
    catch(Exception e) {
      log.error("Error [" + userId + "]: " + e.getMessage(), e);
      try {
        String errorText = getBotErrorText(lang == null ? Lang.EN : lang);
        sendResponse(response, errorText, userId);
      }
      catch(Exception ex) {
        log.error(ex.getMessage(), ex);
      }
    }
    catch(Throwable e) {
      log.fatal("Fatal error occurred [" + userId + "].", e);
      try {
        String errorText = getBotErrorText(lang == null ? Lang.EN : lang);
        sendResponse(response, errorText, userId);
      }
      catch(Exception ex) {
        log.error(ex.getMessage(), ex);
      }
    }
  }

  private static void processAnswer(HttpServletResponse response, String answer, Lang lang, String service, String userId, String eventId, String protocol) throws IOException {
    if(log.isInfoEnabled())
      log.info("Answer [" + userId + "]: \"" + answer + "\"");

    if(answer == null) {
      String notFoundAnswerPage = createReplyPage(getNotFoundAnswerText(lang));
      sendResponse(response, notFoundAnswerPage, userId);
      sendForward(service, userId, eventId, protocol);
      return;
    }

    String answerPage = createReplyPage(answer);
    sendResponse(response, answerPage, userId);
  }

  private static String getNotFoundAnswerText(Lang lang) {
    switch(lang) {
      case RU:
        return NOT_FOUND_ANSWER_TEXT_RU;
      case EN:
      default:
        return NOT_FOUND_ANSWER_TEXT_EN;
    }
  }

  private static String getBotErrorText(Lang lang) {
    switch(lang) {
      case RU:
        return BOT_ERROR_TEXT_RU;
      case EN:
      default:
        return BOT_ERROR_TEXT_EN;
    }
  }

  private static String createReplyPage(String text) {
    return "<page version=\"2.0\" attributes=\"telegram.reply: true;\"><div>" + text + "</div></page>";
  }

  private static String getQuestion(HttpServletRequest request, String botAskCommandName, String userId) {
    String question = request.getParameter("event.text");

    if(log.isDebugEnabled())
      log.debug("Got question [" + userId + "]: \"" + question + "\"");

    if(question == null || question.isEmpty())
      return null;

    String botAskCommand = "/" + botAskCommandName;

    if(!question.startsWith(botAskCommand))
      return null;

    return question.substring(botAskCommand.length()).trim();
  }

  private static Lang determineLanguage(String question) {
    Matcher m = RU_LANG_PATTERN.matcher(question);
    return m.find() ? Lang.RU : Lang.EN;
  }

  private static String getUserId(HttpServletRequest request) {
    String userId = request.getParameter("user_id");

    if(userId == null)
      userId = request.getParameter("subscriber");

    return userId;
  }

  private static void logRequest(HttpServletRequest request, String userId) {
    String requestUrl = request.getRequestURL().toString();
    String query = request.getQueryString();

    if(query != null && !query.isEmpty())
      requestUrl += "?" + query;

    log.debug("Requested URL [" + userId + "]: " + requestUrl);
  }

  private static void sendResponse(HttpServletResponse response, String xmlPage, String userId) throws IOException {
    if(log.isDebugEnabled())
      log.debug("Send response [" + userId + "]: " + xmlPage);

    response.setContentType("text/xml; charset=utf-8");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    PrintWriter out = response.getWriter();
    out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    out.write(xmlPage);
    out.close();
  }

  private static void sendForward(String service, String userId, String eventId, String protocol) throws IOException {
    if(log.isDebugEnabled())
      log.debug("Send forward [" + userId + "]. Event ID: " + eventId);

    String pushUrl = WebContext.getPushUrl() + "?service=" + service + "&user_id=" + userId + "&forward_event=" + eventId + "&protocol=" + protocol + "&message=test"; //message=test - костыль для SADS

    URL url = new URL(pushUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    int responseCode = connection.getResponseCode();

    if(responseCode == 200) {
      if(log.isDebugEnabled())
        log.debug("Forward response code [" + userId + "]: 200");
    }
    else {
      log.error("Unable forward message [" + userId + "]. Response code: " + responseCode + ". User ID: " + userId + ", event ID: " + eventId + ", protocol: " + protocol);
    }
  }

}
