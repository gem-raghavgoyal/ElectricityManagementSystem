package com.gemini.electricityManagementSystem.controller;

import com.gemini.electricityManagementSystem.model.Bill;
import com.gemini.electricityManagementSystem.model.Grievance;
import com.gemini.electricityManagementSystem.model.UserDetails;
import com.gemini.electricityManagementSystem.service.billingService;
import com.gemini.electricityManagementSystem.service.grievanceService;
import com.gemini.electricityManagementSystem.service.userRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author va.gupta
 * controller class electricity management
 */
@RestController
@RequestMapping("api/v1")
public class ElectricityManagementSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElectricityManagementSystemController.class);

    private final userRegistrationService userRegistrationService;
    private final billingService billingService;
    private final grievanceService grievanceService;

    public ElectricityManagementSystemController(final userRegistrationService userRegistrationService, final billingService billingService, com.gemini.electricityManagementSystem.service.grievanceService grievanceService) {
        this.userRegistrationService = userRegistrationService;
        this.billingService = billingService;
        this.grievanceService = grievanceService;
    }

    /**
     * api to register customer
     *
     * @param userDetails obj
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/userRegistration")
    public ResponseEntity<String> registerUser(@RequestBody UserDetails userDetails) throws ExecutionException, InterruptedException {
        UUID customerId = userRegistrationService.register(userDetails);
        if (customerId != null) {
            LOGGER.info("Registered user with ID {}", customerId);
            return new ResponseEntity<>("User registered with ID " + customerId, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Error encountered while registering user", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * api to calculate the electricity bill amount
     *
     * @param noOfUnits
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/calculateBill")
    public Bill calculateBill(@RequestParam Integer noOfUnits) throws ExecutionException,
            InterruptedException {
        LOGGER.info("Calculate bill for {} units of electricity", noOfUnits);
        return billingService.calculateBill(noOfUnits).get();
    }

    /**
     * api to register customer grievance
     *
     * @param grievance obj
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/grievance")
    public ResponseEntity<String> grievance(@RequestBody Grievance grievance) throws ExecutionException, InterruptedException {
        LOGGER.info("Submit grievance- {}", grievance);
        UUID grievanceId = grievanceService.registerGrievance(grievance);
        if (grievanceId != null) {
            LOGGER.info("Grievance submitted with ID {}", grievanceId);
            return new ResponseEntity<>("Grievance submitted with ID " + grievanceId, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Error encountered while submitting grievance", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
