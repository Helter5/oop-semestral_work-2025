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
                            int coverageAmount) {

    }

    public String getContractNumber(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public Person getPolicyHolder(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public InsuranceCompany getInsurer(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public int getCoverageAmount(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public boolean isActive(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void setInactive(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void setCoverageAmount(int coverageAmount){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public ContractPaymentData getContractPaymentData(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void pay(int amount){

    }

    public void updateBalance(){

    }
}
