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

package codes.rayacode.lakeconverter.fileoperations;

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

        if (lastTargetDirectory != null) {
            targetChooser.setInitialDirectory(lastTargetDirectory);
        }
        File selectedDirectory = targetChooser.showDialog(stage);
        if (selectedDirectory != null) {
            lastTargetDirectory = selectedDirectory;
        }
        return selectedDirectory;
    }
}