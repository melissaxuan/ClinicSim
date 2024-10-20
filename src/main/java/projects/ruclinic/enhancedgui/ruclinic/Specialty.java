package projects.ruclinic.enhancedgui.ruclinic;

/**
 * Contains (int) charges for provider's specialty.
 *
 * @author Melissa Xuan, Michael Ehresman
 */
public enum Specialty {
    FAMILY (250),
    PEDIATRICIAN (300),
    ALLERGIST (350);

    private final int charge;

    /**
     * Parameter constructor for the Speciality class.
     * @param charge specific charge per Specialty
     */
    Specialty (int charge) {
        this.charge = charge;
    }

    /**
     * Getter method to get the charge.
     * @return the current  speciality objects charge
     */
    public int getCharge()
    {
        return this.charge;
    }

    /**
     * getter method to get the speciality enum
     * @param specialty string to be converted into the proper enum
     * @return the proper enum converted from the string or null
     */
    public static Specialty getSpecialty(String specialty) {
        switch (specialty.toUpperCase()) {
            case ("FAMILY") : return Specialty.FAMILY;
            case ("PEDIATRICIAN") : return Specialty.PEDIATRICIAN;
            case ("ALLERGIST") : return Specialty.ALLERGIST;
            default : return null;
        }
    }
}
