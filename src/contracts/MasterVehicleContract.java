package contracts;

import java.util.LinkedHashSet;
import java.util.Set;
import company.InsuranceCompany;
import objects.Person;

import static objects.Person.isValidRegistrationNumber;

public class MasterVehicleContract extends AbstractVehicleContract {
    final private Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder)
    {
        super(contractNumber, insurer, beneficiary, policyHolder, null, 0);

        if(!isValidRegistrationNumber(policyHolder.getId())) {
            throw new IllegalArgumentException("Insurer can be only customer with valid IČO");
        }

        this.childContracts = new LinkedHashSet<>();
    }

    public Set<SingleVehicleContract> getChildContracts(){
        return childContracts;
    }

    public void requestAdditionOfChildContract(SingleVehicleContract contract){
        insurer.moveSingleVehicleContractToMasterVehicleContract(this, contract);
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
}
