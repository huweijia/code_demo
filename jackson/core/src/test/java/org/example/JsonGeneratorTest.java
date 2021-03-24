package org.example;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;

public class JsonGeneratorTest {
    @Test
    public void test2() {
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator jsonGenerator = factory.createGenerator(System.out, JsonEncoding.UTF8)) {
            jsonGenerator.writeStartObject();

            jsonGenerator.writeFieldName("zhName");

            jsonGenerator.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws IOException {
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator jg = factory.createGenerator(System.err, JsonEncoding.UTF8)) {
            jg.writeStartObject();
            jg.writeFieldName("names");

            // 写数组
            jg.writeStartArray();
            jg.writeString("A哥");
            jg.writeString("YourBatman");
        }
    }

    @Test
    public void test4() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonGenerator jg = factory.createGenerator(System.err, JsonEncoding.UTF8);

        jg.writeStartObject();
        jg.writeStringField("name","A哥");
        jg.writeEndObject();

         jg.flush();
        // jg.close();
    }

    @Test
    public void test5() throws IOException {
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator jg = factory.createGenerator(System.err, JsonEncoding.UTF8)) {
             jg.disable(QUOTE_FIELD_NAMES);

            jg.writeStartObject();
            jg.writeStringField("name","A哥");
            jg.writeEndObject();
        }
    }
}
