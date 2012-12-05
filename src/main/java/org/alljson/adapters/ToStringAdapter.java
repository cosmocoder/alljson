package org.alljson.adapters;

public class ToStringAdapter extends AbstractTypeAdapter<Object>{
    @Override
    public String adaptNotNullValue(Object input) {
        return input.toString();
    }
}
