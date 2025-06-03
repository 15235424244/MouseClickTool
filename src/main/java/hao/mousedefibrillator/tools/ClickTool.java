package hao.mousedefibrillator.tools;

import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.*;

import static hao.mousedefibrillator.config.GenerateIni.CLICK_DELAY;
import static hao.mousedefibrillator.jwt.homepage.MouseClickPanel.updateBtnStatus;

/**
 * 鼠标点击工具
 */
public class ClickTool {

    private static Robot robot;

    private ExecutorService executorService;



    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行单击或双击
     * @param button  鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param singleClick 是否单击
     */
    private void performClick(int button, boolean singleClick){
        try {
            robot.mousePress(button);
            Thread.sleep(CLICK_DELAY);
            robot.mouseRelease(button);

            if (!singleClick) { // 如果是双击
                robot.mousePress(button);
                Thread.sleep(CLICK_DELAY);
                robot.mouseRelease(button);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 获取Windows当前缩放比例 (100% = 1.0, 125% = 1.25, 150% = 1.5)
     */
    private Point getScalingFactor(int x, int y) {
        User32 user32 = User32.INSTANCE;
        GDI32 gdi32 = GDI32.INSTANCE;
        WinDef.HWND hwnd = user32.GetDesktopWindow();
        WinDef.HDC hdc = user32.GetDC(hwnd);
        int dpi = gdi32.GetDeviceCaps(hdc, 88); // 使用 GDI32 调用
        user32.ReleaseDC(hwnd, hdc);
        double scaling = dpi / 96.0; // 计算缩放比例
        System.out.println(scaling);
        return new Point((int)(x / scaling), (int)(y / scaling));
    }

    /**
     * 根据坐标：执行单击或双击
     * @param button  鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param singleClick 是否单击
     */
    private void cursorPerformClick(int x, int y, int button, boolean singleClick){
        try {
            robot.mouseMove(-1, -1);

            // 获取缩放因子
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            AffineTransform at = gc.getDefaultTransform();
            double scaleX = at.getScaleX();
            double scaleY = at.getScaleY();
            // 检查是否需要转换（逻辑像素）
            if (scaleX > 1.01 || scaleY > 1.01) {
                robot.mouseMove(x, y);
            }else {
                Point point = getScalingFactor(x, y);
                robot.mouseMove(point.x, point.y);
            }
            robot.mousePress(button);
            Thread.sleep(CLICK_DELAY);
            robot.mouseRelease(button);

            if (!singleClick) { // 如果是双击
                robot.mousePress(button);
                Thread.sleep(CLICK_DELAY);
                robot.mouseRelease(button);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }

    /**
     * 点击次数达n次停止：延迟n毫秒后开始点击n次，每次间隔n毫秒
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param clickNum 点击次数
     * @param singleClick 是否单击
     */
    public void delayedClick(int delayMillis, int button, long intervalMillis, int clickNum, boolean singleClick) {
        executorService = Executors.newSingleThreadExecutor(); // 每次启动时创建新的线程池
        executorService.submit(() ->{
            try {
                // 延迟
                Thread.sleep(delayMillis);
                // 点击次数
                for (int i = 0; i < clickNum; i++) {
                    // 执行单机或双击
                    performClick(button, singleClick);
                    // 如果不是最后一次点击，添加间隔
                    if (i < clickNum - 1){
                        Thread.sleep(intervalMillis);
                    }
                }
                updateBtnStatus();
            }catch (InterruptedException e){
                // 线程中断，确保线程安全退出
                Thread.currentThread().interrupt();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 根据坐标点击：点击次数达n次停止：延迟n毫秒后开始点击n次，每次间隔n毫秒
     * @param x 坐标x
     * @param y 坐标y
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param clickNum 点击次数
     * @param singleClick 是否单击
     */
    public void cursorDelayedClick(int x, int y, int delayMillis, int button, long intervalMillis, int clickNum, boolean singleClick) {
        executorService = Executors.newSingleThreadExecutor(); // 每次启动时创建新的线程池
        executorService.submit(() ->{
            try {
                // 延迟
                Thread.sleep(delayMillis);
                // 点击次数
                for (int i = 0; i < clickNum; i++) {
                    // 执行单机或双击
                    cursorPerformClick(x, y, button, singleClick);
                    // 如果不是最后一次点击，添加间隔
                    if (i < clickNum - 1){
                        Thread.sleep(intervalMillis);
                    }
                }
                updateBtnStatus();
            }catch (InterruptedException e){
                // 线程中断，确保线程安全退出
                Thread.currentThread().interrupt();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    // todo 连续点击到手动停止
    /**
     * 重复点击直到手动停止：延迟n毫秒后开始点击直到手动停止，每次间隔n毫秒
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param singleClick 是否单击
     */
    public void continueClick(int delayMillis, int button, long intervalMillis, boolean singleClick){
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                Thread.sleep(delayMillis);
                while (true){
                    // 执行单击或双击
                    performClick(button, singleClick);
                    Thread.sleep(intervalMillis);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 重复点击直到手动停止：延迟n毫秒后开始点击直到手动停止，每次间隔n毫秒
     * @param x 坐标x
     * @param y 坐标y
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param singleClick 是否单击
     */
    public void cursorContinueClick(int x, int y, int delayMillis, int button, long intervalMillis, boolean singleClick){
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                Thread.sleep(delayMillis);
                while (true){
                    // 执行单击或双击
                    cursorPerformClick(x, y, button, singleClick);
                    Thread.sleep(intervalMillis);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 点击时长达：延迟n毫秒后开始点击n毫秒后停止，每次间隔n毫秒
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param time 点击时长(需要换算为毫秒)
     * @param singleClick 是否单击
     */
    public void timeToClick(int delayMillis, int button, long intervalMillis, long time, boolean singleClick){
        executorService = Executors.newSingleThreadExecutor(); // 每次启动时创建新的线程池
        executorService.submit(() -> {
            try {
                // 延迟
                Thread.sleep(delayMillis);
                long startTime = System.currentTimeMillis();
                long endTime = startTime + time;
                while (System.currentTimeMillis() < endTime){
                    // 执行单击或双击
                    performClick(button, singleClick);
                    // 计算剩余时间
                    long remaining = endTime - System.currentTimeMillis();
                    if (remaining <= 0 || remaining < intervalMillis)
                        break;
                    Thread.sleep(intervalMillis);
                }
                updateBtnStatus();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 点击时长达：延迟n毫秒后开始点击n毫秒后停止，每次间隔n毫秒
     * @param x 坐标x
     * @param y 坐标y
     * @param delayMillis 延迟时间（秒）,几秒后开始点击
     * @param button 鼠标按钮：InputEvent.BUTTON1_DOWN_MASK（左键）或 InputEvent.BUTTON3_DOWN_MASK（右键）
     * @param intervalMillis 每次点击的间隔时间
     * @param time 点击时长(需要换算为毫秒)
     * @param singleClick 是否单击
     */
    public void cursorTimeToClick(int x, int y, int delayMillis, int button, long intervalMillis, long time, boolean singleClick){
        executorService = Executors.newSingleThreadExecutor(); // 每次启动时创建新的线程池
        executorService.submit(() -> {
            try {
                // 延迟
                Thread.sleep(delayMillis);
                long startTime = System.currentTimeMillis();
                long endTime = startTime + time;
                while (System.currentTimeMillis() < endTime){
                    // 执行单击或双击
                    cursorPerformClick(x, y, button, singleClick);
                    // 计算剩余时间
                    long remaining = endTime - System.currentTimeMillis();
                    if (remaining <= 0 || remaining < intervalMillis)
                        break;
                    Thread.sleep(intervalMillis);
                }
                updateBtnStatus();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void stopClicking(){
        executorService.shutdownNow();
    }




}
