package com.iessanalberto.dam1.ray_barber_shop.screen;

import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;
import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.services.CitasService;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainScreen {
    // Elementos de layout
    private VBox root = new VBox();
    private HBox fila1 = new HBox();
    private HBox filaButtons = new HBox();
    // Componentes de la ventana
    private Label lblFecha = new Label("Fecha");
    private DatePicker dpFecha = new DatePicker();
    private Button btnBuscar = new Button("Buscar");
    private Button btnNueva = new Button("Nueva");
    private Button btnVolver = new Button("Volver");
    HBox spacer = new HBox();


    // Añadimos la tabla con las citas
    private TableView<CitaTbl> tblCitas = new TableView<>();
    // Añadimos las columnas de la tabla
    private TableColumn<CitaTbl, String> colFecha = new TableColumn<>("Fecha");
    private TableColumn<CitaTbl, String> colHora = new TableColumn<>("Hora");
    private TableColumn<CitaTbl, String> colCliente = new TableColumn<>("Cliente");
    private TableColumn<CitaTbl, String> colDescripcion = new TableColumn<>("Descripción");

    private CitasService citasService = new CitasService();


    public MainScreen(Usuario usuario) {
        // Configuramos elementos de layout
        root.setPadding(new Insets(25));
        root.setSpacing(20);
        fila1.setSpacing(15);
        fila1.setAlignment(Pos.CENTER_LEFT);
        filaButtons.setSpacing(10);
        filaButtons.setAlignment(Pos.CENTER_RIGHT);
        Label title = new Label("AGENDA DE CITAS");
        title.getStyleClass().add("title-label");
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        btnBuscar.getStyleClass().add("button-secondary");
        btnNueva.getStyleClass().add("button-primary");
        btnVolver.getStyleClass().add("button-danger");
        // Asignamos los componentes al layout correspondiente
        fila1.getChildren().addAll(lblFecha, dpFecha, btnBuscar);
        filaButtons.getChildren().addAll(spacer, btnNueva, btnVolver);
        VBox.setVgrow(tblCitas, javafx.scene.layout.Priority.ALWAYS);
        root.getChildren().addAll(title, fila1, tblCitas, filaButtons);
        // Asignamos valores por defecto al datepicker
        dpFecha.setValue(LocalDate.now());
        dpFecha.setShowWeekNumbers(false);

        // Asignar las columnas a la tabla
        tblCitas.getColumns().addAll(colFecha, colHora, colCliente, colDescripcion);
        // Quita la columna que sobra a la derecha
        tblCitas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha()));
        colHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
        colCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));



        try {
            tblCitas.getItems().addAll(citasService.getCitasByFecha(String.valueOf(dpFecha.getValue())));
        } catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al recuperar las citas");
            alert.setHeaderText(null);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
        // Añadir la interactividad a la ventana
        btnBuscar.setOnAction(actionEvent -> {
            try {
                tblCitas.getItems().clear();
                tblCitas.getItems().addAll(citasService.getCitasByFecha(String.valueOf(dpFecha.getValue())));
            } catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error al recuperar las citas");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        });
        btnNueva.setOnAction(actionEvent -> {
            Navigation.navigate(Navigation.Screen.NEW_CITAS_SCREEN, usuario);
        });

        btnVolver.setOnAction(actionEvent -> {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Cerrar Sesión");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Estás seguro de que deseas cerrar la sesión?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Al navegar al LOGIN_SCREEN (que no recibe objeto Usuario),
                    // la referencia se pierde y la sesión queda "limpia".
                    Navigation.navigate(Navigation.Screen.LOGIN_SCREEN);
                }
            });
        });

        // Doble click en una fila de la ventana
        tblCitas.setRowFactory(citaTblTableView -> {
            TableRow<CitaTbl> rowSelected = new TableRow<>();
            rowSelected.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !rowSelected.isEmpty()) {
                    CitaTbl citaTbl = rowSelected.getItem();
                    Navigation.navigate(Navigation.Screen.EDIT_CITA_SCREEN,usuario,citaTbl);
                }
            });
            return rowSelected;
        });
    }

    public VBox getRoot() {
        return root;
    }
}

