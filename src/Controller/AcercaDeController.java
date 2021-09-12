package Controller;

import Util.Graficos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Paulo Aguilar
 */
public class AcercaDeController implements Initializable {

    int i;
    
    @FXML
    private ImageView ivLogo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        i=0;
        ivLogo.setOnMouseClicked(event -> {
            if (i==0) {
                Graficos.lanzarMensajeAdvertencia("EasterEgg", "Hey, no toques el logo!");
            }else if (i == 5) {
                Graficos.lanzarMensajeAdvertencia("EasterEgg-2", "Creimos haberte dicho que NO LO HAGAS!");
            }else if (i == 10) {
                Graficos.lanzarMensajeAdvertencia("YA DETENTE!", "POR FAVOR PARA!");
            }else if (i == 20) {
                Graficos.lanzarMensajeAdvertencia("Meh!", "Meh! ya no importa. TU sigue, pequeño anarquista.");
            }else if (i == 35) {
                Graficos.lanzarMensajeAdvertencia("Última advertencia", "DETENTE! Provocarás la autodestrucción del programa :c");
            }else if (i == 45) {
                Graficos.lanzarMensajeAdvertencia("Auto-destrucción", "Protocolo de auto-destrucción iniciado.");
            }else if (i == 50) {
                Graficos.lanzarMensajeAdvertencia(":c", "Estoy cansado");
            }else if (i == 65) {
                Graficos.lanzarMensajeAdvertencia("xd", "El Ing nos cae bien, aunque nos sacó el aire xd.");
            }else if (i == 70) {
                Graficos.lanzarMensajeAdvertencia("Chau", "A mimir.");
                System.exit(0);
            }
            i++;
        });

    }    
    
}
