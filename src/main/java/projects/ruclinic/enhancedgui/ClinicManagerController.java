package projects.ruclinic.enhancedgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import projects.ruclinic.enhancedgui.ruclinic.Appointment;
import projects.ruclinic.enhancedgui.ruclinic.Patient;
import projects.ruclinic.enhancedgui.ruclinic.Provider;
import projects.ruclinic.enhancedgui.ruclinic.Technician;
import projects.ruclinic.enhancedgui.util.List;
import projects.ruclinic.enhancedgui.util.Sort;

public class ClinicManagerController {
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
    }
    @FXML
    private TextArea TA_printInfo;

    @FXML
    private Button bt_cancel;

    @FXML
    private Button bt_clear;

    @FXML
    private Button bt_reschedule;

    @FXML
    private Button bt_schedule;

    @FXML
    private ComboBox<?> cb_imaging;

    @FXML
    private ComboBox<?> cb_provider;

    @FXML
    private ComboBox<String> cb_sortSelecter;

    @FXML
    private ComboBox<?> cb_timeslot;

    @FXML
    private DatePicker dp_appdate;

    @FXML
    private DatePicker dp_dob;

    @FXML
    private RadioButton rb_office;

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
    void cancelApp(ActionEvent event) {

    }

    @FXML
    void cb_sortSelecter(ActionEvent event) {
       String sortSelected = String.valueOf(event);
        switch (sortSelected) {
            case "Print by Appointment":
                printByAppointment();
                break;
            case "Print by Patient":
          //      printByPatient();
                break;
            case "Print by Location ":
          //      printByLocation();
                break;
            case "Print Bill":
           //     printBill();
                break;
            case "Print by Office":
           //     printByOffice();
                break;
            case "Print by Imaging":
           //     printByImaging();
                break;
           case "Print Credit":
           //     printCredit();
                break;
            default:
                break;
       }

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
    void getAppImaging(ActionEvent event) {

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
    void rescheduleApp(ActionEvent event) {

    }

    @FXML
    void scheduleApp(ActionEvent event) {

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

}
