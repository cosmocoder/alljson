package org.alljson;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.alljson.types.JsonNumber;
import org.alljson.types.JsonObject;
import org.alljson.types.JsonValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonMapperTest {
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


        JsonNumber.create(2f);
        long time = new Date().getTime();
        JsonMapper mapper = JsonMapper.create();
        for (int i = 0; i < 1000; i++) {
            mapper.getJson(ze).toString();
        }
        long timeAllJson = new Date().getTime() - time;
        System.out.println("ALLJSON: " + mapper.getJson(ze) + " " + timeAllJson);

        time = new Date().getTime();
        ObjectMapper jacksonMapper = new ObjectMapper();
        for (int i = 0; i < 1000; i++) {
            jacksonMapper.writeValueAsString(ze);
        }

        long timeJackson = new Date().getTime() - time;
        System.out.println("JACKSON: " + jacksonMapper.writeValueAsString(ze) + " " + timeJackson);

        JsonValue value = mapper.getJson(ze);

        assertThat(jacksonMapper.writeValueAsString(ze), is(mapper.getJson(ze).toString()));

    }



    class User {
        private final String name;
        private int age;
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

        void setAge(final int age) {
            this.age = age;
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
        } */

    }
}
