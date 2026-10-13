package com.ks.ai.agent.mcp.server.demo;


import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherServer {

    //告诉 MCP Server：这个 Java 方法可以作为一个 Tool 暴露出去。
    //SpringAI 1.x 没有McpTool
    @McpTool(name = "weather", description = "查询指定城市当前天气")
    public String weather(
            @McpToolParam(description = "城市名称，例如 Beijing、Shanghai、Tokyo", required = true ) String city ) {
        // 实际项目这里可以调用天气 API
        return city + " 当前天气：晴，温度 25°C";
    }
}

