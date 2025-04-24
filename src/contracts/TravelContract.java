package contracts;

import objects.Person;
import java.util.Set;
import company.InsuranceCompany;
import payment.ContractPaymentData;

public class TravelContract extends AbstractContract {
    private Set<Person> insuredPersons;

    public TravelContract(String contractNumber, InsuranceCompany insurer,
                          Person policyHolder, ContractPaymentData contractPaymentData,
                          int coverageAmount, Set<Person> personsTolnsure) {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
    }

    public Set<Person> getInsuredPersons(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }
}
