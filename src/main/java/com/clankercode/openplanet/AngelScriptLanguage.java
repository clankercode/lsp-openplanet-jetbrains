package com.clankercode.openplanet;

import com.intellij.lang.Language;

public final class AngelScriptLanguage extends Language {
  public static final AngelScriptLanguage INSTANCE = new AngelScriptLanguage();

  private AngelScriptLanguage() {
    super("OpenplanetAngelScript");
  }
}
