package contracts;

import company.InsuranceCompany;
import objects.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import payment.ContractPaymentData;
import payment.PremiumPaymentFrequency;
import shared.TestContract2;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractContractTests2 {
    private String contractNumber;
    private InsuranceCompany insurer;
    private Person policyHolder;
    private ContractPaymentData contractPaymentData;
    private int coverageAmount;


    @BeforeEach
    public void setUp() {
        contractNumber = "123456";
        insurer = new InsuranceCompany(LocalDateTime.now());
        policyHolder = new Person("123456");
        contractPaymentData = new ContractPaymentData(5, PremiumPaymentFrequency.MONTHLY,
                LocalDateTime.now().plusMonths(1), 0);
        coverageAmount = 1000;
    }

    @Test
    public void givenValidData_whenCreatingContract_thenPropertiesAreSet() {
        AbstractContract contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        assertEquals(contractNumber, contract.getContractNumber());
        assertEquals(insurer, contract.getInsurer());
        assertEquals(policyHolder, contract.getPolicyHolder());
        assertEquals(contractPaymentData, contract.getContractPaymentData());
        assertEquals(coverageAmount, contract.getCoverageAmount());
    }

    @Test
    public void givenContractNumberIsNull_whenCreatingContract_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestContract2(null, insurer, policyHolder, contractPaymentData, coverageAmount)
        );
    }

    @Test
    public void givenContractNumberIsEmpty_whenCreatingContract_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestContract2("", insurer, policyHolder, contractPaymentData, coverageAmount)
        );
    }

    @Test
    public void givenInsurerIsNull_whenCreatingContract_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestContract2(contractNumber, null, policyHolder, contractPaymentData, coverageAmount)
        );
    }

    @Test
    public void givenPolicyHolderIsNull_whenCreatingContract_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestContract2(contractNumber, insurer, null, contractPaymentData, coverageAmount)
        );
    }

    @Test
    public void givenNegativeCoverageAmount_whenCreatingContract_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, -100)
        );
    }

    @Test
    public void givenValidData_whenCreatingContract_thenContractIsActive() {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        assertTrue(contract.isActive());
    }

    @Test
    public void givenContractPaymentDataIsNull_whenCreatingContract_thenContractIsCreated() {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, null, coverageAmount);

        assertNotNull(contract);
        assertNull(contract.getContractPaymentData());
    }

    @Test
    public void givenActiveContract_whenSettingInactive_thenContractIsInactive() {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        assertTrue(contract.isActive());

        contract.setInactive();

        assertFalse(contract.isActive());
    }

    @Test
    public void givenInactiveContract_whenSettingInactive_thenContractRemainsInactive() {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
        //contract.isActive = false;
        contract.setInactive();

        assertFalse(contract.isActive());

        contract.setInactive();

        assertFalse(contract.isActive());

    }

    @Test
    public void givenNegativeCoverageAmount_whenSettingCoverageAmount_thenThrowsIllegalArgumentException() {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
        var coverageAmount = -100;

        assertThrows(IllegalArgumentException.class, () -> contract.setCoverageAmount(coverageAmount));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100})
    public void givenNonNegativeCoverageAmount_whenSettingCoverageAmount_thenCoverageAmountIsSet(int coverageAmount) {
        TestContract2 contract = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        contract.setCoverageAmount(coverageAmount);

        assertEquals(coverageAmount, contract.getCoverageAmount());
    }

    @Test
    public void givenTwoContractsHaveTheSameId_whenCheckingEquality_thenTheyAreEqual() {
        contractNumber = "123456";
        insurer = new InsuranceCompany(LocalDateTime.now());
        policyHolder = new Person("123456");
        contractPaymentData = new ContractPaymentData(5, PremiumPaymentFrequency.MONTHLY,
                LocalDateTime.now().plusMonths(1), 0);
        coverageAmount = 1000;
        TestContract2 contract1 = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
        contractNumber = "123456";
        policyHolder = new Person("123457");
        contractPaymentData = new ContractPaymentData(6, PremiumPaymentFrequency.QUARTERLY,
                LocalDateTime.now().plusMonths(2), 1);
        coverageAmount = 1040;
        TestContract2 contract2 = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        assertEquals(contract1, contract2);
    }

    @Test
    public void givenTwoContractWithTheSameIdButDifferentInsuranceCompany_whenCheckingEquality_thenTheyAreNotEqual() {
        contractNumber = "123456";
        insurer = new InsuranceCompany(LocalDateTime.now());
        policyHolder = new Person("123456");
        contractPaymentData = new ContractPaymentData(5, PremiumPaymentFrequency.MONTHLY,
                LocalDateTime.now().plusMonths(1), 0);
        coverageAmount = 1000;
        TestContract2 contract1 = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
        contractNumber = "123456";
        insurer = new InsuranceCompany(LocalDateTime.now());
        policyHolder = new Person("123457");
        contractPaymentData = new ContractPaymentData(6, PremiumPaymentFrequency.QUARTERLY,
                LocalDateTime.now().plusMonths(2), 1);
        coverageAmount = 1040;
        TestContract2 contract2 = new TestContract2(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);

        assertNotEquals(contract1, contract2);
    }
}