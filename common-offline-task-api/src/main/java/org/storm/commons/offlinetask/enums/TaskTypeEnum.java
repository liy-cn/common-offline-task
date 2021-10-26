package org.storm.commons.offlinetask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskTypeEnum {

    ORDER_EXPORT(1, "订单导出"),
    ;

    private Integer taskType;
    private String taskDesc;

    public static TaskTypeEnum getEnumByType(Integer taskType) {
        for (TaskTypeEnum typeEnum : TaskTypeEnum.values()) {
            if (typeEnum.getTaskType().equals(taskType)) {
                return typeEnum;
            }
        }
        return null;
    }
}
