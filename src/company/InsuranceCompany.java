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
    private final Set<AbstractContract> contracts;
    private final PaymentHandler handler;
    private LocalDateTime currentTime;

    public InsuranceCompany(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException("Current time cannot be null");
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
            throw new IllegalArgumentException("Current time cannot be null");
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

    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary,
                                               Person policyHolder, int proposedPremium,
                                               PremiumPaymentFrequency proposedPaymentFrequency,
                                               Vehicle vehicleToInsure) {

        if (vehicleToInsure == null) {
            throw new IllegalArgumentException("Vehicle to insure cannot be null");
        }

        if (proposedPaymentFrequency == null) {
            throw new IllegalArgumentException("Payment frequency cannot be null");
        }

        if (proposedPremium <= 0) {
            throw new IllegalArgumentException("Proposed premium must be greater than zero");
        }

        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
        }

        int totalPremiumValue = proposedPremium * (12 / proposedPaymentFrequency.getValueInMonths());
        int twoPercentOfOriginalValue = (int) (vehicleToInsure.getOriginalValue() * 0.02);

        if (totalPremiumValue < twoPercentOfOriginalValue) {
            throw new IllegalArgumentException("Total premium value must be at least 2% of the vehicle's original value");
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

        if (personsToInsure == null || personsToInsure.isEmpty()) {
            throw new IllegalArgumentException("Persons to insure cannot be null or empty");
        }

        if (proposedPaymentFrequency == null) {
            throw new IllegalArgumentException("Payment frequency cannot be null");
        }

        if (proposedPremium <= 0) {
            throw new IllegalArgumentException("Proposed premium must be greater than zero");
        }

        if (contractAlreadyExists(contracts, contractNumber)) {
            throw new IllegalArgumentException("Contract with this number already exists");
        }

        int totalPremiumValue = proposedPremium * (12 / proposedPaymentFrequency.getValueInMonths());
        if (totalPremiumValue < (5 * personsToInsure.size())) {
            throw new IllegalArgumentException("Total premium value must be at least 5 times the number of persons to insure");
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
            throw new IllegalArgumentException("Master vehicle contract and single vehicle contract cannot be null");
        }

        if (!masterVehicleContract.isActive() || !singleVehicleContract.isActive()) {
            throw new InvalidContractException("One of the contracts is not active");
        }

        if (masterVehicleContract.getInsurer() != this || singleVehicleContract.getInsurer() != this) {
            throw new InvalidContractException("One of the contracts does not belong to this insurer");
        }

        if (!masterVehicleContract.getPolicyHolder().equals(singleVehicleContract.getPolicyHolder())) {
            throw new InvalidContractException("The policy holders of the contracts do not match");
        }

        if (!this.contracts.contains(singleVehicleContract) || !this.contracts.contains(masterVehicleContract)) {
            throw new InvalidContractException("One of the contracts does not belong to this insurer");
        }

        if (!singleVehicleContract.getPolicyHolder().getContracts().contains(singleVehicleContract) ||
                !masterVehicleContract.getPolicyHolder().getContracts().contains(masterVehicleContract)) {
            throw new InvalidContractException("One of the contracts does not belong to this insurer");
        }

        contracts.remove(singleVehicleContract);
        Person policyHolder = singleVehicleContract.getPolicyHolder();
        policyHolder.getContracts().remove(singleVehicleContract);
        masterVehicleContract.getChildContracts().add(singleVehicleContract);
    }

    public void chargePremiumsOnContracts() {
        for (AbstractContract contract : contracts) {
            if (contract.isActive()) {
                contract.updateBalance();
            }
        }
    }

    public void chargePremiumOnContract(MasterVehicleContract contract) {
        for (SingleVehicleContract childContract : contract.getChildContracts()) {
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
            throw new IllegalArgumentException("Travel contract cannot be null");
        }

        if (affectedPersons == null || affectedPersons.isEmpty()) {
            throw new IllegalArgumentException("Affected persons cannot be null or empty");
        }

        if (!travelContract.getInsuredPersons().containsAll(affectedPersons)) {
            throw new IllegalArgumentException("Some persons are not insured under this contract");
        }

        if (!travelContract.isActive()) {
            throw new InvalidContractException("Travel contract is not active");
        }

        int totalClaimAmount = travelContract.getCoverageAmount() / affectedPersons.size();
        for (Person person : affectedPersons) {
            person.payout(totalClaimAmount);
        }

        travelContract.setInactive();
    }

    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages) {
        if (singleVehicleContract == null) {
            throw new IllegalArgumentException("Single vehicle contract cannot be null");
        }

        if (expectedDamages <= 0) {
            throw new IllegalArgumentException("Expected damages must be greater than zero");
        }

        if (!singleVehicleContract.isActive()) {
            throw new InvalidContractException("Single vehicle contract is not active");
        }

        int payoutAmount = singleVehicleContract.getCoverageAmount();
        Person payoutTarget = singleVehicleContract.getBeneficiary();

        if (payoutTarget == null) {
            payoutTarget = singleVehicleContract.getPolicyHolder();
        }
        payoutTarget.payout(payoutAmount);

        if (expectedDamages >= (int) (0.7 * singleVehicleContract.getInsuredVehicle().getOriginalValue())) {
            singleVehicleContract.setInactive();
        }
    }
}