package com.clankercode.openplanet;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

/** Lossless lexical highlighting; semantic analysis is server-owned. */
public final class AngelScriptLexer extends LexerBase {
    public static final IElementType COMMENT=type("COMMENT"), STRING=type("STRING"), NUMBER=type("NUMBER"), KEYWORD=type("KEYWORD"), IDENTIFIER=type("IDENTIFIER"), SYMBOL=type("SYMBOL"), DIRECTIVE=type("DIRECTIVE");
    private static final Set<String> KEYWORDS=Set.of("void","bool","int","int8","int16","int64","uint","uint8","uint16","uint64","float","double","string","auto","const","class","interface","enum","namespace","import","from","return","if","else","for","while","do","switch","case","default","break","continue","true","false","null","is","in","out","inout","private","protected","shared","external","funcdef","typedef","cast","try","catch","this","super","mixin");
    private CharSequence buffer="";
    private int start,end,limit;
    private IElementType token;
    private static IElementType type(String name) { return new IElementType(name,AngelScriptLanguage.INSTANCE); }
    @Override public void start(@NotNull CharSequence text,int startOffset,int endOffset,int initialState) { buffer=text; start=startOffset; limit=endOffset; scan(); }
    @Override public int getState() { return 0; }
    @Override public IElementType getTokenType() { return token; }
    @Override public int getTokenStart() { return start; }
    @Override public int getTokenEnd() { return end; }
    @Override public @NotNull CharSequence getBufferSequence() { return buffer; }
    @Override public int getBufferEnd() { return limit; }
    @Override public void advance() { start=end; scan(); }
    private boolean at(int offset,char c) { return offset<limit && buffer.charAt(offset)==c; }
    private void scan() {
        end=start;
        if(start>=limit) { token=null; return; }
        char c=buffer.charAt(end++);
        if(Character.isWhitespace(c)) { while(end<limit && Character.isWhitespace(buffer.charAt(end))) end++; token=TokenType.WHITE_SPACE; }
        else if(c=='/' && at(end,'/')) { while(end<limit && buffer.charAt(end)!='\n') end++; token=COMMENT; }
        else if(c=='/' && at(end,'*')) { end++; while(end<limit && !(at(end,'*') && at(end+1,'/'))) end++; if(end<limit) end+=2; token=COMMENT; }
        else if(c=='"' || c=='\'') {
            if(at(end,c) && at(end+1,c)) { end+=2; while(end<limit && !(at(end,c) && at(end+1,c) && at(end+2,c))) end++; if(end<limit) end+=3; }
            else { while(end<limit) { char next=buffer.charAt(end++); if(next=='\\' && end<limit) end++; else if(next==c) break; } }
            token=STRING;
        }
        else if(c=='#') { while(end<limit && buffer.charAt(end)!='\n') end++; token=DIRECTIVE; }
        else if(Character.isDigit(c)) { while(end<limit && (Character.isLetterOrDigit(buffer.charAt(end)) || buffer.charAt(end)=='.' || buffer.charAt(end)=='_')) end++; token=NUMBER; }
        else if(Character.isJavaIdentifierStart(c)) { while(end<limit && Character.isJavaIdentifierPart(buffer.charAt(end))) end++; token=KEYWORDS.contains(buffer.subSequence(start,end).toString()) ? KEYWORD : IDENTIFIER; }
        else token=SYMBOL;
    }
}
