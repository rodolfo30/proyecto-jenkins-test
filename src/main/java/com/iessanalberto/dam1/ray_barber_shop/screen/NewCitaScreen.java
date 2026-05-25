package com.iessanalberto.dam1.ray_barber_shop.screen;

import java.time.LocalDate;
import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;
import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.services.CitasService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewCitaScreen {
    private VBox root = new VBox();
    private GridPane grid = new GridPane();
    private HBox filaButtons = new HBox();
    private HBox filaHora = new HBox();

    private TitledPane paneCliente = new TitledPane();
    private Label lblFecha = new Label("Fecha");
    private DatePicker dateFecha = new DatePicker();
    private Label lblHora = new Label("Hora");

    // Selectores para evitar errores de escritura
    private ComboBox<String> cbHora = new ComboBox<>();
    private ComboBox<String> cbMinuto = new ComboBox<>();

    private Label lblCliente = new Label("Cliente");
    private TextField txtCliente = new TextField();
    private Label lblDescripcion = new Label("Descripción");
    private TextField txtDescripcion = new TextField();

    private Button btnGuardar = new Button("Guardar Cita");
    private Button btnCancel = new Button("Cancelar");
    private CitasService citasService = new CitasService();

    public NewCitaScreen(Usuario usuario) {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.getStyleClass().add("main-container");

        grid.setPadding(new Insets(20));
        grid.setVgap(20);
        grid.setHgap(15);
        filaHora.setSpacing(5);
        filaHora.setAlignment(Pos.CENTER_LEFT);
        txtCliente.setMinWidth(300);
        txtDescripcion.setMinWidth(300);
        dateFecha.setMinWidth(300);
        // Rellenar horas y minutos (08:00 a 20:00 por ejemplo)
        for (int i = 8; i <= 20; i++) cbHora.getItems().add(String.format("%02d", i));
        for (int i = 0; i < 60; i += 5) cbMinuto.getItems().add(String.format("%02d", i));
        cbHora.getSelectionModel().selectFirst();
        cbMinuto.getSelectionModel().selectFirst();

        filaHora.getChildren().addAll(cbHora, new Label(":"), cbMinuto);
        dateFecha.setValue(LocalDate.now());


        grid.add(lblFecha, 0, 0); grid.add(dateFecha, 1, 0);
        grid.add(lblHora, 0, 1); grid.add(filaHora, 1, 1);
        grid.add(lblCliente, 0, 2); grid.add(txtCliente, 1, 2);
        grid.add(lblDescripcion, 0, 3); grid.add(txtDescripcion, 1, 3);

        paneCliente.setText("Nueva Cita - Detalles");
        paneCliente.setContent(grid);
        paneCliente.setCollapsible(false);
        btnGuardar.getStyleClass().add("button-primary");
        btnCancel.getStyleClass().add("button-secondary");
        filaButtons.setSpacing(10);
        filaButtons.setAlignment(Pos.CENTER_RIGHT);
        btnGuardar.getStyleClass().add("button-primary");
        filaButtons.getChildren().addAll(btnCancel, btnGuardar);

        root.getChildren().addAll(paneCliente, filaButtons);


        btnGuardar.setOnAction(actionEvent -> {
            try {

                String horaFinal = cbHora.getValue() + ":" + cbMinuto.getValue();
                CitaTbl cita = new CitaTbl(dateFecha.getValue().toString(), horaFinal, txtCliente.getText(), txtDescripcion.getText());
                citasService.crearCita(cita);

                new Alert(Alert.AlertType.INFORMATION, "Cita registrada con éxito").showAndWait();
                Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            }
        });

        btnCancel.setOnAction(e -> Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario));
    }
    public VBox getRoot() { return root; }
}