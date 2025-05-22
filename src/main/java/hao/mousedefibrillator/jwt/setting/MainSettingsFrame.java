package hao.mousedefibrillator.jwt.setting;

import hao.mousedefibrillator.tools.RoundBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;
import static hao.mousedefibrillator.jwt.homepage.MouseClickPanel.startBtn;
import static hao.mousedefibrillator.jwt.setting.MouseClickPanel.*;
import static hao.mousedefibrillator.jwt.setting.ShortcutPanel.cacheShort;
import static hao.mousedefibrillator.config.GenerateIni.*;
import static hao.mousedefibrillator.jwt.setting.ShortcutPanel.shortStart;
import static hao.mousedefibrillator.tools.JwtTools.setUIFont;
import static hao.mousedefibrillator.tools.NativeKeyCodeUtil.getKeyName;

public class MainSettingsFrame extends JFrame {

    private CardLayout cardLayout;

    private JPanel rightPanel;

    private Map<String, JPanel> panelMap = new HashMap<>();
    
    public MainSettingsFrame() {
        setTitle("设置");
        setIconImage(new ImageIcon(getClass().getResource("/image/logo.png")).getImage());
        setSize(650, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 设置全局字体
        setUIFont(new javax.swing.plaf.FontUIResource("宋体", Font.PLAIN, 13));

        // 初始化左侧菜单
        hao.mousedefibrillator.jwt.setting.LeftMenuPanel leftMenu = new hao.mousedefibrillator.jwt.setting.LeftMenuPanel(this::switchPanel);
        leftMenu.setBackground(new Color(245, 245, 255)); // 左侧菜单背景色
        leftMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 15, 10)); // 增加内边距
        add(leftMenu, BorderLayout.WEST);

        // 主内容区域
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(240, 245, 255));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20)); // 右侧留白

        // 内容卡片容器 - 圆角白框
        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setBorder(new RoundBorder(8, new Color(240, 240, 240), false));

        // 卡片布局面板
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);
        rightPanel.setBackground(Color.WHITE);

        // 添加功能面板
        panelMap.put("mouseClickSet", new hao.mousedefibrillator.jwt.setting.MouseClickPanel());
        panelMap.put("shortcutSet", new hao.mousedefibrillator.jwt.setting.ShortcutPanel());
        panelMap.forEach((key, panel) -> rightPanel.add(panel, key));

        cardContainer.add(rightPanel, BorderLayout.CENTER);
        mainContent.add(cardContainer, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // 默认显示鼠标连点面板
        switchPanel("mouseClickSet");

        // 底部按钮
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public void switchPanel(String panelName) {
        cardLayout.show(rightPanel, panelName);
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.white);

        // 统一按钮尺寸
        Dimension buttonSize = new Dimension(80, 30); // 统一宽度和高度

        JButton cancelBtn = new JButton("取消");
        JButton confirmBtn = new JButton("确定");

        cancelBtn.setFont(PLAIN_FONT);
        cancelBtn.setPreferredSize(buttonSize);
        cancelBtn.setBorder(new RoundBorder(10, new Color(220, 220, 220), false));
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setOpaque(true);

        confirmBtn.setFont(PLAIN_FONT);
        confirmBtn.setPreferredSize(buttonSize);
        confirmBtn.setBorder(new RoundBorder(10, new Color(220, 220, 220), false));
        confirmBtn.setBackground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setOpaque(true);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                START_KEYBOARD = cacheShort;
                updateConfig("START_KEYBOARD", START_KEYBOARD);
                startBtn.setText("开始连点 (" + getKeyName(START_KEYBOARD) + ")");
                CLICK_WAIT = cacheWaitMsSinner;
                updateConfig("CLICK_WAIT", CLICK_WAIT);
                CLICK_DELAY = cachePressMsJLabel;
                updateConfig("CLICK_DELAY", CLICK_DELAY);
                // 关闭当前窗口
                SwingUtilities.getWindowAncestor(cancelBtn).dispose();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shortStart.setText(getKeyName(START_KEYBOARD));
                waitMsSinner.setValue(CLICK_WAIT);
                pressMsJLabel.setValue(CLICK_DELAY);
                // 关闭当前窗口
                SwingUtilities.getWindowAncestor(cancelBtn).dispose();
            }
        });


        
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(cancelBtn);
        panel.add(confirmBtn);
        
        return panel;
    }


}