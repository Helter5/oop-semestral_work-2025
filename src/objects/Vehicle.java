package objects;

import java.util.Objects;

public class Vehicle {
    private final String licensePlate;
    private final int originalValue;

    public Vehicle(String licensePlate, int originalValue){
        if (licensePlate == null) throw new IllegalArgumentException("License plate cannot be null");
        if (licensePlate.length() != 7) throw new IllegalArgumentException("License plate must be 7 characters long");
        if (originalValue <= 0) throw new IllegalArgumentException("Original value must be greater than 0");

        boolean isValid = true;
        for (int i=0; i<licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            if (!Character.isDigit(c) && !Character.isUpperCase(c)) {
                isValid = false;
                break;
            }
        }
        if (!isValid) throw new IllegalArgumentException("License plate can only contain uppercase letters and digits");

        this.licensePlate = licensePlate;
        this.originalValue = originalValue;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public int getOriginalValue(){
        return originalValue;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(licensePlate, vehicle.licensePlate) &&
               Objects.equals(originalValue, vehicle.originalValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, originalValue);
    }
     */
}
