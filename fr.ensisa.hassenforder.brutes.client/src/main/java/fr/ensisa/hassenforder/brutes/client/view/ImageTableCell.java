package fr.ensisa.hassenforder.brutes.client.view;

import fr.ensisa.hassenforder.brutes.client.model.INamedPicture;
import fr.ensisa.hassenforder.brutes.client.model.ImagePool;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<S, T> extends TableCell<S, T> {

    private final ImageView imageView;

    public ImageTableCell() {
    	imageView = new ImageView();
    	imageView.setFitWidth(58);
    	imageView.setFitHeight(64);
    	imageView.setPreserveRatio(true);

        setGraphic(imageView);
        setContentDisplay(ContentDisplay.LEFT);
        setMinHeight(64);
        setPrefHeight(64);
        setMaxHeight(64);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText("");
            imageView.setImage(null);
        } else if (item instanceof INamedPicture) {
    		Image image = ImagePool.getINSTANCE().getCharacterImage(((INamedPicture)item).getPicture().get());
    		imageView.setImage(image);
            String name = ((INamedPicture)item).getName().get();
            setText(name);
        } else if (item instanceof Long) {
    		Image image = ImagePool.getINSTANCE().getCharacterImage((Long)item);
    		imageView.setImage(image);
            setText("");
        }
    }
}