package com.iessanalberto.dam1.ray_barber_shop.screen;

import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;
import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.services.CitasService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class EditCitaScreen {
    private VBox root = new VBox();
    private GridPane grid = new GridPane();
    private HBox filaHora = new HBox();

    private Label lblFecha = new Label("Fecha");
    private DatePicker dpFecha = new DatePicker();
    private Label lblHora = new Label("Hora");
    private ComboBox<String> cbHora = new ComboBox<>();
    private ComboBox<String> cbMinuto = new ComboBox<>();
    private Label lblPuntos = new Label(":");
    private Label lblCliente = new Label("Cliente");
    private TextField txtCliente = new TextField();
    private Label lblDescripcion = new Label("Descripción");
    private TextField txtDescripcion = new TextField();
    private Button btnBorrar = new Button("Borrar");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnGuardar = new Button("Guardar Cambios");

    private CitasService citasService = new CitasService();

    public EditCitaScreen(Usuario usuario, CitaTbl cita) {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        grid.setVgap(15);
        grid.setHgap(15);
        filaHora.setSpacing(5);

        // Rellenar horas (08 a 21 por ejemplo)
        for (int i = 8; i <= 21; i++) {
            cbHora.getItems().add(String.format("%02d", i));
        }
        // CAMBIO: Bucle de 5 en 5 minutos
        for (int i = 0; i < 60; i += 5) {
            cbMinuto.getItems().add(String.format("%02d", i));
        }

        filaHora.getChildren().addAll(cbHora, lblPuntos, cbMinuto);
        grid.add(lblFecha, 0, 0); grid.add(dpFecha, 1, 0);
        grid.add(lblHora, 0, 1); grid.add(filaHora, 1, 1);
        grid.add(lblCliente, 0, 2); grid.add(txtCliente, 1, 2);
        grid.add(lblDescripcion, 0, 3); grid.add(txtDescripcion, 1, 3);

        HBox botonesCaja = new HBox(10, btnCancelar, btnBorrar, btnGuardar);
        grid.add(botonesCaja, 1, 4);
        root.getChildren().add(grid);

        // Estilos
        btnGuardar.getStyleClass().add("button-primary");
        if (btnBorrar != null) btnBorrar.getStyleClass().add("button-danger");
        txtCliente.setMinWidth(300);
        txtDescripcion.setMinWidth(300);
        // Cargar datos actuales
        dpFecha.setValue(LocalDate.parse(cita.getFecha()));
        cbHora.setValue(cita.getHora().substring(0, 2));
        cbMinuto.setValue(cita.getHora().substring(3));
        txtCliente.setText(cita.getCliente());
        txtDescripcion.setText(cita.getDescripcion());

        btnCancelar.setOnAction(e -> Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario));

        btnBorrar.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar cita de " + cita.getCliente() + "?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        citasService.deleteCita(cita.getId_cita());
                        Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario);
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                    }
                }
            });
        });

        btnGuardar.setOnAction(e -> {
            try {
                String nuevaHora = cbHora.getValue() + ":" + cbMinuto.getValue();
                citasService.updateCita(new CitaTbl(
                        cita.getId_cita(),
                        dpFecha.getValue().toString(),
                        nuevaHora,
                        txtCliente.getText(),
                        txtDescripcion.getText()
                ));
                new Alert(Alert.AlertType.INFORMATION, "Cita actualizada").showAndWait();
                Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });
    }
    public VBox getRoot() { return root; }
}