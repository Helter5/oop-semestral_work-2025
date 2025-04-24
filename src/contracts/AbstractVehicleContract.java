package contracts;

import objects.Person;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public abstract class AbstractVehicleContract extends AbstractContract {
    protected Person beneficiary;

    public AbstractVehicleContract(String contractNumber, InsuranceCompany insurer,
                                   Person beneficiary, Person policyHolder,
                                   ContractPaymentData contractPaymentData, int coverageAmount) {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
    }

    public void setBeneficiary(Person beneficiary){
    }

    public Person getBeneficiary(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }
}
