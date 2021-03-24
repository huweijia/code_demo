package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Test;

import java.io.IOException;

public class JsonParserTest {
    @Test
    public void test1() throws IOException {
        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18}";
        Person person = new Person();

        JsonFactory factory = new JsonFactory();
        try (JsonParser jsonParser = factory.createParser(jsonStr)) {

            // 只要还没结束"}"，就一直读
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jsonParser.getCurrentName();
                if ("name".equals(fieldname)) {
                    jsonParser.nextToken();
                    person.setName(jsonParser.getText());
                } else if ("age".equals(fieldname)) {
                    jsonParser.nextToken();
                    person.setAge(jsonParser.getIntValue());
                }
            }

            System.out.println(person);
        }
    }

    @Test
    public void test3() throws IOException {
        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18, \"pickName\":null}";

        JsonFactory factory = new JsonFactory();
        try (JsonParser jsonParser = factory.createParser(jsonStr)) {
            jsonParser.setCodec(new PersonObjectCodec());

            System.out.println(jsonParser.readValueAs(Person.class));
        }
    }

    @Test
    public void test2() throws IOException {
        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18, \"pickName\":null}";
        System.out.println(jsonStr);
        JsonFactory factory = new JsonFactory();
        try (JsonParser jsonParser = factory.createParser(jsonStr)) {

            while (true) {
                JsonToken token = jsonParser.nextToken();
                System.out.println(token + " -> 值为:" + jsonParser.getValueAsString());

                if (token == JsonToken.END_OBJECT) {
                    break;
                }
            }
        }
    }

    @Test
    public void test4() throws IOException {
        String jsonStr = "{\n" +
                "\t\"name\" : \"YourBarman\", // 名字\n" +
                "\t\"age\" : 18 // 年龄\n" +
                "}";

        JsonFactory factory = new JsonFactory();
        try (JsonParser jsonParser = factory.createParser(jsonStr)) {
            // 开启注释支持
//             jsonParser.enable(JsonParser.Feature.ALLOW_COMMENTS);

            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jsonParser.getCurrentName();
                if ("name".equals(fieldname)) {
                    jsonParser.nextToken();
                    System.out.println(jsonParser.getText());
                } else if ("age".equals(fieldname)) {
                    jsonParser.nextToken();
                    System.out.println(jsonParser.getIntValue());
                }
            }
        }
    }
}
