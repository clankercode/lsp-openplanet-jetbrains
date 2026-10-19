package com.clankercode.openplanet;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public final class AngelScriptHighlighterFactory extends SyntaxHighlighterFactory {
  public @NotNull SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile file) {
    return new SyntaxHighlighterBase() {
      public @NotNull Lexer getHighlightingLexer() {
        return new AngelScriptLexer();
      }

      public TextAttributesKey @NotNull [] getTokenHighlights(IElementType type) {
        TextAttributesKey key =
            type == AngelScriptLexer.COMMENT
                ? DefaultLanguageHighlighterColors.BLOCK_COMMENT
                : type == AngelScriptLexer.STRING
                    ? DefaultLanguageHighlighterColors.STRING
                    : type == AngelScriptLexer.NUMBER
                        ? DefaultLanguageHighlighterColors.NUMBER
                        : type == AngelScriptLexer.KEYWORD
                            ? DefaultLanguageHighlighterColors.KEYWORD
                            : type == AngelScriptLexer.DIRECTIVE
                                ? DefaultLanguageHighlighterColors.METADATA
                                : null;
        return key == null ? TextAttributesKey.EMPTY_ARRAY : new TextAttributesKey[] {key};
      }
    };
  }
}
