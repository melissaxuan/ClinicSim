package ruclinic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import util.Date;
import util.List;
import util.Sort;

/**
 * Processes commands entered to the terminal.
 *
 * @author Melissa Xuan
 */
public class ClinicManager {
    private List<Provider> providersList;
    private List<Technician> technicianList;
    private List<Appointment> appointmentList;
    private List<Patient> patientList;
    private int techListPtr;

    /**
     * Default constructor to initialize List and MedicalRecord objects.
     */
    public ClinicManager() {
        this.providersList = new List<Provider>();
        this.technicianList = new List<Technician>();
        this.appointmentList = new List<Appointment>();
        this.patientList = new List<Patient>();
        this.techListPtr = 0;
    }

    /**
     * Main method to run the scheduler, reads in the commands one line at a time.
     */
    public void run() {
        readProviders();

        System.out.println("Clinic Manager is running...");
        Scanner scan = new Scanner(System.in);
        String input = "";

        while (scan.hasNextLine()) {
            input = scan.nextLine();
            if (input.equals("Q")) {
                System.out.println("Clinic Manager terminated.");

                scan.close();
                return;
            }
            System.out.println();
            processCmd(input);
        }
    }

    /**
     * Reads list of providers from providers.txt and inputs them into list of providers and
     * list of technicians if they are technicians.
     */
    private void readProviders() {
        try {
            File providersFile = new File("providers.txt");
            Scanner scan = new Scanner(providersFile);
            String input = "";

            while (scan.hasNextLine()) {
                input = scan.nextLine();

                addProvider(input);
            }

            Sort.provider(providersList);
            printProvidersList();
            printTechnicianList();

            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("'providers.txt' file could not be found.");
        }
    }

    /**
     * Helper method to add Provider to list of providers according to whether the Provider is a Doctor or Technician.
     *
     * @param input String of inputted details for Provider
     */
    private void addProvider(String input) {
        String[] tokens = input.split("  ");
        if (tokens[0].equals("D")) {
            Doctor doctor = new Doctor(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
            this.providersList.add(doctor);
        } else if (tokens[0].equals("T")) {
            Technician technician = new Technician(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
            this.providersList.add(technician);
            addTechnician(technician);
        }
    }

    /**
     * Helper method to add a new technician to list of Technicians in reverse order.
     *
     * @param newTechnician new Technician object to be added to the list of Technicians
     */
    private void addTechnician(Technician newTechnician) {
        for (int index = technicianList.size(); index > 0; index--) {
            technicianList.set(index, technicianList.get(index - 1));
        }

        technicianList.set(0, newTechnician);
    }

    /**
     * Helper method to process every possible command inputted in the terminal.
     *
     * @param input inputted command from the terminal
     */
    private void processCmd(String input) {
        String[] tokens = input.split(",");
        if (tokens.length > 0 && !tokens[0].isEmpty() && tokens[0].charAt(0) == 'P') {
            printCheck(tokens);
            return;
        }
        switch (tokens[0]) {
            case "D":
                scheduleDoc(tokens);
                break;
            case "T":
                scheduleTech(tokens);
                break;
            case "C":
                cancel(tokens);
                break;
            case "R":
                reschedule(tokens);
                break;
            case "":
                return;
            default:
                System.out.println("Invalid command!");
        }
    }

    /**
     * Checks if command entered is a print command.
     *
     * @param tokens command inputted in the terminal split into array of String tokens
     */
    private void printCheck(String[] tokens) {
        switch (tokens[0]) {
            case "PA":
                printByAppointment();
                break;
            case "PP":
                printByPatient();
                break;
            case "PL":
                printByLocation();
                break;
            case "PS":
                printBill();
                break;
            case "PO":
                printByOffice();
                break;
            case "PI":
                printByImaging();
                break;
            case "PC":
                printCredit();
                break;
            default:
                System.out.println("Invalid command!");
        }
    }

    /**
     * Helper method to schedule an appointment with a doctor.
     *
     * @param tokens array of details to schedule an appointment with a doctor, including doctor's npi
     */
    private void scheduleDoc(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        if (isAppDatesTimeValid(tokens)) {
            Date appDate = new Date(tokens[1]);
            Timeslot timeslot = new Timeslot(Integer.parseInt(tokens[2]));
            Profile profile = new Profile(tokens[3], tokens[4], new Date(tokens[5]));
            Patient patient = new Patient(profile);
            Doctor provider = new Doctor();
            if (isAppointment(new Appointment(appDate, timeslot, patient, provider))) {
                System.out.println(patient.getProfile().toString() + " has an existing appointment at the same time slot.");
            } else if (!checkDigits(tokens[6])) {
                System.out.println(tokens[6] + " - provider does not exist.");
            } else {
                provider = findDoctor(tokens[6]);

                if (provider == null) {
                    System.out.println(tokens[6] + " - provider does not exist.");
                } else if (!isProviderAvailable(provider, appDate, timeslot)) {
                    System.out.println(provider.toString() + " is not available at slot " + tokens[2] + ".");
                } else {
                    Patient newPatient = findPatient(patient);
                    Appointment newApp = new Appointment(appDate, timeslot, newPatient, provider);
                    appointmentList.add(newApp);
                    newPatient.addVisit(newApp);
                    System.out.println(newApp.toString() + " booked.");
                }
            }
        }
    }

    /**
     * Helper method to schedule an appointment with a technician.
     *
     * @param tokens array of details to schedule an appointment with a technician, including specified imaging service
     */
    private void scheduleTech(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }

        if (isAppDatesTimeValid(tokens)) {
            Date appDate = new Date(tokens[1]);
            Timeslot timeslot = new Timeslot(Integer.parseInt(tokens[2]));
            Profile profile = new Profile(tokens[3], tokens[4], new Date(tokens[5]));
            Patient patient = new Patient(profile);
            Technician provider = new Technician();
            String imaging = tokens[6];
            if (isAppointment(new Appointment(appDate, timeslot, patient, provider))) {
                System.out.println(patient.getProfile().toString() + " has an existing appointment at the same time slot.");
            } else if (!checkImaging(imaging)) {
                System.out.println(imaging + " - imaging service is not provided.");
            } else {
                Technician technician = findTechnician(appDate, timeslot, imaging);
                if (technician == null) {
                    System.out.println("Cannot find an available technician at all locations for " + imaging.toUpperCase() +
                            " at slot " + tokens[2] + ".");
                } else {
                    Patient newPatient = findPatient(patient);
                    Imaging newApp = new Imaging(appDate, timeslot, newPatient, technician, imaging);
                    appointmentList.add(newApp);
                    newPatient.addVisit(newApp);
                    System.out.println(newApp.toString() + " booked.");
                }
            }
        }
    }

    /**
     * Helper method that handles cancel command: removes appointment from List and Patient.
     *
     * @param tokens array of details to be used to cancel an appointment
     */
    private void cancel(String[] tokens) {
        if (tokens.length != 6) {
            System.out.println("Missing data tokens.");
            return;
        }
        if (isAppDatesTimeValid(tokens)) {
            Date appDate = new Date(tokens[1]);
            Timeslot timeslot = new Timeslot(Integer.parseInt(tokens[2]));
            Profile profile = new Profile(tokens[3], tokens[4], new Date(tokens[5]));
            Patient patient = new Patient(profile);
            Provider provider = new Doctor();
            Appointment newApp = new Imaging(appDate, timeslot, patient, provider, "XRAY");

            if (!this.appointmentList.contains(newApp)) {
                System.out.println(newApp.getDate().toString() + " " + newApp.getTimeslot().toString() +
                        " " + newApp.getPatient().getProfile().toString() + " - appointment does not exist.");
            } else {
                this.appointmentList.remove(newApp);
                Patient p = findPatient(patient);
                p.removeVisit(newApp);

                System.out.println(newApp.getDate().toString() + " " + newApp.getTimeslot().toString() +
                        " " + p.getProfile().toString() + " - has been canceled.");
            }
        }
    }

    /**
     * Helper method that handles reschedule command: reschedules appointment to different available timeslot on same day with same provider.
     *
     * @param tokens array of details to be used to reschedule an appointment
     */
    private void reschedule(String[] tokens) {
        if (tokens.length != 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        if (isAppDatesTimeValid(tokens) && isTimeslotValid(tokens[6])) {
            Date appDate = new Date(tokens[1]);
            Timeslot timeslot = new Timeslot(Integer.parseInt(tokens[2]));
            Profile profile = new Profile(tokens[3], tokens[4], new Date(tokens[5]));
            Patient patient = new Patient(profile);
            Provider provider = new Doctor();
            Appointment newApp = new Imaging(appDate, timeslot, patient, provider, "XRAY");

            if (!this.appointmentList.contains(newApp)) {
                System.out.println(newApp.getDate().toString() + " " + newApp.getTimeslot().toString() +
                        " " + newApp.getPatient().getProfile().toString() + " does not exist.");
            } else {
                Provider pr = findProvider(patient, appDate, timeslot);
                if (pr == null) {
                    System.out.println(newApp.getDate().toString() + " " + newApp.getTimeslot().toString() +
                            " " + newApp.getPatient().getProfile().toString() + " does not exist.");
                }
                if (isTimeslotValid(tokens[6])) {
                    Appointment appointment = findAppointment(newApp);
                    if (appointment != null) {
                        Timeslot newTimeslot = new Timeslot(Integer.parseInt(tokens[6]));
                        newApp.setTimeslot(newTimeslot);
                        if (this.appointmentList.contains(newApp)) {
                            System.out.println(newApp.getPatient().getProfile() + " has an existing appointment at " +
                                    newApp.getDate() + " " + newApp.getTimeslot());
                        } else {
                            appointment.setTimeslot(newTimeslot);
                            System.out.println("Rescheduled to " + appointment.toString());
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method that handles print by appointment command: prints appointments sorted by appointment date, time, then provider's name.
     */
    private void printByAppointment() {
        Sort.appointment(appointmentList, Sort.PA_CMD);
        if (this.appointmentList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** List of appointments, ordered by date/time/provider.");
        printAppointmentList(this.appointmentList);
    }

    /**
     * Helper method that handles print by patient command: prints appointments sorted by patient (lname, fname, dob) then appointment date and time.
     */
    private void printByPatient() {
        Sort.appointment(appointmentList, Sort.PP_CMD);
        if (this.appointmentList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** List of appointments, ordered by patient profile/date/time.");
        printAppointmentList(this.appointmentList);
    }

    /**
     * Helper method that handles print by location command: prints appointments sorted by county name, then appointment date and time.
     */
    private void printByLocation() {
        Sort.appointment(appointmentList, Sort.PL_CMD);
        if (this.appointmentList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** List of appointments, ordered by county/date/time.");
        printAppointmentList(this.appointmentList);
    }

    /**
     * Helper method that handles print bill statements command: prints bills sorted by patient profile.
     */
    private void printBill() {
        if (this.appointmentList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        Sort.patient(patientList);
        System.out.println("** Billing statement ordered by patient. **");
        for (int index = 0; index < this.patientList.size(); index++) {
            System.out.println("(" + (index + 1) + ") " + this.patientList.get(index).toString());
        }

        System.out.println("** end of list **");

        this.patientList = new List<Patient>();
        this.appointmentList = new List<Appointment>();
        this.techListPtr = 0;
    }

    /**
     * Helper method that handles print by office command: prints office (doctor) appointments sorted by county name, then date and time.
     */
    private void printByOffice() {
        List<Appointment> docAppList = findDocAppointments();
        Sort.appointment(docAppList, Sort.PL_CMD);
        if (docAppList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** List of office appointments ordered by county/date/time.");
        printAppointmentList(docAppList);
    }

    /**
     * Helper method that handles print by imaging command: prints imaging appointments sorted by county name, then date and time.
     */
    private void printByImaging() {
        List<Appointment> techAppList = findTechAppointments();
        Sort.appointment(techAppList, Sort.PL_CMD);
        if (techAppList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** List of radiology appointments ordered by county/date/time.");
        printAppointmentList(techAppList);
    }

    /**
     * Helper method that handles print credit for providers command: prints expected credit amounts for providers sorted by provider profile.
     */
    private void printCredit() {
        Sort.provider(providersList);
        calculateCredit();
    }

    /**
     * Helper method that calculates and prints the total expected credit amounts for providers for seeing patients.
     */
    private void calculateCredit() {
        if (this.appointmentList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** Credit amount ordered by provider. **");
        int index = 1;
        for (Provider p : providersList) {
            int credit = 0;
            for (Appointment a : appointmentList) {
                if (a.getProvider().equals(p)) {
                    credit += p.rate();
                }
            }
            System.out.println("(" + index + ") " + p.getProfile().toString() +
                    " [credit amount: $" + String.format("%,d", credit) + ".00]");
            index++;
        }
        System.out.println("** end of list **");
    }

    /**
     * Helper method to sort out doctor appointments from within the List of all Appointments.
     *
     * @return List of Appointments with Doctor providers.
     */
    private List<Appointment> findDocAppointments() {
        List<Appointment> doctorAppointments = new List<Appointment>();
        for (Appointment a : appointmentList) {
            if (a.getProvider() instanceof Doctor) {
                doctorAppointments.add(a);
            }
        }
        return doctorAppointments;
    }

    /**
     * Helper method to sort out technician appointments from within the List of all Appointments.
     *
     * @return List of Appointments with Technician providers.
     */
    private List<Appointment> findTechAppointments() {
        List<Appointment> techAppointments = new List<Appointment>();
        for (Appointment a : appointmentList) {
            if (a.getProvider() instanceof Technician) {
                techAppointments.add(a);
            }
        }
        return techAppointments;
    }

    /**
     * Checks if imaging service is a provided imaging service.
     *
     * @param imaging name of requested imaging service
     * @return true if the imaging service is provided, false if not
     */
    private boolean checkImaging(String imaging) {
        for (Radiology r : Radiology.values()) {
            if (imaging.equalsIgnoreCase(r.name())) return true;
        }
        return false;
    }

    /**
     * Finds patient in list of Patients and creates a new Patient if not found
     *
     * @param patient Patient object to be searched for in list of Patients
     * @return Patient from list of Patients or newly created Patient added to list of Patients
     */
    private Patient findPatient(Patient patient) {
        for (Patient p : patientList) {
            if (p.getProfile().equals(patient.getProfile())) {
                return p;
            }
        }
        patientList.add(patient);
        return patient;
    }

    /**
     * Checks if provider is available at the specific date and timeslot.
     *
     * @param provider provider to check availability for
     * @param date     date to check for provider's availability
     * @param timeslot timeslot corresponding to date to check for provider's availability
     * @return true if provider is available, false if provider is not available
     */
    private boolean isProviderAvailable(Provider provider, Date date, Timeslot timeslot) {
        for (Appointment a : appointmentList) {
            if (a.getProvider().equals(provider) && a.getDate().equals(date) &&
                    a.getTimeslot().equals(timeslot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if appointment can be found in list of Appointments.
     *
     * @param app Appointment object to be searched for
     * @return true if appointment was found, false if appointment was not found
     */
    private boolean isAppointment(Appointment app) {
        for (Appointment a : this.appointmentList) {
            if (a.equals(app)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if inputted String contains only numerical characters.
     *
     * @param s String to be checked
     * @return true if String contains only numerical characters, false if String does not
     */
    private boolean checkDigits(String s) {
        for (Character c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if an intended Date object contains only numerical characters.
     *
     * @param s String version of date to be checked.
     * @return true if the intended Date object contains only numerical characters disregarding the '/'s, false otherwise
     */
    private boolean checkDateDigits(String s, String use) {
        String[] dateVals = s.split("/");

        for (String d : dateVals) {
            if (!checkDigits(d)) {
                if (use.equals("appointment")) {
                    System.out.print("Appointment date: ");
                } else {
                    System.out.print("Patient dob: ");
                }
                System.out.println(s + " is not a valid calendar date");
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if appointment can be found in the list of appointments
     * @param app appointment to be searched for
     * @return Appointment object if found in list of appointments, otherwise return null
     */
    private Appointment findAppointment(Appointment app) {
        for (Appointment a : appointmentList) {
            if (a.equals(app)) {
                return a;
            }
        }

        return null;
    }

    /**
     * Checks if doctor can be found in the list of providers according to the npi value.
     *
     * @param npi npi value to be matched against each doctor in the list of providers
     * @return Doctor object if found in list of providers, otherwise return null
     */
    private Doctor findDoctor(String npi) {
        for (Provider p : providersList) {
            if (p instanceof Doctor) {
                Doctor d = (Doctor) p;
                if (d.getNpi().equals(npi))
                    return d;
            }
        }
        return null;
    }

    /**
     * Finds the next available technician that is available at the specified date and time, and the location is available for the imaging service.
     *
     * @param date     requested date of the appointment
     * @param timeslot requested time slot of the appointment
     * @param imaging  requested imaging service
     * @return Technician object if an available technician is found, null if no technician is available matching request
     */
    private Technician findTechnician(Date date, Timeslot timeslot, String imaging) {
        int counter = 0;
        while (counter < technicianList.size()) {
            if (techListPtr > technicianList.size() - 1) {
                techListPtr = 0;
            }

            if (isTechnicianAvailable(technicianList.get(techListPtr),
                    date, timeslot, imaging)) {
                return technicianList.get(techListPtr++);
            }

            if (techListPtr >= technicianList.size() - 1) {
                techListPtr = 0;
            } else {
                techListPtr++;
            }
            counter++;
        }
        return null;
    }

    /**
     * Finds provider matching an appointment's patient, date, and time.
     *
     * @param patient  Patient that scheduled the appointment
     * @param date     date of the appointment
     * @param timeslot time slot of the appointment
     * @return Provider object matching the appointment's patient, date, and time details, otherwise returns null
     */
    private Provider findProvider(Patient patient, Date date, Timeslot timeslot) {
        for (Appointment a : appointmentList) {
            if (a.getPatient().equals(patient) && a.getDate().equals(date) && a.getTimeslot().equals(timeslot)) {
                return a.getProvider();
            }
        }

        return null;
    }

    /**
     * Checks if technician is available at the requested date and time, and if the imaging service is available at their location.
     *
     * @param technician Technician to be checked
     * @param date       requested date of appointment
     * @param time       requested time slot of appointment
     * @param imaging    requested imaging service
     * @return true if specified technician is available according to requested details, false if specified technician is unavailable
     */
    private boolean isTechnicianAvailable(Technician technician, Date date, Timeslot time, String imaging) {
        if (this.appointmentList.isEmpty()) {
            return true;
        }
        for (Appointment a : this.appointmentList) {
            if (a instanceof Imaging) {
                if ((a.getProvider().equals(technician) && a.getDate().equals(date) && a.getTimeslot().equals(time)) ||
                        !isLocationAvailable(date, time, imaging, technician.getLocation())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if imaging service room is available at requested date and timeslot at the specified technician's location.
     *
     * @param date     requested date of appointment
     * @param timeslot requested time slot of appointment
     * @param imaging  requested imaging service
     * @param location specified technician's location
     * @return true if imaging service room is available according to requests, false if imaging service room is unavailable
     */
    private boolean isLocationAvailable(Date date, Timeslot timeslot, String imaging, Location location) {
        for (Appointment a : appointmentList) {
            if (a instanceof Imaging) {
                Imaging i = (Imaging) a;
                if (i.getDate().equals(date) && i.getTimeslot().equals(timeslot) &&
                        i.getProvider().getLocation().name().equals(location.name()) && i.getRoom().name().equalsIgnoreCase(imaging)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if there are a valid number of tokens in the command and if the dates and times are valid.
     *
     * @param tokens array of details to schedule an appointment with a doctor, including doctor's npi
     * @return true if there are a valid number of tokens in the command and if the dates and times are valid, return false otherwise
     */
    private boolean isAppDatesTimeValid(String[] tokens) {
        if (!checkDateDigits(tokens[1], "appointment") || !isAppDateValid(tokens[1])) {
            return false;
        } else if (!checkDigits(tokens[2]) || !isTimeslotValid(tokens[2])) {
            System.out.println(tokens[2] + " is not a valid time slot.");
            return false;
        } else if (!checkDateDigits(tokens[1], "dob") || !isDobValid(tokens[5])) {
            return false;
        }

        return true;
    }

    /**
     * Helper method to check if appointment date is valid.
     *
     * @param date appointment date to be checked
     * @return true if appointment date is valid, false if appointment date is invalid
     */
    private boolean isAppDateValid(String date) {
        Date appDate = new Date(date);
        if (!appDate.isValid()) {
            System.out.println("Appointment date: " + date + " is not a valid calendar date");
        } else if (appDate.isToday() || appDate.isPast()) {
            System.out.println("Appointment date: " + date + " is today or a date before today.");
        } else if (appDate.isWeekend()) {
            System.out.println("Appointment date: " + date + " is Saturday or Sunday.");
        } else if (!appDate.isInSixMonths()) {
            System.out.println("Appointment date: " + date + " is not within six months.");
        }

        return appDate.isValid() && !appDate.isToday() &&
                !appDate.isPast() && !appDate.isWeekend() && appDate.isInSixMonths();
    }

    /**
     * Helper method to check if time slot is valid.
     *
     * @param timeslot timeslot to be checked
     * @return true if timeslot is valid, false if timeslot is invalid
     */
    private boolean isTimeslotValid(String timeslot) {
        return Integer.parseInt(timeslot) >= Timeslot.EARLIEST_TIMESLOT &&
                Integer.parseInt(timeslot) <= Timeslot.LATEST_TIMESLOT;
    }

    /**
     * Helper method to check if date of birth is valid.
     *
     * @param date date of birth to be checked
     * @return true if date of birth is valid, false if date of birth is invalid
     */
    private boolean isDobValid(String date) {
        Date dob = new Date(date);
        if (!dob.isValid()) {
            System.out.println("Patient dob: " + date + " is not a valid calendar date");
        } else if (dob.isToday() || dob.isFuture()) {
            System.out.println("Patient dob: " + date + " is today or a day after today.");
        }
        return dob.isValid() && !dob.isToday() && !dob.isFuture();
    }

    /**
     * Helper method to print out all the Providers in List of Providers.
     */
    private void printProvidersList() {
        System.out.println("Providers loaded to the list.");
        for (Provider p : this.providersList) {
            System.out.println(p);
        }
        System.out.println();
    }

    /**
     * Helper method to print out all the Technicians in List of Technicians.
     */
    private void printTechnicianList() {
        System.out.println("Rotation list for the technicians.");
        int index = 1;
        for (Technician p : this.technicianList) {
            System.out.print(p.getProfile().getFname() + " " + p.getProfile().getLname() +
                    " (" + p.getLocation() + ")");
            if (index != this.technicianList.size()) {
                System.out.print(" --> ");
            }
            index++;
        }
        System.out.println("\n");
    }

    /**
     * Helper method to print out all the Appointments in List of Appointments.
     */
    private void printAppointmentList(List<Appointment> appList) {
        for (Appointment a : appList) {
            System.out.println(a);
        }
        System.out.println("** end of list **");
    }
}