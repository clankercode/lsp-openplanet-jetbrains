package com.clankercode.openplanet;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.*;
import org.jetbrains.annotations.NotNull;

/** Lossless PSI bridge; openplanet-lsp is the authoritative parser. */
public final class AngelScriptParserDefinition implements ParserDefinition {
  private static final IFileElementType FILE = new IFileElementType(AngelScriptLanguage.INSTANCE);

  public @NotNull Lexer createLexer(Project project) {
    return new AngelScriptLexer();
  }

  public @NotNull PsiParser createParser(Project project) {
    return (root, builder) -> {
      var mark = builder.mark();
      while (!builder.eof()) builder.advanceLexer();
      mark.done(root);
      return builder.getTreeBuilt();
    };
  }

  public @NotNull IFileElementType getFileNodeType() {
    return FILE;
  }

  public @NotNull TokenSet getCommentTokens() {
    return TokenSet.create(AngelScriptLexer.COMMENT);
  }

  public @NotNull TokenSet getStringLiteralElements() {
    return TokenSet.create(AngelScriptLexer.STRING);
  }

  public @NotNull PsiElement createElement(ASTNode node) {
    return new ASTWrapperPsiElement(node);
  }

  public @NotNull PsiFile createFile(@NotNull FileViewProvider view) {
    return new PsiFileBase(view, AngelScriptLanguage.INSTANCE) {
      public @NotNull com.intellij.openapi.fileTypes.FileType getFileType() {
        return AngelScriptFileType.INSTANCE;
      }
    };
  }
}
