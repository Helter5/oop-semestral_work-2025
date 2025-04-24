package contracts;

public class SingleVehicleContract {
    private Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder,
                                 contractPaymentData contractPaymentData, int coverageAmount,
                                 Vehicle vehicleToInsure){

    };

    public Vehicle getInsuredVehicle();
}
