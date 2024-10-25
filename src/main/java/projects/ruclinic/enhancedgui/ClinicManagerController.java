package projects.ruclinic.enhancedgui;

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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import projects.ruclinic.enhancedgui.ruclinic.Doctor;
import projects.ruclinic.enhancedgui.ruclinic.Provider;
import projects.ruclinic.enhancedgui.ruclinic.Radiology;
import projects.ruclinic.enhancedgui.ruclinic.Technician;
import projects.ruclinic.enhancedgui.util.List;
import projects.ruclinic.enhancedgui.util.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private ComboBox<Provider> cb_provider;

    @FXML
    private ComboBox<String> cb_sortSelecter;

    @FXML
    private ComboBox<String> cb_timeslot;

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

    private List<Provider> providersList;
    private List<Technician> techniciansList;

    public ClinicManagerController() {
        providersList = new List<Provider>();
        techniciansList = new List<Technician>();
    }

    public void initialize() {

    }

    @FXML
    void cancelApp(ActionEvent event) {

    }

    @FXML
    void cb_sortSelecter(ActionEvent event) {

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
//                printProvidersList();
                ObservableList<Provider> providers;
                providers = FXCollections.observableArrayList();
                cb_provider.setItems(providers);
//                printTechnicianList();

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
        for (int index = techniciansList.size(); index > 0; index--) {
            techniciansList.set(index, techniciansList.get(index - 1));
        }

        techniciansList.set(0, newTechnician);
    }
}
