package com.tencent.wxcloudrun.utils;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Astar
 * ClassName:OpenAIAPI.java
 * date:2023-03-03 16:49
 * Description:
 */
@UtilityClass
public class OpenAIAPI {
    /**
     * 聊天端点
     */
    String chatEndpoint = "https://api.openai.com/v1/chat/completions";
    /**
     * api密匙
     */
    String apiKey = "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";


    /**
     * 发送消息paramMap
     *
//     * @param txt 内容
     * @return {@link String}
     */
    public String chat(List<Map<String, String>> dataList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "gpt-3.5-turbo");
//        List<Map<String, String>> dataList = new ArrayList<>();
//        dataList.add(new HashMap<String, String>(){{
//            put("role", "user");
//            put("content", txt);
//        }});
        paramMap.put("messages", dataList);
        JSONObject message = null;
        try {
            String body = HttpRequest.post(chatEndpoint)
                    .header("Authorization", "Bearer "+apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(paramMap))
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (HttpException | ConvertException e) {
            return "出现了异常";
        }
        return message.getStr("content");
    }

//    public static void main(String[] args) {
//        System.out.println(chat("Hello，一个小浪吴啊"));
//    }
}

