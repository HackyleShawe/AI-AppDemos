package com.ks.ai.invoke.qwen;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/image")
@RestController
public class Text2ImageController {
    @Autowired
    DashScopeImageModel imageModel;
    @GetMapping("/genImage")
    public String genImage() {
        DashScopeImageOptions imageOptions = DashScopeImageOptions.builder()
                //.withModel("wanx2.1-t2i-turbo")
                .build();

        ImageResponse imageResponse = imageModel.call(
                new ImagePrompt("程序员徐庶", imageOptions));
        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        // 图片url
        System.out.println(imageUrl);
        return imageUrl;
    }


}
