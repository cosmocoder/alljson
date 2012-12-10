package org.alljson;

import com.google.common.collect.*;
import org.alljson.adapters.*;
import org.alljson.serializers.*;
import org.alljson.serializers.SerializationContext;
import org.alljson.types.JsonValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.*;

public class JsonMapperIntegrationTest {
    @Test
    public void serialize() throws Exception {
        Map<String, Integer> map = ImmutableMap.of(
                "UM", 1, "DOIS", 2, "TRES", 3);
        User ze = new User(
                "José da Silva",
                45,
                Lists.newArrayList("Flamengo", "Fluminense"), true,
                ImmutableMultimap.<String, LocalDate>builder()
                        .putAll("BIRTH", new LocalDate(1945, 9, 7))
                        .putAll("WEDDING", new LocalDate(1965, 5, 4), new LocalDate(1980, 4, 2))
                        .build()
        );


        JsonMapper mapper = new JsonMapper(newSerializationContext());
        ObjectMapper jacksonMapper = new ObjectMapper();
        System.out.println(mapper.getJson(ze));
        Long time = new Date().getTime();
        for (int i=0; i<40; i++) {
            mapper.getJson(ze).toString();
        }
        System.out.println(new Date().getTime() - time);

        time = new Date().getTime();
        for (int i=0; i<40; i++) {
            jacksonMapper.writeValueAsString(ze);
        }
        System.out.println(new Date().getTime() - time);

        System.out.println(mapper.getJson(ze));
        System.out.println(jacksonMapper.writeValueAsString(ze));

        JsonValue value = mapper.getJson(ze);
        System.out.println(mapper.getJson(value));

    }

    private SerializationContext newSerializationContext() {
        Map<Class, TypeAdapter> adapters;
        Map<Class, Serializer> serializers;
        Map<Class, TypeAdapter> fallbackAdapters;

        adapters = new LinkedHashMap<Class, TypeAdapter>();
        adapters.put(Object[].class, new ArrayAdapter());
        adapters.put(Multimap.class, new MultimapAdapter());
        adapters.put(LocalDate.class, new ToStringAdapter());
        adapters.put(Enum.class, new ToStringAdapter());

        serializers = new LinkedHashMap<Class, Serializer>();
        serializers.put(String.class, new StringSerializer());
        serializers.put(Number.class, new NumberSerializer());
        serializers.put(Boolean.class, new BooleanSerializer());
        serializers.put(Iterable.class, new IterableSerializer());
        serializers.put(Map.class, new MapSerializer());
        serializers.put(JsonValue.class, new JsonValueSerializer());

        fallbackAdapters = new LinkedHashMap<Class, TypeAdapter>();
        fallbackAdapters.put(Object.class, new BeanAdapter());

        return new SerializationContext(adapters, fallbackAdapters, serializers);
    }

    class User {
        private final String name;
        private final int age;
        private final List<String> teams;
        private final boolean black;
        private final Multimap<String, LocalDate> lifeEvents;

        User(String name, int age, List<String> teams, boolean black, final Multimap<String, LocalDate> lifeEvents) {
            this.name = name;
            this.age = age;
            this.teams = teams;
            this.black = black;
            this.lifeEvents = lifeEvents;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String[] getTeams() {
            return teams.toArray(new String[]{""});
        }

        public boolean isBlack() {
            return black;
        }
       /*
        public Multimap<String, LocalDate> getLifeEvents() {
            return lifeEvents;
        }

        */
    }
}
