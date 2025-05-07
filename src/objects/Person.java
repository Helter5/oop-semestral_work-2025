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
    final private Set<AbstractContract> contracts;
    private int paidOutAmount;

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
        if (string == null) return false;

        for (int i=0; i<string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidBirthNumber(String birthNumber) {
        if (!isStringNumber(birthNumber)) return false;

        int length = birthNumber.length();
        if (length != 9 && length != 10) return false;

        int year = Integer.parseInt(birthNumber.substring(0, 2));
        int month = Integer.parseInt(birthNumber.substring(2, 4));
        int day = Integer.parseInt(birthNumber.substring(4, 6));

        if (!((month >= 1 && month <= 12) || (month >= 51 && month <= 62))) return false;

        if (month > 50) month -= 50;

        boolean isValid = !(length == 9 && year > 53);
        if (!isValid) return false;

        year += (length == 9) ? 1900 : (year >= 54 ? 1900 : 2000);

        if(length == 10) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                int digit = birthNumber.charAt(i) - '0';
                sum += ((i % 2 == 0) ? 1 : -1) * digit;
            }
            return sum % 11 == 0;
        }

        return isValidDate(day, month, year);
    }

    // IČO
    public static boolean isValidRegistrationNumber(String registrationNumber){
        if (registrationNumber == null) return false;

        int length = registrationNumber.length();
        if (length != 6 && length != 8) return false;

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
        if (contract == null) throw new IllegalArgumentException();
        contracts.add(contract);
    }

    public void payout(int paidOutAmount){
        if (paidOutAmount <= 0) throw new IllegalArgumentException();
        this.paidOutAmount += paidOutAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}