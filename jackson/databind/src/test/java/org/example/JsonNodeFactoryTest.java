package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

public class JsonNodeFactoryTest {
    @Test
    public void test1() {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        System.out.println("------ValueNode值节点示例------");
        // 数字节点
        JsonNode node = factory.numberNode(1);
        System.out.println(node.isNumber() + ":" + node.intValue());

        // null节点
        node = factory.nullNode();
        System.out.println(node.isNull() + ":" + node.asText());

        // missing节点
        node = factory.missingNode();
        System.out.println(node.isMissingNode() + "_" + node.asText());

        // POJONode节点
        node = factory.pojoNode(new Person("YourBatman", 18));
        System.out.println(node.isPojo() + ":" + node.asText());

        System.out.println("---" + node.isValueNode() + "---");
    }

    @Test
    public void test2() {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        System.out.println("------构建一个JSON结构数据------");
        ObjectNode rootNode = factory.objectNode();

        // 添加普通值节点
        rootNode.put("zhName", "A哥"); // 效果完全同：rootNode.set("zhName", factory.textNode("A哥"))
        rootNode.put("enName", "YourBatman");
        rootNode.put("age", 18);

        // 添加数组容器节点
        ArrayNode arrayNode = factory.arrayNode();
        arrayNode.add("java")
                .add("javascript")
                .add("python");
        rootNode.set("languages", arrayNode);

        // 添加对象节点
        ObjectNode dogNode = factory.objectNode();
        dogNode.put("name", "大黄")
                .put("age", 3);
        rootNode.set("dog", dogNode);

        System.out.println(rootNode);
        System.out.println(rootNode.get("dog").get("name"));
    }


}
