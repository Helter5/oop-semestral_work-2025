package objects;


import java.util.Set;
import contracts.AbstractContract;

public class Person {
    final private String id;
    final private LegalForm legalForm;
    private int paidOutAmount;
    final private Set<AbstractContract> contracts;

    public Person(String id){

    }

    public static boolean isValidBirthNumber(String birthNumber){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public static boolean isValidRegistrationNumber(String registrationNumber){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public String getId(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public int getPaidOutAmount(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public LegalForm getLegalForm(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public Set<AbstractContract> getContracts(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public void addContract(AbstractContract contract){
    }

    public void payout(int paidOutAmount){
    }
}
