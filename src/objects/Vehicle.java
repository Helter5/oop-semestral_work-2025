package objects;

public class Vehicle {
    final private String licensePlate;
    final private int originalValue;

    public Vehicle(String licensePlate, int originalValue){
        if (licensePlate == null) throw new IllegalArgumentException();
        if (licensePlate.length() != 7) throw new IllegalArgumentException();
        if (originalValue <= 0) throw new IllegalArgumentException();

        boolean isValid = true;
        for (int i=0; i<licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            if (!Character.isDigit(c) && !Character.isUpperCase(c)) {
                isValid = false;
                break;
            }
        }
        if (!isValid) throw new IllegalArgumentException();

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
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(licensePlate, vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(licensePlate);
    }
     */
}
