package projects.ruclinic.enhancedgui.util;

import projects.ruclinic.enhancedgui.ruclinic.Appointment;
import projects.ruclinic.enhancedgui.ruclinic.Patient;
import projects.ruclinic.enhancedgui.ruclinic.Provider;

/**
 * Sorts lists of objects in specific order.
 */
public class Sort {
    public static final int PA_CMD = 1;
    public static final int PP_CMD = 2;
    public static final int PL_CMD = 3;

    /**
     * Sorts List of appointments with quicksort algorithm.
     * @param list list of appointments to be sorted
     * @param cmd command to sort in specific order
     */
    public static void appointment(List<Appointment> list, int cmd) {
        quicksortAppointment(list, 0, list.size() - 1, cmd);
    }

    /**
     * Sorts List of providers with quicksort algorithm.
     * @param list list of providers to be sorted
     */
    public static void provider(List<Provider> list) {
        quicksortProvider(list, 0, list.size() - 1);
    }

    /**
     * Sorts List of patients with quicksort algorithm.
     * @param list list of patients to be sorted
     */
    public static void patient(List<Patient> list) {
        quicksortPatient(list, 0, list.size() - 1);
    }

    /**
     * Implements quick sort algorithm to sort list of Patients.
     * @param arr list of Patients to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     */
    private static void quicksortProvider(List<Provider> arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionProvider(arr, low, high);
            quicksortProvider(arr, low, pivotIndex - 1);
            quicksortProvider(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Helper method for quicksort.
     * @param arr list of Patients to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     * @return low bound to be sorted
     */
    private static int partitionProvider(List<Provider> arr, int low, int high) {
        Provider pivot = arr.get(high);
        int lBound = low - 1;

        for (int rBound = low; rBound < high; rBound++) {
            if (arr.get(rBound).getProfile().compareTo(pivot.getProfile()) <= 0) {
                lBound++;
                Provider temp = arr.get(lBound);
//                arr.get(lBound) = arr.get(rBound);
                arr.set(lBound, arr.get(rBound));
//                arr.get(rBound) = temp;
                arr.set(rBound, temp);
            }
        }

        Provider temp = arr.get(lBound + 1);
        arr.set(lBound + 1, arr.get(high));
        arr.set(high, temp);
//        arr[lBound + 1] = arr[high];
//        arr[high] = temp;
        return lBound + 1;
    }

    /**
     * Implements quick sort algorithm to sort list of Patients.
     * @param arr list of Patients to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     */
    private static void quicksortPatient(List<Patient> arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionPatient(arr, low, high);
            quicksortPatient(arr, low, pivotIndex - 1);
            quicksortPatient(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Helper method for quicksort.
     * @param arr list of Patients to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     * @return low bound to be sorted
     */
    private static int partitionPatient(List<Patient> arr, int low, int high) {
        Patient pivot = arr.get(high);
        int lBound = low - 1;

        for (int rBound = low; rBound < high; rBound++) {
            if (arr.get(rBound).getProfile().compareTo(pivot.getProfile()) <= 0) {
                lBound++;
                Patient temp = arr.get(lBound);
//                arr.get(lBound) = arr.get(rBound);
                arr.set(lBound, arr.get(rBound));
//                arr.get(rBound) = temp;
                arr.set(rBound, temp);
            }
        }

        Patient temp = arr.get(lBound + 1);
        arr.set(lBound + 1, arr.get(high));
        arr.set(high, temp);
//        arr[lBound + 1] = arr[high];
//        arr[high] = temp;
        return lBound + 1;
    }

    /**
     * Implements quick sort algorithm to sort list of Appointments.
     * @param arr list of Appointments to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     * @param cmd specific command to sort by certain order
     */
    private static void quicksortAppointment(List<Appointment> arr, int low, int high, int cmd) {
        if (low < high) {
            int pivotIndex = partitionAppointment(arr, low, high, cmd);
            quicksortAppointment(arr, low, pivotIndex - 1, cmd);
            quicksortAppointment(arr, pivotIndex + 1, high, cmd);
        }
    }

    /**
     * Helper method for quicksort.
     * @param arr list of Appointments to be sorted
     * @param low low bound to be sorted
     * @param high high bound to be sorted
     * @param cmd specific command to sort by certain order
     * @return low bound to be sorted
     */
    private static int partitionAppointment(List<Appointment> arr, int low, int high, int cmd) {
        Appointment pivot = arr.get(high);
        int lBound = low - 1;

        for (int rBound = low; rBound < high; rBound++) {
            if (processCompareCmd(arr.get(rBound), pivot, cmd) <= 0) {
                lBound++;
                Appointment temp = arr.get(lBound);
//                arr.get(lBound) = arr.get(rBound);
                arr.set(lBound, arr.get(rBound));
//                arr.get(rBound) = temp;
                arr.set(rBound, temp);
            }
        }

        Appointment temp = arr.get(lBound + 1);
        arr.set(lBound + 1, arr.get(high));
        arr.set(high, temp);
//        arr[lBound + 1] = arr[high];
//        arr[high] = temp;
        return lBound + 1;
    }

    /**
     * Calls specific helper method to sort in a certain order according to input cmd.
     * @param a this Appointment to be sorted
     * @param b other Appointment to be sorted
     * @param cmd command to specify which order to sort the Appointments
     * @return int indicates 0 for exactly equal Appointments,
     *         positive if this Appointment is greater than the other lexicographically,
     *         negative if this Appointment is smaller than the other lexicographically
     */
    private static int processCompareCmd(Appointment a, Appointment b, int cmd) {
        return switch (cmd) {
            case (PA_CMD) -> a.compareByAppointment(b);
            case (PP_CMD) -> a.compareByPatient(b);
            case (PL_CMD) -> a.compareByLocation(b);
            default -> 0;
        };
    }

}


