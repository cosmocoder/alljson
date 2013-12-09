package org.alljson.types;

import org.alljson.internal.ParseResult;

interface Parser<T> {
    T parse(String text);
}
