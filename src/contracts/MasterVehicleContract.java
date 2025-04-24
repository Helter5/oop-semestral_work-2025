package contracts;

public class MasterVehicleContract {
    Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder){

    };

    public Set<SingleVehicleContract> getChildContracts();

    public void requestAdditionOfChildContract(SingleVehicleContract contract);
}
