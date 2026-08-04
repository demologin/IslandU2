package com.javarush.island.baksalyar;

import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.managers.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application{

    public static void main(String[] args) {
        Application.launch(Launcher.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WindowManager.switchWindow(Setting.NAME_HOME_FILE, MessageBundle.get("window.name.home"), null);
    }
}
