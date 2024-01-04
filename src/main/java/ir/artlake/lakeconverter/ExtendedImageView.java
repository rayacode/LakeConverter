package ir.artlake.lakeconverter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExtendedImageView extends ImageView {
    private String imagePath;

    public ExtendedImageView() {
        super();
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        super.setImage(new Image(imagePath));
    }
}