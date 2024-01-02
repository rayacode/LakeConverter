package ir.artlake.lakeconverter;

import java.util.Iterator;
import java.util.List;

import java.util.concurrent.*;

import java.util.concurrent.*;

import java.util.concurrent.*;

public class QConversionManager {

    private final List<FileConverterInit> fileConverterInitList;

    public QConversionManager(List<FileConverterInit> fileConverterInitList) {
        this.fileConverterInitList = fileConverterInitList;
    }

    public void startConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitList) {
            fileConverterInit.startConversion();
        }
    }
}
