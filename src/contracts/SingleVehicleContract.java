package contracts;

import objects.Vehicle;
import objects.Person;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public class SingleVehicleContract extends AbstractVehicleContract {
    final private Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer,
                                 Person beneficiary, Person policyHolder,
                                 ContractPaymentData contractPaymentData, int coverageAmount,
                                 Vehicle vehicleToInsure)
    {
        super(contractNumber, insurer, beneficiary, policyHolder, contractPaymentData, coverageAmount);

        if (vehicleToInsure == null) {
            throw new IllegalArgumentException("Vehicle To Insure can't be null");
        }

        if (contractPaymentData == null) {
            throw new IllegalArgumentException("Contract Payment Data can't be null");
        }

        this.insuredVehicle = vehicleToInsure;
    }

    public Vehicle getInsuredVehicle(){
        return insuredVehicle;
    }
}
