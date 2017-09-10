package com.guichaguri.commandlang;

import com.guichaguri.commandlang.lexer.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guichaguri
 */
public class Lexer {

    private final InputStream in;
    private char character;
    private boolean finished = false;
    private int line = 1, pos = 1;

    private List<Token> tokens = new ArrayList<>();

    public Lexer(InputStream in) {
        this.in = in;
    }

    public boolean next() throws IOException {
        int r = in.read();
        if(r <= -1) {
            finished = true;
            return false;
        }

        if(character == '\n') {
            line++;
            pos = 1;

            // Adds the line separator token, something being parsed or not
            tokens.add(Punctuation.LINE_SEPARATOR);
        } else {
            pos++;
        }

        character = (char)r;
        return true;
    }

    public char peek() {
        return character;
    }

    public void run() throws IOException {
        while(next()) {
            while(!finished) {
                Object token = parse();

                if(token == null) {
                    break;
                } else if(token instanceof Token) {
                    tokens.add((Token)token);
                } else if(token instanceof List) {
                    tokens.addAll((List<Token>)token);
                }
            }
        }
    }

    protected Object parse() throws IOException {
        char c = peek();

        if(Character.isWhitespace(c)) {
            return null;
        } else if(Variable.isVariable(c)) {
            return Variable.parse(this);
        } else if(Selector.isSelector(c)) {
            return Selector.parse(this);
        } else if(Color.isColor(c)) {
            return Color.parse(this);
        } else if(StringValue.isString(c)) {
            return StringValue.parse(this, c);
        } else if(Operator.isOperator(c)) {
            return Operator.parse(this);
        } else if(Punctuation.isPunctuation(c)) {
            return Punctuation.parse(this, c);
        } else if(Character.isDigit(c)) {
            return NumberValue.parse(this);
        } else if(Character.isLetter(c)) {
            return Keyword.parse(this);
        } else {
            throw new LexerException("Unknown token: " + c);
        }
    }

    public int getLineNumber() {
        return line;
    }

    public int getLinePosition() {
        return pos;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
