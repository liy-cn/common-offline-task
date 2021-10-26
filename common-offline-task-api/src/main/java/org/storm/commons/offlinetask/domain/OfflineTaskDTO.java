package org.storm.commons.offlinetask.domain;

import lombok.Data;

@Data
public class OfflineTaskDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 调用端的id，如1，2，3
     */
    private Integer clientCode;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 类型，是枚举字典的值，根据它去找到对应的业务逻辑类，，如1：订单导出类
     */
    private Integer taskType;

    /**
     * 任务状态1待执行,2执行中,3成功,4失败,5已取消
     */
    private Integer taskStatus;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 任务进度
     */
    private Double taskProgress;

    /**
     * 任务参数（json格式的字符串）
     */
    private String taskParam;

    /**
     * 最后的下载链接的地址
     */
    private String taskFileAddress;

    /**
     * 任务返回的结果信息
     */
    private String taskResultMsg;

    /**
     * 调用端扩展字段，json格式，可以放appCode，appName等
     */
    private String clientInfo;
}
