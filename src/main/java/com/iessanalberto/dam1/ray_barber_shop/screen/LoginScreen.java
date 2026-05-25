package com.iessanalberto.dam1.ray_barber_shop.screen;

import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.services.LoginServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class LoginScreen {
    private VBox root = new VBox();
    private HBox fila1 = new HBox();
    private HBox fila2 = new HBox();

    private Image logo = new Image("Ray_Barber_Shop.jpg");
    private ImageView logoView = new ImageView(logo);

    private Label lblUser = new Label("Usuario");
    private TextField txtUser = new TextField();
    private Label lblPassword = new Label("Contraseña");
    private PasswordField txtPassword = new PasswordField();
    private Button btnConectar = new Button("CONECTAR");

    private LoginServices loginServices = new LoginServices();

    public LoginScreen() {
        // 1. Configuración del contenedor principal
        root.setPadding(new Insets(40));
        root.setSpacing(25);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("main-container");

        // 2. Imagen ajustada (180px)
        logoView.setFitWidth(180);
        logoView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(180, 160);
        clip.setArcWidth(25);
        clip.setArcHeight(25);
        logoView.setClip(clip);

        // 3. Alineación y PromptText
        lblUser.setMinWidth(100);
        lblPassword.setMinWidth(100);

        txtUser.setPromptText("Introduce tu usuario");
        txtPassword.setPromptText("Introduce tu contraseña");

        txtUser.setPrefWidth(250);
        txtPassword.setPrefWidth(250);

        fila1.setAlignment(Pos.CENTER);
        fila1.setSpacing(10);
        fila1.getChildren().addAll(lblUser, txtUser);

        fila2.setAlignment(Pos.CENTER);
        fila2.setSpacing(10);
        fila2.getChildren().addAll(lblPassword, txtPassword);

        // 4. Botón de acción principal
        btnConectar.getStyleClass().add("button-primary");
        btnConectar.setMinWidth(360);
        btnConectar.setMinHeight(50);

        // --- CLAVE: Función ENTER ---
        // Esto permite que al pulsar Enter en cualquier campo, se ejecute el botón
        btnConectar.setDefaultButton(true);

        Label title = new Label("ACCESO PERSONAL");
        title.getStyleClass().add("title-label");

        // 5. Construcción de la vista
        root.getChildren().addAll(logoView, title, fila1, fila2, btnConectar);

        // Lógica de conexión
        btnConectar.setOnAction(actionEvent -> {
            ejecutarLogin();
        });
    }


    private void ejecutarLogin() {
        try {
            Usuario usuario = loginServices.login(txtUser.getText(), txtPassword.getText());
            Navigation.navigate(Navigation.Screen.MAIN_SCREEN, usuario);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de acceso");
            alert.setHeaderText(null);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    public VBox getRoot() {
        return root;
    }
}