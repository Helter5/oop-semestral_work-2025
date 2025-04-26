package objects;

public class Vehicle {
    final private String licensePlate;
    final private int originalValue;

    public Vehicle(String licensePlate, int originalValue){
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate can't be null");
        }

        if (licensePlate.length() != 7) {
            throw new IllegalArgumentException("Length of the license plate has to be 7");
        }

        for(int i = 0; i < licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            if(!((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                throw new IllegalArgumentException("License plate must contain only characters A-Z and numbers 0-9");
            }
        }

        if (originalValue < 0) {
            throw new IllegalArgumentException("Original value must be positive");
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
}
