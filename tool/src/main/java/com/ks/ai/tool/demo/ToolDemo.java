package com.ks.ai.tool.demo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class ToolDemo {
    /**
     * 查询城市天气
     * @param city 城市名称，例如：北京、上海
     * @return 天气结果
     */
    @Tool(description = "查询指定城市的实时天气，当用户询问某地天气时调用")
    public String getWeather(@ToolParam(description = "城市名称，中文") String city) {
        System.out.println("调用Tool：getWeather，入参="+ city);
        // 模拟远程天气接口，真实场景替换http调用
        return switch (city) {
            case "北京" -> "北京：晴，26℃，西北风2级";
            case "上海" -> "上海：多云，29℃，东南风3级";
            case "广州" -> "广州：小雨，31℃";
            default -> city + "暂无天气数据";
        };
    }

    /**
     * 加法计算器
     */
    @Tool(description = "两个数字相加，数学加法计算")
    public double add(@ToolParam(description = "第一个数字") double a,
            @ToolParam(description = "第二个数字") double b ) {
        return a + b;
    }
}
