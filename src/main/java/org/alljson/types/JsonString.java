package org.alljson.types;

import com.google.common.base.CharMatcher;
import org.alljson.internal.ParseResult;

public class JsonString extends JsonPrimitive {
    private String value;

    private static String QUOTATION_MARK = "\"";
    private static String REVERSE_SOLIDUS = "\\";
    private static String SOLIDUS = "/";
    private static String BACKSPACE = "\b";
    private static String FORMFEED = "\f";
    private static String NEWLINE = "\n";
    private static String CARRIAGE_RETURN = "\r";
    private static String HORIZONTAL_TAB = "\t";

    private static String QUOTATION_MARK_ENCODED = "\\\"";
    private static String REVERSE_SOLIDUS_ENCODED = "\\\\";
    private static String SOLIDUS_ENCODED = "//";
    private static String BACKSPACE_ENCODED = "\\b";
    private static String FORMFEED_ENCODED = "\\f";
    private static String NEWLINE_ENCODED = "\\n";
    private static String CARRIAGE_RETURN_ENCODED = "\\r";
    private static String HORIZONTAL_TAB_ENCODED = "\\t";

    public JsonString(final String value) {
        this.value = value;
    }

    static final JsonStringParser PARSER = new JsonStringParser();

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        appendStringTo(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    void appendStringTo(final StringBuilder stringBuilder) {
        stringBuilder.append(QUOTATION_MARK);
        String encoded = value.replace(QUOTATION_MARK, QUOTATION_MARK_ENCODED)
                .replace(REVERSE_SOLIDUS, REVERSE_SOLIDUS_ENCODED)
                .replace(SOLIDUS, SOLIDUS_ENCODED)
                .replace(BACKSPACE, BACKSPACE_ENCODED)
                .replace(FORMFEED, FORMFEED_ENCODED)
                .replace(NEWLINE, NEWLINE_ENCODED)
                .replace(CARRIAGE_RETURN, CARRIAGE_RETURN_ENCODED)
                .replace(HORIZONTAL_TAB, HORIZONTAL_TAB_ENCODED);
        stringBuilder.append(encoded);
        stringBuilder.append(QUOTATION_MARK);
    }

    public static JsonString parse(String text) {
        return PARSER.parse(text);
    }

    static final class JsonStringParser extends AbstractParser<JsonString>{
        @Override
        public ParseResult<JsonString> doPartialParse(final String text) {
            char[] charArray = text.toCharArray();
            boolean escape = false;
            boolean firstFound = false;
            int firstPosition = -1;
            for(int i=0; i<text.length(); i++) {
                if(!escape && charArray[i] == '"') {
                    if(!firstFound) {
                        firstFound = true;
                        firstPosition = i;
                    } else {
                        final int nextPosition = i + 1;
                        final String substring = text.substring(0, nextPosition);
                        return new ParseResult<JsonString>(exactParse(substring), nextPosition);
                    }
                } else if (charArray[i] == '\\') {
                    escape = true;
                }
            }
            throw new IllegalArgumentException(String.format("Can't parse JsonString from string = \"%s\"", text));
        }

        public JsonString exactParse(final String text) {
            if (text.length() > 1 && text.startsWith(QUOTATION_MARK) && text.endsWith(QUOTATION_MARK)) {
                return new JsonString(text.substring(1,text.length()-1).replace(QUOTATION_MARK_ENCODED, QUOTATION_MARK)
                                .replace(REVERSE_SOLIDUS_ENCODED, REVERSE_SOLIDUS)
                                .replace(SOLIDUS_ENCODED, SOLIDUS)
                                .replace(BACKSPACE_ENCODED, BACKSPACE)
                                .replace(FORMFEED_ENCODED, FORMFEED)
                                .replace(NEWLINE_ENCODED, NEWLINE)
                                .replace(CARRIAGE_RETURN_ENCODED, CARRIAGE_RETURN)
                                .replace(HORIZONTAL_TAB_ENCODED, HORIZONTAL_TAB));
            }

            throw new IllegalArgumentException(String.format("Can't parse JsonString from text = \"%s\"", text));
        }

    }
}
