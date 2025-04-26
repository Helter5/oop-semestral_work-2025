package company;

import java.time.LocalDateTime;
import java.util.Set;
import contracts.AbstractContract;
import contracts.SingleVehicleContract;
import objects.Person;
import payment.PaymentHandler;
import payment.PremiumPaymentFrequency;
import objects.Vehicle;
import contracts.TravelContract;
import contracts.MasterVehicleContract;

public class InsuranceCompany {
    final private Set<AbstractContract> contracts;
    final private PaymentHandler handler;
    private LocalDateTime currentTime;

    public InsuranceCompany(LocalDateTime currentTime){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public LocalDateTime getCurrentTime(){
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime){

    }

    public Set<AbstractContract> getContracts(){
        return contracts;
    }

    public PaymentHandler getHandler(){
        return handler;
    }

    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary,
                                               Person policyHolder, int proposedPremium,
                                               PremiumPaymentFrequency proposedPaymentFrequency,
                                               Vehicle vehicleToInsure){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public TravelContract insurePersons(String contractNumber, Person policyHolder, int proposedPremium,
                                        PremiumPaymentFrequency proposedPaymentFrequency, Set<Person> personsToInsure){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public MasterVehicleContract createMasterVehicleContract(String contractNumber, Person beneficiary,
                                                             Person policyHolder){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void moveSingleVehicleContractToMasterVehicleContract(MasterVehicleContract masterVehicleContract,
                                                                 SingleVehicleContract singleVehicleContract){
        // to do
    }

    public void chargePremiumsOnContracts(){
        // to do
    }

    public void chargePremiumOnContract(MasterVehicleContract contract){
        // to do
    }

    public void chargePremiumOnContract(AbstractContract contract){
        // to do
    }

    public void processClaim(TravelContract travelContract, Set<Person> affectedPersons){
        // to do
    }

    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages){
        // to do
    }
}
