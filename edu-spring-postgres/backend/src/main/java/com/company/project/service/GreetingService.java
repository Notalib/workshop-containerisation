package com.company.project.service;

import com.company.project.entity.Greeting;
import com.company.project.repository.IGreetingRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements IGreetingService {

    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    private final IGreetingRepository iGreetingRepository;

    @Autowired
    public GreetingService(IGreetingRepository iGreetingRepository) {
        this.iGreetingRepository = iGreetingRepository;
    }

    public Optional<Greeting> showHome(String name) {
        Optional<Greeting> greeting = iGreetingRepository.findGreetingByName(name);
        return greeting;
    }

    public Iterable<Greeting> listGreetings() {
        Iterable<Greeting> greetings = iGreetingRepository.findAll();
        long countedGreetings = iGreetingRepository.count();
        log.info("Returned {} greetings from DB", countedGreetings);
        return greetings;
    }

    public Optional<Greeting> sayHello(UUID id) {
        Optional<Greeting> greeting = iGreetingRepository.findById(id);
        return greeting;
    }

    public Greeting createGreeting(String name) {
        Greeting greeting = iGreetingRepository.save(new Greeting(name));
        return greeting;
    }
}
