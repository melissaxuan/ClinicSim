package ruclinic;

/**
 * Contains (String) County and (String) Zip for each Location.
 *
 * @author Melissa Xuan
 */
public enum Location {
    BRIDGEWATER ("Somerset", "08807"),
    EDISON ("Middlesex", "08817"),
    PISCATAWAY ("Middlesex", "08854"),
    PRINCETON ("Mercer", "08542"),
    MORRISTOWN ("Morris", "07960"),
    CLARK ("Union", "07066");

    private final String county;
    private final String zip;

    /**
     * Location argument constructor to take in county and zip.
     * @param county county where the town is located
     * @param zip zip code where the town is located
     */
    Location (String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Prints location details including location name, county, and zip.
     * @param location location of details to be printed
     * @return String with location details including location name, county, and zip
     */
    public static String print(Location location) {
        return location.name() + ", " + location.getCounty() + " " + location.getZip();
    }

    /**
     * Getter method to return county.
     * @return county as String
     */
    public String getCounty() {
        return county;
    }

    /**
     * Getter method to return zip code.
     * @return zip code as String
     */
    public String getZip() {
        return zip;
    }

    /**
     * Method used to convert the string to the proper location enum.
     * @param location location to be set
     * @return the enum version of the string location passed in or null
     */
    public static Location getLocation(String location) {
        switch (location.toUpperCase()) {
            case ("BRIDGEWATER") : return Location.BRIDGEWATER;
            case ("EDISON") : return Location.EDISON;
            case ("PISCATAWAY") : return Location.PISCATAWAY;
            case ("PRINCETON") : return Location.PRINCETON;
            case ("MORRISTOWN") : return Location.MORRISTOWN;
            case ("CLARK") : return Location.CLARK;
            default : return null;
        }
    }
}
