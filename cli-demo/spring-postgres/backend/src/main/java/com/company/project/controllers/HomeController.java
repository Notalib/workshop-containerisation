package com.company.project.controllers;

import com.company.project.entity.Greeting;
import com.company.project.repository.GreetingRepository;
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

    @Autowired
    private GreetingRepository repository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @GetMapping("/")
    public String showHome(String name, Model model) {
        Greeting dockerGreeting = repository.findById(1).orElse(new Greeting("DB not found 😕"));
        model = model.addAttribute("name", dockerGreeting.getName());
        model = model.addAttribute("body", "Connected to database: " + datasourceUrl);
        return "home";
    }

    @GetMapping("/greetings")
    public String listGreetings(Model model) {
        model.addAttribute("greetings", repository.findAll());
        return "greetings";
    }

    @GetMapping("/greetings/{id}")
    public String sayHello(@PathVariable int id, Model model) {
        Greeting greeting = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Greeting " + id + " not found"));
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
        Greeting saved = repository.save(new Greeting(name));
        return "redirect:/greetings/" + saved.getId();
    }

}
