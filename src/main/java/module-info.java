module ir.artlake.lakeconverter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jave.core;

    opens ir.artlake.lakeconverter to javafx.fxml;
    exports ir.artlake.lakeconverter;
}