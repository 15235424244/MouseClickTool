package hao.mousedefibrillator.tools;

public class TimeConverter {
    /**
     * 将时分秒毫秒转换为总毫秒数
     * @param hours 小时数
     * @param minutes 分钟数
     * @param seconds 秒数
     * @param milliseconds 毫秒数
     * @return 总毫秒数
     * @throws IllegalArgumentException 如果输入参数为负数
     */
    public static long convertToMilliseconds(int hours, int minutes, int seconds, int milliseconds) {
        // 参数校验
        if (hours < 0 || minutes < 0 || seconds < 0 || milliseconds < 0) {
            throw new IllegalArgumentException("时间参数不能为负数");
        }

        // 计算总毫秒数
        long totalMilliseconds = 0L;
        totalMilliseconds += hours * 60L * 60L * 1000L;    // 小时转毫秒
        totalMilliseconds += minutes * 60L * 1000L;        // 分钟转毫秒
        totalMilliseconds += seconds * 1000L;              // 秒转毫秒
        totalMilliseconds += milliseconds;                 // 毫秒

        return totalMilliseconds;
    }

    /**
     * 根据时间单位将数值转换为毫秒数
     * @param value 时间数值
     * @param unit 时间单位 ("h"/"hour"/"时", "m"/"min"/"分", "s"/"sec"/"秒", "ms"/"milli"/"毫秒")
     * @return 对应的毫秒数
     * @throws IllegalArgumentException 如果数值为负或单位不合法
     */
    public static long convertToMilliseconds(double value, String unit) {
        if (value < 0) {
            throw new IllegalArgumentException("时间值不能为负数");
        }

        // 统一转换为小写并去除前后空格
        String normalizedUnit = unit.trim().toLowerCase();

        switch (normalizedUnit) {
            case "h":
            case "hour":
            case "时":
                return (long)(value * 60 * 60 * 1000);
            case "m":
            case "min":
            case "分":
                return (long)(value * 60 * 1000);
            case "s":
            case "sec":
            case "秒":
                return (long)(value * 1000);
            case "ms":
            case "milli":
            case "毫秒":
                return (long)value;
            default:
                throw new IllegalArgumentException("不合法的时间单位: " + unit +
                        " (可用单位: h/hour/时, m/min/分, s/sec/秒, ms/milli/毫秒)");
        }
    }

}