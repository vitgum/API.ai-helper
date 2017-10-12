package com.eyelinecom.whoisd.sads2.apiai.bot;

import com.eyeline.utils.config.ConfigException;
import com.eyeline.utils.config.xml.XmlConfig;
import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.Services;
import com.eyelinecom.whoisd.sads2.apiai.bot.services.ServicesException;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * author: Denis Enenko
 * date: 11.09.17
 */
public class InitListener implements ServletContextListener {

  private final static String CONFIG_DIR = "sads-apiai-bot.config.dir";
  private final static String DEFAULT_CONFIG_DIR = "conf";
  private final static String CONFIG_FILE_NAME = "config.xml";


  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    final File configDir = getConfigDir();

    initLog4j(configDir);

    XmlConfig cfg = loadXmlConfig(configDir);
    XmlConfigSection botCfg = getBotConfig(cfg);

    Services services = initServices(botCfg);

    String apiAiClientAccessToken = getApiAiClientAccessToken(botCfg);
    String botAskCommandName = getBotAskCommandName(botCfg);
    String pushUrl = getPushUrl(botCfg);
    List<String> adminsUserIds = getAdminsUserIds(botCfg);

    WebContext.init(services, apiAiClientAccessToken, botAskCommandName, pushUrl, adminsUserIds);
  }

  private File getConfigDir() {
    String configDir = System.getProperty(CONFIG_DIR);

    if(configDir == null) {
      configDir = DEFAULT_CONFIG_DIR;
      System.err.println("System property '" + CONFIG_DIR + "' is not set. Using default value: " + configDir);
    }

    File cfgDir = new File(configDir);

    if(!cfgDir.exists())
      throw new RuntimeException("Config directory '" + cfgDir.getAbsolutePath() + "' does not exist");

    System.out.println("Using config directory '" + cfgDir.getAbsolutePath() + "'");

    return cfgDir;
  }

  private XmlConfig loadXmlConfig(File configDir) {
    final File cfgFile = new File(configDir, CONFIG_FILE_NAME);
    XmlConfig cfg = new XmlConfig();

    try {
      cfg.load(cfgFile);
    }
    catch(ConfigException e) {
      throw new RuntimeException("Unable to load config.xml", e);
    }

    return cfg;
  }

  private XmlConfigSection getBotConfig(XmlConfig config) {
    try {
      return config.getSection("api.ai.bot");
    }
    catch(ConfigException e) {
      throw new RuntimeException("Section api.ai.bot is not found in config.", e);
    }
  }

  private Services initServices(XmlConfigSection botCfg) {
    try {
      return new Services(botCfg);
    }
    catch(ServicesException e) {
      throw new RuntimeException("Can't init services", e);
    }
  }

  private String getApiAiClientAccessToken(XmlConfigSection botCfg) {
    try {
      return botCfg.getString("api.ai.client.access.token");
    }
    catch(ConfigException e) {
      throw new RuntimeException("Parameter api.ai.client.access.token is not found in api.ai.bot section.", e);
    }
  }

  private String getPushUrl(XmlConfigSection botCfg) {
    try {
      return botCfg.getString("push.url");
    }
    catch(ConfigException e) {
      throw new RuntimeException("Parameter push.url is not found in api.ai.bot section.", e);
    }
  }

  @SuppressWarnings("unchecked")
  private List<String> getAdminsUserIds(XmlConfigSection botCfg) {
    try {
      return botCfg.getStringList("admins.user.ids", ",");
    }
    catch(ConfigException e) {
      throw new RuntimeException("Parameter admins.user.ids is not found in api.ai.bot section.", e);
    }
  }

  private String getBotAskCommandName(XmlConfigSection botCfg) {
    try {
      return botCfg.getString("bot.ask.command.name");
    }
    catch(ConfigException e) {
      throw new RuntimeException("Parameter bot.ask.command.name is not found in api.ai.bot section.", e);
    }
  }

  private void initLog4j(File configDir) {
    final File log4jProps = new File(configDir, "log4j.properties");
    System.out.println("Log4j conf file: " + log4jProps.getAbsolutePath() + ", exists: " + log4jProps.exists());
    PropertyConfigurator.configureAndWatch(log4jProps.getAbsolutePath(), TimeUnit.MINUTES.toMillis(1));
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    WebContext.shutdown();
  }

}