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

public class ClinicManagerController {

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
    private ComboBox<?> cb_sortSelecter;

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

}
