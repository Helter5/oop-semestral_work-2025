package payment;

import java.util.Map;
import java.util.Set;
import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;


public class PaymentHandler {
    private Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    private InsuranceCompany insurer;

    public PaymentHandler(InsuranceCompany insurer){

    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void pay(MasterVehicleContract contract, int amount){
        // to do
    }

    public void pay(AbstractContract contract, int amount){
        // to do
    }
}
