package org.gradle;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

      public String sayHello(){
        System.out.println("Saying Hello World.");
        return "Hello World.";
    }
}
