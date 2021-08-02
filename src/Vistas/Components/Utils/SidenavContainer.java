package Vistas.Components.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Contenedor de items de un menú de navegación lateral.
 * @author Paulo Aguilar
 */
public class SidenavContainer extends HBox {

    private Pane iconsPane;
    private VBox itemsPane;
    private int openPane;
    private Node OvelayPane;    
    
    /**
     * Contstructor de la clase
     * @param managerButton Nodo que llevará el control de cuando se abre/cierra el menú
     * @param OvelayPane Nodo que contiene la capa transparentable de fondo 
     * utilizada en la animación 
     */
    public SidenavContainer( Node managerButton, Node OvelayPane) {
        super();
        this.OvelayPane = OvelayPane;
        this.iconsPane = new Pane();
        this.iconsPane.getStyleClass().add("side-navbar");
        this.iconsPane.setPrefWidth(64);
        this.itemsPane = new VBox(0);
        this.itemsPane.getStyleClass().add("side-navbar-items-container");
        this.itemsPane.setFillWidth(true);
        this.itemsPane.setPrefWidth(280);
        this.setPrefWidth(358);
        this.getChildren().addAll(iconsPane, itemsPane);
        
        managerButton.setOnMouseClicked(e -> hideOrShow());
        openPane = 1;
        hideOrShow();
    }
    
    /**
     * Añade un item al menú con su ícono.
     * @param item Item colapsable de navegación.
     * @param icon Imageview del ícono asociado al item colapsable.
     */
    public void addItem(SidenavItem item, ImageView icon) {
        int size = iconsPane.getChildren().size();
        iconsPane.setLayoutX(19);
        iconsPane.getChildren().add(icon);
        itemsPane.getChildren().add(item);
        icon.setScaleY(icon.getScaleY()*0.8);
        icon.setScaleX(icon.getScaleX()*0.8);
        icon.setOnMouseClicked(e -> hideOrShow());
        item.setHeightTopButton(icon.getFitHeight()+20);
        icon.layoutYProperty().bind(item.layoutYProperty().add(10));
    }
    
    /**
     * Abre o cierra el menú lateral
     */
    public void hideOrShow() {
        if (openPane == 1) {
            openPane = -1;
            FadeTransition fadeTransition1 = new FadeTransition(
                    Duration.seconds(0.2),
                    OvelayPane
            );
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(1);
            fadeTransition1.play();
            
            TranslateTransition translateTransition1 = new TranslateTransition(
                    Duration.seconds(0.5),
                    itemsPane
            );
            translateTransition1.setByX(-600);
            translateTransition1.play();
            translateTransition1.setOnFinished(
                    e -> {
                        openPane = 0;
                    }
            );
        } else if (openPane == 0) {
            openPane = -1;
            FadeTransition fadeTransition1 = new FadeTransition(
                    Duration.seconds(0.5),
                    OvelayPane
            );
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();
            
            TranslateTransition translateTransition1 = new TranslateTransition(
                    Duration.seconds(0.5),
                    itemsPane
            );
            translateTransition1.setByX(+600);
            translateTransition1.play();
            translateTransition1.setOnFinished(
                    e -> {
                        openPane = 1;
                    }
            );
        }
    }
}
