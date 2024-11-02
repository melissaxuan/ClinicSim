module projects.ruclinic.enhancedgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens projects.ruclinic.enhancedgui to javafx.fxml;
    exports projects.ruclinic.enhancedgui;
    opens projects.ruclinic.enhancedgui.ruclinic to javafx.fxml;
    exports projects.ruclinic.enhancedgui.ruclinic;
}