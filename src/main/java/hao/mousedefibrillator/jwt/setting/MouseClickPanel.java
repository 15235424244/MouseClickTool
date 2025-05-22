package hao.mousedefibrillator.jwt.setting;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static hao.mousedefibrillator.config.GenerateIni.*;
import static hao.mousedefibrillator.config.GenerateIni.INTERVAL_MS;
import static hao.mousedefibrillator.config.JwtStyle.BOLD_FONT;
import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;
import static hao.mousedefibrillator.tools.JwtTools.addLabelAndComponent;
import static hao.mousedefibrillator.tools.JwtTools.createTimeSpinner;

public class MouseClickPanel extends JPanel {

    public static JSpinner waitMsSinner, pressMsJLabel;
    public static int cacheWaitMsSinner = CLICK_WAIT, cachePressMsJLabel = CLICK_DELAY;

    public MouseClickPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 主内容面板
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndComponent(contentPanel, "鼠标连点设置", null, gbc, 0, BOLD_FONT);
        addLabelAndComponent(contentPanel, "鼠标按压时长", createPressPanel(), gbc, 1, PLAIN_FONT);
        addLabelAndComponent(contentPanel, "开始点击等待时长", createWaitPanel(), gbc, 2, PLAIN_FONT);

        // 空白填充
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(Box.createGlue(), gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * 按压时长
     * @return
     */
    private JPanel createPressPanel(){
        // 使用GridBagLayout实现更精确的布局控制
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0,4,0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 4,0, 4); // 左右间距4像素

        // 创建时间Spinner
        pressMsJLabel = createTimeSpinner(CLICK_DELAY, 0, 99999);
        pressMsJLabel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cachePressMsJLabel = Integer.parseInt(pressMsJLabel.getValue().toString());
            }
        });

        gbc.gridx++;
        panel.add(pressMsJLabel, gbc);

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
     * 鼠标点击等待时长
     * @return
     */
    private JPanel createWaitPanel(){
// 使用GridBagLayout实现更精确的布局控制
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0,4,0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 4,0, 4); // 左右间距4像素

        // 创建时间Spinner
        waitMsSinner = createTimeSpinner(CLICK_WAIT, 0, 99999);
        waitMsSinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cacheWaitMsSinner = Integer.parseInt(waitMsSinner.getValue().toString());
            }
        });

        gbc.gridx++;
        panel.add(waitMsSinner, gbc);

        gbc.gridx++;
        JLabel sJLabel = new JLabel("秒");
        sJLabel.setFont(PLAIN_FONT);
        panel.add(sJLabel, gbc);

        // 添加左右空白填充使整体居中
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(Box.createHorizontalGlue(), gbc);

        return panel;
    }


}