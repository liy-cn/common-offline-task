# CommonOfflineTask

Common Offline Task (Java implements async task management)

Java实现的通用离线任务

```sql
CREATE TABLE `common_offline_task`
(
    `id`                bigint(20) unsigned NOT NULL COMMENT '主键',
    `client_code`       tinyint(4)    DEFAULT NULL COMMENT '调用端的id，如1：你的客户端',
    `task_no`           varchar(50)   DEFAULT NULL COMMENT '任务编号',
    `task_type`         tinyint(4)    DEFAULT NULL COMMENT '类型，是枚举字典的值，根据它去找到对应的业务逻辑类，，如1：导出类',
    `task_status`       tinyint(4)    DEFAULT NULL COMMENT '任务状态1待执行,2执行中,3成功,4失败,5已取消',
    `task_name`         varchar(50)   DEFAULT NULL COMMENT '任务名称',
    `task_description`  varchar(100)  DEFAULT NULL COMMENT '任务描述',
    `task_progress`     double        DEFAULT NULL COMMENT '任务进度(规则自己定)',
    `task_param`        varchar(2000) DEFAULT NULL COMMENT '任务参数（json格式的字符串）',
    `task_file_address` varchar(500)  DEFAULT NULL COMMENT '最后的下载链接的地址',
    `task_result_msg`   varchar(2000) DEFAULT NULL COMMENT '任务返回的结果信息',
    `client_info`       varchar(2000) DEFAULT NULL COMMENT '调用端扩展字段，json格式，可以放appCode，appName等有用的信息',
    `creator`           varchar(50)   DEFAULT NULL COMMENT '创建人',
    `created`           datetime      DEFAULT NULL COMMENT '创建时间',
    `modifier`          varchar(50)   DEFAULT NULL COMMENT '修改人',
    `modified`          datetime      DEFAULT NULL COMMENT '修改时间',
    `yn`                tinyint(2)    DEFAULT '1' COMMENT '删除标记',
    PRIMARY KEY (`id`)    
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通用离线任务表';
```
