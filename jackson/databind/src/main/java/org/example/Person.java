package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    public Person(String name, Integer age) {
        this.name= name;
        this.age = age;
    }
    private String name;
    private Integer age;
    private Dog dog;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dog {
        private String name;
        private Integer age;
    }
}
