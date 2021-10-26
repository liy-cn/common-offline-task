package org.storm.commons.offlinetask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatusEnum {

    DAI_ZHI_XING(1, "待执行"),
    ZHI_XING_ZHONG(2, "执行中"),
    CHENG_GONG(3, "执行成功"),
    SHI_BAI(4, "执行失败"),
    YI_QU_XIAO(5, "已取消"),
    ;

    private Integer code;
    private String desc;

}
