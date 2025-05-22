package hao.mousedefibrillator.jwt.setting;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;

import hao.mousedefibrillator.tools.RoundBorder;

import static hao.mousedefibrillator.config.GenerateIni.*;
import static hao.mousedefibrillator.config.JwtStyle.BOLD_FONT;
import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;
import static hao.mousedefibrillator.tools.KeyCodeConverter.awtToNative;
import static hao.mousedefibrillator.tools.NativeKeyCodeUtil.getKeyName;

public class ShortcutPanel extends JPanel {

    public static int cacheShort = START_KEYBOARD;

    public static JTextField shortStart;

    public ShortcutPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 主内容面板
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndComponent(contentPanel, "快捷键设置", null, gbc, 0, BOLD_FONT);
        addLabelAndComponent(contentPanel, "鼠标连点开始/停止快捷键设置", createShortStartPanel(), gbc, 1, PLAIN_FONT);

        // 空白填充
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(Box.createGlue(), gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * 启动/停止快捷键设置
     * @return
     */
    private JPanel createShortStartPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // 使用FlowLayout实现水平排列
        panel.setOpaque(false);

        // 快捷按钮
        JPanel coordinatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        coordinatePanel.setOpaque(false);
        coordinatePanel.setVisible(true);

        shortStart = new JTextField(getKeyName(START_KEYBOARD), 15);
        JButton clearBtn = new JButton("清除");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shortStart.setText("");
            }
        });

        shortStart.setEditable(false); // 不可编辑
        shortStart.setFocusable(true); // 确保可以获取焦点

        shortStart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (shortStart.isFocusOwner()) {
                    int key = awtToNative(e.getKeyCode());
                    // 获取按键的字符串表示
                    String shortKey = getKeyName(key);
                    cacheShort = key;
                    // 将按键内容追加到文本框中
                    shortStart.setText("");
                    shortStart.setText(shortKey);
                }

            }
        });
        // 添加FocusListener来监听焦点变化
        shortStart.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                shortStart.setBackground(Color.lightGray);
            }

            @Override
            public void focusLost(FocusEvent e) {
                shortStart.setBackground(new Color(238, 238, 238));
            }
        });
        // 添加MouseListener和MouseMotionListener来管理焦点
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!shortStart.getBounds().contains(e.getPoint())) {
                    shortStart.transferFocus();
                }
            }
        });

        // 设置组件样式
        shortStart.setFont(PLAIN_FONT);
        shortStart.setPreferredSize(new Dimension(
                shortStart.getPreferredSize().width, // 保持自动计算的宽度
                25 // 固定高度25像素
        ));
        clearBtn.setFont(PLAIN_FONT);
        clearBtn.setBorder(new RoundBorder(10, Color.gray, false));
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setPreferredSize(new Dimension(
                clearBtn.getPreferredSize().width, // 保持自动计算的宽度
                25 // 固定高度25像素，与输入框一致
        ));

        clearBtn.setFocusPainted(false);
        clearBtn.setOpaque(true);

        // 添加组件到坐标面板
        coordinatePanel.add(shortStart);
        coordinatePanel.add(clearBtn);

        panel.add(coordinatePanel);

        return panel;
    }

    /**
     * 添加按键
     * @param parent
     * @param label
     * @param comp
     * @param gbc
     * @param row
     */
    private void addLabelAndComponent(JPanel parent, String label, JComponent comp, GridBagConstraints gbc, int row, Font font) {
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel jlabel = new JLabel(label);
        jlabel.setFont(font);
        parent.add(jlabel, gbc);

        gbc.gridx = 1;
        if (comp == null)
            return;
        parent.add(comp, gbc);
    }

}