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
        validateBeneficiary(beneficiary);

        this.beneficiary = beneficiary;
    }

    private void validateBeneficiary(Person beneficiary) {
        if (beneficiary != null && beneficiary.equals(policyHolder)) {
            throw new IllegalArgumentException("Beneficiary cannot be the same as policy holder.");
        }
    }

    public void setBeneficiary(Person beneficiary){
        validateBeneficiary(beneficiary);
        this.beneficiary = beneficiary;
    }

    public Person getBeneficiary(){
        return beneficiary;
    }
}
