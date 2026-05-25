package com.iessanalberto.dam1.ray_barber_shop.screen;

import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import com.iessanalberto.dam1.ray_barber_shop.services.LoginServices;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class RegisterScreen {
    // Elementos de layout
    private VBox root = new  VBox();
    private HBox fila1 = new HBox();
    private HBox fila2 = new HBox();
    private HBox fila3 = new HBox();
    private HBox fila4 = new HBox();

    // Componentes de la ventana
    private Image logo = new Image("Ray_barber_shop.jpg");
    private ImageView logoView = new ImageView(logo);
    Rectangle rectanguloImage = new Rectangle(150,130);
    private Label lblUser = new Label("Usuario");
    private TextField txtUser = new TextField();
    private Label lblPassword = new Label("Contraseña");
    private PasswordField txtPassword = new PasswordField();
    private Label lblRepeatPassword = new Label("Repite la contraseña");
    private PasswordField txtRepeatPassword = new PasswordField();
    private Button btnRegistrar = new Button("Registrar");
    private Button btnCancel = new Button("Cancelar");

    // Añadimos los servicios de la ventana
    private LoginServices loginServices = new LoginServices();

    // Constructor
    public RegisterScreen() {
        // Configurar los elementos de layout
        root.setPadding(new Insets(10, 10, 10 ,10 ));
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        fila1.setSpacing(5);
        fila2.setSpacing(5);
        fila3.setSpacing(5);
        fila4.setSpacing(5);
        fila1.setAlignment(Pos.CENTER);
        fila2.setAlignment(Pos.CENTER);
        fila3.setAlignment(Pos.CENTER);
        fila4.setAlignment(Pos.CENTER);

        // Configuramos la imagen
        logoView.setFitWidth(150);
        logoView.setFitHeight(150);
        logoView.setPreserveRatio(true);
        rectanguloImage.setArcHeight(10);
        rectanguloImage.setArcWidth(10);
        logoView.setClip(rectanguloImage);

        // Añadimos los componentes al layout correspondiente
        fila1.getChildren().addAll(lblUser, txtUser);
        fila2.getChildren().addAll(lblPassword, txtPassword);
        fila3.getChildren().addAll(lblRepeatPassword, txtRepeatPassword);
        fila4.getChildren().addAll(btnCancel, btnRegistrar);
        root.getChildren().addAll(logoView, fila1, fila2, fila3,fila4);
        // Añadimos la interactividad de la ventana
        btnRegistrar.setOnAction(actionEvent -> {
            try {
                loginServices.register(txtUser.getText(), txtPassword.getText(), txtRepeatPassword.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Usuario registrado");
                alert.setHeaderText(null);
                alert.setContentText("Usuario registrado con éxito");
                alert.showAndWait();
                Navigation.navigate(Navigation.Screen.LOGIN_SCREEN);
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de conexión");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        });
        btnCancel.setOnAction(actionEvent -> {
            Navigation.navigate(Navigation.Screen.LOGIN_SCREEN);
        });

    }
    public VBox getRoot() {
        return root;
    }
}

