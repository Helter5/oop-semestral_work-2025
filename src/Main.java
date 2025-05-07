import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.SingleVehicleContract;
import contracts.TravelContract;
import objects.LegalForm;
import objects.Person;
import objects.Vehicle;
import payment.ContractPaymentData;
import payment.PaymentHandler;
import payment.PaymentInstance;
import payment.PremiumPaymentFrequency;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {



    public static void main(String[] args) {
        Person person1 = new Person("0405144707");
        Person person2 = new Person("111111111");
        InsuranceCompany insurer = new InsuranceCompany(LocalDateTime.now());
        Vehicle vehicle1 = new Vehicle("LC068BD", 10_000);

        TravelContract c1 = insurer.insurePersons("1", person1, 5, PremiumPaymentFrequency.ANNUAL, Set.of(person2));
        SingleVehicleContract c2 = insurer.insureVehicle("2", person1, person2, 200, PremiumPaymentFrequency.ANNUAL, vehicle1);
        insurer.setCurrentTime(LocalDateTime.now().plusMonths(12));

        insurer.chargePremiumsOnContracts();

        System.out.println(c1.getContractPaymentData().getOutstandingBalance());
        System.out.println(c2.getContractPaymentData().getOutstandingBalance());
    }
}
