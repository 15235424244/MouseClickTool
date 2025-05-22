package hao.mousedefibrillator.tools;

import java.awt.event.KeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

/**
 * AWT/Swing KeyEvent 和 JNativeHook NativeKeyEvent 键码转换工具类
 * 支持常用功能键、字母、数字、符号的互相转换
 */
public class KeyCodeConverter {

    /**
     * 将 AWT KeyEvent 键码转换为 JNativeHook 键码
     * @param awtKeyCode AWT 键码 (KeyEvent.VK_XXX)
     * @return 对应的 NativeKeyEvent.VC_XXX 键码
     */
    public static int awtToNative(int awtKeyCode) {
        switch (awtKeyCode) {
            // 功能键
            case KeyEvent.VK_ESCAPE:      return NativeKeyEvent.VC_ESCAPE;
            case KeyEvent.VK_F1:          return NativeKeyEvent.VC_F1;
            case KeyEvent.VK_F2:          return NativeKeyEvent.VC_F2;
            case KeyEvent.VK_F3:          return NativeKeyEvent.VC_F3;
            case KeyEvent.VK_F4:          return NativeKeyEvent.VC_F4;
            case KeyEvent.VK_F5:          return NativeKeyEvent.VC_F5;
            case KeyEvent.VK_F6:          return NativeKeyEvent.VC_F6;
            case KeyEvent.VK_F7:          return NativeKeyEvent.VC_F7;
            case KeyEvent.VK_F8:          return NativeKeyEvent.VC_F8;
            case KeyEvent.VK_F9:          return NativeKeyEvent.VC_F9;
            case KeyEvent.VK_F10:         return NativeKeyEvent.VC_F10;
            case KeyEvent.VK_F11:         return NativeKeyEvent.VC_F11;
            case KeyEvent.VK_F12:         return NativeKeyEvent.VC_F12;
            case KeyEvent.VK_PRINTSCREEN: return NativeKeyEvent.VC_PRINTSCREEN;
            case KeyEvent.VK_SCROLL_LOCK: return NativeKeyEvent.VC_SCROLL_LOCK;
            case KeyEvent.VK_PAUSE:       return NativeKeyEvent.VC_PAUSE;
            case KeyEvent.VK_INSERT:      return NativeKeyEvent.VC_INSERT;
            case KeyEvent.VK_DELETE:      return NativeKeyEvent.VC_DELETE;
            case KeyEvent.VK_HOME:        return NativeKeyEvent.VC_HOME;
            case KeyEvent.VK_END:         return NativeKeyEvent.VC_END;
            case KeyEvent.VK_PAGE_UP:     return NativeKeyEvent.VC_PAGE_UP;
            case KeyEvent.VK_PAGE_DOWN:   return NativeKeyEvent.VC_PAGE_DOWN;

            // 控制键
            case KeyEvent.VK_BACK_SPACE:  return NativeKeyEvent.VC_BACKSPACE;
            case KeyEvent.VK_ENTER:       return NativeKeyEvent.VC_ENTER;
            case KeyEvent.VK_TAB:         return NativeKeyEvent.VC_TAB;
            case KeyEvent.VK_SHIFT:       return NativeKeyEvent.VC_SHIFT;
            case KeyEvent.VK_CONTROL:     return NativeKeyEvent.VC_CONTROL;
            case KeyEvent.VK_ALT:         return NativeKeyEvent.VC_ALT;
            case KeyEvent.VK_CAPS_LOCK:   return NativeKeyEvent.VC_CAPS_LOCK;
            case KeyEvent.VK_SPACE:       return NativeKeyEvent.VC_SPACE;
            case KeyEvent.VK_WINDOWS:     return NativeKeyEvent.VC_META;

            // 方向键
            case KeyEvent.VK_UP:          return NativeKeyEvent.VC_UP;
            case KeyEvent.VK_DOWN:        return NativeKeyEvent.VC_DOWN;
            case KeyEvent.VK_LEFT:        return NativeKeyEvent.VC_LEFT;
            case KeyEvent.VK_RIGHT:       return NativeKeyEvent.VC_RIGHT;

            // 字母键（A-Z）
            case KeyEvent.VK_A:           return NativeKeyEvent.VC_A;
            case KeyEvent.VK_B:           return NativeKeyEvent.VC_B;
            case KeyEvent.VK_C:           return NativeKeyEvent.VC_C;
            case KeyEvent.VK_D:           return NativeKeyEvent.VC_D;
            case KeyEvent.VK_E:           return NativeKeyEvent.VC_E;
            case KeyEvent.VK_F:           return NativeKeyEvent.VC_F;
            case KeyEvent.VK_G:           return NativeKeyEvent.VC_G;
            case KeyEvent.VK_H:           return NativeKeyEvent.VC_H;
            case KeyEvent.VK_I:           return NativeKeyEvent.VC_I;
            case KeyEvent.VK_J:           return NativeKeyEvent.VC_J;
            case KeyEvent.VK_K:           return NativeKeyEvent.VC_K;
            case KeyEvent.VK_L:           return NativeKeyEvent.VC_L;
            case KeyEvent.VK_M:           return NativeKeyEvent.VC_M;
            case KeyEvent.VK_N:           return NativeKeyEvent.VC_N;
            case KeyEvent.VK_O:           return NativeKeyEvent.VC_O;
            case KeyEvent.VK_P:           return NativeKeyEvent.VC_P;
            case KeyEvent.VK_Q:           return NativeKeyEvent.VC_Q;
            case KeyEvent.VK_R:           return NativeKeyEvent.VC_R;
            case KeyEvent.VK_S:           return NativeKeyEvent.VC_S;
            case KeyEvent.VK_T:           return NativeKeyEvent.VC_T;
            case KeyEvent.VK_U:           return NativeKeyEvent.VC_U;
            case KeyEvent.VK_V:           return NativeKeyEvent.VC_V;
            case KeyEvent.VK_W:           return NativeKeyEvent.VC_W;
            case KeyEvent.VK_X:           return NativeKeyEvent.VC_X;
            case KeyEvent.VK_Y:           return NativeKeyEvent.VC_Y;
            case KeyEvent.VK_Z:           return NativeKeyEvent.VC_Z;

            // 数字键（0-9）
            case KeyEvent.VK_0:           return NativeKeyEvent.VC_0;
            case KeyEvent.VK_1:           return NativeKeyEvent.VC_1;
            case KeyEvent.VK_2:           return NativeKeyEvent.VC_2;
            case KeyEvent.VK_3:           return NativeKeyEvent.VC_3;
            case KeyEvent.VK_4:           return NativeKeyEvent.VC_4;
            case KeyEvent.VK_5:           return NativeKeyEvent.VC_5;
            case KeyEvent.VK_6:           return NativeKeyEvent.VC_6;
            case KeyEvent.VK_7:           return NativeKeyEvent.VC_7;
            case KeyEvent.VK_8:           return NativeKeyEvent.VC_8;
            case KeyEvent.VK_9:           return NativeKeyEvent.VC_9;

            // 符号键
            case KeyEvent.VK_COMMA:       return NativeKeyEvent.VC_COMMA;
            case KeyEvent.VK_PERIOD:      return NativeKeyEvent.VC_PERIOD;
            case KeyEvent.VK_SLASH:       return NativeKeyEvent.VC_SLASH;
            case KeyEvent.VK_SEMICOLON:   return NativeKeyEvent.VC_SEMICOLON;
            case KeyEvent.VK_EQUALS:      return NativeKeyEvent.VC_EQUALS;
            case KeyEvent.VK_OPEN_BRACKET:return NativeKeyEvent.VC_OPEN_BRACKET;
            case KeyEvent.VK_BACK_SLASH:  return NativeKeyEvent.VC_BACK_SLASH;
            case KeyEvent.VK_CLOSE_BRACKET:return NativeKeyEvent.VC_CLOSE_BRACKET;
            case KeyEvent.VK_QUOTE:       return NativeKeyEvent.VC_QUOTE;
            case KeyEvent.VK_BACK_QUOTE:  return NativeKeyEvent.VC_BACKQUOTE;

            // 默认返回原值（无法映射时）
            default:                      return awtKeyCode;
        }
    }

}