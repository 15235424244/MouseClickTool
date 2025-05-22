package hao.mousedefibrillator.jwt.setting;

import hao.mousedefibrillator.tools.RoundBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;
import static hao.mousedefibrillator.tools.JwtTools.createMenuButton;

public class LeftMenuPanel extends JPanel {

    private JButton selectedButton; // 记录当前选中的按钮

    public LeftMenuPanel(Consumer<String> panelSwitcher) {
        setPreferredSize(new Dimension(150, 600));
        setBackground(new Color(240, 240, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initMenuItems(panelSwitcher);

    }

    private void initMenuItems(Consumer<String> panelSwitcher) {
        String[][] menuItems = {
                {"鼠标连点", "mouseClickSet"},
                {"快捷键", "shortcutSet"}
        };

        for (String[] item : menuItems) {
            JButton menuButton = createMenuButton(item[0]);
            menuButton.addActionListener(e -> {
                setSelectedButton(menuButton); // 更新选中状态
                panelSwitcher.accept(item[1]);
            });
            add(menuButton);
            add(Box.createRigidArea(new Dimension(0, 5)));

            // 默认选中第一个按钮
            if (getComponentCount() > 0 && getComponent(0) instanceof JButton) {
                setSelectedButton((JButton) getComponent(0));
            }
        }
    }

    private void setSelectedButton(JButton button) {
        // 重置之前选中的按钮样式
        if (selectedButton != null) {
            selectedButton.setOpaque(false);
            selectedButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            selectedButton.setBackground(null);
        }

        // 设置新选中按钮样式
        selectedButton = button;
        selectedButton.setOpaque(true);
        selectedButton.setBackground(Color.WHITE);
        selectedButton.setBorder(new RoundBorder(10, new Color(220, 220, 220), false));
    }


}