package org.alljson.serialization;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.alljson.serialization.adapters.ArrayToListAdapter;
import org.alljson.serialization.adapters.BeanToMapAdapter;
import org.alljson.serialization.adapters.MultimapToMapAdapter;
import org.alljson.serialization.adapters.ToStringAdapter;
import org.alljson.serialization.templates.SimpleSerializer;
import org.alljson.templates.ComposedConverter;
import org.alljson.templates.Converter;
import org.alljson.types.JsonValue;
import org.joda.time.LocalDate;

import javax.annotation.Nullable;
import java.util.*;

public class JsonSerializer extends SimpleSerializer<Object> {
    private Map<Class<? extends Object>, Adaptation> adaptationsByInputClass;
    private Map<Class, Converter> adapterCache;

    protected JsonSerializer(Iterable<Adaptation> adaptations) {
        super(Object.class);
        adaptationsByInputClass = Maps.uniqueIndex(adaptations, Adaptation.INPUT_CLASS);
    }

    @Override
    protected JsonValue convertNotNullValue(final Object input) {
        if (input instanceof JsonValue) {
            return (JsonValue) input;
        }
        Class<? extends Object> clazz = input.getClass();
        Converter adapter = null; //change to nop adapter
        do {
            Adaptation<? extends Object, ? extends Object> adaptation = bestAdaptation(clazz);
            adapter = adapter == null ?
                    adaptation.getAdapter() :
                    new ComposedConverter(adapter, adaptation.getAdapter(), adapter.getInputType(), adapter.getOutputClass());
            clazz = adaptation.getOutputClass();
        } while (!JsonValue.class.isAssignableFrom(clazz));
        return (JsonValue) adapter.convert(input, this);
    }

    private Adaptation<? extends Object, ? extends Object> bestAdaptation(final Class<? extends Object> clazz) {
        Adaptation<? extends Object, ? extends Object> adaptation = adaptationsByInputClass.get(clazz);
        if (adaptation != null) {
            return adaptation;
        } else {
            Class closestClass = Collections.min(adaptationsByInputClass.keySet(), getClosestClassComparator(clazz));
            if (!closestClass.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException(String.format("%s have no converters defined.", clazz.getCanonicalName()));
            }
            return adaptationsByInputClass.get(closestClass);
        }
    }

    public static class Adaptation<I, O> {
        private final Class<I> inputClass;
        private final Class<O> outputClass;
        private final Converter<? extends I, ? super O> adapter;

        public Adaptation(final Class<I> inputClass, final Class<O> outputClass, final Converter<? extends I, ? super O> adapter) {
            this.inputClass = inputClass;
            this.outputClass = outputClass;
            this.adapter = (Converter) adapter;
        }

        public Class<I> getInputClass() {
            return inputClass;
        }

        public Class<O> getOutputClass() {
            return outputClass;
        }

        public Converter<? extends I, ? super O> getAdapter() {
            return adapter;
        }

        public static final Function<Adaptation, Class<?>> INPUT_CLASS = new Function<Adaptation, Class<?>>() {
            @Override
            public Class<?> apply(@Nullable final Adaptation adaptation) {
                return adaptation.getInputClass();
            }
        };
    }

    private Comparator<Class> getClosestClassComparator(final Class myClass) {
        return new Comparator<Class>() {
            private static final int FIRST_IS_CLOSER = -1;
            private static final int SECOND_IS_CLOSER = 1;
            private static final int CLASSES_ARE_EQUAL = 0;

            @Override
            public int compare(final Class first, final Class second) {

                if (first.equals(second)) {
                    /* equal classes */
                    return CLASSES_ARE_EQUAL;
                }

                if (first.equals(myClass)) {
                    /* first class is equal to current */
                    return FIRST_IS_CLOSER;
                }

                if (second.equals(myClass)) {
                    /* second class is equal to current */
                    return SECOND_IS_CLOSER;
                }

                if (first.isAssignableFrom(myClass)) {
                    if (!second.isAssignableFrom(myClass)) {
                        /* fist class is a superclass/superinterface, but the second is not */
                        return FIRST_IS_CLOSER;
                    }
                } else if (second.isAssignableFrom(myClass)) {
                    /* second class is a superclass/superinterface, but the first is not */
                    return SECOND_IS_CLOSER;
                } else {
                    /* neither two classes are superclasses/superinterfaces,sort by cannonical name */
                    return first.getCanonicalName().compareTo(second.getCanonicalName());
                }


                //Instance of both classes, which one is closest?

                if (second.isAssignableFrom(first)) {
                    // Same hierachy: MyClass --|> FIRST --|> SECOND
                    return FIRST_IS_CLOSER;
                }

                if (first.isAssignableFrom(second)) {
                    // Same hierachy:  MyClass --|> SECOND --|> FIRST
                    return SECOND_IS_CLOSER;
                }

                //Classes are not in the same hierarchy

                //If one of the classes is object the other wins
                if (first.equals(Object.class)) {
                    // MyClass -|> SECOND, FIRST=Object
                    return SECOND_IS_CLOSER;
                }

                if (second.equals(Object.class)) {
                    // MyClass -|> FIRST, SECOND=Object
                    return FIRST_IS_CLOSER;
                }

                //The superclass beats the interface
                if (!first.isInterface()) {
                    return FIRST_IS_CLOSER;
                }

                if (!second.isInterface()) {
                    return SECOND_IS_CLOSER;
                }

                //Both are interfaces (in different hierarchy),first declared wins

                Class currentClass = myClass;
                do {
                    for (Class currentInterface : currentClass.getInterfaces()) {
                        if (currentInterface.equals(first)) {
                            return FIRST_IS_CLOSER;
                        }

                        if (currentInterface.equals(second)) {
                            return SECOND_IS_CLOSER;
                        }
                    }
                    currentClass = myClass.getSuperclass();
                } while (!currentClass.equals(Object.class));

                throw new IllegalStateException("Could not compare.");
            }
        };
    }

    private static Ready defaultBuilder() {
        return new Builder().using(new ArrayToListAdapter()).whenConvertingFrom(Object[].class).toAnyPossibleClass()
                .using(new MultimapToMapAdapter()).whenConvertingFrom(Multimap.class).toAnyPossibleClass()
                .using(new ToStringAdapter()).whenConvertingFrom(LocalDate.class).toAnyPossibleClass()
                .using(new ToStringAdapter()).whenConvertingFrom(Enum.class).toAnyPossibleClass()
                .using(new StringSerializer()).whenConvertingFrom(String.class).toAnyPossibleClass()
                .using(new NumberSerializer()).whenConvertingFrom(Number.class).toAnyPossibleClass()
                .using(new IterableSerializer()).whenConvertingFrom(Iterable.class).toAnyPossibleClass()
                .using(new BooleanSerializer()).whenConvertingFrom(Boolean.class).toAnyPossibleClass()
                .using(new MapSerializer()).whenConvertingFrom(Map.class).toAnyPossibleClass()
                .using(new JsonValueSerializer()).whenConvertingFrom(JsonValue.class).toAnyPossibleClass()
                .using(new BeanToMapAdapter()).whenConvertingFrom(Object.class).toAnyPossibleClass();
    }

    public static AfterUsing using(Converter converter) {
        return defaultBuilder().using(converter);
    }

    public static JsonSerializer create() {
        return defaultBuilder().create();
    }

    private static class Builder implements Ready, AfterUsing, AfterFrom {
        Set<Adaptation> adaptations = new LinkedHashSet<Adaptation>();
        private ThreadLocal<Class> from = new ThreadLocal<Class>();
        private ThreadLocal<Converter> adapter = new ThreadLocal<Converter>();

        @Override
        public JsonSerializer create() {
            return new JsonSerializer(adaptations);
        }

        @Override
        public Ready to(final Class clazz) {
            Adaptation adaptation = new Adaptation(from.get(), clazz, adapter.get());
            adapter.set(null);
            from.set(null);
            adaptations.add(adaptation);
            return this;
        }

        @Override
        public AfterFrom whenConvertingFrom(final Class clazz) {
            from.set(clazz);
            return this;
        }

        @Override
        public Ready whenConvertingTo(final Class clazz) {
            final Converter adapter = this.adapter.get();
            Adaptation adaptation = new Adaptation(adapter.getInputType(), clazz, adapter);
            this.adapter.set(null);
            from.set(null);
            adaptations.add(adaptation);
            return this;
        }

        @Override
        public Ready whenPossible() {
            final Converter adapter = this.adapter.get();
            Adaptation adaptation = new Adaptation(adapter.getInputType(), adapter.getOutputClass(), adapter);
            this.adapter.set(null);
            from.set(null);
            adaptations.add(adaptation);
            return this;
        }

        @Override
        public AfterUsing using(final Converter converter) {
            adapter.set(converter);
            return this;
        }

        @Override
        public Ready toAnyPossibleClass() {
            final Converter adapter = this.adapter.get();
            Adaptation adaptation = new Adaptation(from.get(), adapter.getOutputClass(), adapter);
            this.adapter.set(null);
            from.set(null);
            adaptations.add(adaptation);
            return this;
        }
    }

    public static interface Ready {
        public AfterUsing using(Converter converter);

        public JsonSerializer create();
    }

    public static interface AfterUsing {
        public AfterFrom whenConvertingFrom(Class clazz);

        public Ready whenConvertingTo(Class clazz);

        public Ready whenPossible();
    }

    public static interface AfterFrom {
        public Ready to(Class clazz);

        public Ready toAnyPossibleClass();
    }

}

