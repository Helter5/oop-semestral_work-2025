package company;

public class InsuranceCompany {
    private Set<AbstractContract> contracts;
    private PaymentHandler handler;
    private LocalDateTime currentTime;

    public InsuranceCompany(LocalDateTime currentTime){

    };

    public LocalDateTime getCurrentTime();

    public void setCurrentTime(LocalDateTime currentTime);

    public Set<AbstractContract> getContracts();

    public PaymentHandler getHandler();

    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary,
                                               Person policyHolder, int proposedPremium,
                                               PremiumPaymentFrequency proposedPaymentFrequency,
                                               Vehicle vehicleToInsure);

    public TravelContract insurePersons(String contractNumber, Person policyHolder, int proposedPremium,
                         PremiumPaymentFrequency proposedPaymentFrequency, Set<Person> personsToInsure);

    public MasterVehicleContract createMasterVehicleContract(String contractNumber, Person beneficiary,
                                                             Person policyHolder);

    public void moveSingleVehicleContractToMasterVehicleContract(MasterVehicleContract masterVehicleContract,
                                                                 SingleVehicleContract singleVehicleContract);

    public void chargePremiumsOnContracts();

    public void chargePremiumOnContract(MasterVehicleContract contract);

    public void chargePremiumOnContract(AbstractContract contract);

    public void processClaim(TravelContract travelContract, Set<Person> affectedPersons);

    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages);
}
