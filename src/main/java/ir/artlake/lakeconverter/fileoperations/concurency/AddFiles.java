package ir.artlake.lakeconverter.fileoperations.concurency;

import ir.artlake.lakeconverter.UIUpdater;
import ir.artlake.lakeconverter.conversion.FileConverterInit;
import ir.artlake.lakeconverter.fileoperations.FileService;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class AddFiles extends Service<Boolean> {
    private Semaphore semaphore;
    private List<File> selectedFiles;
    private FileService fileService;
    private Button convertButton;
    private Button deleteAllButton;
    private UIUpdater uiUpdater;
    private Label filesCounter;
    private boolean isSelected;
    public AddFiles(List<File> selectedFiles,
                    FileService fileService ,
                    Button convertButton,
                    boolean isSelected,
                    UIUpdater uiUpdater,
                    Semaphore semaphore) {
      this.selectedFiles = selectedFiles;
      this.fileService = fileService;
      this.convertButton = convertButton;
      this.isSelected = isSelected;
      this.uiUpdater = uiUpdater;
      this.semaphore = semaphore;


    }

    @Override
    protected Task<Boolean> createTask() {
        return new Adding();
    }


    class Adding extends Task<Boolean> {

        AddFilesProgressListener addFilesProgressListener
                = new AddFilesProgressListener(this::updateProgress);

        @Override
        protected Boolean call() throws Exception {

            //semaphore.acquire();

            try {

                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    System.out.println("I'm played");
                    List<FileConverterInit> newFileConverterInit = fileService.initializeConversions();
                    fileService.addButtonListenersToList(convertButton);
                    uiUpdater.handleFileSelection(selectedFiles, newFileConverterInit, addFilesProgressListener);
                    isSelected = true;
                    Platform.runLater(()->{
                        convertButton.setDisable(false);
                        convertButton.setVisible(true);
                        convertButton.setText("Convert All");
                    });

                    for(FileConverterInit fileConverterInit : newFileConverterInit){
                        System.out.println("testVideoInfo1");

                        File source = new File(fileConverterInit.getSource(),"");
                        MultimediaObject mo = new MultimediaObject(source);
                        MultimediaInfo mi= mo.getInfo();
                        /*AudioInfo ai= mi.getAudio();
                        VideoInfo vi= mi.getVideo();
                        System.out.println(ai.toString());
                        System.out.println(vi.toString());*/
                    }

                    return true;
                } else {
                    Platform.runLater(() ->uiUpdater.showFadeTransition("No file selected!"));

                    return true;
                }
            }
            finally {
                //semaphore.release();
            }

        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            boolean cancelled = super.cancel(mayInterruptIfRunning);

            return cancelled;
        }


    }


}
