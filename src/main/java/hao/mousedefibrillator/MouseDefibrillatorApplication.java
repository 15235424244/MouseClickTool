package hao.mousedefibrillator;

import hao.mousedefibrillator.config.GlobalKeyListener;
import javafx.embed.swing.JFXPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

import java.io.IOException;

import static hao.mousedefibrillator.config.GenerateIni.CONFIG_PATH;

@SpringBootApplication
public class MouseDefibrillatorApplication {

    public static void main(String[] args) {
        // 禁用无头模式
        new SpringApplicationBuilder(MouseDefibrillatorApplication.class)
                .headless(false)
                .run(args);
        // 在程序启动时调用
        GlobalKeyListener.register();
    }

}
