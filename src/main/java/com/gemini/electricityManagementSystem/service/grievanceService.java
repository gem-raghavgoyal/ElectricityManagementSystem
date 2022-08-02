package com.gemini.electricityManagementSystem.service;

import com.gemini.electricityManagementSystem.dao.ElectricityManagementSystemDao;
import com.gemini.electricityManagementSystem.model.Grievance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class grievanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(billingService.class);

    private final ElectricityManagementSystemDao electricityManagementSystemDao;

    public grievanceService(ElectricityManagementSystemDao electricityManagementSystemDao) {
        this.electricityManagementSystemDao = electricityManagementSystemDao;
    }

    /**
     * method to register customer grievance
     *
     * @param grievance details
     * @return
     * @return grievance registration ID
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public UUID registerGrievance(Grievance grievance) throws ExecutionException, InterruptedException {
        LOGGER.info("Registering grievance with details {}", grievance);
        UUID grievanceId = UUID.randomUUID();
        Boolean flag = CompletableFuture.supplyAsync(() ->
                electricityManagementSystemDao.registerGrievance(grievance, grievanceId)).get();
        LOGGER.info("Grievance registration flag {}", flag);
        if (flag) {
            return grievanceId;
        } else
            return null;
    }
}
