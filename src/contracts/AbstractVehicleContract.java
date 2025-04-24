package contracts;

public abstract class AbstractVehicleContract {
    protected Person beneficiary;

    public AbstractVehicleContract(String contractNumber, InsuranceCompany insurer,
                                   Person beneficiary, Person policyHolder,
                                   ContractPaymentData contractPaymentData, int coverageAmount) {

    };

    public void setBeneficiary(Person beneficiary);

    public Person getBeneficiary();
}
