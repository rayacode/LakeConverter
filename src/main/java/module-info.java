module ir.artlake.lakeconverter {
    requires javafx.controls;
    requires javafx.fxml;

   // requires atlantafx.base;
    requires jave.core;
    requires org.jetbrains.annotations;
    requires org.slf4j;
    requires jaudiotagger;
    requires org.apache.commons.collections4;

    opens ir.artlake.lakeconverter to javafx.fxml;
    exports ir.artlake.lakeconverter;
    exports ir.artlake.lakeconverter.fileoperations;
    opens ir.artlake.lakeconverter.fileoperations to javafx.fxml;
}