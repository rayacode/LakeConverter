/*  LakeConverter: A java Gui wrapper with jave for ffmpeg
 *  Copyright (C) 2023 Mohammad Ali Solhjoo mohammadalisolhjoo@live.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package codes.rayacode.lakeconverter;

import codes.rayacode.lakeconverter.conversion.FileConverterInit;
import codes.rayacode.lakeconverter.fileoperations.FileService;
import codes.rayacode.lakeconverter.fileoperations.concurency.AddFilesProgressListener;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UIUpdater {
    private ListView convListView;
    private Label messageLabel;
    private ProgressBar fileAddProgressBar;


    public UIUpdater(){
        

    }
    public void setConvWidgetsContainer(ListView<ConvertCellWidget> convWidgetsContainer, ProgressBar progressBar) {
        this.convListView = convWidgetsContainer;
        this.fileAddProgressBar = progressBar;

        convListView.setItems(items);
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }
    
    public static List<File> addedFiles = new ArrayList<>();
    
    public static ObservableList<ConvertCellWidget> items =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());




    public  synchronized void handleFileSelection(List<File> sourceFiles,
                                     List<FileConverterInit> fileConverterInitList,
                                     AddFilesProgressListener addFilesProgressListener) throws Exception {
        Platform.runLater(()->{
            fileAddProgressBar.setVisible(true);
            fileAddProgressBar.setDisable(false);
        });


        int index;
        System.out.println("s size f size "+sourceFiles.size() + "  " + fileConverterInitList.size());
        for (int i = 0; i < sourceFiles.size() && i < fileConverterInitList.size(); i++) {
            index = i;
            File file = sourceFiles.get(i);
            
            if (!addedFiles.contains(file)) {
                

                ConvertCellWidget fileBox = new ConvertCellWidget();
                fileBox.setConvertCell(fileConverterInitList.get(i), file);
                final int newIndex = index;

                Platform.runLater(()->{

                    UIUpdater.items.add(fileBox);
                    UIUpdater.addedFiles.add(file);

                    addFilesProgressListener.progress(newIndex + 1, sourceFiles.size());

                });

            }
        }
        Platform.runLater(()->{
            fileAddProgressBar.setVisible(false);
            fileAddProgressBar.setDisable(true);
        });


    }
    public void removeFromList(ConvertCellWidget fileBox, File file){
        FileService.everSelectedFiles.remove(file);
        items.remove(fileBox);
        addedFiles.remove(file);



        
    }
    public void removeAllList(){
        FileService.everSelectedFiles.clear();
        items.clear();
        addedFiles.clear();
        FileService.fileConverterInitMap.clear();
    }
    public void showFadeTransition(String message) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        messageLabel.setText(message);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished(event -> {
            messageLabel.setText("");
        });
    }
}