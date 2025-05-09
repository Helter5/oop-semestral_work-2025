package payment;

import java.time.LocalDateTime;
import java.util.*;

import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.InvalidContractException;


public class PaymentHandler {
    private final Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    private final InsuranceCompany insurer;

    public PaymentHandler(InsuranceCompany insurer) {
        if (insurer == null) throw new IllegalArgumentException("Insurer can not be null");

        this.insurer = insurer;
        this.paymentHistory = new HashMap<>();
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory() {
        return paymentHistory;
    }

    public void pay(MasterVehicleContract contract, int amount) {
        validateContractAndAmount(contract, amount);
        if (contract.getChildContracts().isEmpty()) throw new InvalidContractException("Contract has no child contracts");

        int remainingAmount = payOffOutstandingBalance(contract, amount);
        payForPremiums(contract, remainingAmount);

        recordPayment(contract, amount);
    }

    public void pay(AbstractContract contract, int amount) {
        validateContractAndAmount(contract, amount);

        int outstandingBalance = contract.getContractPaymentData().getOutstandingBalance();
        contract.getContractPaymentData().setOutstandingBalance(outstandingBalance - amount);

        recordPayment(contract, amount);
    }

    /*
    @ help methods
     */
    private void validateContractAndAmount(AbstractContract contract, int amount) {
        if (contract == null) throw new IllegalArgumentException("Contract can not be null");
        if (amount <= 0) throw new IllegalArgumentException("Amount is not positive");
        if (!contract.isActive()) {
            throw new InvalidContractException("Contract is not active");
        }
        if (!contract.getInsurer().equals(insurer)) {
            throw new InvalidContractException("Contract does not belong to this insurer");
        }
    }

    private int payOffOutstandingBalance(MasterVehicleContract contract, int amount) {
        int remainingAmount = amount;

        for (AbstractContract childContract : contract.getChildContracts()) {
            if (!childContract.isActive()) continue;

            ContractPaymentData contractPaymentData = childContract.getContractPaymentData();
            int outstandingBalance = contractPaymentData.getOutstandingBalance();

            if (outstandingBalance > 0) {
                if (remainingAmount >= outstandingBalance) {
                    remainingAmount -= outstandingBalance;
                    contractPaymentData.setOutstandingBalance(0);
                } else {
                    contractPaymentData.setOutstandingBalance(outstandingBalance - remainingAmount);
                    remainingAmount = 0;
                    break;
                }
            }
        }

        return remainingAmount;
    }

    private void payForPremiums(MasterVehicleContract contract, int amount) {
        while (amount > 0) {
            int initialAmount = amount;

            for (AbstractContract child : contract.getChildContracts()) {
                if (!child.isActive()) continue;

                ContractPaymentData data = child.getContractPaymentData();
                int premium = data.getPremium();
                if (premium <= 0) continue;

                int payment = Math.min(amount, premium);
                data.setOutstandingBalance(data.getOutstandingBalance() - payment);
                amount -= payment;

                if (amount == 0) break;
            }

            if (amount == initialAmount) break; // no progress made
        }
    }

    private void recordPayment(AbstractContract contract, int amount) {
        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, amount);

        paymentHistory.putIfAbsent(contract, new TreeSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }
}
