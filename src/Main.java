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
        Person beneficiary = new Person("7804129971");
        LocalDateTime currentTime = LocalDateTime.of(2025, 4, 10, 10, 0);
        InsuranceCompany insurer = new InsuranceCompany(LocalDateTime.of(2025, 6, 15, 12, 0));
        InsuranceCompany insurer2 = new InsuranceCompany(LocalDateTime.of(2025, 2, 7, 4, 0));



        // Vytvorenie dvoch PaymentHandler-ov pre tú istú poisťovňu
        PaymentHandler handler1 = new PaymentHandler(insurer);
        PaymentHandler handler2 = new PaymentHandler(insurer);

        // Vytvorenie osoby (poistníka)
        Person policyHolder = new Person("17318211");
        Person policyHolder2 = new Person("12344557");

        // Vytvorenie vozidla
        Vehicle vehicle1 = new Vehicle("AA111AA", 2500);
        Vehicle vehicle2 = new Vehicle("AA222AA", 3300);
        Vehicle vehicle3 = new Vehicle("AA333AA", 5500);

        // Vytvorenie platobných údajov pre zmluvu
        ContractPaymentData paymentData = new ContractPaymentData(
                500, PremiumPaymentFrequency.MONTHLY, currentTime, 0);

        // Vytvorenie zmluvy
        SingleVehicleContract single1 = new SingleVehicleContract(
                "1111", insurer, beneficiary, policyHolder, paymentData, 50_000, vehicle1);

        // Naplnenie handler1
        handler1.pay(single1, 500);
        handler1.pay(single1, 300);

        // Naplnenie handler2 (rovnaké dáta ako handler1)
        handler2.pay(single1, 500);
        handler2.pay(single1, 300);

        // Porovnanie handler1 a handler2
        System.out.println("handler1.equals(handler2): " + handler1.equals(handler2));

        // Naplnenie handler2 s inou platbou, aby boli rôzne
        handler2.pay(single1, 200);
        System.out.println("handler1.equals(handler2) po zmene: " + handler1.equals(handler2));


        MasterVehicleContract master1 = new MasterVehicleContract("1111", insurer, beneficiary, policyHolder);
        System.out.println(master1.isActive());

        insurer.moveSingleVehicleContractToMasterVehicleContract(master1, single1);

        System.out.println(master1.isActive());

        //master1.pay(10);
        //single1.pay(10);


        SingleVehicleContract single11 = insurer2.insureVehicle("1111x", beneficiary, policyHolder,
                30, PremiumPaymentFrequency.MONTHLY, vehicle1);
        SingleVehicleContract single22 = insurer2.insureVehicle("2222x", beneficiary, policyHolder,
                50, PremiumPaymentFrequency.MONTHLY, vehicle2);
        SingleVehicleContract single33 = insurer2.insureVehicle("3333x", beneficiary, policyHolder,
                75, PremiumPaymentFrequency.MONTHLY, vehicle3);
        single33.getContractPaymentData().setOutstandingBalance(100);



        System.out.println();
        System.out.println(single1.getContractPaymentData().getNextPaymentTime());
        insurer.chargePremiumOnContract(single1);
        System.out.println(single1.getContractPaymentData().getNextPaymentTime());
        System.out.println("--------------------------------");


        MasterVehicleContract master2 = insurer2.createMasterVehicleContract("masterC1", beneficiary, policyHolder);



        insurer2.moveSingleVehicleContractToMasterVehicleContract(master2, single11);
        insurer2.moveSingleVehicleContractToMasterVehicleContract(master2, single22);
        insurer2.moveSingleVehicleContractToMasterVehicleContract(master2, single33);

        for(SingleVehicleContract c : master2.getChildContracts()){
            System.out.println(c.getContractNumber());
        }
        System.out.println();

        for(AbstractContract c : policyHolder.getContracts()){
            System.out.println(c.getContractNumber());
        }


        System.out.println("--------------------------------");

        Person p1 = new Person("8302129264");
        Person p2 = new Person("8301210720");
        Person p3 = new Person("8301210720");

        Set<Person> personsToInsure = new HashSet<>();
        personsToInsure.add(p1);
        personsToInsure.add(p2);
        personsToInsure.add(p3);

        TravelContract travel1 = new TravelContract("travelCon1", insurer, policyHolder,
                paymentData, 30, personsToInsure);

        System.out.println("--------------------------------");


        travel1.updateBalance();
        master1.updateBalance();

        System.out.println("--------------------------------");

        master1.pay(100);
        single1.pay(100);

        System.out.println("--------------------------------");

        for(SingleVehicleContract c : master2.getChildContracts()){
            System.out.println(c.getContractPaymentData().getOutstandingBalance());
        }
        System.out.println();
        master2.pay(400);
        for(SingleVehicleContract c : master2.getChildContracts()){
            System.out.println(c.getContractPaymentData().getOutstandingBalance());
        }

        master2.getInsurer().getHandler().getPaymentHistory().forEach((contract, instances) -> {
            instances.forEach(instance -> {
                System.out.println(contract.getContractNumber() + " " + instance.getPaymentAmount());
            });
        });

        /*currentTime = LocalDateTime.of(2025, 4, 10, 10, 1);
        insurer2.setCurrentTime(currentTime);
        master2.pay(10);
        for(SingleVehicleContract c : master2.getChildContracts()){
            System.out.println(c.getContractPaymentData().getOutstandingBalance());
        }
        System.out.println(master2.getInsurer().getHandler().getPaymentHistory().get(master2).size());
        master2.getInsurer().getHandler().getPaymentHistory().forEach((contract, instances) -> {
            instances.forEach(instance -> {
                System.out.println(contract.getContractNumber() + " " + instance.getPaymentAmount());
            });
        });*/

        System.out.println("--------------------------------");









    }
}
