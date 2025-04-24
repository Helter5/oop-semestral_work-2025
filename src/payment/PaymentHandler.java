package payment;

public class PaymentHandler {
    private Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    private InsuranceCompany insurer;

    public PaymentHandler(InsuranceCompany insurer){

    };

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory();

    public void pay(MasterVehicleContract contract, int amount);

    public void pay(AbstractContract contract, int amount);
}
