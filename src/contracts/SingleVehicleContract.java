package contracts;

import objects.Vehicle;
import objects.Person;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public class SingleVehicleContract extends AbstractVehicleContract {
    private final Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder,
                                 ContractPaymentData contractPaymentData, int coverageAmount,
                                 Vehicle vehicleToInsure)
    {

        super(contractNumber, insurer, beneficiary, policyHolder, contractPaymentData, coverageAmount);

        if (vehicleToInsure == null) {
            throw new IllegalArgumentException("Vehicle to insure cannot be null.");
        }

        if (contractPaymentData == null) {
            throw new IllegalArgumentException("Contract payment data cannot be null.");
        }

        this.insuredVehicle = vehicleToInsure;
    }

    public Vehicle getInsuredVehicle(){
        return insuredVehicle;
    }

    /*
    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setInactive() {
        this.isActive = false;
    }
    */
}