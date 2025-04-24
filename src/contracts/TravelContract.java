package contracts;

public class TravelContract {
    private Set<Person> insuredPersons;

    public TravelContract(String contactNumber, InsuranceCompany insurer,
                          Person policyHolder, ContractPaymentData contractPaymentData,
                          int coverageAmount, Set<Person> personsTolnsure) {

    };

    public Set<Person> getInsuredPersons();
}
