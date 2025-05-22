package hao.mousedefibrillator.config;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 生成配置文件
 */
@Component
public class GenerateIni {
    private static LinkedHashMap<String, String> configMap = new LinkedHashMap<>();
    // 文件路径 (可根据需要修改)
//    private static final String CONFIG_PATH = "D:/code/MouseDefibrillator/mouse_clicker.ini";
    public static final String CONFIG_PATH;

    static {
        String jarPath;
        try {
            // 获取 JAR 文件所在目录
            String path = GenerateIni.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            // 处理 Windows 路径中的特殊字符和斜杠
            if (path.startsWith("/") && System.getProperty("os.name").contains("Windows")) {
                path = path.substring(1);
            }
            jarPath = new File(path).getParent();
        } catch (Exception e) {
            // 如果无法获取 JAR 路径，则使用当前工作目录
            jarPath = System.getProperty("user.dir");
        }
        CONFIG_PATH = jarPath + File.separator + "mouse_clicker.ini";
    }

    // 鼠标按键：左、右
    public static int CLICK_KEY = InputEvent.BUTTON1_DOWN_MASK;
    // 是否单击
    public static boolean SINGLE_CLICK = true;
    // 点击位置：true为开启光标位置、false为关闭光标位置使用坐标位置
    public static boolean CURSOR = true;
    // 点击位置：坐标位置
    public static int X = 0;
    public static int Y = 0;
    // 间隔时间-h
    public static int INTERVAL_H = 0;
    // 间隔时间-min
    public static int INTERVAL_MIN = 0;
    // 间隔时间-s
    public static int INTERVAL_S = 0;
    // 间隔时间-ms
    public static int INTERVAL_MS = 1000;
    // 重复次数或时长：重复点击直到手动停止、点击次数达、点击时长达
    public static List<String> ALL_CLICK_SITUATION = new ArrayList<>();
    public static String CLICK_SITUATION = "重复点击直到手动停止";
    // 点击次数
    public static int CLICK_NUM = 1;
    // 点击时长：100
    public static int CLICK_TIME = 10000;
    // 3秒后开始点击
    public static int CLICK_WAIT = 3;
    // 鼠标点击与松开间延迟
    // todo 设置页面中用，设置页面中有快捷键设置，点击与松开延迟设置，
    public static int CLICK_DELAY = 30;
    // 所有时间单位:时、分、秒、毫秒
    public static List<String> ALL_TIME_UNIT = new ArrayList<>();;
    // 重复时长时间单位：毫秒
    public static String REPEAT_UNIT = "毫秒";
    // 开始点击时快捷键
    public static int START_KEYBOARD = NativeKeyEvent.VC_F2;
    static {
        // 初始化列表数据
        ALL_CLICK_SITUATION.add("重复点击直到手动停止");
        ALL_CLICK_SITUATION.add("点击次数达");
        ALL_CLICK_SITUATION.add("点击时长达");

        ALL_TIME_UNIT.add("毫秒");
        ALL_TIME_UNIT.add("秒");
        ALL_TIME_UNIT.add("分");
        ALL_TIME_UNIT.add("时");
    }

    @PostConstruct
    public void initConfig() {
        Path configFile = Paths.get(CONFIG_PATH);
        try {
            if (Files.exists(configFile)) {
                // 配置文件存在，读取配置
                loadConfig();
                System.out.println("配置文件已加载: " + CONFIG_PATH);
            } else {
                // 配置文件不存在，创建并写入默认值
                Files.createDirectories(configFile.getParent());
                saveConfig();
                System.out.println("已创建配置文件: " + CONFIG_PATH);
            }
        } catch (IOException e) {
            System.err.println("文件操作失败: " + e.getMessage());
        }
    }

    private void loadConfig() throws IOException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            props.load(input);

            // 读取并设置配置值
            CLICK_KEY = Integer.parseInt(props.getProperty("CLICK_KEY", String.valueOf(InputEvent.BUTTON1_DOWN_MASK)));
            SINGLE_CLICK = Boolean.parseBoolean(props.getProperty("SINGLE_CLICK", "true"));
            CURSOR = Boolean.parseBoolean(props.getProperty("CURSOR", "true"));
            X = Integer.parseInt(props.getProperty("X", "0"));
            Y = Integer.parseInt(props.getProperty("Y", "0"));
            INTERVAL_H = Integer.parseInt(props.getProperty("INTERVAL_H", "0"));
            INTERVAL_MIN = Integer.parseInt(props.getProperty("INTERVAL_MIN", "0"));
            INTERVAL_S = Integer.parseInt(props.getProperty("INTERVAL_S", "0"));
            INTERVAL_MS = Integer.parseInt(props.getProperty("INTERVAL_MS", "1000"));
            CLICK_NUM = Integer.parseInt(props.getProperty("CLICK_NUM", "1"));
            CLICK_TIME = Integer.parseInt(props.getProperty("CLICK_TIME", "100"));
            CLICK_WAIT = Integer.parseInt(props.getProperty("CLICK_WAIT", "3"));
            START_KEYBOARD = Integer.parseInt(props.getProperty("START_KEYBOARD", String.valueOf(NativeKeyEvent.VC_F2)));
            CLICK_DELAY = Integer.parseInt(props.getProperty("CLICK_DELAY", "30"));
            REPEAT_UNIT = new String(props.getProperty("REPEAT_UNIT", "毫秒").getBytes("ISO-8859-1"), "utf-8");
            CLICK_SITUATION = new String(props.getProperty("CLICK_SITUATION", "重复点击直到手动停止").getBytes("ISO-8859-1"), "UTF-8");
        }
    }

    private void saveConfig() throws IOException {

        configMap.put("# Mouse connector configuration file", "");
        configMap.put("# 自动生成的配置文件，请勿手动修改注释格式", "");

        // 设置配置值（带中文注释）
        configMap.put("# 鼠标按键：左键,右键", "");
        configMap.put("CLICK_KEY", String.valueOf(CLICK_KEY));

        configMap.put("# 是否单击", "");
        configMap.put("SINGLE_CLICK", String.valueOf(SINGLE_CLICK));

        configMap.put("# 点击位置模式：true=使用光标位置,false=使用固定坐标", "");
        configMap.put("CURSOR", String.valueOf(CURSOR));

        configMap.put("# 固定点击位置X坐标", "");
        configMap.put("X", String.valueOf(X));

        configMap.put("# 固定点击位置Y坐标", "");
        configMap.put("Y", String.valueOf(Y));

        configMap.put("# 点击间隔时间（小时部分）", "");
        configMap.put("INTERVAL_H", String.valueOf(INTERVAL_H));

        configMap.put("# 点击间隔时间（分钟部分）", "");
        configMap.put("INTERVAL_MIN", String.valueOf(INTERVAL_MIN));

        configMap.put("# 点击间隔时间（秒部分）", "");
        configMap.put("INTERVAL_S", String.valueOf(INTERVAL_S));

        configMap.put("# 点击间隔时间（毫秒部分）", "");
        configMap.put("INTERVAL_MS", String.valueOf(INTERVAL_MS));

        configMap.put("# 点击次数（当选择点击次数达时生效）", "");
        configMap.put("CLICK_NUM", String.valueOf(CLICK_NUM));

        configMap.put("# 点击时长（秒，当选择点击时长达时生效）", "");
        configMap.put("CLICK_TIME", String.valueOf(CLICK_TIME));

        configMap.put("# 开始点击前的等待时间（秒）", "");
        configMap.put("CLICK_WAIT", String.valueOf(CLICK_WAIT));

        configMap.put("# 鼠标按下与松开之间的延迟（毫秒）", "");
        configMap.put("CLICK_DELAY", String.valueOf(CLICK_DELAY));

        configMap.put("# 重复时长显示单位", "");
        configMap.put("REPEAT_UNIT", REPEAT_UNIT);

        configMap.put("# 重复次数或时长设置", "");
        configMap.put("CLICK_SITUATION", CLICK_SITUATION);

        configMap.put("# 开始点击时所用快捷键", "");
        configMap.put("START_KEYBOARD", String.valueOf(START_KEYBOARD));

        // 自定义写入方式以保留注释
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(CONFIG_PATH), StandardCharsets.UTF_8)) {
            writer.write("# Mouse connector configuration file\n");
            writer.write("# 自动生成的配置文件，请勿手动修改注释格式\n");
            // 写入所有属性
            for (Map.Entry<String, String> entry : configMap.entrySet()) {
                if (entry.getKey().startsWith("#")) {
                    // 写入注释行
                    writer.write(entry.getKey() + "\n");
                } else {
                    // 写入配置项
                    writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
                }
            }
        }
    }

    // 获取配置文件的完整路径
    public static String getConfigPath() {
        return CONFIG_PATH;
    }

    /**
     * 更新单个配置项并保存到文件
     * @param key 配置键（如"KEY", "CURSOR"等）
     * @param value 配置值
     */
    public static void updateConfig(String key, Object value) {
        // 1. 先加载现有配置
        LinkedHashMap<String, String> props = loadConfigFile();
        // 2. 更新指定配置
        props.put(key, String.valueOf(value));
        // 3. 更新内存中的配置值
        updateMemoryConfig(key, value);
        // 4. 保存到文件
        saveConfigWithComments(props);
    }

    // 加载配置文件内容
    private static LinkedHashMap<String, String> loadConfigFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#") && !line.trim().isEmpty()) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        configMap.put(parts[0].trim(), parts[1].trim());
                    }
                }else if (line.trim().startsWith("#")){
                    configMap.put(line.trim(), "");
                }
            }
        } catch (Exception e){
            System.out.println("加载配置文件内容出现异常：" + e.getMessage());
        }
        return configMap;
    }

    // 更新内存中的配置值
    private static void updateMemoryConfig(String key, Object value) {
        switch (key) {
            case "CLICK_KEY":
                CLICK_KEY = Integer.parseInt(String.valueOf(value));
                break;
            case "SINGLE_CLICK":
                SINGLE_CLICK = Boolean.parseBoolean(String.valueOf(value));
                break;
            case "CURSOR":
                CURSOR = Boolean.parseBoolean(String.valueOf(value));
                break;
            case "X":
                X = Integer.parseInt(String.valueOf(value));
                break;
            case "Y":
                Y = Integer.parseInt(String.valueOf(value));
                break;
            case "INTERVAL_H":
                INTERVAL_H = Integer.parseInt(String.valueOf(value));
                break;
            case "INTERVAL_MIN":
                INTERVAL_MIN = Integer.parseInt(String.valueOf(value));
                break;
            case "INTERVAL_S":
                INTERVAL_S = Integer.parseInt(String.valueOf(value));
                break;
            case "INTERVAL_MS":
                INTERVAL_MS = Integer.parseInt(String.valueOf(value));
                break;
            case "CLICK_NUM":
                CLICK_NUM = Integer.parseInt(String.valueOf(value));
                break;
            case "CLICK_TIME":
                CLICK_TIME = Integer.parseInt(String.valueOf(value));
                break;
            case "CLICK_WAIT":
                CLICK_WAIT = Integer.parseInt(String.valueOf(value));
                break;
            case "CLICK_DELAY":
                CLICK_DELAY = Integer.parseInt(String.valueOf(value));
                break;
            case "REPEAT_UNIT":
                REPEAT_UNIT = String.valueOf(value);
                break;
            case "CLICK_SITUATION":
                CLICK_SITUATION = String.valueOf(value);
                break;
            case "START_KEYBOARD":
                START_KEYBOARD = Integer.parseInt(String.valueOf(value));
                break;
            // 其他配置项...
            default:
                throw new IllegalArgumentException("未知的配置项: " + key);
        }
    }

    // 带注释的配置文件保存
    private static void saveConfigWithComments(LinkedHashMap<String, String> props) {
        // 自定义写入方式以保留注释
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
            // 写入所有属性
            for (Map.Entry<String, String> entry : props.entrySet()) {
                if (entry.getKey().startsWith("#")) {
                    // 写入注释行
                    writer.write(entry.getKey() + "\n");
                } else {
                    // 写入配置项
                    writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
                }
            }

        }catch (Exception e){
            System.out.println("配置文件保存出现异常：" + e.getMessage());
        }
    }
}