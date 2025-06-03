package hao.mousedefibrillator.jwt.homepage;

import hao.mousedefibrillator.jwt.setting.MainSettingsFrame;
import hao.mousedefibrillator.tools.ClickTool;
import hao.mousedefibrillator.tools.RoundBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static hao.mousedefibrillator.config.GenerateIni.*;
import static hao.mousedefibrillator.config.JwtStyle.*;
import static hao.mousedefibrillator.jwt.homepage.MainApplication.mainFrame;
import static hao.mousedefibrillator.tools.JwtTools.addLabelAndComponent;
import static hao.mousedefibrillator.tools.JwtTools.createTimeSpinner;
import static hao.mousedefibrillator.tools.NativeKeyCodeUtil.getKeyName;
import static hao.mousedefibrillator.tools.SelectCoordinates.getClickCoordinate;
import static hao.mousedefibrillator.tools.TimeConverter.convertToMilliseconds;
import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;


public class MouseClickPanel extends JPanel {

    private ClickTool clickTool = new ClickTool();

    private JSpinner hourSpinner, minSpinner, secSpinner, msSpinner;
    private JSpinner countSpinner, timeValueSpinner;

    public static JButton startBtn;

    public MouseClickPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 主内容面板
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 基础按键设置
        addLabelAndComponent(contentPanel,"鼠标按键:", createMouseButtonPanel(), gbc, 0, BOLD_FONT);
        // 点击方式
        addLabelAndComponent(contentPanel, "点击方式:", createClickButtonPanel(), gbc, 1, BOLD_FONT);
        // 点击位置
        addLabelAndComponent(contentPanel, "点击位置:", createPositionPanel(), gbc, 2, BOLD_FONT);
        // 每次点击间隔时间
        addLabelAndComponent(contentPanel, "每次点击间隔时间:", createIntervalPanel(), gbc, 3, BOLD_FONT);
        // 重复次数或时长
        addLabelAndComponent(contentPanel, "重复次数或时长:", createRepeatPanel(), gbc, 4, BOLD_FONT);

        // 空白填充
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(Box.createGlue(), gbc);

        // 添加底部按钮面板
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * 按钮样式
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(PLAIN_FONT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        button.setBackground(Color.WHITE);
        return button;
    }


    /**
     * 底部按钮面板
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 添加1px灰色分割线（与图片完全一致）
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(220, 220, 220));
        panel.add(separator, BorderLayout.NORTH);

        // 按钮容器
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        buttonContainer.setBackground(Color.WHITE);

        // 统一按钮尺寸
        Dimension buttonSize = new Dimension(120, 36); // 统一宽度和高度

        // 设置按钮（圆角样式）
        JButton settingsBtn = new JButton("设置");
        settingsBtn.setFont(PLAIN_FONT);
        settingsBtn.setPreferredSize(buttonSize);
        settingsBtn.setBorder(new RoundBorder(10, new Color(220, 220, 220), false));
        settingsBtn.setBackground(Color.WHITE);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setOpaque(true);
        buttonContainer.add(settingsBtn);

        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainSettingsFrame frame = new MainSettingsFrame();
                frame.setVisible(true);
            }
        });

        // 开始连点按钮（圆角蓝色样式）
        startBtn = new JButton("开始连点 (" + getKeyName(START_KEYBOARD) + ")"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 按压状态处理（匹配图片中的交互效果）
                if (getModel().isPressed()) {
                    g2.setColor(new Color(45, 20, 205)); // 按压时加深颜色
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    // 添加按压下沉效果
                    g2.setColor(Color.BLUE);
                    g2.fillRoundRect(0, 2, getWidth(), getHeight()-2, 10, 10);
                } else {
                    g2.setColor(getBackground()); // 默认状态
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                g2.dispose();

                super.paintComponent(g); // 绘制文本
            }
        };
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(BOLD_FONT);
        startBtn.setBackground(new Color(65, 105, 225)); // 图片中的蓝色
        startBtn.setBorder(new RoundBorder(10, new Color(65, 105, 225), false));
        startBtn.setPreferredSize(new Dimension(120, 36));

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startBtn.getText().startsWith("开始")) {
                    countdown(() ->{
                        updateConfig("INTERVAL_H", hourSpinner.getValue());
                        updateConfig("INTERVAL_MIN", minSpinner.getValue());
                        updateConfig("INTERVAL_S", secSpinner.getValue());
                        updateConfig("INTERVAL_MS", msSpinner.getValue());
                        updateConfig("CLICK_NUM", countSpinner.getValue());
                        updateConfig("CLICK_TIME", timeValueSpinner.getValue());
                        mainFrame.setState(JFrame.ICONIFIED); // 窗口小化
                        startClickProcess();
                    });
                } else {
                    startBtn.setText("开始连点 (" + getKeyName(START_KEYBOARD) + ")");
                    startBtn.setBackground(new Color(65, 105, 225));
                    stopClickProcess();
                }
            }
        });

        buttonContainer.add(startBtn);

        panel.add(buttonContainer, BorderLayout.CENTER);
        return panel;
    }

    /**
     * 鼠标按键
     * @return
     */
    private JPanel createMouseButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);

        // 创建单选按钮组
        ButtonGroup buttonGroup = new ButtonGroup();

        // 自定义单选按钮图标
        UIManager.put("RadioButton.icon", ICON);

        // 鼠标左键选项
        JRadioButton leftButton = new JRadioButton("鼠标左键", CLICK_KEY == BUTTON1_DOWN_MASK);
        leftButton.setOpaque(false);
        leftButton.setFont(PLAIN_FONT);
        leftButton.setFocusPainted(false);
        buttonGroup.add(leftButton);
        panel.add(leftButton);

        // 鼠标右键选项
        JRadioButton rightButton = new JRadioButton("鼠标右键", CLICK_KEY == BUTTON3_DOWN_MASK);
        rightButton.setOpaque(false);
        rightButton.setFont(PLAIN_FONT);
        rightButton.setFocusPainted(false);
        buttonGroup.add(rightButton);
        panel.add(rightButton);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CLICK_KEY != BUTTON1_DOWN_MASK){
                    updateConfig("CLICK_KEY", BUTTON1_DOWN_MASK);
                }
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CLICK_KEY != BUTTON3_DOWN_MASK){
                    updateConfig("CLICK_KEY", BUTTON3_DOWN_MASK);
                }
            }
        });

        return panel;
    }

    /**
     * 点击方式
     * @return
     */
    private JPanel createClickButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);

        // 创建单选按钮组
        ButtonGroup buttonGroup = new ButtonGroup();

        // 鼠标单击选项
        JRadioButton clickButton = new JRadioButton("单击", SINGLE_CLICK);
        clickButton.setOpaque(false);
        clickButton.setFont(PLAIN_FONT);
        clickButton.setFocusPainted(false);
        buttonGroup.add(clickButton);
        panel.add(clickButton);

        // 鼠标双击选项
        JRadioButton doubleClickButton = new JRadioButton("双击", !SINGLE_CLICK);
        doubleClickButton.setOpaque(false);
        doubleClickButton.setFont(PLAIN_FONT);
        doubleClickButton.setFocusPainted(false);
        buttonGroup.add(doubleClickButton);
        panel.add(doubleClickButton);

        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!SINGLE_CLICK){
                    updateConfig("SINGLE_CLICK", true);
                }
            }
        });

        doubleClickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SINGLE_CLICK){
                    updateConfig("SINGLE_CLICK", false);
                }
            }
        });


        return panel;
    }

    /**
     * 点击位置
     * @return
     */
    private JPanel createPositionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // 使用FlowLayout实现水平排列
        panel.setOpaque(false);

        // 下拉选择框
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{
                "鼠标光标所在位置",
                "指定坐标"
        });
        // 根据参数设置默认选中项
        positionCombo.setSelectedIndex(CURSOR ? 0 : 1);

        positionCombo.setFont(PLAIN_FONT);
        positionCombo.setPreferredSize(new Dimension(180, 28));

        // 坐标输入面板（初始隐藏）
        JPanel coordinatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        coordinatePanel.setOpaque(false);
        coordinatePanel.setVisible(!CURSOR); // 默认隐藏

        JTextField xField = new JTextField(String.valueOf(X), 5); // 宽度调整为3个字符
        JTextField yField = new JTextField(String.valueOf(Y), 5);
        JButton selectBtn = new JButton("选取坐标");
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);

                new Thread(() ->{
                    try {
                        Thread.sleep(300); // 让窗口有时间从屏幕上消失
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // 进行坐标获取或截图
                    Point clickCoordinate = getClickCoordinate();

                    SwingUtilities.invokeLater(() -> {
                        xField.setText(String.valueOf((int) clickCoordinate.getX()));
                        yField.setText(String.valueOf((int) clickCoordinate.getY()));
                        updateConfig("X", (int) clickCoordinate.getX());
                        updateConfig("Y", (int) clickCoordinate.getY());

                        mainFrame.setVisible(true); // 截图完成后再显示窗口
                    });
                }).start();

            }
        });

        positionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (positionCombo.getSelectedIndex() == 0){
                    updateConfig("CURSOR", true);
                }else {
                    updateConfig("CURSOR", false);

                }
            }
        });

        // 设置组件样式
        xField.setFont(PLAIN_FONT);
        yField.setFont(PLAIN_FONT);
        selectBtn.setFont(PLAIN_FONT);
        selectBtn.setFont(PLAIN_FONT);
        selectBtn.setBorder(new RoundBorder(10, Color.gray, false));
        selectBtn.setBackground(Color.WHITE);
        selectBtn.setFocusPainted(false);
        selectBtn.setOpaque(true);

        // 添加组件到坐标面板
        coordinatePanel.add(new JLabel("X:"));
        coordinatePanel.add(xField);
        coordinatePanel.add(new JLabel("Y:"));
        coordinatePanel.add(yField);
        coordinatePanel.add(selectBtn);

        // 切换监听器
        positionCombo.addActionListener(e -> {
            boolean isCoordinate = positionCombo.getSelectedIndex() == 1;
            coordinatePanel.setVisible(isCoordinate);
        });

        // 将下拉框和坐标面板添加到主面板（同一行）
        panel.add(positionCombo);
        panel.add(coordinatePanel);

        return panel;
    }

    /**
     * 每次间隔时间
     * @return
     */
    private JPanel createIntervalPanel() {
        // 使用GridBagLayout实现更精确的布局控制
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0,4,0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 4,0, 4); // 左右间距4像素

        // 创建时间Spinner
        hourSpinner = createTimeSpinner(INTERVAL_H, 0, 99999);
        minSpinner = createTimeSpinner(INTERVAL_MIN, 0, 99999);
        secSpinner = createTimeSpinner(INTERVAL_S, 0, 99999);
        msSpinner = createTimeSpinner(INTERVAL_MS, 0, 99999);

        // 添加自动进位监听（保持不变）
        msSpinner.addChangeListener(e -> {
            int ms = (int) msSpinner.getValue();
            if (ms >= 1000) {
                msSpinner.setValue(ms % 1000);
                secSpinner.setValue((int) secSpinner.getValue() + ms / 1000);
            }

        });

        secSpinner.addChangeListener(e -> {
            int sec = (int) secSpinner.getValue();
            if (sec >= 60) {
                secSpinner.setValue(sec % 60);
                minSpinner.setValue((int) minSpinner.getValue() + sec / 60);
            }
        });

        minSpinner.addChangeListener(e -> {
            int min = (int) minSpinner.getValue();
            if (min >= 60) {
                minSpinner.setValue(min % 60);
                hourSpinner.setValue((int) hourSpinner.getValue() + min / 60);
            }
        });

        // 添加组件到面板（居中均匀分布）
        gbc.gridx = 0;
        panel.add(hourSpinner, gbc);

        gbc.gridx++;
        JLabel hJLabel = new JLabel("时");
        hJLabel.setFont(PLAIN_FONT);
        panel.add(hJLabel, gbc);

        gbc.gridx++;
        panel.add(minSpinner, gbc);

        gbc.gridx++;
        JLabel minJLabel = new JLabel("分");
        minJLabel.setFont(PLAIN_FONT);
        panel.add(minJLabel, gbc);

        gbc.gridx++;
        panel.add(secSpinner, gbc);

        gbc.gridx++;
        JLabel sJLabel = new JLabel("秒");
        sJLabel.setFont(PLAIN_FONT);
        panel.add(sJLabel, gbc);

        gbc.gridx++;
        panel.add(msSpinner, gbc);

        gbc.gridx++;
        JLabel msJLabel = new JLabel("毫秒 (1秒=1000毫秒)");
        msJLabel.setFont(PLAIN_FONT);
        panel.add(msJLabel, gbc);

        // 添加左右空白填充使整体居中
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(Box.createHorizontalGlue(), gbc);

        return panel;
    }

    /**
     * 重复次数或时长
     * @return
     */
    private JPanel createRepeatPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);

        // 主下拉框
        JComboBox<String> repeatCombo = new JComboBox<>(new String[]{
                "重复点击直到手动停止",
                "点击次数达",
                "点击时长达"
        });
        repeatCombo.setSelectedIndex(ALL_CLICK_SITUATION.indexOf(CLICK_SITUATION));
        repeatCombo.setFont(PLAIN_FONT);
        repeatCombo.setPreferredSize(new Dimension(180, 28));
        repeatCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (repeatCombo.getSelectedIndex() == 0){
                    updateConfig("CLICK_SITUATION", ALL_CLICK_SITUATION.get(0));
                }else if (repeatCombo.getSelectedIndex() == 1){
                    updateConfig("CLICK_SITUATION", ALL_CLICK_SITUATION.get(1));
                }else {
                    updateConfig("CLICK_SITUATION", ALL_CLICK_SITUATION.get(2));

                }
            }
        });

        // 次数设置面板（图1样式）
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        countPanel.setOpaque(false);
        countPanel.setVisible(repeatCombo.getSelectedIndex() ==1);

        countPanel.add(new JLabel("重复点击次数达"));

        countSpinner = new JSpinner(new SpinnerNumberModel(CLICK_NUM, 1, 9999, 1));
        countSpinner.setFont(PLAIN_FONT);
        countSpinner.setPreferredSize(new Dimension(80, 28));
        countPanel.add(countSpinner);

        countPanel.add(new JLabel("次后停止"));

        // 时长设置面板（图2样式）
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timePanel.setOpaque(false);
        timePanel.setVisible(repeatCombo.getSelectedIndex() == 2);

        timePanel.add(new JLabel("重复点击时长达"));

        // 时间数值输入（带箭头）
        timeValueSpinner = new JSpinner(new SpinnerNumberModel(CLICK_TIME, 1, Integer.MAX_VALUE, 1));
        timeValueSpinner.setFont(PLAIN_FONT);
        timeValueSpinner.setPreferredSize(new Dimension(80, 28));
        timePanel.add(timeValueSpinner);

        // 时间单位下拉框
        JComboBox<String> timeUnitCombo = new JComboBox<>(new String[]{
                "毫秒", "秒", "分", "时"
        });
        timeUnitCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeUnitCombo.getSelectedIndex() == 0){
                    updateConfig("REPEAT_UNIT", ALL_TIME_UNIT.get(0));
                } else if (timeUnitCombo.getSelectedIndex() == 1) {
                    updateConfig("REPEAT_UNIT", ALL_TIME_UNIT.get(1));
                } else if (timeUnitCombo.getSelectedIndex() ==2) {
                    updateConfig("REPEAT_UNIT", ALL_TIME_UNIT.get(2));
                } else{
                    updateConfig("REPEAT_UNIT", ALL_TIME_UNIT.get(3));
                }
            }
        });
        timeUnitCombo.setFont(PLAIN_FONT);
        timeUnitCombo.setSelectedIndex(ALL_TIME_UNIT.indexOf(REPEAT_UNIT));
        timeUnitCombo.setPreferredSize(new Dimension(60, 28));
        timePanel.add(timeUnitCombo);

        timePanel.add(new JLabel("后停止"));

        // 切换监听器
        repeatCombo.addActionListener(e -> {
            String selected = (String)repeatCombo.getSelectedItem();
            countPanel.setVisible("点击次数达".equals(selected));
            timePanel.setVisible("点击时长达".equals(selected));
        });

        // 组装主面板（所有选项在一行）
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mainPanel.setOpaque(false);
        mainPanel.add(repeatCombo);
        mainPanel.add(countPanel);
        mainPanel.add(timePanel);

        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }


    /**
     * 开始点击
     */
    private void startClickProcess(){
        long intervalMillis = convertToMilliseconds(INTERVAL_H, INTERVAL_MIN, INTERVAL_S, INTERVAL_MS);
        long clickTime = convertToMilliseconds(CLICK_TIME, REPEAT_UNIT);
        // true为鼠标位置，false为光标位置
        if (!CURSOR){
            switch (CLICK_SITUATION){
                case "重复点击直到手动停止":
                    clickTool.cursorContinueClick(X, Y, 0, CLICK_KEY, intervalMillis, SINGLE_CLICK);
                    break;
                case "点击次数达":
                    clickTool.cursorDelayedClick(X, Y, 0, CLICK_KEY, intervalMillis, CLICK_NUM, SINGLE_CLICK);
                    break;
                case "点击时长达":
                    clickTool.cursorTimeToClick(X, Y, 0, CLICK_KEY, intervalMillis, clickTime, SINGLE_CLICK);
                    break;
                default:
                    System.out.println("没有该功能");
            }
        } else {
            switch (CLICK_SITUATION){
                case "重复点击直到手动停止":
                    clickTool.continueClick(0, CLICK_KEY, intervalMillis, SINGLE_CLICK);
                    break;
                case "点击次数达":
                    clickTool.delayedClick(0, CLICK_KEY, intervalMillis, CLICK_NUM, SINGLE_CLICK);
                    break;
                case "点击时长达":
                    clickTool.timeToClick(0, CLICK_KEY, intervalMillis, clickTime, SINGLE_CLICK);
                    break;
            default:
                System.out.println("没有该功能");
            }
        }
    }
    private void stopClickProcess(){
        clickTool.stopClicking();
    }

    private void countdown(Runnable onFinish) {
        startBtn.setEnabled(false); // 禁用按钮防止重复点击

        // 使用Swing Timer代替Thread.sleep
        new Timer(1000, new ActionListener() {
            int count = CLICK_WAIT;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    startBtn.setText(count + "秒后开始");
                    count--;
                    // 添加视觉反馈
                    startBtn.setBackground(new Color(65, 105, 225));
                    startBtn.repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                    startBtn.setText("停止连点 (" + getKeyName(START_KEYBOARD) + ")");
                    startBtn.setBackground(new Color(200, 60, 60));
                    onFinish.run();
                    startBtn.setEnabled(true);
                }
            }
        }).start();
    }

    public static void updateBtnStatus(){
        if (startBtn.getText().startsWith("停止")){
            startBtn.setText("开始连点 (" + getKeyName(START_KEYBOARD) + ")");
            startBtn.setBackground(new Color(65, 105, 225));
        }
    }
}