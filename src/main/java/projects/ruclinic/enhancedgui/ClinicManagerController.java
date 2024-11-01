package projects.ruclinic.enhancedgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import projects.ruclinic.enhancedgui.ruclinic.*;
import projects.ruclinic.enhancedgui.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import projects.ruclinic.enhancedgui.ruclinic.Provider;
import projects.ruclinic.enhancedgui.ruclinic.Technician;
import projects.ruclinic.enhancedgui.ruclinic.Doctor;
import projects.ruclinic.enhancedgui.util.List;
import projects.ruclinic.enhancedgui.util.Sort;

public class ClinicManagerController {

    @FXML
    private TextArea TA_printInfo;

    @FXML
    private Button bt_cancel;

    @FXML
    private Button bt_clear;

    @FXML
    private Button bt_loadprovider;

    @FXML
    private Button bt_reschedule;

    @FXML
    private Button bt_schedule;

    @FXML
    private ComboBox<Radiology> cb_imaging;

    @FXML
    private ComboBox<Doctor> cb_doctor;

    @FXML
    private ComboBox<String> cb_sortSelecter;

    @FXML
    private ComboBox<Timeslot> cb_timeslot;

    @FXML
    private DatePicker dp_appdate;

    @FXML
    private DatePicker dp_dob;

    @FXML
    private RadioButton rb_office;

    @FXML
    private RadioButton rb_imaging;

    @FXML
    private TextArea ta_output;

    @FXML
    private TextField tf_fname;

    @FXML
    private TextField tf_lname;

    @FXML
    private Text txt_appDate;

    @FXML
    private Text txt_appheader;

    @FXML
    private Text txt_appimaging;

    @FXML
    private Text txt_appprovider;

    @FXML
    private Text txt_apptime;

    @FXML
    private Text txt_apptype;

    @FXML
    private Text txt_patientdob;

    @FXML
    private Text txt_patientfname;

    @FXML
    private Text txt_patientheader;

    @FXML
    private Text txt_patientlname;

    @FXML
    private ToggleGroup tg_apptype;

    private List<Provider> providersList;
    private List<Technician> technicianList;
    private List<Appointment> appointmentList;
    private List<Patient> patientList;
    private List<Timeslot> timeslotList;
    private int techListPtr;

    /**
     * Default constructor to initialize List and MedicalRecord objects.
     */
    public ClinicManagerController() {
        this.providersList = new List<Provider>();
        this.technicianList = new List<Technician>();
        this.appointmentList = new List<Appointment>();
        this.patientList = new List<Patient>();
        this.timeslotList = new List<Timeslot>();
        loadTimeslots();
        this.techListPtr = 0;
    }

    public void initialize() {
        cb_sortSelecter.getItems().addAll(
                "Print by Appointment",
                "Print by Patient",
                "Print by Location",
                "Print Bill",
                "Print by Office",
                "Print by Imaging",
                "Print Credit"
        );

        cb_doctor.setPromptText("(No Provider File Loaded)");
        cb_imaging.setPromptText("(No Provider File Loaded)");

        for (int i = Timeslot.EARLIEST_TIMESLOT; i <= Timeslot.LATEST_TIMESLOT; i++) {
            cb_timeslot.getItems().add(timeslotList.get(i - 1));
        }

//        tg_apptype = new ToggleGroup();
//        rb_office.setToggleGroup(tg_apptype);
//        rb_office.setSelected(true);
//        rb_imaging.setToggleGroup(tg_apptype);
    }

    @FXML
    void cancelApp(ActionEvent event) {

    }

    /**
     * Method to convert the event to a string and print the correct list
     * @param event The type of sort selection to print to the text area
     */
    @FXML
    void cb_sortSelecter(ActionEvent event) {
        ComboBox<String> comboBox = (ComboBox<String>) event.getSource();
        String sortSelected = comboBox.getValue();
        TA_printInfo.setText("");
        switch (sortSelected) {
            case "Print by Appointment":
                printByAppointment();
                break;
            case "Print by Patient":
                printByPatient();
                break;
            case "Print by Location":
                printByLocation();
                break;
            case "Print Bill":
                printBill();
                break;
            case "Print by Office":
                printByOffice();
                break;
            case "Print by Imaging":
                printByImaging();
                break;
           case "Print Credit":
                printCredit();
                break;
            default:
                break;
       }

    }

    @FXML
    void clearAppTab(ActionEvent event) {
        tf_fname.clear();
        tf_lname.clear();
        dp_dob.getEditor().clear();
        dp_appdate.getEditor().clear();
        cb_timeslot.getSelectionModel().clearSelection();
        cb_doctor.getSelectionModel().clearSelection();
        cb_imaging.getSelectionModel().clearSelection();
        ta_output.clear();
    }

    @FXML
    void getAppDate(ActionEvent event) {

    }

    @FXML
    void getAppProvider(ActionEvent event) {

    }

    @FXML
    void getAppTime(ActionEvent event) {

    }

    @FXML
    void getPatientDOB(ActionEvent event) {

    }

    @FXML
    void getPatientFirstName(ActionEvent event) {

    }

    @FXML
    void getPatientLastName(ActionEvent event) {

    }

    @FXML
    void loadProviders(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            File providersFile = fileChooser.showOpenDialog(null);
            if (providersFile != null) {
                Scanner scan = new Scanner(providersFile);
                String input = "";
                this.providersList = new List<>();
                this.technicianList = new List<>();

                while (scan.hasNextLine()) {
                    input = scan.nextLine();

                    addProvider(input);
                }

                Sort.provider(providersList);
                for (Provider p : providersList) {
                    if (p instanceof Doctor doctor)
                        cb_doctor.getItems().add(doctor);
                }

                ObservableList<Radiology> radiology =
                        FXCollections.observableArrayList(Radiology.values());
                cb_imaging.setItems(radiology);

                cb_doctor.setPromptText("(Select a Provider)");
                cb_imaging.setPromptText("(Select an Imaging Service)");
                scan.close();
            }
        }
        catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FileNotFoundException");
            alert.setHeaderText(null);
            alert.setContentText("File could not be found.");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);

            alert.showAndWait();
        }
    }

    @FXML
    void rescheduleApp(ActionEvent event) {
        String missing = checkMissingData();
        if (!missing.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText(missing);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);
            alert.showAndWait();
        }
        else {
            if (tg_apptype.getSelectedToggle().equals(rb_office)) {
                String[] tokens = {"D", dp_appdate.getValue().toString(), Integer.toString(cb_timeslot.getSelectionModel().getSelectedIndex() + 1),
                        tf_fname.toString(), tf_lname.toString(), dp_dob.getValue().toString(), cb_doctor.getValue().getNpi().toString()};
//                scheduleDoc(tokens);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reschedule ALert");
                alert.setHeaderText(null);
                alert.setContentText("Appointments with Technicians can not be rescheduled.");
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(480, 150);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void scheduleApp(ActionEvent event) {
        String missing = checkMissingData();
        if (!missing.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText(missing);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);

            alert.showAndWait();
        }
        else {
            if (tg_apptype.getSelectedToggle().equals(rb_office)) {
                String[] tokens = {"D", convertDate(dp_appdate.getValue()),
                        Integer.toString(cb_timeslot.getSelectionModel().getSelectedIndex() + 1),
                        tf_fname.toString(), tf_lname.toString(), convertDate(dp_dob.getValue()),
                        cb_doctor.getValue().getNpi()};
                scheduleDoc(tokens);
            }
            else {
                String[] tokens = {"T", convertDate(dp_appdate.getValue()),
                        Integer.toString(cb_timeslot.getSelectionModel().getSelectedIndex() + 1),
                        tf_fname.toString(), tf_lname.toString(), convertDate(dp_dob.getValue()),
                        cb_imaging.getValue().toString()};
                scheduleTech(tokens);
            }
        }
    }

    @FXML
    public void setOffice(ActionEvent event) {
        txt_appprovider.setOpacity(1.0);
        cb_doctor.setDisable(false);
        txt_appimaging.setOpacity(0.25);
        cb_imaging.setDisable(true);
    }

    @FXML
    public void setImaging(ActionEvent event) {
        txt_appprovider.setOpacity(0.25);
        cb_doctor.setDisable(true);
        txt_appimaging.setOpacity(1.0);
        cb_imaging.setDisable(false);
    }

    /**
     * Helper method that handles print by appointment command: prints appointments sorted by appointment date, time, then provider's name.
     */
    private void printByAppointment() {
        Sort.appointment(appointmentList, Sort.PA_CMD);
       if (this.appointmentList.isEmpty()) {
           TA_printInfo.appendText("Schedule calendar is empty. \n");
            return;
        }
        TA_printInfo.appendText("** List of appointments, ordered by date/time/provider.\n");
        printAppointmentList(this.appointmentList);
    }
     /**
     * Helper method to print out all the Appointments in List of Appointments.
     */
    private void printAppointmentList(List<Appointment> appList) {
        for (Appointment a : appList) {
            TA_printInfo.appendText(a+ "\n");
        }
        TA_printInfo.appendText("** End of List **");
    }
     /**
     * Helper method that handles print by patient command: prints appointments sorted by patient (lname, fname, dob) then appointment date and time.
     */
    private void printByPatient() {
        Sort.appointment(appointmentList, Sort.PP_CMD);
        if (this.appointmentList.isEmpty()) {
            TA_printInfo.appendText("Schedule calendar is empty. \n");
            return;
        }
        TA_printInfo.appendText("** List of appointments, ordered by patient profile/date/time.\n");
        printAppointmentList(this.appointmentList);
    }
     /**
     * Helper method that handles print by location command: prints appointments sorted by county name, then appointment date and time.
     */
    private void printByLocation() {
        Sort.appointment(appointmentList, Sort.PL_CMD);
        if (this.appointmentList.isEmpty()) {
            TA_printInfo.appendText("Schedule calendar is empty. \n");
            return;
        }
        TA_printInfo.appendText("** List of appointments, ordered by county/date/time. \n");
        printAppointmentList(this.appointmentList);
    }
       /**
       * Helper method that handles print bill statements command: prints bills sorted by patient profile.
     */
    private void printBill() {
        if (this.appointmentList.isEmpty()) {
            TA_printInfo.appendText("Schedule calendar is empty.\n");
            return;
        }
        Sort.patient(patientList);
        TA_printInfo.appendText("** Billing statement ordered by patient. **");
        for (int index = 0; index < this.patientList.size(); index++) {
            TA_printInfo.appendText("(" + (index + 1) + ") " + this.patientList.get(index).toString()+"/n");
        }

        TA_printInfo.appendText("** end of list **");

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
            TA_printInfo.appendText("Schedule calendar is empty.\n");
            return;
        }
        TA_printInfo.appendText("** List of office appointments ordered by county/date/time.\n");
        printAppointmentList(docAppList);
    }
        /**
     * Helper method that handles print by imaging command: prints imaging appointments sorted by county name, then date and time.
     */
    private void printByImaging() {
        List<Appointment> techAppList = findTechAppointments();
        Sort.appointment(techAppList, Sort.PL_CMD);
        if (techAppList.isEmpty()) {
            TA_printInfo.appendText("Schedule calendar is empty.\n");
            return;
        }
        TA_printInfo.appendText("** List of radiology appointments ordered by county/date/time.\n");
        printAppointmentList(techAppList);
   }
    /**
     //     * Helper method to sort out doctor appointments from within the List of all Appointments.
     //     *
     //     * @return List of Appointments with Doctor providers.
     //     */
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
     //     * Helper method to sort out technician appointments from within the List of all Appointments.
     //     *
     //     * @return List of Appointments with Technician providers.
     //     */
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
     //     * Helper method that handles print credit for providers command: prints expected credit amounts for providers sorted by provider profile.
     //     */
    private void printCredit() {
        Sort.provider(providersList);
        calculateCredit();
    }

    /**
     * Helper method that calculates and prints the total expected credit amounts for providers for seeing patients.
     */
    private void calculateCredit() {
        if (this.appointmentList.isEmpty()) {
            TA_printInfo.appendText("Schedule calendar is empty.\n");
            return;
        }
        TA_printInfo.appendText("** Credit amount ordered by provider. **");
        int index = 1;
        for (Provider p : providersList) {
            int credit = 0;
            for (Appointment a : appointmentList) {
                if (a.getProvider().equals(p)) {
                    credit += p.rate();
                }
            }
            TA_printInfo.appendText("(" + index + ") " + p.getProfile().toString() +
                    " [credit amount: $" + String.format("%,d", credit) + ".00]" +"\n");
           index++;
        }
        TA_printInfo.appendText("** end of list **\n");
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
     * Helper method to schedule an appointment with a doctor.
     *
     * @param tokens array of details to schedule an appointment with a doctor, including doctor's npi
     */
    private void scheduleDoc(String[] tokens) {
        if (isAppDatesTimeValid(tokens)) {
            ta_output.clear();
            Date appDate = new Date(tokens[1]);
            Timeslot timeslot = new Timeslot(Integer.parseInt(tokens[2]));
            Profile profile = new Profile(tokens[3], tokens[4], new Date(tokens[5]));
            Patient patient = new Patient(profile);
            Doctor provider = new Doctor();
            if (this.appointmentList.contains(new Appointment(appDate, timeslot, patient, provider))) {
                ta_output.setText(patient.getProfile().toString() + " has an existing appointment at the same time slot.");
            } else if (!checkDigits(tokens[6])) {
                ta_output.setText(tokens[6] + " - provider does not exist.");
            } else {
                provider = findDoctor(tokens[6]);

                if (provider == null) {
                    ta_output.setText(tokens[6] + " - provider does not exist.");
                } else if (!isProviderAvailable(provider, appDate, timeslot)) {
                    ta_output.setText(provider.toString() + " is not available at slot " + tokens[2] + ".");
                } else {
                    Patient newPatient = findPatient(patient);
                    Appointment newApp = new Appointment(appDate, timeslot, newPatient, provider);
                    appointmentList.add(newApp);
                    newPatient.addVisit(newApp);
                    ta_output.setText(newApp.toString() + " booked.");
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
            if (this.appointmentList.contains(new Appointment(appDate, timeslot, patient, provider))) {
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
     * Checks if there are a valid number of tokens in the command and if the dates and times are valid.
     *
     * @param tokens array of details to schedule an appointment with a doctor, including doctor's npi
     * @return true if there are a valid number of tokens in the command and if the dates and times are valid, return false otherwise
     */
    private boolean isAppDatesTimeValid(String[] tokens) {
        if (!checkDateDigits(tokens[1], "appointment") || !isAppDateValid(tokens[1])) {
            return false;
        } else if (!checkDigits(tokens[2]) || !isTimeslotValid(tokens[2])) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText(tokens[2] + " is not a valid time slot.");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);
            alert.showAndWait();
            return false;
        } else if (!checkDateDigits(tokens[1], "dob") || !isDobValid(tokens[5])) {
            return false;
        }

        return true;
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
     * Check if an intended Date object contains only numerical characters.
     *
     * @param s String version of date to be checked.
     * @return true if the intended Date object contains only numerical characters disregarding the '/'s, false otherwise
     */
    private boolean checkDateDigits(String s, String use) {
        String[] dateVals = s.split("-");

        for (String d : dateVals) {
            if (!checkDigits(d)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);

                String error = "";
                if (use.equals("appointment")) {
                    error = error + "Appointment date: ";
                } else {
                    error = error + "Patient dob: ";
                }
                error = error + s + " is not a valid calendar date";

                alert.setContentText(error);
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(480, 150);
                alert.showAndWait();

                return false;
            }
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

        String error = "";
        if (!appDate.isValid()) {
            error = error.concat("Appointment date: " + date + " is not a valid calendar date");
        } else if (appDate.isToday() || appDate.isPast()) {
            error = error.concat("Appointment date: " + date + " is today or a date before today.");
        } else if (appDate.isWeekend()) {
            error = error.concat("Appointment date: " + date + " is Saturday or Sunday.");
        } else if (!appDate.isInSixMonths()) {
            error = error.concat("Appointment date: " + date + " is not within six months.");
        }

        if (!error.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);
            alert.showAndWait();
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

        String error = "";
        if (!dob.isValid()) {
            error = error.concat("Patient dob: " + date + " is not a valid calendar date");
        } else if (dob.isToday() || dob.isFuture()) {
            error = error.concat("Patient dob: " + date + " is today or a day after today.");
        }
        if (!error.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 150);
            alert.showAndWait();
        }
        return dob.isValid() && !dob.isToday() && !dob.isFuture();
    }

    /**
     * Checks if any data in the update appointments tab is empty.
     * @return String with alert message to input any missing data
     */
    private String checkMissingData() {
        String missing = "";
        int missingCount = 0;
        if (tf_fname.getText().isEmpty()) {
            missing = missing + ", first name of the patient";
            missingCount++;
        }
        if (tf_lname.getText().isEmpty()) {
            missing = missing + ", last name of the patient";
            missingCount++;
        }
        if (dp_dob.getValue() == null) {
            missing = missing + ", date of birth of the patient";
            missingCount++;
        }
        if (dp_appdate.getValue() == null) {
            missing = missing + ", date of the appointment";
            missingCount++;
        }
        if (cb_timeslot.getValue() == null) {
            missing = missing + ", time slot of the appointment";
            missingCount++;
        }
        if (tg_apptype.getSelectedToggle().equals(rb_office) && cb_doctor.getValue() == null) {
            missing = missing + ", provider of the appointment";
            missingCount++;
        }
        if (tg_apptype.getSelectedToggle().equals(rb_imaging) && cb_imaging.getValue() == null) {
            missing = missing + ", imaging type of the appointment";
            missingCount++;
        }

        if (missingCount == 1) {
            missing = "The " + missing.substring(2) + " needs to be inputted.";
        }
        else if (missingCount == 2) {
            missing = "The " + missing.substring(2, missing.lastIndexOf(",")) + " and " +
                    missing.substring(missing.lastIndexOf(",") + 2) + " need to be inputted.";
        }
        else if (missingCount > 1) {
            missing = "The " + missing.substring(2, missing.lastIndexOf(",")) + ", and " +
                    missing.substring(missing.lastIndexOf(",") + 2) + " need to be inputted.";
        }

        return missing;
    }

    /**
     * Loads in the appointment time slots to the timeslotList
     */
    private void loadTimeslots() {
        for (int i = Timeslot.EARLIEST_TIMESLOT; i <= Timeslot.LATEST_TIMESLOT; i++) {
            this.timeslotList.add(new Timeslot(i));
        }
    }

    private String convertDate(LocalDate date) {
        String d = date.toString();
        String[] dateFormat = d.split("-");
        return dateFormat[1] + "/" + dateFormat[2] + "/" + dateFormat[0];
    }
}
