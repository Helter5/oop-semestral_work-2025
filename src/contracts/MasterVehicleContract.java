package contracts;

import java.util.LinkedHashSet;
import java.util.Set;
import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;

public class MasterVehicleContract extends AbstractVehicleContract {
    final private Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder)
    {
        super(contractNumber, insurer, beneficiary, policyHolder, null, 0);
        validatePolicyHolderLegalForm(policyHolder);

        this.childContracts = new LinkedHashSet<>();
    }

    private void validatePolicyHolderLegalForm(Person policyHolder) {
        if (policyHolder.getLegalForm() != LegalForm.LEGAL) {
            throw new IllegalArgumentException();
        }
    }

    public Set<SingleVehicleContract> getChildContracts(){
        return childContracts;
    }

    public void requestAdditionOfChildContract(SingleVehicleContract contract){
        childContracts.add(contract);
    }

    @Override
    public boolean isActive() {
        return childContracts.isEmpty() ?
                isActive : childContracts.stream().anyMatch(SingleVehicleContract::isActive);
    }

    @Override
    public void setInactive() {
        childContracts.forEach(SingleVehicleContract::setInactive);
        this.isActive = false;
    }

    @Override
    public void pay(int amount) {
        // chyba pri zadani - viz. discord
        insurer.getHandler().pay(this, amount);
    }

    @Override
    public void updateBalance() {
        insurer.chargePremiumOnContract(this);
    }
}
