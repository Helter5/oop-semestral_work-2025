package contracts;

import objects.Person;
import java.util.Set;
import company.InsuranceCompany;
import payment.ContractPaymentData;
import objects.LegalForm;

public class TravelContract extends AbstractContract {
    private final Set<Person> insuredPersons;

    public TravelContract(String contractNumber, InsuranceCompany insurer,
                          Person policyHolder, ContractPaymentData contractPaymentData,
                          int coverageAmount, Set<Person> personsToInsure)
    {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        if (personsToInsure == null || personsToInsure.isEmpty()) {
            throw new IllegalArgumentException("Persons to insure cannot be null or empty");
        }

        if (contractPaymentData == null) {
            throw new IllegalArgumentException("Contract payment data cannot be null");
        }

        //for (Person person : personsToInsure) {
            //if(!isValidBirthNumber(person.getId())) {
         //       throw new IllegalArgumentException();
         //   }
        //}

        for (Person person : personsToInsure) {
            if (person.getLegalForm() != LegalForm.NATURAL) {
                throw new IllegalArgumentException("All insured persons must be natural persons");
            }
        }

        //this.insuredPersons = personsToInsure;
        this.insuredPersons = personsToInsure;

    }

    public Set<Person> getInsuredPersons(){
        return insuredPersons;
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
