package objects;

public class Person {
    private String id;
    private LegalForm legalForm;
    private int paidOutAmount;
    private Set<AbstractContract> contracts;

    public Person(String id){

    };

    public static boolean isValidBirthNumber(String birthNumber);

    public static boolean isValidRegistrationNumber(String registrationNumber);

    public String getId();

    public int getPaidOutAmount();

    public LegalForm getLegalForm();

    public Set<AbstractContract> getContracts();

    public void addContract(AbstractContract contract);

    public void payout(int paidOutAmount);
}
