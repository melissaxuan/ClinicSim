package projects.ruclinic.enhancedgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import projects.ruclinic.enhancedgui.ruclinic.Doctor;
import projects.ruclinic.enhancedgui.ruclinic.Provider;
import projects.ruclinic.enhancedgui.ruclinic.Radiology;
import projects.ruclinic.enhancedgui.ruclinic.Specialty;
import projects.ruclinic.enhancedgui.ruclinic.Technician;
import projects.ruclinic.enhancedgui.util.List;
import projects.ruclinic.enhancedgui.util.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import projects.ruclinic.enhancedgui.ruclinic.Appointment;
import projects.ruclinic.enhancedgui.ruclinic.Patient;
import projects.ruclinic.enhancedgui.ruclinic.Provider;
import projects.ruclinic.enhancedgui.ruclinic.Technician;
import projects.ruclinic.enhancedgui.ruclinic.Doctor;
import projects.ruclinic.enhancedgui.util.List;
import projects.ruclinic.enhancedgui.util.Sort;
import projects.ruclinic.enhancedgui.ruclinic.Location;

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
    private ComboBox<String> cb_imaging;

    @FXML
    private ComboBox<String> cb_provider;

    @FXML
    private ComboBox<String> cb_sortSelecter;

    @FXML
    private ComboBox<String> cb_timeslot;

    @FXML
    private ComboBox<String> cb_printData;

    @FXML
    private ComboBox<String> cb_tablePrint;

    @FXML
    private DatePicker dp_appdate;

    @FXML
    private DatePicker dp_dob;

    @FXML
    private RadioButton rb_apptype;

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
    private TableView<Location> tv_printLocation;

    @FXML
    private TableColumn<Location,String> tc_col1;

    @FXML
    private TableColumn<Location, String> tc_col2;

    @FXML
    private TableColumn<Location, String> tc_col3;

    @FXML
    private TableView<Specialty> tv_printSpecialty;

    @FXML
    private TableColumn<Specialty,String> tc_col4;

    @FXML
    private TableColumn<Specialty, String> tc_col5;

    @FXML
    private TableView<Radiology> tv_printRadiology;

    @FXML
    private TableColumn<Radiology,String> tc_col6;



    private List<Provider> providersList;
    private List<Technician> technicianList;
    private List<Appointment> appointmentList;
    private List<Patient> patientList;
    private int techListPtr;

    /**
     * Default constructor to initialize List and MedicalRecord objects.
     */
    public ClinicManagerController() {
        this.providersList = new List<Provider>();
        this.technicianList = new List<Technician>();
        this.appointmentList = new List<Appointment>();
        this.patientList = new List<Patient>();
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
        cb_tablePrint.getItems().addAll(
                "Print Locations",
                "Print Specialties",
                "Print Radiology Choices"
        );

        cb_provider.setPromptText("(No Provider File Loaded)");
        cb_imaging.setPromptText("(No Provider File Loaded)");
        cb_imaging.getItems().addAll(
                "CAT Scan",
                "Ultrasound",
                "X-Ray"
        );
        tc_col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocName()));
        tc_col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCounty()));
        tc_col3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZip()));
        tv_printLocation.setItems(FXCollections.observableArrayList(Location.values()));

        tc_col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecName()));
        tc_col5.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf((cellData.getValue().getCharge()))));
        tv_printSpecialty.setItems(FXCollections.observableArrayList(Specialty.values()));

        tc_col6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRadName()));
        tv_printRadiology.setItems(FXCollections.observableArrayList(Radiology.values()));
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
    /**
     * Method to convert the event to a string and show the correct table
     * @param event The type of table to be visible
     */
    @FXML
    void cb_tablePrint(ActionEvent event){
        ComboBox<String> comboBox = (ComboBox<String>) event.getSource();
        String tablePrint = comboBox.getValue();
        tv_printLocation.setVisible(false);
        tv_printSpecialty.setVisible(false);
        tv_printRadiology.setVisible(false);
        switch(tablePrint)
        {
            case  "Print Locations":
                tv_printLocation.setVisible(true);
                break;
            case "Print Specialties":
                tv_printSpecialty.setVisible(true);
                break;
            case "Print Radiology Choices":
                tv_printRadiology.setVisible(true);
                break;
            default:
                break;
        }
    }


    /**
     * Helper method to print the locations to the table view
     */
    private void printTableLocations() {

    }

    @FXML
    void clearAppTab(ActionEvent event) {

    }

    @FXML
    void disableImaging(InputMethodEvent event) {

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

                while (scan.hasNextLine()) {
                    input = scan.nextLine();

                    addProvider(input);
                }

                Sort.provider(providersList);
                for (Provider p : providersList) {
                    if (p instanceof Doctor)
                        cb_provider.getItems().add(p.toString());
                }
                cb_provider.setPromptText("(Select a Provider)");
                cb_imaging.setPromptText("(Select an Imaging Service)");
                scan.close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        }
    }

    @FXML
    void rescheduleApp(ActionEvent event) {

    }

    @FXML
    void scheduleApp(ActionEvent event) {

    }

    @FXML
    public void setOffice(ActionEvent event) {
        txt_appprovider.setOpacity(1.0);
        cb_provider.setDisable(false);
        txt_appimaging.setOpacity(0.25);
        cb_imaging.setDisable(true);
    }

    @FXML
    public void setImaging(ActionEvent event) {
        txt_appprovider.setOpacity(0.25);
        cb_provider.setDisable(true);
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
        System.out.println("** Billing statement ordered by patient. **");
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
     * Helper method to sort out doctor appointments from within the List of all Appointments.
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
          * Helper method that handles print credit for providers command: prints expected credit amounts for providers sorted by provider profile.
     /    */
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


}
