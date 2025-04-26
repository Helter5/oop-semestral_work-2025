package objects;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import contracts.AbstractContract;

public class Person {
    final private String id;
    final private LegalForm legalForm;
    private int paidOutAmount;
    final private Set<AbstractContract> contracts;

    public Person(String id){
        if(id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID can't be null or empty string");
        }

        if(isValidBirthNumber(id)) {
            this.legalForm = LegalForm.NATURAL;
        } else if (isValidRegistrationNumber(id)) {
            this.legalForm = LegalForm.LEGAL;
        } else {
            throw new IllegalArgumentException("ID is invalid (it has to be registration number or birth number)");
        }

        this.id = id;
        this.paidOutAmount = 0;
        this.contracts = new LinkedHashSet<>();
    }

    // Rodne číslo
    public static boolean isValidBirthNumber(String birthNumber){
        int birthNumberLength = birthNumber.length();

        if (birthNumber == null) {
            return false;
        }
        if (birthNumberLength != 9 || birthNumberLength != 10) {
            return false;
        }

        for(int i = 0; i < birthNumberLength; i++) {
            char c = birthNumber.charAt(i);
            if(!Character.isDigit(c)) {
                return false;
            }
        }

        int year = Integer.parseInt(birthNumber.substring(0, 2));
        int month = Integer.parseInt(birthNumber.substring(2, 4));
        int day = Integer.parseInt(birthNumber.substring(4, 6));

        if (!((month >= 1 && month <= 12) || (month >= 51 && month <= 62))) {
            return false;
        }

        if(month > 50) {
            month -= 50;
        }

        if(birthNumberLength == 9) {
            if(year > 53) {
                return false;
            }
            year += 1900;
        } else {
            year += (year >= 54) ? 1900 : 2000;
        }

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e){
            return false;
        }



    }

    // IČO
    public static boolean isValidRegistrationNumber(String registrationNumber){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public String getId(){
        return id;
    }

    public int getPaidOutAmount(){
        return paidOutAmount;
    }

    public LegalForm getLegalForm(){
        return legalForm;
    }

    public Set<AbstractContract> getContracts(){
        return contracts;
    }

    public void addContract(AbstractContract contract){
    }

    public void payout(int paidOutAmount){
    }
}
