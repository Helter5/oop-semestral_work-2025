package contracts;

import company.InsuranceCompany;
import objects.Person;
import payment.PaymentHandler;
import payment.ContractPaymentData;

import java.util.Objects;

public abstract class AbstractContract {
    final private String contractNumber;
    final protected InsuranceCompany  insurer;
    final protected Person policyHolder;
    final protected ContractPaymentData contractPaymentData;
    protected int coverageAmount;
    protected boolean isActive;

    public AbstractContract(String contractNumber, InsuranceCompany insurer,
                            Person policyHolder, ContractPaymentData contractPaymentData,
                            int coverageAmount)
    {
        if (contractNumber == null || contractNumber.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (insurer == null) {
            throw new IllegalArgumentException();
        }

        if (policyHolder == null) {
            throw new IllegalArgumentException();
        }

        //if (contractPaymentData == null) {
        //    throw new IllegalArgumentException();
        //}

        if (coverageAmount < 0) {
            throw new IllegalArgumentException();
        }

        this.contractNumber = contractNumber;
        this.insurer = insurer;
        this.policyHolder = policyHolder;
        this.contractPaymentData = contractPaymentData;
        this.coverageAmount = coverageAmount;
        this.isActive = true;
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

    public abstract boolean isActive();

    public abstract void setInactive(); // doplnit metody a skontrolovat ci to ma byt abstract

    public void setCoverageAmount(int coverageAmount) {
        if (coverageAmount < 0) {
            throw new IllegalArgumentException();
        }
        this.coverageAmount = coverageAmount;
    }

    public ContractPaymentData getContractPaymentData() {
        return contractPaymentData;
    }

    /* to do */
    public void pay(int amount) {
        PaymentHandler paymentHandler = insurer.getHandler();
        paymentHandler.pay(this, amount);
    }

    public void updateBalance(){
        insurer.chargePremiumOnContract(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractContract that)) return false;
        return Objects.equals(contractNumber, that.contractNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contractNumber);
    }
}
