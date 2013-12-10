package org.alljson.internal;

import com.google.common.base.Objects;

import java.util.Iterator;

public abstract class AbstractType<T> implements HandledType<T> {

    @Override
    public boolean isAssignableFrom(HandledType<?> other) {   //otherObject instanceOf this type
        if(!this.toClass().isAssignableFrom(other.toClass())) {
            return false;
        }
        Iterator<HandledType<?>> thisParametersIterator = this.getParameters().iterator();
        Iterator<HandledType<?>> otherParametersIterator = other.getParameters().iterator();

        while (thisParametersIterator.hasNext() && otherParametersIterator.hasNext()) {
            HandledType thisParameter = thisParametersIterator.next();
            HandledType otherParameter = otherParametersIterator.next();
            if(!thisParameter.isAssignableFrom(otherParameter)) {
                return false;
            }
        }
        //everybody is assignable
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("class", toClass().getSimpleName()).add("parameters", getParameters()).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if(!(o instanceof HandledType)) {
            return false;
        }
        HandledType<?> other = (HandledType<?>) o;
        return Objects.equal(this.toClass(),other.toClass()) &&
                Objects.equal(this.getParameters(), other.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toClass(), getParameters());
    }
}
