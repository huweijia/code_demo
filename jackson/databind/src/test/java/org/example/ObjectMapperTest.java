package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectMapperTest {
    @Test
    public void test1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // 绑定简单类型  和 Map类型
        Integer age = objectMapper.readValue("1", int.class);
        Map map = objectMapper.readValue("{\"name\":  \"YourBatman\"}", Map.class);
        System.out.println(age);
        System.out.println(map);
    }

    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Person person = objectMapper.readValue("{\"name\":  \"YourBatman\", \"age\": 18}", Person.class);
        System.out.println(person);
    }

    @Test
    public void test3() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("----------写简单类型----------");
        System.out.println(objectMapper.writeValueAsString(18));
        System.out.println(objectMapper.writeValueAsString("YourBatman"));

        System.out.println("----------写集合类型----------");
        System.out.println(objectMapper.writeValueAsString(Arrays.asList(1, 2, 3)));
        System.out.println(objectMapper.writeValueAsString(new HashMap<String, String>() {{
            put("zhName", "A哥");
            put("enName", "YourBatman");
        }}));

        System.out.println("----------写POJO----------");
        System.out.println(objectMapper.writeValueAsString(new Person("A哥", 18)));
    }

    @Test
    public void test4() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("----------读简单类型----------");
        System.out.println(objectMapper.readValue("18", Integer.class));
        // 抛错：JsonParseException  单独的一个串，解析会抛错
        // System.out.println(objectMapper.readValue("YourBatman", String.class));

        System.out.println("----------读集合类型----------");
        System.out.println(objectMapper.readValue("[1,2,3]", List.class));
        System.out.println(objectMapper.readValue("{\"zhName\":\"A哥\",\"enName\":\"YourBatman\"}", Map.class));

        System.out.println("----------读POJO----------");
        System.out.println(objectMapper.readValue("{\"name\":\"A哥\",\"age\":18}", Person.class));
    }
}
