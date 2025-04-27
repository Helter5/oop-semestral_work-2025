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
            throw new IllegalArgumentException("Current Time can't be null");
        }

        this.currentTime = currentTime;
        this.contracts = new LinkedHashSet<>();
        this.handler = new PaymentHandler(this);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
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

    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary,
                                               Person policyHolder, int proposedPremium,
                                               PremiumPaymentFrequency proposedPaymentFrequency,
                                               Vehicle vehicleToInsure) {
        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
        }

        if (proposedPremium < 0.02 * vehicleToInsure.getOriginalValue()) {
            throw new IllegalArgumentException("Proposed premium is too low");
        }

        ContractPaymentData newContractPaymentData = new ContractPaymentData(
                proposedPremium, proposedPaymentFrequency, currentTime, 0
        );

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
        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
        }

        if (proposedPremium < 5 * personsToInsure.size()) {
            throw new IllegalArgumentException("Proposed premium is too low");
        }

        ContractPaymentData newContractPaymentData = new ContractPaymentData(
                proposedPremium, proposedPaymentFrequency, currentTime, 0
        );

        TravelContract newContract = new TravelContract(
                contractNumber, this, policyHolder,
                newContractPaymentData, 10 * proposedPremium, personsToInsure
        );

        chargePremiumOnContract(newContract);
        contracts.add(newContract);
        policyHolder.addContract(newContract);
        return newContract;
    }

    public MasterVehicleContract createMasterVehicleContract(String contractNumber, Person beneficiary,
                                                             Person policyHolder) {
        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
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
            throw new IllegalArgumentException("Contracts can't be null");
        }

        if (!masterVehicleContract.isActive() || !singleVehicleContract.isActive()) {
            throw new InvalidContractException("Contracts must be active");
        }

        if (!masterVehicleContract.getInsurer().equals(singleVehicleContract.getInsurer())) {
            throw new InvalidContractException("Contracts must be from the same insurer");
        }

        if (!masterVehicleContract.getPolicyHolder().equals(singleVehicleContract.getPolicyHolder())) {
            throw new InvalidContractException("Contracts must have the same policy holder");
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
        for (AbstractContract childContract : contract.getChildContracts()) {
            chargePremiumOnContract(childContract);
        }
    }

    public void chargePremiumOnContract(AbstractContract contract) {
        ContractPaymentData paymentData = contract.getContractPaymentData();
        while (!paymentData.getNextPaymentTime().isBefore(currentTime)) {
            paymentData.setOutstandingBalance(paymentData.getOutstandingBalance() + paymentData.getPremium());
            paymentData.updateNextPaymentTime();
        }
    }

    public void processClaim(TravelContract travelContract, Set<Person> affectedPersons) {
        if (travelContract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }

        if (affectedPersons == null || affectedPersons.isEmpty()) {
            throw new IllegalArgumentException("Affected persons can't be null or empty");
        }

        if (!travelContract.getInsuredPersons().containsAll(affectedPersons)) {
            throw new IllegalArgumentException("Affected persons must be insured");
        }

        if (!travelContract.isActive()) {
            throw new InvalidContractException("Contract must be active");
        }

        int totalClaimAmount =  travelContract.getCoverageAmount() / affectedPersons.size();
        for (Person person : affectedPersons) {
            person.payout(totalClaimAmount);
        }

        travelContract.setInactive();
    }

    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages) {
        if (singleVehicleContract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }

        if (expectedDamages < 0) {
            throw new IllegalArgumentException("Expected damages can't be negative");
        }

        if (!singleVehicleContract.isActive()) {
            throw new InvalidContractException("Contract must be active");
        }

        if (singleVehicleContract.getInsurer() != null) {
            singleVehicleContract.pay(expectedDamages);
        } else {
            Person policyHolder = singleVehicleContract.getPolicyHolder();
            policyHolder.payout(expectedDamages);
        }

        if (expectedDamages >= 0.7 * singleVehicleContract.getInsuredVehicle().getOriginalValue()) {
            singleVehicleContract.setInactive();
        }
    }
}