package com.ks.ai.invoke.output;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/out")
public class StructureOutputController {
    @Qualifier("ollamaChatClient")
    @Autowired
    private ChatClient chatClient;

    /**
     * 结构化输出：Boolean
     */
    @GetMapping("outBoolean")
    public void hi() {
        Boolean isComplain = chatClient
                .prompt()
                .system("""
                    请判断用户信息是否表达了投诉意图?
                    只能用 true 或 false 回答，不要输出多余内容
                 """)
                .user("你们家的快递迟迟不到,我要退货！")
                .call()
                .entity(Boolean.class);

        // 分支逻辑
        if (Boolean.TRUE.equals(isComplain)) {
            System.out.println("用户是投诉，转接人工客服！");
        } else {
            System.out.println("用户不是投诉，自动流转客服机器人。");
            // todo 继续调用
        }
    }

    @GetMapping("outObj")
    public void outObj() {
        @Data
        class Address  {
            String name;// 收件人姓名
            String phone;// 联系电话
            String province;// 省
            String city;// 市
            String district;// 区/县
            String detail;       // 详细地址
        }

        Address address = chatClient.prompt()
                .system("""
                        请从下面这条文本中提取收货信息
                        """)
                .user("收货人：张三，电话13588888888，地址：浙江省杭州市西湖区文一西路100号8幢202室")
                .call()
                .entity(Address.class);
        System.out.println(JSON.toJSONString(address));

    }
}
