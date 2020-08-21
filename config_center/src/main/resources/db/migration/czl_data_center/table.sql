create database if not exists czl_data_center;
use czl_data_center;

DROP TABLE IF EXISTS `sys_data_dict`;
CREATE TABLE `sys_data_dict` (
  `did` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '字典id',
  `parent_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '父id',
  `dcode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典编码',
  `dname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典名称',
  PRIMARY KEY (`did`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='数据字典表';

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `PERMISSION_ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '权限表主键',
  `P_PERMISSION_ID` bigint(16) NOT NULL COMMENT '父权限编号,顶级权限的父权限编码为0',
  `PERMISSION_NAME` varchar(32) NOT NULL COMMENT '权限名称',
  `PERMISSION_TYPE` int(1) NOT NULL COMMENT '权限类型.1:左菜单导航目录,2:左菜单导航链接,3:按钮,4:链接',
  `SHOW_FLAG` int(1) NOT NULL COMMENT '是否显示.1:显示,0:隐藏',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

DROP TABLE IF EXISTS `sys_permission_config`;
CREATE TABLE `sys_permission_config` (
  `PERMISSION_CONFIG_ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '权限配置表主键',
  `PERMISSION_URL` varchar(128) DEFAULT NULL COMMENT '链接地址.表示点击菜单或按钮触发的URL地址',
  `PERMISSION_CONFIG` varchar(128) NOT NULL COMMENT '权限.表示访问此地址需要具备的权限',
  `SEQ_NO` int(4) DEFAULT NULL COMMENT '序号.shiro filter由上到下执行',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '备注',
  `PERMISSION_NAME` varchar(50) NOT NULL COMMENT '菜单名称',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  `PERMISSION_TYPE` bit(1) DEFAULT b'0' COMMENT '0目录 1 链接',
  `P_PERMISSION_ID` int(10) DEFAULT 0 COMMENT '父级id',
  `SYS_ID` varchar(20) DEFAULT NULL COMMENT '系统平台的标识',
  `SORT_NO` int(11) DEFAULT 0 COMMENT '排序号',
  `DISPLAY` int(1) DEFAULT 1 COMMENT '是否显示 1是 0 否',
  `ALIVE_FLAG` char(1) DEFAULT NULL COMMENT '生效标志.1:生效;0:删除.',
  `ADD_USER_ID` bigint(16) DEFAULT NULL COMMENT '添加用户编号',
  `ADD_TIME` datetime DEFAULT NULL COMMENT '添加时间',
  `OPR_USER_ID` bigint(16) DEFAULT NULL COMMENT '修改用户编号',
  `OPR_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `SUBSYS_CODE` varchar(9) NOT NULL COMMENT '系统标识',
  `CHECK_FLAG` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_CONFIG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=4096 COMMENT='权限配置表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `ROLE_CODE` varchar(20) NOT NULL COMMENT '角色标识',
  `ROLE_NAME` varchar(64) NOT NULL COMMENT '角色名称',
  `ALIVE_FLAG` char(1) DEFAULT NULL COMMENT '生效标志.1:生效;0:删除.',
  `ADD_USER_ID` bigint(16) DEFAULT NULL COMMENT '添加用户编号',
  `ADD_TIME` datetime DEFAULT NULL COMMENT '添加时间',
  `OPR_USER_ID` bigint(16) DEFAULT NULL COMMENT '修改用户编号',
  `OPR_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `ROLE_TYPE` int(1) NOT NULL COMMENT '角色类型 1管理员 0会员 2：管理员职务相关 3:会员职务相关',
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `UK_SYS_ROLE_1` (`ROLE_CODE`),
  UNIQUE KEY `UK_SYS_ROLE_2` (`ROLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=8192 COMMENT='角色表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `ROLE_ID` bigint(16) NOT NULL COMMENT '角色编号',
  `PERMISSION_ID` bigint(16) NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=585 COMMENT='角色权限关联表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `USER_ACCOUNT` varchar(100) NOT NULL COMMENT '登录账号',
  `USER_PASSWORD` varchar(64) NOT NULL COMMENT '登录密码',
  `USER_NAME` varchar(16) NOT NULL COMMENT '用户名称',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '手机',
  `ADD_TIME` datetime NOT NULL COMMENT '创建时间',
  `LOGIN_TIME` datetime DEFAULT NULL COMMENT '登录时间',
  `LOGIN_IP` varchar(16) DEFAULT NULL COMMENT '登录IP',
  `STATUS` int(1) NOT NULL COMMENT '状态.1:启用,0:禁用,-1:删除',
  `WX_ID` varchar(128) DEFAULT NULL COMMENT '绑定微信id',
  `IMG_URL` varchar(128) DEFAULT NULL COMMENT '图片url地址',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_SYS_USER_ACCOUNT` (`USER_ACCOUNT`),
  KEY `IDX_STATUS` (`STATUS`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=8192 COMMENT='用户表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ID` bigint(16) NOT NULL COMMENT '用户编号',
  `ROLE_ID` bigint(16) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=5461 COMMENT='用户角色关联表';

DROP TABLE IF EXISTS `router_config`;
CREATE TABLE `router_config` (
  `ID` varchar(32) NOT NULL COMMENT '接口本系统内id',
  `INTERFACE_NAME` varchar(128) DEFAULT NULL COMMENT '接口名称',
  `CONTEXT_NAME` varchar(45) DEFAULT NULL COMMENT '上下文名称',
  `DESCRIPTION` text COMMENT '描述',
  `MAPPING_PATH` varchar(255) DEFAULT NULL COMMENT '映射路径',
  `STATUS` int(11) DEFAULT NULL COMMENT '接口状态，1：开启 0：关闭',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由配置表';


