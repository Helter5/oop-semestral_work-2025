package objects;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import contracts.AbstractContract;
import static java.lang.Math.pow;

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

    public static boolean isStringNumber(String string) {
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidBirthNumber(String birthNumber){
        int birthNumberLength = birthNumber.length();

        if (birthNumber == null) {
            return false;
        }
        if (birthNumberLength != 9 || birthNumberLength != 10) {
            return false;
        }

        if(!isStringNumber(birthNumber)) {
            return false;
        }

        int year = Integer.parseInt(birthNumber.substring(0, 2));
        int month = Integer.parseInt(birthNumber.substring(2, 4));
        int day = Integer.parseInt(birthNumber.substring(4, 6));

        if (!((month >= 1 && month <= 12) || (month >= 51 && month <= 62))) {
            return false;
        }

        if (month > 50) {
            month -= 50;
        }

        if (birthNumberLength == 9) {
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

        if(birthNumberLength == 10) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                int digit = birthNumber.charAt(i) - '0';
                sum += pow(-1, i) * digit;
            }

            if (sum % 11 != 0) {
                return false;
            }
        }

        return true;
    }

    // IČO
    public static boolean isValidRegistrationNumber(String registrationNumber){
        int registrationNumberLength = registrationNumber.length();

        if (registrationNumber == null) {
            return false;
        }

        if (registrationNumberLength != 6 || registrationNumberLength != 8) {
            return false;
        }

        if(!isStringNumber(registrationNumber)) {
            return false;
        }

        return true;
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
        if (contract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }
        contracts.add(contract);
    }

    public void payout(int paidOutAmount){
        if (paidOutAmount < 0) {
            throw new IllegalArgumentException("Value can't be negative");
        }
        this.paidOutAmount += paidOutAmount;
    }
}
