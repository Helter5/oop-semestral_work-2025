package company;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import contracts.*;
import objects.Person;
import payment.ContractPaymentData;
import payment.PaymentHandler;
import payment.PremiumPaymentFrequency;
import objects.Vehicle;

public class InsuranceCompany {
    final private Set<AbstractContract> contracts;
    final private PaymentHandler handler;
    private LocalDateTime currentTime;

    public InsuranceCompany(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException();
        }

        this.currentTime = currentTime;
        this.contracts = new LinkedHashSet<>();
        this.handler = new PaymentHandler(this);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException();
        }
        this.currentTime = currentTime;
    }

    public Set<AbstractContract> getContracts() {
        return contracts;
    }

    public PaymentHandler getHandler() {
        return handler;
    }

    public static boolean contractAlreadyExists(Set<AbstractContract> contracts, String contractNumber) {
        for (AbstractContract contract : contracts) {
            if (contract.getContractNumber().equals(contractNumber)) {
                return true;
            }
        }
        return false;
    }
        // zaokruhlit dole
    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary,
                                               Person policyHolder, int proposedPremium,
                                               PremiumPaymentFrequency proposedPaymentFrequency,
                                               Vehicle vehicleToInsure) {

        if (vehicleToInsure == null || proposedPaymentFrequency == null || proposedPremium <= 0) {
            throw new IllegalArgumentException();
        }

        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
        }

        double totalPremiumValue = proposedPremium * (12.0 / proposedPaymentFrequency.getValueInMonths());
        if ( totalPremiumValue < vehicleToInsure.getOriginalValue() * 0.02) {
            throw new IllegalArgumentException();
        }

        ContractPaymentData newContractPaymentData = new ContractPaymentData(
                proposedPremium, proposedPaymentFrequency, currentTime, proposedPremium
        );

        newContractPaymentData.updateNextPaymentTime();

        SingleVehicleContract newContract = new SingleVehicleContract(
                contractNumber, this, beneficiary, policyHolder,
                newContractPaymentData, vehicleToInsure.getOriginalValue() / 2, vehicleToInsure
        );

        chargePremiumOnContract(newContract);
        contracts.add(newContract);
        policyHolder.addContract(newContract);
        return newContract;
    }

    public TravelContract insurePersons(String contractNumber, Person policyHolder, int proposedPremium,
                                        PremiumPaymentFrequency proposedPaymentFrequency, Set<Person> personsToInsure) {

        if (personsToInsure == null || personsToInsure.isEmpty() || proposedPaymentFrequency == null || proposedPremium <= 0) {
            throw new IllegalArgumentException();
        }

        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException();
        }

        double totalPremiumValue = proposedPremium * (12.0 / proposedPaymentFrequency.getValueInMonths());
        if (totalPremiumValue < 5 * personsToInsure.size()) {
            throw new IllegalArgumentException();
        }

        ContractPaymentData newContractPaymentData = new ContractPaymentData(
                proposedPremium, proposedPaymentFrequency, currentTime, 0
        );

        TravelContract newContract = new TravelContract(
                contractNumber, this, policyHolder,
                newContractPaymentData, 10 * personsToInsure.size(), personsToInsure
        );

        chargePremiumOnContract(newContract);
        contracts.add(newContract);
        policyHolder.addContract(newContract);
        return newContract;
    }

    public MasterVehicleContract createMasterVehicleContract(String contractNumber, Person beneficiary,
                                                             Person policyHolder) {
        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException();
        }

        MasterVehicleContract newContract = new MasterVehicleContract(
                contractNumber, this, beneficiary, policyHolder
        );

        contracts.add(newContract);
        policyHolder.addContract(newContract);
        return newContract;
    }

    public void moveSingleVehicleContractToMasterVehicleContract(MasterVehicleContract masterVehicleContract,
                                                                 SingleVehicleContract singleVehicleContract) {
        if (masterVehicleContract == null || singleVehicleContract == null) {
            throw new IllegalArgumentException();
        }

        if (!masterVehicleContract.isActive() || !singleVehicleContract.isActive()) {
            throw new InvalidContractException("contract exception");
        }

        if (!masterVehicleContract.getInsurer().equals(singleVehicleContract.getInsurer())) {
            throw new InvalidContractException("contract exception");
        }

        if (!masterVehicleContract.getPolicyHolder().equals(singleVehicleContract.getPolicyHolder())) {
            throw new InvalidContractException("contract exception");
        }

        contracts.remove(singleVehicleContract);
        Person policyHolder = singleVehicleContract.getPolicyHolder();
        policyHolder.getContracts().remove(singleVehicleContract);
        masterVehicleContract.requestAdditionOfChildContract(singleVehicleContract);

    }

    public void chargePremiumsOnContracts() {
        for (AbstractContract contract : contracts) {
            if (contract.isActive()) {
                contract.updateBalance();
            }
        }
    }

    public void chargePremiumOnContract(MasterVehicleContract contract) {
        for (AbstractVehicleContract childContract : contract.getChildContracts()) {
            chargePremiumOnContract(childContract);
        }
    }

    public void chargePremiumOnContract(AbstractContract contract) {
        ContractPaymentData paymentData = contract.getContractPaymentData();
        while (paymentData.getNextPaymentTime().isBefore(currentTime) || paymentData.getNextPaymentTime().isEqual(currentTime)) {
            paymentData.setOutstandingBalance(paymentData.getOutstandingBalance() + paymentData.getPremium());
            paymentData.updateNextPaymentTime();
        }
    }

    public void processClaim(TravelContract travelContract, Set<Person> affectedPersons) {
        if (travelContract == null) {
            throw new IllegalArgumentException();
        }

        if (affectedPersons == null || affectedPersons.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!travelContract.getInsuredPersons().containsAll(affectedPersons)) {
            throw new IllegalArgumentException();
        }

        if (!travelContract.isActive()) {
            throw new InvalidContractException("contract exception");
        }

        int totalClaimAmount =  travelContract.getCoverageAmount() / affectedPersons.size();
        for (Person person : affectedPersons) {
            person.payout(totalClaimAmount);
        }

        travelContract.setInactive();
    }

    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages) {
        if (singleVehicleContract == null) {
            throw new IllegalArgumentException();
        }

        if (expectedDamages <= 0) {
            throw new IllegalArgumentException();
        }

        if (!singleVehicleContract.isActive()) {
            throw new InvalidContractException("contract exception");
        }

        int payoutAmount = singleVehicleContract.getCoverageAmount();
        Person payoutTarget = singleVehicleContract.getBeneficiary();

        if (payoutTarget == null) {
            payoutTarget = singleVehicleContract.getPolicyHolder();
        }
        payoutTarget.payout(payoutAmount);

        if (expectedDamages >= 0.7 * singleVehicleContract.getInsuredVehicle().getOriginalValue()) {
            singleVehicleContract.setInactive();
        }
    }
}