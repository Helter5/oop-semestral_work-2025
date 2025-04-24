package contracts;

public class AbstractContract {
    private String contractNumber;
    protected InsuranceCompany  insurer;
    protected Person policyHolder;
    protected CotnractPaymentData contractPaymentData;
    protected int coverageAmount;
    protected boolean isActive;

    public AbstractContract(String contractNumber, InsuranceCompany insurer,
                            Person policyHolder, ContractPaymentData contractPaymentData,
                            int coverageAmount) {

    };

    public String getContractNumber();

    public Person getPolicyHolder();

    public InsuranceCompany getInsurer();

    public int getCoverageAmount();

    public boolean isActive();

    public void setInactive();

    public void setCoverageAmount(int coverageAmount);

    public ContractPaymentData getContractPaymentData();

    public void pay(int amount);

    public void updateBalance();
}
