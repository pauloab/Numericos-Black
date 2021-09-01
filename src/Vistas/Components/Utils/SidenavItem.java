package Vistas.Components.Utils;

import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Componente de item colapsable de un menú lateral vertical
 *
 * @author Paulo Aguilar
 */
public class SidenavItem extends VBox {

    private VBox vBoxItems;
    private JFXButton topButton;
    private Transition collapseAnimation;
    private Transition collapseUpAniation;
    private double startSize;
    private boolean isOpened;

    /**
     * Cnstrutor de la clase
     *
     * @param mainName Cadena de texto que irá en el botón principal que
     * colapsará los subitems.
     * @param buttonList Lista de botones que serán los subItems del componente.
     */
    public SidenavItem(String mainName, ArrayList<JFXButton> buttonList) {
        super();
        this.topButton = new JFXButton(mainName);
        this.topButton.setPrefWidth(280);
        this.topButton.setAlignment(Pos.CENTER_LEFT);
        this.isOpened = false;
        this.vBoxItems = new VBox();
        this.vBoxItems.setPadding(new Insets(0, 0, 0, 20));
        for (JFXButton b : buttonList) {
            b.setPrefHeight(40);
            b.setPrefWidth(260);
            b.getStyleClass().add("side-navbar-subitem");
            b.setAlignment(Pos.CENTER_LEFT);
            this.vBoxItems.getChildren().add(b);
        }
        this.topButton.getStyleClass().setAll("side-navbar-item");

        this.getChildren().addAll(topButton, vBoxItems);
        this.topButton.setOnMouseClicked(event -> {
            playCollapse();
            ((SidenavContainer)this.getParent().getParent()).collapseUpExcept(this);
        });
        this.vBoxItems.setMinHeight(0);
        this.vBoxItems.setFillWidth(true);

        startSize = 32 * buttonList.size();
        collapseAnimation = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
            }

            @Override
            protected void interpolate(double frac) {
                vBoxItems.setPrefHeight(startSize * (frac));
                vBoxItems.setOpacity(frac);
            }
        };
        collapseUpAniation = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
            }

            @Override
            protected void interpolate(double frac) {
                vBoxItems.setPrefHeight(startSize * (1 - frac));
                vBoxItems.setOpacity(1 - frac);
            }
        };
        collapseUpAniation.play();
    }

    /**
     * Establece la altura del botón principal
     *
     * @param h
     */
    public void setHeightTopButton(double h) {
        topButton.setPrefHeight(h);
    }

    /**
     * Reproduce la animación de colapsar o de-colapsar los subitems
     */
    public void playCollapse() {
        isOpened = !isOpened;
        if (!isOpened) {
            collapseUpAniation.play();
        } else {
            collapseAnimation.play();
        }
        
    }

    public boolean isOpened() {
        return isOpened;
    }
    
}
