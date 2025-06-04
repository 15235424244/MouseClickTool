package hao.mousedefibrillator.tools;



import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 坐标选取工具类
 */
public class SelectCoordinates {

    /**
     * 获取屏幕截图并根据鼠标点击情况获取存储坐标
     * @return clickPoint.x clickPoint.y为坐标位置
     */
    public static Point getClickCoordinate() {
        try {
//            // 获取实际屏幕分辨率（物理像素）
//            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//            DisplayMode dm = gd.getDisplayMode();
//            int screenWidth = dm.getWidth();
//            int screenHeight = dm.getHeight();
//
//            // 比下面那个截取的高清，但是部分电脑使用截图会出现问题
//            Robot robot = new Robot(gd);
//            Rectangle screenRect = new Rectangle(0, 0, screenWidth, screenHeight);
//            BufferedImage screenImage = robot.createScreenCapture(screenRect);

            // 1. 截取整个屏幕
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenImage = robot.createScreenCapture(screenRect);

            // 2. 创建显示截图的窗口
            JFrame frame = new JFrame();
            frame.setUndecorated(true); // 无边框
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 3. 将截图设置为窗口背景
            JLabel background = new JLabel(new ImageIcon(screenImage));
            frame.setContentPane(background);

            // 4. 设置窗口全屏
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();

            // 5. 存储点击坐标
            final Point[] clickPoint = new Point[1];

            // 6. 添加鼠标点击监听器
            background.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickPoint[0] = e.getPoint();
                    frame.dispose(); // 获取坐标后关闭窗口
                }
            });

            // 7. 显示窗口
            frame.setVisible(true);

            // 8. 等待用户点击
            while (clickPoint[0] == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return clickPoint[0];

        } catch (AWTException e) {
            System.out.println("选取坐标出错！");
            e.printStackTrace();
            return null;
        }
    }

}
