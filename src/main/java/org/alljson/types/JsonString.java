package org.alljson.types;

public class JsonString implements JsonPrimitive {
    private String value;

    private String QUOTATION_MARK = "\"";
    private String REVERSE_SOLIDUS = "\\";
    private String SOLIDUS = "/";
    private String BACKSPACE = "\b";
    private String FORMFEED = "\f";
    private String NEWLINE = "\n";
    private String CARRIAGE_RETURN = "\r";
    private String HORIZONTAL_TAB = "\t";

    private String QUOTATION_MARK_ENCODED = "\\\"";
    private String REVERSE_SOLIDUS_ENCODED = "\\\\";
    private String SOLIDUS_ENCODED = "//";
    private String BACKSPACE_ENCODED = "\\b";
    private String FORMFEED_ENCODED = "\\f";
    private String NEWLINE_ENCODED = "\\n";
    private String CARRIAGE_RETURN_ENCODED = "\\r";
    private String HORIZONTAL_TAB_ENCODED = "\\t";

    public JsonString(final String value) {
        this.value = value;
    }

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
    public void appendStringTo(final StringBuilder stringBuilder) {
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
}
