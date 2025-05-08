package contracts;

import java.util.LinkedHashSet;
import java.util.Set;
import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;

public class MasterVehicleContract extends AbstractVehicleContract {
    private final Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder)
    {
        super(contractNumber, insurer, beneficiary, policyHolder, null, 0);
        validatePolicyHolderLegalForm(policyHolder);

        this.childContracts = new LinkedHashSet<>();
    }

    private void validatePolicyHolderLegalForm(Person policyHolder) {
        if (policyHolder.getLegalForm() != LegalForm.LEGAL) {
            throw new IllegalArgumentException("Policy holder must be a legal entity.");
        }
    }

    public Set<SingleVehicleContract> getChildContracts(){
        return childContracts;
    }

    public void requestAdditionOfChildContract(SingleVehicleContract contract){
        insurer.moveSingleVehicleContractToMasterVehicleContract(this, contract);
    }


    @Override
    public boolean isActive() {
        if (childContracts.isEmpty()) {
            return isActive;
        }

        for (SingleVehicleContract contract : childContracts) {
            if (contract.isActive()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void setInactive() {
        for (SingleVehicleContract contract : childContracts) {
            contract.setInactive();
        }
        this.isActive = false;
    }

    @Override
    public void pay(int amount) {
        insurer.getHandler().pay(this, amount);
    }

    @Override
    public void updateBalance() {
        MasterVehicleContract self = this;
        insurer.chargePremiumOnContract(self);
    }
}
