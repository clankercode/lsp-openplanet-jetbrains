package com.clankercode.openplanet;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(name = "OpenplanetSettings", storages = @Storage("openplanet.xml"))
public final class OpenplanetSettings implements PersistentStateComponent<OpenplanetSettings> {
  public String serverPath = "";
  public String initializationOptions = "";

  public static OpenplanetSettings getInstance() {
    return ApplicationManager.getApplication().getService(OpenplanetSettings.class);
  }

  public OpenplanetSettings getState() {
    return this;
  }

  public void loadState(OpenplanetSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }
}
