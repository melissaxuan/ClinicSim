module projects.ruclinic.enhancedgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens projects.ruclinic.enhancedgui to javafx.fxml;
    exports projects.ruclinic.enhancedgui;
}