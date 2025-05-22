package hao.mousedefibrillator.tools;

import javax.swing.*;
import java.awt.*;

import static hao.mousedefibrillator.config.JwtStyle.BOLD_FONT;
import static hao.mousedefibrillator.config.JwtStyle.PLAIN_FONT;

/**
 * jwt的一些工具类
 */
public class JwtTools {
    /**
     * 创建时间微调控制组件
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static JSpinner createTimeSpinner(int value, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        spinner.setFont(PLAIN_FONT);
        spinner.setPreferredSize(new Dimension(50, 25));

        // 设置编辑器样式
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);

        return spinner;
    }


    /**
     * 全局字体
     * @param f
     */
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    /**
     * 添加按键
     * @param parent
     * @param label
     * @param comp
     * @param gbc
     * @param row
     */
    public static void addLabelAndComponent(JPanel parent, String label, JComponent comp, GridBagConstraints gbc, int row, Font font) {
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

    public static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBackground(new Color(240, 245, 255));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        button.setFont(PLAIN_FONT);
        return button;
    }

}
