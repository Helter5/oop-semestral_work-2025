package payment;

public class ContractPaymentData {
    private int premium;
    private PremiumPaymentFrequency premiumPaymentFrequency;
    private LocalDateTime nextPaymentTime;
    private int outstandingBalance;

    public ContractPaymentData(int premium, PremiumPaymentFrequency premiumPaymentFrequency,
                               LocalDateTime nextPaymentTime, int outstandingBalance){

    };

    public int getPremium();

    public void setPremium(int premium);

    public void setOutstandingBalance(int outstandingBalance);

    public int getOutstandingBalance();

    public void setPremiumPaymentFrequency(PremiumPaymentFrequency premiumPaymentFrequency);

    public PremiumPaymentFrequency getPremiumPaymentFrequency();

    public LocalDateTime getNextPaymentTime();

    public void updateNextPaymentTime();
}
