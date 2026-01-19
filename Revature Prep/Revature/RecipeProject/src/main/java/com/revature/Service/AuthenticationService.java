package com.revature.Service;

import com.revature.Model.Chef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AuthenticationService {
    private ChefService chefService;
    public static Map<String, Chef> loggedInChefs = new HashMap<>();

    public AuthenticationService(ChefService chefService) {
        this.chefService = chefService;
        loggedInChefs = new HashMap<>();
    }

    public String login(Chef chef) {
        List<Chef> existingChefs = chefService.searchChefs(chef.getUsername());

        for (Chef existingChef : existingChefs) {
            if (existingChef.getUsername().equals(chef.getUsername()) && existingChef.getPassword().equals(chef.getPassword())) {
                String token = UUID.randomUUID().toString();
                loggedInChefs.put(token, existingChef);

                return token;
            }
        }

        return null;
    }

    public void logout(String token) {
        loggedInChefs.remove(token);
    }

    public Chef registerChef(Chef chef) {
        chefService.saveChef(chef);

        return chef;
    }

    public Chef getChefFromSessionToken(String token) {
        return loggedInChefs.get(token);
    }
}
