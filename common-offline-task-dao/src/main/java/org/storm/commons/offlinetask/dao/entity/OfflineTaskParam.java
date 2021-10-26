package org.storm.commons.offlinetask.dao.entity;

import lombok.Data;

@Data
public class OfflineTaskParam {

    /**
     * 默认页码
     */
    private static final Integer DEFAULT_PAGE_NO = 1;
    /**
     * 默认页大小
     */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

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

    /**
     * 取当前页
     */
    public Integer getCurrentPage() {
        return pageNum !=null ? pageNum : DEFAULT_PAGE_NO;
    }

    public void setCurrentPage(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 取页大小
     * @return
     */
    public Integer getPageSize() {
        return pageSize !=null ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return (this.getPageNum() - 1) * this.getPageSize();
    }
}
