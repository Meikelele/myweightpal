package com.example.myweightpal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @GetMapping("/test/db")
    public String testDatabase() {
        try {
            mongoTemplate.getCollectionNames();
            return "✅ Połączenie z MongoDB działa!\n" +
                    "🔒 URI: " + hidePassword(mongoUri);
        } catch (Exception e) {
            return "❌ Błąd połączenia: " + e.getMessage();
        }
    }

    private String hidePassword(String uri) {
        // Ukryj hasło dla bezpieczeństwa
        return uri.replaceAll("://([^:]+):([^@]+)@", "://$1:****@");
    }
}