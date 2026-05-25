package com.iessanalberto.dam1.ray_barber_shop.Navigation;

import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;
import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.screen.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigation {
    public enum Screen {
        LOGIN_SCREEN, REGISTER_SCREEN, MAIN_SCREEN, NEW_CITAS_SCREEN, EDIT_CITA_SCREEN
    }

    public static Stage stage = new Stage();

    // Ruta del CSS (asegúrate de que el nombre coincide)
    private static final String CSS_PATH = Navigation.class.getResource("/style.css").toExternalForm();

    public static void navigate(Screen screen) {
        Scene scene = null;
        switch (screen) {
            case LOGIN_SCREEN -> {
                LoginScreen loginScreen = new LoginScreen();
                scene = new Scene(loginScreen.getRoot(), 600, 700);
                stage.setTitle("Conexión - Ray Barber Shop");
            }
            case REGISTER_SCREEN -> {
                RegisterScreen registerScreen = new RegisterScreen();
                scene = new Scene(registerScreen.getRoot(), 600, 600);
                stage.setTitle("Registro de Usuario");
            }
        }
        applyAndShow(scene);
    }

    public static void navigate(Screen screen, Usuario usuario) {
        Scene scene = null;
        switch (screen) {
            case MAIN_SCREEN -> {
                MainScreen mainScreen = new MainScreen(usuario);
                scene = new Scene(mainScreen.getRoot(), 1100, 700);
                stage.setTitle("Panel Principal - " + usuario.getRol());
            }
            case NEW_CITAS_SCREEN -> {
                NewCitaScreen newCitaScreen = new NewCitaScreen(usuario);
                scene = new Scene(newCitaScreen.getRoot(), 600, 450);
                stage.setTitle("Nueva Cita");
            }
        }
        applyAndShow(scene);
    }

    public static void navigate(Screen screen, Usuario usuario, CitaTbl cita) {
        Scene scene = null;
        if (screen == Screen.EDIT_CITA_SCREEN) {
            EditCitaScreen editCitaScreen = new EditCitaScreen(usuario, cita);
            scene = new Scene(editCitaScreen.getRoot(), 600, 450);
            stage.setTitle("Editar Cita");
        }
        applyAndShow(scene);
    }


    private static void applyAndShow(Scene scene) {
        if (scene != null) {
            scene.getStylesheets().add(CSS_PATH);
            stage.setScene(scene);
            stage.show();
        }
    }
}