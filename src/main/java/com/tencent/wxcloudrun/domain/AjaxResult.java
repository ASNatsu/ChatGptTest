package com.tencent.wxcloudrun.domain;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tencent.wxcloudrun.enums.HttpStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 操作消息提醒
 *
 * @author Lion Li
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("请求响应对象")
@Builder
public class AjaxResult<T>{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码
     */
    @ApiModelProperty("消息状态码")
    private int code;
    
    /**
     * 返回内容
     */
    @ApiModelProperty("消息内容")
    private String msg;
    
    /**
     * 数据对象
     */
    @ApiModelProperty("数据对象")
    private T data;

    @ApiModelProperty("总记录数")
    private long total;

    @JsonIgnore
    private StackTraceElement controllerStack;

    public AjaxResult() {
        this.code = HttpStatusEnum.SUCCESS.getCode();
        this.msg = HttpStatusEnum.SUCCESS.getDesc();
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AjaxResult(int httpOk, String msg, T data) {
        this.code = httpOk;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult<Void> success() {
        return AjaxResult.success("操作成功");
    }
    
    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(T data) {
        return AjaxResult.success("操作成功", data);
    }
    public static <T> AjaxResult<T> build(Process<AjaxResult<T>> consumer) throws Exception {
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.controllerStack = Thread.currentThread().getStackTrace()[2];
        consumer.run(ajaxResult);
        return ajaxResult;
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult<Void> success(String msg) {
        return AjaxResult.success(msg, null);
    }
    
    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(String msg, T data) {
        return new AjaxResult<>(HttpStatus.HTTP_OK, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult<Void> error() {
        return AjaxResult.error("操作失败");
    }
    
    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult<Void> error(String msg) {
        return AjaxResult.error(msg, null);
    }
    
    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> AjaxResult<T> error(String msg, T data) {
        return new AjaxResult<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }
    
    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult<Void> error(int code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }

    public interface Process<T> {
        void run(T t) throws Exception;
    }
}
