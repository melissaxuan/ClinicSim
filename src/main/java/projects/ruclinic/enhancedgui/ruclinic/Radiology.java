package projects.ruclinic.enhancedgui.ruclinic;

/**
 * Contains (String) radiology choices for the imaging class.
 * @author Michael Ehresman
 */
public enum Radiology {
    CATSCAN(),
    ULTRASOUND(),
    XRAY();

    /**
     * Radiology default constructor.
     */
    Radiology() {

    }

    /**
     * Method to convert a string to the proper enum value.
     * @param radioType string to be converted to enum
     * @return the proper enum comverted from the string or null
     */
    public static Radiology getRadiology(String radioType) {
        switch (radioType.toUpperCase()) {
            case ("CATSCAN"):
                return CATSCAN;
            case ("ULTRASOUND"):
                return ULTRASOUND;
            case ("XRAY"):
                return XRAY;
            default:
                return null;
        }
    }
}
