package payment;

public enum PremiumPaymentFrequency {
    ANNUAL, SEMI_ANNUAL, QUARTERLY, MONTHLY;

    public int getValueInMonths(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }
}
