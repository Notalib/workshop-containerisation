package com.company.project.controller;

import com.company.project.entity.Greeting;
import com.company.project.service.IGreetingService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private final IGreetingService iGreetingService;

    @Autowired
    public GreetingController(IGreetingService iGreetingService) {
        this.iGreetingService = iGreetingService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        String greetingDocker = "Docker container";
        log.info("GET / — loading default greeting with name='{}'", greetingDocker);
        Greeting dockerGreeting = iGreetingService.showHome(greetingDocker).orElseThrow(() -> {
            log.warn("Greeting with name='{}' not found", greetingDocker);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Greeting '" + greetingDocker + "' not found");
        });
        log.info("Loaded greeting: '{}'", dockerGreeting.getName());
        model.addAttribute("name", dockerGreeting.getName());
        model.addAttribute("id", dockerGreeting.getId());
        model.addAttribute("body", "Connected to database!");
        return "greeting-single";
    }

    @GetMapping("/greetings")
    public String listGreetings(Model model) {
        log.info("GET /greetings — listing all greetings");
        Iterable<Greeting> greetings = iGreetingService.listGreetings();
        model.addAttribute("greetings", greetings);
        return "greetings";
    }

    @GetMapping("/greetings/{id}")
    public String sayHello(@PathVariable UUID id, Model model) {
        log.info("GET /greetings/{} — looking up greeting", id);
        Greeting greeting = iGreetingService.sayHello(id).orElseThrow(() -> {
            log.warn("Greeting with id='{}' not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Greeting '" + id + "' not found");
        });
        log.info("Found greeting id='{}' name='{}'", id, greeting.getName());
        model.addAttribute("name", greeting.getName());
        model.addAttribute("id", id);
        return "greeting-single";
    }

    @GetMapping("/new")
    public String newGreetingForm() {
        return "new";
    }

    @PostMapping("/greetings")
    public String createGreeting(@RequestParam String name) {
        log.info("POST /greetings — creating new greeting with name='{}'", name);
        Greeting greeting = iGreetingService.createGreeting(name);
        log.info("Saved new greeting id='{}' name='{}'", greeting.getId(), greeting.getName());
        return "redirect:/greetings/" + greeting.getId();
    }
}
