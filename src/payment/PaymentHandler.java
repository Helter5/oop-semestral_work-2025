package payment;

import java.time.LocalDateTime;
import java.util.*;

import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.InvalidContractException;


public class PaymentHandler {
    final private Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    final private InsuranceCompany insurer;

    public PaymentHandler(InsuranceCompany insurer) {
        if (insurer == null) {
            throw new IllegalArgumentException();
        }

        this.insurer = insurer;
        this.paymentHistory = new HashMap<>();
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory() {
        return paymentHistory;
    }

    public void pay(MasterVehicleContract contract, int amount) {
        if (contract == null) {
            throw new IllegalArgumentException();
        }

        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        if (!contract.isActive() || !contract.getInsurer().equals(insurer) || contract.getChildContracts().isEmpty()) {
            throw new InvalidContractException("contract exception");
        }

        int originalAmount = amount;

        // Step 1: Pay off outstanding balances for active child contracts
        for (AbstractContract childContract : contract.getChildContracts()) {
            // Skip inactive contracts
            if (!childContract.isActive()) continue;

            ContractPaymentData paymentData = childContract.getContractPaymentData();
            int outstandingBalance = paymentData.getOutstandingBalance();

            if (outstandingBalance > 0) {
                // If we have enough funds to cover the outstanding balance
                if (amount >= outstandingBalance) {
                    amount -= outstandingBalance;
                    paymentData.setOutstandingBalance(0);
                } else {
                    // If we don't have enough funds
                    paymentData.setOutstandingBalance(outstandingBalance - amount);
                    amount = 0;
                    break;
                }
            }
        }

        // Step 2: While we still have funds, apply payments as credit against future premiums
        while (amount > 0) {
            boolean fundsApplied = false;

            for (AbstractContract childContract : contract.getChildContracts()) {
                // Skip inactive contracts
                if (!childContract.isActive()) continue;

                ContractPaymentData paymentData = childContract.getContractPaymentData();
                int premium = paymentData.getPremium();

                // Skip contracts with no premium
                if (premium <= 0) continue;

                // If we have enough funds to cover the premium
                if (amount >= premium) {
                    paymentData.setOutstandingBalance(paymentData.getOutstandingBalance() - premium);
                    amount -= premium;
                    fundsApplied = true;
                } else {
                    // If we don't have enough funds for the full premium
                    paymentData.setOutstandingBalance(paymentData.getOutstandingBalance() - amount);
                    amount = 0;
                    fundsApplied = true;
                    break;
                }
            }

            // If we couldn't apply any funds in this cycle, break out
            if (!fundsApplied) break;
        }

        // Create payment record with original amount
        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, originalAmount);

        paymentHistory.putIfAbsent(contract, new TreeSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }

    public void pay(AbstractContract contract, int amount) {
        if (contract == null) {
            throw new IllegalArgumentException();
        }

        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        if (!contract.isActive() || !contract.getInsurer().equals(insurer)) {
            throw new InvalidContractException("contract exception");
        }

        int outstandingBalance = contract.getContractPaymentData().getOutstandingBalance();
        contract.getContractPaymentData().setOutstandingBalance(outstandingBalance - amount);

        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, amount);

        paymentHistory.putIfAbsent(contract, new HashSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }
}
