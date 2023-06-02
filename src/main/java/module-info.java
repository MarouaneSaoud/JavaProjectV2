module org.yaam.javaprojectv2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.yaam.javaprojectv2 to javafx.fxml;
    exports org.yaam.javaprojectv2;
    exports org.yaam.javaprojectv2.controller;
    opens org.yaam.javaprojectv2.controller to javafx.fxml;
    requires poi;
    requires poi.ooxml;
    requires org.json;

    exports org.yaam.javaprojectv2.jdbc.entities;
    opens org.yaam.javaprojectv2.jdbc.entities to javafx.fxml;
}