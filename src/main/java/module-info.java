module ir.artlake.lakeconverter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jave.core;
    requires org.jetbrains.annotations;
    requires org.slf4j;

    opens ir.artlake.lakeconverter to javafx.fxml;
    exports ir.artlake.lakeconverter;
}