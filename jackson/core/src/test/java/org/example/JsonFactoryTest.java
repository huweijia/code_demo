package org.example;

import com.fasterxml.jackson.core.*;
import org.junit.Test;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonFactory.Feature.*;
import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;
import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.json.JsonWriteFeature.ESCAPE_NON_ASCII;
import static com.fasterxml.jackson.core.json.JsonWriteFeature.QUOTE_FIELD_NAMES;

public class JsonFactoryTest {
    @Test
    public void test1() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();

        JsonGenerator jsonGenerator1 = jsonFactory.createGenerator(System.out);
        JsonGenerator jsonGenerator2 = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);

        System.out.println(jsonGenerator1);
        System.out.println(jsonGenerator2);
    }

    @Test
    public void test2() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();

        JsonParser jsonParser1 = jsonFactory.createParser("{}");
        // JsonParser jsonParser2 = jsonFactory.createParser(new FileReader("..."));
        JsonParser jsonParser3 = jsonFactory.createNonBlockingByteArrayParser();

        System.out.println(jsonParser1);
        // System.out.println(jsonParser2);
        System.out.println(jsonParser3);
    }

    @Test
    public void test3() throws IOException {
        String jsonStr = "{\"age\":18, \"age\": 28 }";

        JsonFactory factory = new JsonFactory();
        factory.disable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);

        try (JsonParser jsonParser = factory.createParser(jsonStr)) {
            // 使用factory定制将不生效
//             factory.disable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);

            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jsonParser.getCurrentName();
                if ("age".equals(fieldname)) {
                    jsonParser.nextToken();
                    System.out.println(jsonParser.getIntValue());
                }
            }
        }
    }
    @Test
    public void test4() throws IOException {
        JsonFactory jsonFactory = new JsonFactoryBuilder()
                // jsonFactory自己的特征
                .enable(INTERN_FIELD_NAMES)
                .enable(CANONICALIZE_FIELD_NAMES)
                .enable(USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING)
                // JsonParser的特征
                .enable(ALLOW_SINGLE_QUOTES, ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                // JsonGenerator的特征
                .enable(QUOTE_FIELD_NAMES, ESCAPE_NON_ASCII)

                .build();

        // 创建读/写实例
        // jsonFactory.createParser(...);
        // jsonFactory.createGenerator(...);
    }
}
