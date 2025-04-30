package objects;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import contracts.AbstractContract;

public class Person {
    final private String id;
    final private LegalForm legalForm;
    private int paidOutAmount;
    final private Set<AbstractContract> contracts;

    public Person(String id){
        if(id == null || id.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (isValidBirthNumber(id)) {
            this.legalForm = LegalForm.NATURAL;
        } else if (isValidRegistrationNumber(id)) {
            this.legalForm = LegalForm.LEGAL;
        } else {
            throw new IllegalArgumentException();
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
        if (birthNumber == null) {
            return false;
        }

        int length = birthNumber.length();
        if (length != 9 && length != 10) {
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

        if (length == 9) {
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

        if(length == 10) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                int digit = birthNumber.charAt(i) - '0';
                //sum += pow(-1, i) * digit;
                sum += ((i % 2 == 0) ? 1 : -1) * digit;
                //sum += (i % 2 == 0 ? 1 : -1) * digit;
            }

            return sum % 11 == 0;
        }

        return true;
    }

    // IČO
    public static boolean isValidRegistrationNumber(String registrationNumber){
        if (registrationNumber == null) {
            return false;
        }

        int length = registrationNumber.length();
        if (length != 6 && length != 8) {
            return false;
        }

        return isStringNumber(registrationNumber);
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
            throw new IllegalArgumentException();
        }
        contracts.add(contract);
    }

    public void payout(int paidOutAmount){
        if (paidOutAmount <= 0) {
            throw new IllegalArgumentException();
        }
        this.paidOutAmount += paidOutAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}