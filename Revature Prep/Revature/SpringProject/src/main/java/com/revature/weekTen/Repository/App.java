package com.revature.weekTen.Repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("=== Spring Data JPA App ===");

        ApplicationContext ctx = SpringApplication.run(App.class, args);
        PersonService personService = ctx.getBean(PersonService.class);

        Person drew = new Person();
        drew.setName("Drew");
        drew.setEmail("drew@example.com");
        drew.setAddress(new Address("123 Main St", "This City", "IL", "12345"));

        Person notDrew = new Person("Not Drew", "not.drew@example.com", new Address("321 Not Main St.", "Not This City", "IL", "54321"));

        personService.savePerson(drew);
        personService.savePerson(notDrew);

        System.out.println();
        System.out.println("=== All People in Database ===");
        List<Person> people = personService.getAllPersons();
        people.forEach(p -> System.out.println(p.toString()));

        System.out.println();
        System.out.println("=== Person with ID \"1\" ===");
        Person person = personService.getPersonById(1L);
        System.out.println(person.toString());

        System.out.println();
        System.out.println("=== People in Illinois ===");
        List<Person> peopleInIllinois = personService.getPeopleByState("IL");
        peopleInIllinois.forEach(p -> System.out.println(p.toString()));

        System.out.println();
        System.out.println("=== People with Name \"Drew\" ===");
        List<Person> peopleByName = personService.getPeopleByName("Drew");
        peopleByName.forEach(p -> System.out.println(p.toString()));

        SpringApplication.exit(ctx);
    }
}
