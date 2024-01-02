package ir.artlake.lakeconverter;

import java.util.Iterator;
import java.util.List;

public class QConversionManager {
    private int activeConversions = 0;
    private static final int MAX_CONCURRENT_CONVERSIONS = 3;
    List<FileConverterInit> fileConverterInitList;
    public QConversionManager(List<FileConverterInit> fileConverterInitList) {
        this.fileConverterInitList = fileConverterInitList;
    }
    public void startConversions() {
        Iterator<FileConverterInit> fci_it = fileConverterInitList.iterator();
        while (activeConversions < MAX_CONCURRENT_CONVERSIONS && fci_it.hasNext()) {
            FileConverterInit fileConverterInit = fci_it.next();
            fileConverterInit.startConversion();
            activeConversions++;
        }
    }
}
