package org.storm.commons.offlinetask.domain;

import lombok.Data;

@Data
public class QueryOfflineTaskParam {
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 调用端的id，如1
     */
    private Integer clientCode;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 门店id
     */
    private Long storeId;

    /**
     * pin
     */
    private String pin;

    /**
     * erp
     */
    private String erp;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 类型，是枚举字典的值，根据它去找到对应的业务逻辑类，如1：订单导出类
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
     * clientInfo 扩展字段
     */
    private String clientInfo;
}
