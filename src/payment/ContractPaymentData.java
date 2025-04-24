package payment;

import java.time.LocalDateTime;

public class ContractPaymentData {
    private int premium;
    private PremiumPaymentFrequency premiumPaymentFrequency;
    private LocalDateTime nextPaymentTime;
    private int outstandingBalance;

    public ContractPaymentData(int premium, PremiumPaymentFrequency premiumPaymentFrequency,
                               LocalDateTime nextPaymentTime, int outstandingBalance){

    }

    public int getPremium(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void setPremium(int premium){
        // to do
    }

    public void setOutstandingBalance(int outstandingBalance){
        // to do
    }

    public int getOutstandingBalance(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void setPremiumPaymentFrequency(PremiumPaymentFrequency premiumPaymentFrequency){
        // to do
    }

    public PremiumPaymentFrequency getPremiumPaymentFrequency(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public LocalDateTime getNextPaymentTime(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void updateNextPaymentTime(){
        // to do
    }
}
