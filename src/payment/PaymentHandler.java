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
        if (insurer == null) throw new IllegalArgumentException();

        this.insurer = insurer;
        this.paymentHistory = new HashMap<>();
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory() {
        return paymentHistory;
    }

    public void pay(MasterVehicleContract contract, int amount) {
        validateContractAndAmount(contract, amount);
        if (contract.getChildContracts().isEmpty()) throw new InvalidContractException("contract exception");

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
        if (contract == null) throw new IllegalArgumentException();
        if (amount <= 0) throw new IllegalArgumentException();
        if (!contract.isActive() || !contract.getInsurer().equals(insurer)) {
            throw new InvalidContractException("contract exception");
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
            boolean fundsApplied = false;

            for (AbstractContract childContract : contract.getChildContracts()) {
                if (!childContract.isActive()) continue;

                ContractPaymentData contractPaymentData = childContract.getContractPaymentData();
                int premium = contractPaymentData.getPremium();

                if (premium <= 0) continue;

                if (amount >= premium) {
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - premium);
                    amount -= premium;
                    fundsApplied = true;
                } else {
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - amount);
                    amount = 0;
                    fundsApplied = true;
                    break;
                }
            }

            if (!fundsApplied) break;
        }
    }

    private void recordPayment(AbstractContract contract, int amount) {
        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, amount);

        paymentHistory.putIfAbsent(contract, new TreeSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }
}
