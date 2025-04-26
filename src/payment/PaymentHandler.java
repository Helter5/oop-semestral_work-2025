package payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.InvalidContractException;


public class PaymentHandler {
    final private Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    final private InsuranceCompany insurer;

    public PaymentHandler(InsuranceCompany insurer){
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer can't be null");
        }

        this.insurer = insurer;
        this.paymentHistory = new HashMap<>();
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void pay(MasterVehicleContract contract, int amount){
        // to do
    }

    public void pay(AbstractContract contract, int amount){
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





    }
}
