package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public class Color implements Token {

    public static boolean isColor(char c) {
        return c == '&' || c == '\u00A7';
    }

    public static Color parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();

        while(lexer.next()) {
            char c = lexer.peek();

            if(!Character.isLetterOrDigit(c)) {
                break;
            }

            builder.append(c);
        }

        if(builder.length() == 0) {
            throw new LexerException("A color code cannot be empty");
        }

        return new Color(builder.toString());
    }

    public static Color parseShort(Lexer lexer) throws IOException {
        if(!lexer.next() || !Character.isLetterOrDigit(lexer.peek())) {
            throw new LexerException("Couldn't parse color code");
        }

        return new Color(String.valueOf(lexer.peek()));
    }

    private final String code;

    public Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Color{" +
                "code='" + code + '\'' +
                '}';
    }
}
