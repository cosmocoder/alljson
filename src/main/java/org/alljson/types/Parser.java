package org.alljson.types;

import org.alljson.internal.ParseResult;

/**
 * To change this template use File | Settings | File Templates.
 */
interface Parser<T> {
    T parse(String text);
}
