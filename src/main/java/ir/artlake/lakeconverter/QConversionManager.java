package ir.artlake.lakeconverter;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.concurrent.*;

import java.util.concurrent.*;

import java.util.concurrent.*;

public class QConversionManager {

    private final Map<File, FileConverterInit> fileConverterInitMap;

    public QConversionManager(Map<File, FileConverterInit> fileConverterInitMap) {
        this.fileConverterInitMap = fileConverterInitMap;
    }

    public void startConversions() {
        for (FileConverterInit fileConverterInit : fileConverterInitMap.values()) {
            fileConverterInit.startConversion();
        }
    }
}
