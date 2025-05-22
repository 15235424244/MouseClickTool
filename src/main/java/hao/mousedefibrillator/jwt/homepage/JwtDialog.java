//package hao.mousedefibrillator.jwt;
//
//import hao.mousedefibrillator.tools.ClickTool;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
//
//
//@Component
//public class JwtDialog {
//
//    private JFrame frame;
//    private JButton startButton;
//    private JComboBox<String> mouseButtonCombo;
//    private JComboBox<String> clickTypeCombo;
//    private JTextField xField, yField;
//    private JSpinner intervalSpinner;
//    private JRadioButton manualStopRadio, repeatCountRadio, repeatTimeRadio;
//    private JComboBox<Integer> countCombo, timeCombo;
//
//    @Autowired
//    private ClickTool clickTool;
//
////    @PostConstruct
//    public void init() {
//        // 创建主窗口 - 模仿金舟软件的蓝白配色
//        frame = new JFrame("鼠标连点器");
//        frame.setSize(600, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.getContentPane().setBackground(new Color(240, 245, 255));
//
//        // 设置全局字体为仿宋
//        setUIFont(new javax.swing.plaf.FontUIResource("仿宋", Font.PLAIN, 12));
//
//        // 左侧菜单栏 - 使用BoxLayout确保垂直排列
//        JPanel leftMenu = new JPanel();
//        leftMenu.setPreferredSize(new Dimension(120, 400));
//        leftMenu.setBackground(new Color(230, 240, 255));
//        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
//        leftMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        String[] menuItems = {"鼠标连点", "键盘连点", "键鼠录制", "个人中心", "开通会员", "使用教程", "设置", "官方网站"};
//        for (String item : menuItems) {
//            JButton menuButton = new JButton(item);
//            menuButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
//            menuButton.setBackground(new Color(240, 245, 255));
//            menuButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//            menuButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
//            leftMenu.add(menuButton);
//            leftMenu.add(Box.createRigidArea(new Dimension(0, 5)));
//        }
//        frame.add(leftMenu, BorderLayout.WEST);
//
//        // 主内容区域 - 使用嵌套面板确保布局稳定
//        JPanel mainContent = new JPanel(new BorderLayout());
//
//        // 右侧主设置区域 - 使用GridBagLayout更精确控制布局
//        JPanel mainPanel = new JPanel(new GridBagLayout());
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        mainPanel.setBackground(Color.WHITE);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.anchor = GridBagConstraints.WEST;
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // 鼠标按键选择
//        gbc.gridx = 0; gbc.gridy = 0;
//        mainPanel.add(new JLabel("鼠标按键:"), gbc);
//        gbc.gridx = 1;
//        mouseButtonCombo = new JComboBox<>(new String[]{"鼠标左键", "鼠标右键", "鼠标中键"});
//        mainPanel.add(mouseButtonCombo, gbc);
//
//        // 点击方式
//        gbc.gridx = 0; gbc.gridy = 1;
//        mainPanel.add(new JLabel("点击方式:"), gbc);
//        gbc.gridx = 1;
//        clickTypeCombo = new JComboBox<>(new String[]{"单击", "双击"});
//        mainPanel.add(clickTypeCombo, gbc);
//
//        // 点击位置
//        gbc.gridx = 0; gbc.gridy = 2;
//        mainPanel.add(new JLabel("点击位置:"), gbc);
//        gbc.gridx = 1;
//        JPanel positionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
//        positionPanel.setOpaque(false);
//        xField = new JTextField("0", 5);
//        yField = new JTextField("0", 5);
//        JButton selectPosButton = new JButton("选取坐标");
//        positionPanel.add(new JLabel("X:"));
//        positionPanel.add(xField);
//        positionPanel.add(new JLabel("Y:"));
//        positionPanel.add(yField);
//        positionPanel.add(selectPosButton);
//        mainPanel.add(positionPanel, gbc);
//
//        // 点击间隔时间
//        gbc.gridx = 0; gbc.gridy = 3;
//        mainPanel.add(new JLabel("每次点击间隔时间(毫秒):"), gbc);
//        gbc.gridx = 1;
//        intervalSpinner = new JSpinner(new SpinnerNumberModel(1000, 100, 60000, 100));
//        mainPanel.add(intervalSpinner, gbc);
//
//        // 重复方式
//        gbc.gridx = 0; gbc.gridy = 4;
//        mainPanel.add(new JLabel("重复方式:"), gbc);
//        gbc.gridx = 1;
//        manualStopRadio = new JRadioButton("重复点击直到手动停止");
//        manualStopRadio.setSelected(true);
//        mainPanel.add(manualStopRadio, gbc);
//
//        // 重复点击次数
//        gbc.gridx = 0; gbc.gridy = 5;
//        mainPanel.add(new JLabel(""), gbc);
//        gbc.gridx = 1;
//        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
//        countPanel.setOpaque(false);
//        repeatCountRadio = new JRadioButton("重复点击次数:");
//        countCombo = new JComboBox<>(new Integer[]{10, 50, 100, 200, 500, 1000});
//        countCombo.setEnabled(false);
//        countPanel.add(repeatCountRadio);
//        countPanel.add(countCombo);
//        mainPanel.add(countPanel, gbc);
//
//        // 重复点击时长
//        gbc.gridx = 0; gbc.gridy = 6;
//        mainPanel.add(new JLabel(""), gbc);
//        gbc.gridx = 1;
//        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
//        timePanel.setOpaque(false);
//        repeatTimeRadio = new JRadioButton("重复点击时长:");
//        timeCombo = new JComboBox<>(new Integer[]{5, 10, 30, 60, 120, 300});
//        timeCombo.setEnabled(false);
//        timePanel.add(repeatTimeRadio);
//        timePanel.add(timeCombo);
//        mainPanel.add(timePanel, gbc);
//
//        // 单选按钮组
//        ButtonGroup repeatGroup = new ButtonGroup();
//        repeatGroup.add(manualStopRadio);
//        repeatGroup.add(repeatCountRadio);
//        repeatGroup.add(repeatTimeRadio);
//
//        // 单选按钮监听器
//        ActionListener radioListener = e -> {
//            countCombo.setEnabled(repeatCountRadio.isSelected());
//            timeCombo.setEnabled(repeatTimeRadio.isSelected());
//        };
//        manualStopRadio.addActionListener(radioListener);
//        repeatCountRadio.addActionListener(radioListener);
//        repeatTimeRadio.addActionListener(radioListener);
//
//        // 添加空白区域填充剩余空间
//        gbc.gridx = 0; gbc.gridy = 7;
//        gbc.gridwidth = 2;
//        gbc.weightx = 1.0; gbc.weighty = 1.0;
//        gbc.fill = GridBagConstraints.BOTH;
//        mainPanel.add(Box.createGlue(), gbc);
//
//        mainContent.add(mainPanel, BorderLayout.CENTER);
//        frame.add(mainContent, BorderLayout.CENTER);
//
//        // 底部控制面板
//        JPanel bottomPanel = new JPanel(new BorderLayout());
//        bottomPanel.setPreferredSize(new Dimension(0, 60));
//        bottomPanel.setBackground(new Color(240, 245, 255));
//        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//
//        // 开始按钮
//        startButton = new JButton("开始连点 (F9)");
//        startButton.setPreferredSize(new Dimension(150, 40));
//        startButton.setBackground(new Color(65, 105, 225));
//        startButton.setForeground(Color.WHITE);
//        startButton.setFont(new Font("仿宋", Font.BOLD, 14));
//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (startButton.getText().startsWith("开始")) {
//                    startButton.setText("停止连点 (F9)");
//                    startButton.setBackground(new Color(220, 20, 60));
//                    startClickProcess(getRepeatMode());
//                } else {
//                    startButton.setText("开始连点 (F9)");
//                    startButton.setBackground(new Color(65, 105, 225));
//                    stopClickProcess();
//                }
//            }
//        });
//        bottomPanel.add(startButton, BorderLayout.WEST);
//
//        // 版本信息
//        JLabel versionLabel = new JLabel("版本号: V2.4.8");
//        bottomPanel.add(versionLabel, BorderLayout.EAST);
//
//        frame.add(bottomPanel, BorderLayout.SOUTH);
//
//        // 显示窗口
//        frame.setVisible(true);
//    }
//
//    // 设置全局字体
//    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
//        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
//        while (keys.hasMoreElements()) {
//            Object key = keys.nextElement();
//            Object value = UIManager.get(key);
//            if (value instanceof javax.swing.plaf.FontUIResource) {
//                UIManager.put(key, f);
//            }
//        }
//    }
//
//    private String getRepeatMode() {
//        if (manualStopRadio.isSelected()) {
//            return "重复点击直到手动停止";
//        } else if (repeatCountRadio.isSelected()) {
//            return "重复点击次数: " + countCombo.getSelectedItem();
//        } else {
//            return "重复点击时长: " + timeCombo.getSelectedItem() + "秒";
//        }
//    }
//
//    // 启动点击过程
//    private void startClickProcess(String fun) {
//        switch (fun){
//            case "重复点击直到手动停止":
//                clickTool.continueClick(3000, BUTTON1_DOWN_MASK, 2000, true);
//                break;
//            case "点击次数达":
//                clickTool.delayedClick(3000, BUTTON1_DOWN_MASK, 3000, 50, true); // 3秒后左键点击
//                break;
//            case "点击时长达":
//                clickTool.timeToClick(3000, BUTTON1_DOWN_MASK, 3000, 15000, true);
//                break;
////            default:
////                clickTool.continueClick(3000, BUTTON1_DOWN_MASK, 3000, true);
////                break;
//        }
//
//
//    }
//
//    // 停止点击过程
//    private void stopClickProcess() {
//        // 在这里添加停止点击的逻辑
//        clickTool.stopClicking();
//    }
//
//}