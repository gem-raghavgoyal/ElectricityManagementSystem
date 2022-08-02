package com.gemini.electricityManagementSystem.service;

import com.gemini.electricityManagementSystem.dao.ElectricityManagementSystemDao;
import com.gemini.electricityManagementSystem.model.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class billingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(billingService.class);

    private final ElectricityManagementSystemDao electricityManagementSystemDao;

    public billingService(final ElectricityManagementSystemDao electricityManagementSystemDao) {
        this.electricityManagementSystemDao = electricityManagementSystemDao;
    }

    /**
     * method to calculate total bill
     * @param noOfUnits
     * @return obj
     */
    public CompletableFuture<Bill> calculateBill(Integer noOfUnits) {
        LOGGER.info("Calculated bill for units {}", noOfUnits);
        return CompletableFuture.supplyAsync(() -> calculateBillAmount(noOfUnits))
                .thenApply(this::billWithGreenTax)
                .thenApply(this::totalBillAmountWithGST)
                .thenApply(totalBillAmount ->
                {
                    LOGGER.info("Calculated bill details {}", totalBillAmount);
                    return new Bill.Builder()
                            .setTotalBillAmount(totalBillAmount).build();
                });
    }

    /**
     * method to calculate total bill amount with GST
     * @param billAmountWithGreenTax
     * @return double
     */
    private double totalBillAmountWithGST(Double billAmountWithGreenTax) {
        double totalBillAmountWithGST = billAmountWithGreenTax + billAmountWithGreenTax * 18 / 100;
        LOGGER.info("Calculated total bill amount with GST {}", totalBillAmountWithGST);
        return totalBillAmountWithGST;
    }

    /**
     * method to calculate bill amount with green tax
     * @param billAmount
     * @return double
     */
    private double billWithGreenTax(double billAmount) {
        double billAmountWithGreenTax = billAmount + billAmount * 3 / 100;
        LOGGER.info("Calculated bill amount with green tax {}", billAmountWithGreenTax);
        return billAmountWithGreenTax;
    }

    /**
     * method to calculate the bill amount
     * @param noOfUnits spent for bill
     * @return double
     */
    private double calculateBillAmount(Integer noOfUnits) {
        double billAmountWithoutTax = 0;
        if (noOfUnits >= 0 && noOfUnits <= 100)
            billAmountWithoutTax = noOfUnits * 5;
        else if (noOfUnits > 100 && noOfUnits <= 400)
            billAmountWithoutTax = noOfUnits * 6;
        else if (noOfUnits > 400) {
            billAmountWithoutTax = noOfUnits * 8;
        }
        LOGGER.info("Bill amount without tax {}", billAmountWithoutTax);
        return billAmountWithoutTax;
    }


}
