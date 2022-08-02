package com.gemini.electricityManagementSystem.service;

import com.gemini.electricityManagementSystem.dao.ElectricityManagementSystemDao;
import com.gemini.electricityManagementSystem.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class userRegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(userRegistrationService.class);

    private final ElectricityManagementSystemDao electricityManagementSystemDao;

    public userRegistrationService(final ElectricityManagementSystemDao electricityManagementSystemDao) {
        this.electricityManagementSystemDao = electricityManagementSystemDao;
    }

    public UUID register(UserDetails userDetails) throws ExecutionException, InterruptedException {
        LOGGER.info("Registering user- {}", userDetails);
        UUID customerId = UUID.randomUUID();
        Boolean flag = CompletableFuture.supplyAsync(() ->
                electricityManagementSystemDao.createUser(userDetails, customerId)).get();
        LOGGER.info("User registration flag {}", flag);
        if (flag)
            return customerId;
        else
            return null;
    }
}
