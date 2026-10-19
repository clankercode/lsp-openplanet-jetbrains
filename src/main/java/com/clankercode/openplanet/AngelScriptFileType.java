package com.clankercode.openplanet;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import javax.swing.Icon;

public final class AngelScriptFileType extends LanguageFileType {
  public static final AngelScriptFileType INSTANCE = new AngelScriptFileType();

  private AngelScriptFileType() {
    super(AngelScriptLanguage.INSTANCE);
  }

  public String getName() {
    return "Openplanet AngelScript";
  }

  public String getDescription() {
    return "Openplanet AngelScript";
  }

  public String getDefaultExtension() {
    return "as";
  }

  public Icon getIcon() {
    return AllIcons.FileTypes.Text;
  }
}
