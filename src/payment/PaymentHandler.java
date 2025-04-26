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
            throw new IllegalArgumentException("Insurer can't be null");
        }

        this.insurer = insurer;
        this.paymentHistory = new HashMap<>();
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory() {
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void pay(MasterVehicleContract contract, int amount) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Amount has to be positive");
        }

        if (!contract.isActive() || !contract.getInsurer().equals(insurer) || contract.getChildContracts().isEmpty()) {
            throw new InvalidContractException("The contract is either inactive, doesn't belong to this insurer, or has no child contracts");
        }

        for (AbstractContract childContract : contract.getChildContracts()) {
            ContractPaymentData paymentData = childContract.getContractPaymentData();
            int outstandingBalance = paymentData.getOutstandingBalance();

            if (outstandingBalance > 0) {
                int payment = Math.min(outstandingBalance, amount);
                paymentData.setOutstandingBalance(outstandingBalance - payment);
                amount -= payment;

                if (amount == 0) break;
            }
        }

        while (amount > 0) {
            boolean fundsUsed = false;

            for (AbstractContract childContract : contract.getChildContracts()) {
                ContractPaymentData paymentData = childContract.getContractPaymentData();
                int premium = paymentData.getPremium();

                int overpayment = Math.min(premium, amount);
                paymentData.setOutstandingBalance(paymentData.getOutstandingBalance() - overpayment);
                amount -= overpayment;
                fundsUsed = true;

                if (amount == 0) break;
            }

            if (!fundsUsed) break;
        }

        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, amount);

        paymentHistory.putIfAbsent(contract, new TreeSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }

    public void pay(AbstractContract contract, int amount) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Amount has to be positive value");
        }

        if (!contract.isActive() || !contract.getInsurer().equals(insurer)) {
            throw new InvalidContractException("The contract is either inactive or does not belong to this insurer");
        }

        int outstandingBalance = contract.getContractPaymentData().getOutstandingBalance();
        contract.getContractPaymentData().setOutstandingBalance(outstandingBalance - amount);

        LocalDateTime currentTime = insurer.getCurrentTime();
        PaymentInstance paymentInstance = new PaymentInstance(currentTime, amount);

        paymentHistory.putIfAbsent(contract, new HashSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }
}
