package contracts;

import java.util.LinkedHashSet;
import java.util.Set;
import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;
import payment.ContractPaymentData;
import payment.PremiumPaymentFrequency;

import static objects.Person.isValidRegistrationNumber;

public class MasterVehicleContract extends AbstractVehicleContract {
    final private Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder)
    {
        super(contractNumber, insurer, beneficiary, policyHolder, null, 0);

        if (policyHolder.getLegalForm() != LegalForm.LEGAL) {
            throw new IllegalArgumentException();
        }

        this.childContracts = new LinkedHashSet<>();
    }

    public Set<SingleVehicleContract> getChildContracts(){
        return childContracts;
    }

    public void requestAdditionOfChildContract(SingleVehicleContract contract){
        childContracts.add(contract);
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
        insurer.chargePremiumOnContract(this);
    }
}
