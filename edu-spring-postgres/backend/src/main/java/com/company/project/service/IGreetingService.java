package com.company.project.service;

import com.company.project.entity.Greeting;
import java.util.Optional;
import java.util.UUID;

public interface IGreetingService {

    Optional<Greeting> showHome(String name);

    Iterable<Greeting> listGreetings();

    Optional<Greeting> sayHello(UUID id);

    Greeting createGreeting(String name);
}
