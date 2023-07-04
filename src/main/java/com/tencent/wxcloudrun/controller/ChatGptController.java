package com.tencent.wxcloudrun.controller;



import com.tencent.wxcloudrun.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "ChatGpt", tags = "ChatGPT")
@RequestMapping("/chatGpt")
@Validated
@RequiredArgsConstructor
public class ChatGptController {


    /**
     * 修改试飞架次号信息
     */
    @ApiOperation(value = "修改试飞架次号信息")
    @PostMapping("/openAi")
    public AjaxResult<?> openAi(String test)  throws Exception {
//        return AjaxResult.success(OpenAIAPI.chat(test));
        return AjaxResult.success();
    }
}
