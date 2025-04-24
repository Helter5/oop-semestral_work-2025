package contracts;

import objects.Vehicle;
import objects.Person;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public class SingleVehicleContract extends AbstractVehicleContract {
    private Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder,
                                 ContractPaymentData contractPaymentData, int coverageAmount,
                                 Vehicle vehicleToInsure){
        super(contractNumber, insurer, beneficiary, policyHolder, contractPaymentData, coverageAmount);
    }

    public Vehicle getInsuredVehicle(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }
}
