package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guichaguri
 */
public class Selector implements Token {

    public static boolean isSelector(char c) {
        return c == '@';
    }

    public static Selector parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean parseProps = false;

        while(lexer.next()) {
            char c = lexer.peek();

            if(c == '[') {
                parseProps = true;
                break;
            } else if(!Character.isLetter(c)) {
                break;
            }

            builder.append(c);
        }

        if(builder.length() == 0) {
            throw new LexerException("A selector cannot be empty");
        }

        if(parseProps) {
            return new Selector(builder.toString(), parseProperties(lexer));
        } else {
            return new Selector(builder.toString(), null);
        }
    }

    private static Map<String, String> parseProperties(Lexer lexer) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        String key = null;

        while(lexer.next()) {
            char c = lexer.peek();

            if(c == '=') {
                key = builder.toString();
                builder = new StringBuilder();
                continue;
            } else if(c == ',') {
                map.put(key, builder.toString());
                builder = new StringBuilder();
                continue;
            } else if(c == ']') {
                map.put(key, builder.toString());
                break;
            }

            builder.append(c);
        }

        return map;
    }

    private final String ref;
    private final Map<String, String> properties;

    public Selector(String ref, Map<String, String> properties) {
        this.ref = ref;
        this.properties = properties;
    }

    public String getName() {
        return ref;
    }
}
