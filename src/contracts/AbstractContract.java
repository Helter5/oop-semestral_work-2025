package contracts;

import company.InsuranceCompany;
import objects.Person;
import payment.PaymentHandler;
import payment.ContractPaymentData;

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
            throw new IllegalArgumentException("Contract number can't be null or empty");
        }

        if (insurer == null) {
            throw new IllegalArgumentException("Insurer can't be null");
        }

        if (policyHolder == null) {
            throw new IllegalArgumentException("Policy holder can't be null");
        }

        if (coverageAmount < 0) {
            throw new IllegalArgumentException("Coverage can't be negative value");
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

    public boolean isActive(){
        return isActive;
    }

    public abstract void setInactive(); // doplnit metody a skontrolovat ci to ma byt abstract

    public void setCoverageAmount(int coverageAmount) {
        if (coverageAmount < 0) {
            throw new IllegalArgumentException("Coverage can't be negative value");
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

    }
}
