package Util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;


/**
 * Clase de utilidades para campos de texto, lanzamiento de errores y funciones 
 * de vista varias.
 * @author Paulo Aguilar
 */
public class Graficos {
    
    /**
     * Conveirte un TextField en un campo que solo admite digitos y '-'
     * @param textField Campo de texto donde se aplicará la restricción.
     */
    public static void convertirEnInputEnteros(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[-,0,1,2,3,4,5,6,7,8,9]")) {
                    textField.setText(newValue.replaceAll("[^-,0,1,2,3,4,5,6,7,8,9]", ""));
                }
            }
        });
    }
    
    /**
     * Conveirte un TextField en un campo que solo admite digitos, ',' y '-'
     * @param textField Campo de texto donde se aplicará la restricción.
     */
    public static void convertirEnInputFlotantes(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[-,0,1,2,3,4,5,6,7,8,9,.]")) {
                    textField.setText(newValue.replaceAll("[^-,0,1,2,3,4,5,6,7,8,9,.]", ""));
                }
            }
        });
    }

    /**
     * Lanza una alerta de error con un encabezado y un comentario corto.
     * @param header Encabezado de la ventana
     * @param content Texto corto describiendo el eror.
     */
    public static void lanzarMensajeError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Error - Numeric Blacks");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Carga los datos de una matriz de datos en un tableview de JavaFx, 
     * utilizando sus encabezados 
     * @param tableview TableView sobre el que se va a aplicar la carga
     * @param data Matriz de datos en String que se carga en las celdas
     * @param headers Encabezados de el tableview
     */
    public static void cargarEnTableView(TableView tableview, String[][] data, String[] headers){
        ObservableList<ObservableList> dataColumns = FXCollections.observableArrayList();
        String head = "#";
        tableview.getColumns().clear();
        for (int i = -1; i < headers.length; i++) {
            final int j = i+1;
            head = i >=0 ? headers[i]: head;
            TableColumn col = new TableColumn(head);
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(col);
        }

        for (int i= 0; i < data.length; i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(data[i][0] != null  ? i+1+"": "");
            for (int j = 0; j < headers.length; j++) {
                row.add(data[i][j] == null ? "" : data[i][j]);
            }
            dataColumns.add(row);
        }
        tableview.setItems(dataColumns);
    }
    
        /**
     * Carga los datos de una matriz de datos en un tableview de JavaFx, 
     * utilizando sus encabezados 
     * @param tableview TableView sobre el que se va a aplicar la carga
     * @param data Matriz de datos en String que se carga en las celdas
     * @param headers Encabezados de el tableview
     */
    public static void cargarEnTableViewBairstow(TableView tableview, String[][] data, String[] headers){
        ObservableList<ObservableList> dataColumns = FXCollections.observableArrayList();
        String head = "";
        tableview.getColumns().clear();
        for (int i = 0; i < headers.length; i++) {
            final int j = i;   
            head =headers[i]; 
            TableColumn col = new TableColumn(head);
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(col);
        }

        for (int i= 0; i < data.length; i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int j = 0; j < headers.length; j++) {
                row.add(data[i][j] == null ? "" : data[i][j]);
            }
            dataColumns.add(row);
        }
        tableview.setItems(dataColumns);
    }
    
   
    
    /**
     * Valida y retorna un número entero y remarca con un estilo de error.
     * @param txt TextField de entrada
     * @return Valor numérico extraido del textField o null en caso de error
     */
    public static Integer validarTextFieldEnteros(TextField txt) {
        Integer d = null;
        try {
            d = Integer.parseInt(txt.getText());
            txt.getStyleClass().remove("error");
        } catch (Exception e) {
            txt.getStyleClass().add("error");            
        }
        return d;
    }
    
    /**
     * Valida y retorna un número no entero y remarca con un estilo de error.
     * @param txt TextField de entrada
     * @return Valor numérico extraido del textField o null en caso de error
     */
    public static Double validarTextFieldDouble(TextField txt) {
        Double d = null;
        try {
            d = Double.parseDouble(txt.getText());
            txt.getStyleClass().remove("error");
        } catch (Exception e) {
            txt.getStyleClass().add("error");            
        }
        return d;
    }
}
