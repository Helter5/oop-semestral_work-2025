package contracts;

import objects.Person;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public abstract class AbstractVehicleContract extends AbstractContract {
    protected Person beneficiary;

    public AbstractVehicleContract(String contractNumber, InsuranceCompany insurer,
                                   Person beneficiary, Person policyHolder,
                                   ContractPaymentData contractPaymentData, int coverageAmount)
    {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        if(beneficiary.equals(policyHolder)) {
            throw new IllegalArgumentException("Beneficiary can't be same as Policy holder");
        }

        this.beneficiary = beneficiary;
    }

    public void setBeneficiary(Person beneficiary){
        this.beneficiary = beneficiary;
    }

    public Person getBeneficiary(){
        return beneficiary;
    }
}
