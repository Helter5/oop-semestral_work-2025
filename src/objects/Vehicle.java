package objects;

import java.util.Objects;

public class Vehicle {
    final private String licensePlate;
    final private int originalValue;

    public Vehicle(String licensePlate, int originalValue){
        if (licensePlate == null) {
            throw new IllegalArgumentException();
        }

        if (licensePlate.length() != 7) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            if(!((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                throw new IllegalArgumentException();
            }
        }

        if (originalValue <= 0) {
            throw new IllegalArgumentException();
        }

        this.licensePlate = licensePlate;
        this.originalValue = originalValue;

    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public int getOriginalValue(){
        return originalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(licensePlate, vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(licensePlate);
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(licensePlate, vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(licensePlate);
    }
     */
}
