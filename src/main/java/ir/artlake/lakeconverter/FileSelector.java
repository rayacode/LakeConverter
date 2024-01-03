package ir.artlake.lakeconverter;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class FileSelector {
    private FileChooser sourceChooser;
    private DirectoryChooser targetChooser;
    private File lastFileDirectory;
    private File lastTargetDirectory;
    public List<File> chooseSourceFiles(Stage stage) {
        sourceChooser = new FileChooser();
        sourceChooser.setTitle("Select files to convert");
        if (lastFileDirectory != null) {
            sourceChooser.setInitialDirectory(lastFileDirectory);
        }
        List<File> selectedFiles = sourceChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null) {
            lastFileDirectory = selectedFiles.get(0).getParentFile();
        }
        return selectedFiles;
    }

    public File chooseTargetDirectory(Stage stage) {
        targetChooser = new DirectoryChooser();

        if (lastFileDirectory != null) {
            targetChooser.setInitialDirectory(lastFileDirectory);
        }
        File selectedDirectory = targetChooser.showDialog(stage);
        if (selectedDirectory != null) {
            lastFileDirectory = selectedDirectory;
        }
        return selectedDirectory;
    }
}
