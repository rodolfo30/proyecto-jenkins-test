package com.iessanalberto.dam1.ray_barber_shop;

import com.iessanalberto.dam1.ray_barber_shop.Navigation.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigation.navigate(Navigation.Screen.LOGIN_SCREEN);

    }
}