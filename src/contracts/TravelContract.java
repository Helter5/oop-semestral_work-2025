package contracts;

import objects.Person;
import java.util.Set;
import company.InsuranceCompany;
import payment.ContractPaymentData;

import static objects.Person.isValidBirthNumber;

public class TravelContract extends AbstractContract {
    final private Set<Person> insuredPersons;

    public TravelContract(String contractNumber, InsuranceCompany insurer,
                          Person policyHolder, ContractPaymentData contractPaymentData,
                          int coverageAmount, Set<Person> personsToInsure)
    {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        if (personsToInsure == null || personsToInsure.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (contractPaymentData == null) {
            throw new IllegalArgumentException();
        }

        for (Person person : personsToInsure) {
            if(!isValidBirthNumber(person.getId())) {
                throw new IllegalArgumentException();
            }
        }

        this.insuredPersons = personsToInsure;

    }

    public Set<Person> getInsuredPersons(){
        return insuredPersons;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setInactive() {
        this.isActive = false;
    }
}
