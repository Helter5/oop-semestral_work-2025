package contracts;

import company.InsuranceCompany;
import objects.Person;
import payment.PaymentHandler;
import payment.ContractPaymentData;

import java.util.Objects;

public abstract class AbstractContract {
    private final String contractNumber;
    protected final InsuranceCompany  insurer;
    protected final Person policyHolder;
    protected final ContractPaymentData contractPaymentData;
    protected int coverageAmount;
    protected boolean isActive;

    public AbstractContract(String contractNumber, InsuranceCompany insurer,
                            Person policyHolder, ContractPaymentData contractPaymentData,
                            int coverageAmount)
    {
        if (insurer == null) throw new IllegalArgumentException("Insurance company cannot be null");
        if (policyHolder == null) throw new IllegalArgumentException("Policy holder cannot be null");

        this.contractNumber = validateContractNumber(contractNumber);
        this.insurer = insurer;
        this.policyHolder = policyHolder;
        this.contractPaymentData = contractPaymentData;
        this.coverageAmount = validateCoverageAmount(coverageAmount);
        this.isActive = true;
    }

    private String validateContractNumber(String contractNumber) {
        if (contractNumber == null || contractNumber.isEmpty()) {
            throw new IllegalArgumentException("Contract number cannot be null or empty");
        }
        return contractNumber;
    }

    private int validateCoverageAmount(int coverageAmount) {
        if (coverageAmount < 0) {
            throw new IllegalArgumentException("Coverage amount cannot be negative");
        }
        return coverageAmount;
    }

    public String getContractNumber(){
        return contractNumber;
    }

    public Person getPolicyHolder(){
        return policyHolder;
    }

    public InsuranceCompany getInsurer(){
        return insurer;
    }

    public int getCoverageAmount(){
        return coverageAmount;
    }

    //public abstract boolean isActive();

    //public abstract void setInactive();

    public boolean isActive() {
        return isActive;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public void setCoverageAmount(int coverageAmount) {
        validateCoverageAmount(coverageAmount);
        this.coverageAmount = coverageAmount;
    }

    public ContractPaymentData getContractPaymentData() {
        return contractPaymentData;
    }

    public void pay(int amount) {
        PaymentHandler paymentHandler = insurer.getHandler();
        paymentHandler.pay(this, amount);
    }

    public void updateBalance(){
        AbstractContract self = this;
        insurer.chargePremiumOnContract(self);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractContract that)) return false;
        return Objects.equals(contractNumber, that.contractNumber) &&
                Objects.equals(insurer, that.insurer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractNumber, insurer);
    }
}
