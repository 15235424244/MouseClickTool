package hao.mousedefibrillator.tools;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * 设置方框圆角工具
 */
public class RoundBorder extends AbstractBorder {
    private final int radius;
    private final Color color;
    private final boolean fill;

    public RoundBorder(int radius, Color color, boolean fill) {
        this.radius = radius;
        this.color = color;
        this.fill = fill;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(fill) {
            g2.setColor(color);
            g2.fillRoundRect(x, y, width-1, height-1, radius, radius);
        } else {
            g2.setColor(color);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius/2, radius/2, radius/2, radius/2);
    }
}
