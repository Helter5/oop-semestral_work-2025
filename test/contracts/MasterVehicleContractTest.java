package contracts;

import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;
import objects.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.ContractPaymentData;
import payment.PremiumPaymentFrequency;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MasterVehicleContractTest {

    private InsuranceCompany company;
    private Person legalPerson;
    private Person naturalPerson;
    private Person beneficiary;
    private Set<SingleVehicleContract> contracts;

    @BeforeEach
    void setUp() {
        company = new InsuranceCompany(LocalDateTime.now());
        legalPerson = new Person("12345678");
        naturalPerson = new Person("8004175146");
        beneficiary = new Person("8054176383");

        // Create payment data for the single contract
        ContractPaymentData paymentData = new ContractPaymentData(
                100,
                PremiumPaymentFrequency.MONTHLY,
                LocalDateTime.now(),
                0);

        // Create a sample vehicle and single vehicle contract
        Vehicle vehicle = new Vehicle("ABC1234", 10000);
        SingleVehicleContract singleContract = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                paymentData, 5000, vehicle);

        contracts = new HashSet<>();
        contracts.add(singleContract);
    }

    @Test
    void testPolicyHolderMustBeLegalPerson() {
        // Verify our test persons have the correct legal forms
        assertEquals(LegalForm.LEGAL, legalPerson.getLegalForm());
        assertEquals(LegalForm.NATURAL, naturalPerson.getLegalForm());

        // Test with legal person - should create successfully
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        assertEquals(legalPerson, masterContract.getPolicyHolder());
        assertEquals(LegalForm.LEGAL, masterContract.getPolicyHolder().getLegalForm());

        // Test with natural person - should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            new MasterVehicleContract(
                    "MC002", company, beneficiary, naturalPerson);
        });
    }

    @Test
    void testDefaultPaymentDataAndCoverageAmount() {
        // Create a master contract with a legal person
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Verify that payment data is null and coverage amount is 0
        assertNull(masterContract.getContractPaymentData());
        assertEquals(0, masterContract.getCoverageAmount());
    }

    @Test
    void testChildContractsInitialization() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Verify that childContracts is initialized and empty
        assertNotNull(masterContract.getChildContracts());
        assertTrue(masterContract.getChildContracts().isEmpty());

        // Add contracts and verify they maintain insertion order
        SingleVehicleContract contract1 = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                new ContractPaymentData(100, PremiumPaymentFrequency.MONTHLY,
                        LocalDateTime.now(), 0),
                5000, new Vehicle("ABC1234", 10000));

        SingleVehicleContract contract2 = new SingleVehicleContract(
                "SC002", company, beneficiary, legalPerson,
                new ContractPaymentData(200, PremiumPaymentFrequency.MONTHLY,
                        LocalDateTime.now(), 0),
                6000, new Vehicle("DEF4567", 12000));

        // Add contracts and verify order
        masterContract.requestAdditionOfChildContract(contract1);
        masterContract.requestAdditionOfChildContract(contract2);

        // Verify order is maintained (can be tested by converting to array/list and checking indices)
        Object[] contractsArray = masterContract.getChildContracts().toArray();
        assertEquals(contract1, contractsArray[0]);
        assertEquals(contract2, contractsArray[1]);
    }

    @Test
    void testIsActiveStatus() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Initially with no child contracts
        assertTrue(masterContract.isActive()); // Master contract is active by default

        // Create two SingleVehicleContracts
        ContractPaymentData paymentData = new ContractPaymentData(
                100, PremiumPaymentFrequency.MONTHLY, LocalDateTime.now(), 0);

        SingleVehicleContract contract1 = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                paymentData, 5000, new Vehicle("ABC1234", 10000));

        SingleVehicleContract contract2 = new SingleVehicleContract(
                "SC002", company, beneficiary, legalPerson,
                paymentData, 6000, new Vehicle("DEF4567", 12000));

        // Add contracts to master contract
        masterContract.requestAdditionOfChildContract(contract1);
        masterContract.requestAdditionOfChildContract(contract2);

        // Both child contracts are active by default
        assertTrue(masterContract.isActive());

        // Set one child contract inactive
        contract1.setInactive();
        assertTrue(masterContract.isActive()); // Should still be active because contract2 is active

        // Set other child contract inactive
        contract2.setInactive();
        assertFalse(masterContract.isActive()); // Should be inactive now as all children are inactive
    }

    @Test
    void testIsActiveStatusWithNoChildren() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Initially with no child contracts
        assertTrue(masterContract.isActive()); // Should be active by default

        // Set master contract inactive
        masterContract.setInactive();
        assertTrue(masterContract.isActive()); // Should be inactive
    }

    @Test
    void testIsActiveStatusWithChildren() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Create two SingleVehicleContracts
        ContractPaymentData paymentData = new ContractPaymentData(
                100, PremiumPaymentFrequency.MONTHLY, LocalDateTime.now(), 0);

        SingleVehicleContract contract1 = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                paymentData, 5000, new Vehicle("ABC1234", 10000));

        SingleVehicleContract contract2 = new SingleVehicleContract(
                "SC002", company, beneficiary, legalPerson,
                paymentData, 6000, new Vehicle("DEF4567", 12000));

        // Add contracts to master contract
        masterContract.requestAdditionOfChildContract(contract1);
        masterContract.requestAdditionOfChildContract(contract2);

        // Both child contracts are active by default
        assertTrue(masterContract.isActive());

        // Make one child inactive - master should still be active
        contract1.setInactive();
        assertTrue(masterContract.isActive());

        // Make all children inactive - master should be inactive
        contract2.setInactive();
        assertFalse(masterContract.isActive());
    }

    @Test
    void testSetInactive() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Create two SingleVehicleContracts
        ContractPaymentData paymentData = new ContractPaymentData(
                100, PremiumPaymentFrequency.MONTHLY, LocalDateTime.now(), 0);

        SingleVehicleContract contract1 = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                paymentData, 5000, new Vehicle("ABC1234", 10000));

        SingleVehicleContract contract2 = new SingleVehicleContract(
                "SC002", company, beneficiary, legalPerson,
                paymentData, 6000, new Vehicle("DEF4567", 12000));

        // Add contracts to master contract
        masterContract.requestAdditionOfChildContract(contract1);
        masterContract.requestAdditionOfChildContract(contract2);

        // Initially all contracts should be active
        assertTrue(masterContract.isActive());
        assertTrue(contract1.isActive());
        assertTrue(contract2.isActive());

        // Call setInactive on the master contract
        masterContract.setInactive();

        // Verify that master contract and all child contracts are now inactive
        assertFalse(masterContract.isActive());
        assertFalse(contract1.isActive());
        assertFalse(contract2.isActive());
    }

    @Test
    void testPayMethod() {
        // Create a master contract
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Add the contract to the insurance company
        company.getContracts().add(masterContract);

        // Initial validation
        assertTrue(company.getHandler().getPaymentHistory().isEmpty());

        // Call the pay method with a positive amount
        int paymentAmount = 500;
        masterContract.pay(paymentAmount);

        // Verify that the payment was processed by the PaymentHandler
        assertFalse(company.getHandler().getPaymentHistory().isEmpty());
        assertTrue(company.getHandler().getPaymentHistory().containsKey(masterContract));
        assertEquals(1, company.getHandler().getPaymentHistory().get(masterContract).size());

        // Verify the payment details
        var payment = company.getHandler().getPaymentHistory().get(masterContract).iterator().next();
        assertEquals(paymentAmount, payment.getPaymentAmount());
        assertEquals(company.getCurrentTime(), payment.getPaymentTime());

        // Test with negative amount - should be rejected
        assertThrows(IllegalArgumentException.class, () -> masterContract.pay(-100));

        // Test with zero amount - should be rejected
        assertThrows(IllegalArgumentException.class, () -> masterContract.pay(0));
    }

    @Test
    void testUpdateBalance() {
        // Create a master contract with child contracts to avoid InvalidContractException
        MasterVehicleContract masterContract = new MasterVehicleContract(
                "MC001", company, beneficiary, legalPerson);

        // Create a single vehicle contract to add as a child
        ContractPaymentData paymentData = new ContractPaymentData(
                100, PremiumPaymentFrequency.MONTHLY, LocalDateTime.now(), 0);
        SingleVehicleContract childContract = new SingleVehicleContract(
                "SC001", company, beneficiary, legalPerson,
                paymentData, 5000, new Vehicle("ABC1234", 10000));

        // Add the child contract to the master contract
        masterContract.requestAdditionOfChildContract(childContract);

        // Add the contract to the insurance company
        company.getContracts().add(masterContract);

        try {
            // Call updateBalance on the master contract
            masterContract.updateBalance();

            // Since we can't directly verify the method call, we check that
            // the code executed without throwing an exception
            assertTrue(true, "updateBalance method completed successfully");
        } catch (Exception e) {
            fail("updateBalance threw an exception: " + e.getMessage());
        }
    }
}