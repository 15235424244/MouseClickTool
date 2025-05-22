package hao.mousedefibrillator.config;

import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;

public class JwtStyle {

    public static Font PLAIN_FONT = new Font("宋体", Font.PLAIN, 14);
    public static Font BOLD_FONT = new Font("宋体", Font.BOLD, 14);

    // 自定义单选按钮图标
    public static Icon ICON  = new Icon() {
        private final int SIZE = 16;

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (((AbstractButton)c).isSelected()) {
                // 蓝色选中状态
                g2.setColor(new Color(0, 122, 255)); // 系统蓝色
                g2.fillOval(x, y, SIZE, SIZE);

                // 白色中心点
                g2.setColor(Color.WHITE);
                g2.fillOval(x+4, y+4, SIZE-8, SIZE-8);
            } else {
                // 白色未选中状态
                g2.setColor(Color.WHITE);
                g2.fillOval(x, y, SIZE, SIZE);

                // 灰色边框
                g2.setColor(new Color(200, 200, 200));
                g2.drawOval(x, y, SIZE-1, SIZE-1);
            }
            g2.dispose();
        }

        @Override
        public int getIconWidth() { return SIZE; }

        @Override
        public int getIconHeight() { return SIZE; }
    };
}
