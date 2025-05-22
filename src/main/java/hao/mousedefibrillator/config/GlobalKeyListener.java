package hao.mousedefibrillator.config;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.keyboard.*;

import javax.swing.*;

import static hao.mousedefibrillator.config.GenerateIni.START_KEYBOARD;
import static hao.mousedefibrillator.jwt.homepage.MouseClickPanel.startBtn;

public class GlobalKeyListener {
    /**
     * 用于快捷键启动连点器使用
     */
    public static void register() {
        try {
            // 初始化全局钩子
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("注册全局钩子失败: " + e.getMessage());
            return;
        }

        // 添加监听器
        GlobalScreen.addNativeKeyListener(new NativeKeyAdapter() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == START_KEYBOARD) {
                    // 确保在EDT线程执行
                    SwingUtilities.invokeLater(() -> {
                        if (startBtn.isEnabled()) {
                            startBtn.doClick();
                        }
                    });
                }
            }
        });
    }
}

