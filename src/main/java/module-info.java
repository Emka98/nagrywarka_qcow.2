module pl.nagrywarka {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens pl.nagrywarka to javafx.fxml;
    exports pl.nagrywarka;
}
