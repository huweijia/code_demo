package org.example;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

public class TreeNodeTest {
    @Test
    public void test1() {
        ObjectMapper mapper = new ObjectMapper();

        Person person = new Person();
        person.setName("YourBatman");
        person.setAge(18);

        person.setDog(new Person.Dog("旺财", 3));

        JsonNode node = mapper.valueToTree(person);

        System.out.println(person);
        // 遍历打印所有属性
        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            JsonNode nextNode = it.next();
            if (nextNode.isContainerNode()) {
                if (nextNode.isObject()) {
                    System.out.println("狗的属性：：：");

                    System.out.println(nextNode.get("name"));
                    System.out.println(nextNode.get("age"));
                }
            } else {
                System.out.println(nextNode.asText());
            }
        }

        // 直接获取
        System.out.println("---------------------------------------");
        System.out.println(node.get("dog").get("name"));
        System.out.println(node.get("dog").get("age"));
    }

    @Test
    public void test2() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonFactory factory = new JsonFactory();
        try (JsonGenerator jsonGenerator = factory.createGenerator(System.err, JsonEncoding.UTF8)) {

            // 1、得到一个jsonNode（为了方便我直接用上面API生成了哈）
            Person person = new Person();
            person.setName("YourBatman");
            person.setAge(18);
            JsonNode jsonNode = mapper.valueToTree(person);

            // 使用JsonGenerator写到输出流
            mapper.writeTree(jsonGenerator, jsonNode);
        }
    }

    @Test
    public void test3() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18,\"dog\":null}";
        // 直接映射为一个实体对象
        // mapper.readValue(jsonStr, Person.class);
        // 读取为一个树模型
        JsonNode node = mapper.readTree(jsonStr);

        // ... 略
    }

    @Test
    public void test4() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18,\"dog\":{\"name\":\"旺财\",\"color\":\"WHITE\"},\"hobbies\":[\"篮球\",\"football\"]}";
        JsonNode node = mapper.readTree(jsonStr);

        System.out.println(node.get("dog").get("color").asText());
    }

    @Test
    public void test5() throws JsonProcessingException {
        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18}";

        JsonNode node = new ObjectMapper().readTree(jsonStr);

        System.out.println("-------------向结构里动态添加节点------------");
        // 动态添加一个myDiy节点，并且该节点还是ObjectNode节点
        ((ObjectNode) node).with("myDiy").put("contry", "China");

        System.out.println(node);
    }
}
