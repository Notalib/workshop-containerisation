package com.company.project.controllers;

import com.company.project.entity.Greeting;
import com.company.project.repository.GreetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private GreetingRepository repository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @GetMapping("/")
    public String showHome(String name, Model model) {
        log.info("GET / — loading default greeting (id=1) from {}", datasourceUrl);
        Greeting dockerGreeting = repository.findById(1).orElseGet(() -> {
            log.warn("Default greeting (id=1) not found in DB");
            return new Greeting("DB not found!");
        });
        log.info("Loaded greeting: {}", dockerGreeting.getName());
        model = model.addAttribute("name", dockerGreeting.getName());
        model = model.addAttribute("body", "Connected to database: " + datasourceUrl);
        return "home";
    }

    @GetMapping("/greetings")
    public String listGreetings(Model model) {
        log.info("GET /greetings — listing all greetings");
        Iterable<Greeting> greetings = repository.findAll();
        long count = 0;
        for (Greeting g : greetings) count++;
        log.info("Returned {} greetings from DB", count);
        model.addAttribute("greetings", greetings);
        return "greetings";
    }

    @GetMapping("/greetings/{id}")
    public String sayHello(@PathVariable int id, Model model) {
        log.info("GET /greetings/{} — looking up greeting", id);
        Greeting greeting = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Greeting {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Greeting " + id + " not found");
                });
        log.info("Found greeting #{}: {}", id, greeting.getName());
        model.addAttribute("name", greeting.getName());
        model.addAttribute("body", "Greeting #" + id);
        return "home";
    }

    @GetMapping("/new")
    public String newGreetingForm() {
        return "new";
    }

    @PostMapping("/greetings")
    public String createGreeting(@RequestParam String name) {
        log.info("POST /greetings — creating new greeting with name='{}'", name);
        Greeting saved = repository.save(new Greeting(name));
        log.info("Saved new greeting id={} name='{}'", saved.getId(), saved.getName());
        return "redirect:/greetings/" + saved.getId();
    }

}
