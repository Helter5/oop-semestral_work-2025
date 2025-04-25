package contracts;

import java.util.Set;
import company.InsuranceCompany;
import objects.Person;

public class MasterVehicleContract extends AbstractVehicleContract {
    final private Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder){
        super(contractNumber, insurer, beneficiary, policyHolder, null, 0);
    }

    public Set<SingleVehicleContract> getChildContracts(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void requestAdditionOfChildContract(SingleVehicleContract contract){
        // to do
    }
}
