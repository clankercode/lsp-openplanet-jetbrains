package com.clankercode.openplanet;

import static org.junit.Assert.*;

import com.intellij.psi.tree.IElementType;
import org.junit.Test;

public class AngelScriptLexerTest {
  @Test
  public void highlightsLiteralsCommentsAndPreprocessor() {
    token("const", AngelScriptLexer.KEYWORD);
    token("uint64", AngelScriptLexer.KEYWORD);
    token("thing", AngelScriptLexer.IDENTIFIER);
    token("@", AngelScriptLexer.SYMBOL);
    token("123.5f", AngelScriptLexer.NUMBER);
    token("0xff", AngelScriptLexer.NUMBER);
    token("// comment", AngelScriptLexer.COMMENT);
    token("/* line\nline */", AngelScriptLexer.COMMENT);
    token("\"escaped \\\" quote\"", AngelScriptLexer.STRING);
    token("'x'", AngelScriptLexer.STRING);
    token("\"\"\"hello\nworld\"\"\"", AngelScriptLexer.STRING);
    token("#if TMNEXT", AngelScriptLexer.DIRECTIVE);
  }

  @Test
  public void losslessAndRestartableAtEveryTokenBoundary() {
    for (String input :
        new String[] {
          "const T@ &in value;",
          "T &out x;",
          "T@ &inout y;",
          "/* unfinished",
          "\"unfinished",
          "\"\"\"unterminated",
          "void Main() {\n print(\"Hi\");\n}"
        }) {
      var lexer = new AngelScriptLexer();
      lexer.start(input);
      int end = 0;
      while (lexer.getTokenType() != null) {
        assertEquals(end, lexer.getTokenStart());
        assertTrue(lexer.getTokenEnd() > end);
        var restarted = new AngelScriptLexer();
        restarted.start(input, lexer.getTokenStart(), input.length(), lexer.getState());
        assertEquals(lexer.getTokenType(), restarted.getTokenType());
        assertEquals(lexer.getTokenEnd(), restarted.getTokenEnd());
        end = lexer.getTokenEnd();
        lexer.advance();
      }
      assertEquals(input.length(), end);
    }
  }

  private static void token(String source, IElementType type) {
    var lexer = new AngelScriptLexer();
    lexer.start(source);
    assertEquals(type, lexer.getTokenType());
    assertEquals(source.length(), lexer.getTokenEnd());
    lexer.advance();
    assertNull(lexer.getTokenType());
  }
}
