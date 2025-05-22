package hao.mousedefibrillator.jwt.homepage;

import hao.mousedefibrillator.tools.RoundBorder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;
import static hao.mousedefibrillator.tools.JwtTools.setUIFont;

@Component
public class MainApplication {

    public static JFrame mainFrame;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private Map<String, JPanel> panelMap = new HashMap<>();


    @PostConstruct
    private void initializeUI() {
//        // 在程序启动时调用
//        GlobalKeyListener.register();
        mainFrame = new JFrame("鼠标连点器 V1.0.0");
        mainFrame.setIconImage(new ImageIcon(getClass().getResource("/image/logo.png")).getImage());
        mainFrame.setSize(860, 560);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(240, 245, 255));

        // 设置全局字体
        setUIFont(new javax.swing.plaf.FontUIResource("宋体", Font.PLAIN, 13));

        // 初始化左侧菜单
        LeftMenuPanel leftMenu = new LeftMenuPanel(this::switchPanel);
        leftMenu.setBackground(new Color(240, 245, 255)); // 左侧菜单背景色
        leftMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 15, 10)); // 增加内边距
        mainFrame.add(leftMenu, BorderLayout.WEST);

        // 主内容区域
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(240, 245, 255));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20)); // 右侧留白

        // 内容卡片容器 - 圆角白框
        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setBorder(new RoundBorder(8, new Color(220, 220, 220), false));

        // 卡片布局面板
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);
        rightPanel.setBackground(Color.WHITE);

        // 添加功能面板
        panelMap.put("mouseClick", new MouseClickPanel());
        panelMap.forEach((key, panel) -> rightPanel.add(panel, key));

        cardContainer.add(rightPanel, BorderLayout.CENTER);
        mainContent.add(cardContainer, BorderLayout.CENTER);

        // 底部区域（版本信息等）
        JPanel bottomPanel = createBottomPanel();
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.add(mainContent, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // 版本信息（右下角）
        JLabel versionLabel = new JLabel("版本号: V1.0.0", SwingConstants.RIGHT);
        versionLabel.setFont(PLAIN_FONT);
        panel.add(versionLabel, BorderLayout.EAST);

        return panel;
    }

    private void switchPanel(String panelName) {
        cardLayout.show(rightPanel, panelName);
    }



}