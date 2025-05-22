package hao.mousedefibrillator.tools;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.util.HashMap;
import java.util.Map;

public class NativeKeyCodeUtil {
    private static final Map<Integer, String> keyNameMap = new HashMap<>();

    static {
        // 功能键 F1-F24
        for (int i = 0; i <= 23; i++) {
            keyNameMap.put(NativeKeyEvent.VC_F1 + i, "F" + (i + 1));
        }

        // 字母键 A-Z"
        keyNameMap.put(NativeKeyEvent.VC_A, "A");
        keyNameMap.put(NativeKeyEvent.VC_B, "B");
        keyNameMap.put(NativeKeyEvent.VC_C, "C");
        keyNameMap.put(NativeKeyEvent.VC_D, "D");
        keyNameMap.put(NativeKeyEvent.VC_E, "E");
        keyNameMap.put(NativeKeyEvent.VC_F, "F");
        keyNameMap.put(NativeKeyEvent.VC_G, "G");
        keyNameMap.put(NativeKeyEvent.VC_H, "H");
        keyNameMap.put(NativeKeyEvent.VC_I, "I");
        keyNameMap.put(NativeKeyEvent.VC_J, "J");
        keyNameMap.put(NativeKeyEvent.VC_K, "K");
        keyNameMap.put(NativeKeyEvent.VC_L, "L");
        keyNameMap.put(NativeKeyEvent.VC_M, "M");
        keyNameMap.put(NativeKeyEvent.VC_N, "N");
        keyNameMap.put(NativeKeyEvent.VC_O, "O");
        keyNameMap.put(NativeKeyEvent.VC_P, "P");
        keyNameMap.put(NativeKeyEvent.VC_Q, "Q");
        keyNameMap.put(NativeKeyEvent.VC_R, "R");
        keyNameMap.put(NativeKeyEvent.VC_S, "S");
        keyNameMap.put(NativeKeyEvent.VC_T, "T");
        keyNameMap.put(NativeKeyEvent.VC_U, "U");
        keyNameMap.put(NativeKeyEvent.VC_V, "V");
        keyNameMap.put(NativeKeyEvent.VC_W, "W");
        keyNameMap.put(NativeKeyEvent.VC_X, "X");
        keyNameMap.put(NativeKeyEvent.VC_Y, "Y");
        keyNameMap.put(NativeKeyEvent.VC_Z, "Z");

        // 数字键 0-9
        keyNameMap.put(NativeKeyEvent.VC_0, "0");
        keyNameMap.put(NativeKeyEvent.VC_1, "1");
        keyNameMap.put(NativeKeyEvent.VC_2, "2");
        keyNameMap.put(NativeKeyEvent.VC_3, "3");
        keyNameMap.put(NativeKeyEvent.VC_4, "4");
        keyNameMap.put(NativeKeyEvent.VC_5, "5");
        keyNameMap.put(NativeKeyEvent.VC_6, "6");
        keyNameMap.put(NativeKeyEvent.VC_7, "7");
        keyNameMap.put(NativeKeyEvent.VC_8, "8");
        keyNameMap.put(NativeKeyEvent.VC_9, "9");

        // 控制键
        keyNameMap.put(NativeKeyEvent.VC_ESCAPE, "Esc");
        keyNameMap.put(NativeKeyEvent.VC_ENTER, "Enter");
        keyNameMap.put(NativeKeyEvent.VC_SPACE, "Space");
        keyNameMap.put(NativeKeyEvent.VC_TAB, "Tab");
        keyNameMap.put(NativeKeyEvent.VC_BACKSPACE, "Backspace");
        keyNameMap.put(NativeKeyEvent.VC_CONTROL, "Ctrl");
        keyNameMap.put(NativeKeyEvent.VC_ALT, "Alt");
        keyNameMap.put(NativeKeyEvent.VC_SHIFT, "Shift");
        keyNameMap.put(NativeKeyEvent.VC_CAPS_LOCK, "CapsLock");

        // 符号键
        keyNameMap.put(NativeKeyEvent.VC_SEMICOLON, ";");
        keyNameMap.put(NativeKeyEvent.VC_EQUALS, "=");
        keyNameMap.put(NativeKeyEvent.VC_COMMA, ",");
        keyNameMap.put(NativeKeyEvent.VC_PERIOD, ".");
        keyNameMap.put(NativeKeyEvent.VC_SLASH, "/");
        keyNameMap.put(NativeKeyEvent.VC_OPEN_BRACKET, "[");
        keyNameMap.put(NativeKeyEvent.VC_CLOSE_BRACKET, "]");
        keyNameMap.put(NativeKeyEvent.VC_QUOTE, "'");
        keyNameMap.put(NativeKeyEvent.VC_BACKQUOTE, "`");

        // 方向键
        keyNameMap.put(NativeKeyEvent.VC_UP, "↑");
        keyNameMap.put(NativeKeyEvent.VC_DOWN, "↓");
        keyNameMap.put(NativeKeyEvent.VC_LEFT, "←");
        keyNameMap.put(NativeKeyEvent.VC_RIGHT, "→");
    }

    /**
     * 将 NativeKeyEvent.VC_* 键码转为可读键名
     * @param nativeKeyCode jnativehook 的键码（如 NativeKeyEvent.VC_F2）
     * @return 如 "F2"、"Ctrl"、";"、"↑"，未知键返回 "Key_[码值]"
     */
    public static String getKeyName(int nativeKeyCode) {
        return keyNameMap.getOrDefault(nativeKeyCode, "Key_" + nativeKeyCode);
    }
}