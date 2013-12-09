package org.alljson.types;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

public class JsonArray extends JsonValue implements List<JsonValue> {

    static final String INITIAL_CHAR = "[";
    static final String FINAL_CHAR = "]";
    static final String VALUE_SEPARATOR = ",";
    static final JsonArrayParser PARSER = new JsonArrayParser();
    private List<JsonValue> values;

    public JsonArray(Iterable<JsonValue> values) {
        this.values = newArrayList(values);
    }

    public JsonArray() {
        this.values = newArrayList();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object key) {
        return values.contains(key);
    }

    @Override
    public Iterator<JsonValue> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(JsonValue jsonValue) {
        return values.add(jsonValue);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends JsonValue> c) {
        return values.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JsonValue> c) {
        return values.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return values.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return values.retainAll(c);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public boolean equals(Object o) {
        return values.equals(o);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public JsonValue get(int index) {
        return values.get(index);
    }

    @Override
    public JsonValue set(int index, JsonValue element) {
        return values.set(index, element);
    }

    @Override
    public void add(int index, JsonValue element) {
        values.add(index, element);
    }

    @Override
    public JsonValue remove(int index) {
        return values.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @Override
    public ListIterator<JsonValue> listIterator() {
        return values.listIterator();
    }

    @Override
    public ListIterator<JsonValue> listIterator(int index) {
        return values.listIterator(index);
    }

    @Override
    public List<JsonValue> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        appendStringTo(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    void appendStringTo(StringBuilder stringBuilder) {
        stringBuilder.append(INITIAL_CHAR);
        Iterator<JsonValue> valuesIterator = values.iterator();
        if (valuesIterator.hasNext()) {
            stringBuilder.append(valuesIterator.next());
        }
        while (valuesIterator.hasNext()) {
            stringBuilder.append(VALUE_SEPARATOR);
            valuesIterator.next().appendStringTo(stringBuilder);
        }
        stringBuilder.append(FINAL_CHAR);
    }

    public static JsonArray parse(String text) {
        return JsonArray.PARSER.parse(text);
    }

    static final class JsonArrayParser extends AbstractParser<JsonArray>{
        @Override
        public ParseResult<JsonArray> doPartialParse(final String text) {
            if (text.length() > 1 && text.startsWith(INITIAL_CHAR)) {
                JsonArray json = new JsonArray(new ArrayList<JsonValue>());
                char[] objectString = text.toCharArray();
                String remainingText = text.substring(1);
                for(int i=1; i< text.length()-1; i++) {
                    ParseResult<JsonValue> valueParse = JsonValue.PARSER.partialParse(remainingText);
                    remainingText = remainingText.substring(valueParse.getNextPosition());
                    ParseResult<String> separatorParse = SeparatorParser.INSTANCE.partialParse(remainingText);
                    remainingText = remainingText.substring(separatorParse.getNextPosition());
                    json.add(valueParse.getParsedValue());
                    String separator = separatorParse.getParsedValue();
                    if(separator.equals(FINAL_CHAR)) {
                        break;
                    } else if(separator.equals(VALUE_SEPARATOR)) {
                        continue;
                    } else {
                        throw new IllegalArgumentException(String.format("Can't parse JsonArray from text, = \"%s\", unexpected separator = %s", text, separator));
                    }
                }
                return new ParseResult<JsonArray>(json, text.length() - remainingText.length());
            }

            throw new IllegalArgumentException(String.format("Can't parse JsonArray from text = \"%s\"", text));
        }
    }
}
