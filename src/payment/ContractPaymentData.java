package payment;

import java.time.LocalDateTime;

public class ContractPaymentData {
    private int premium;
    private PremiumPaymentFrequency premiumPaymentFrequency;
    private LocalDateTime nextPaymentTime;
    private int outstandingBalance;

    public ContractPaymentData(int premium, PremiumPaymentFrequency premiumPaymentFrequency,
                               LocalDateTime nextPaymentTime, int outstandingBalance)
    {
        if (premium < 0) {
            throw new IllegalArgumentException("Premium value has to be positive");
        }

        if (premiumPaymentFrequency == null) {
            throw new IllegalArgumentException("Premium payment frequency can't be null");
        }

        if (nextPaymentTime == null) {
            throw new IllegalArgumentException("Next payment time can't be null");
        }

        this.premium = premium;
        this.premiumPaymentFrequency = premiumPaymentFrequency;
        this.nextPaymentTime = nextPaymentTime;
        this.outstandingBalance = outstandingBalance;
    }

    public int getPremium(){
        return premium;
    }

    public void setPremium(int premium){
        if (premium < 0) {
            throw new IllegalArgumentException("Premium value has to be positive");
        }
        this.premium = premium;
    }

    public void setOutstandingBalance(int outstandingBalance){
        this.outstandingBalance = outstandingBalance;
    }

    public int getOutstandingBalance(){
        return outstandingBalance;
    }

    public void setPremiumPaymentFrequency(PremiumPaymentFrequency premiumPaymentFrequency){
        if (premiumPaymentFrequency == null) {
            throw new IllegalArgumentException("Premium payment frequency can't be null");
        }
        this.premiumPaymentFrequency = premiumPaymentFrequency;
    }

    public PremiumPaymentFrequency getPremiumPaymentFrequency(){
        return premiumPaymentFrequency;
    }

    public LocalDateTime getNextPaymentTime(){
        return nextPaymentTime;
    }

    public void updateNextPaymentTime(){
        // pripočíta počet mesiacov podľa PremiumPaymentFrequency
        int monthsToAdd = premiumPaymentFrequency.getValueInMonths();
        this.nextPaymentTime = this.nextPaymentTime.plusMonths(monthsToAdd);
    }
}
