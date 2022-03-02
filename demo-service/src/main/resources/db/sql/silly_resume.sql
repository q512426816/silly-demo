/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : silly_activiti

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 02/03/2022 08:52:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for silly_resume
-- ----------------------------
DROP TABLE IF EXISTS `silly_resume`;
CREATE TABLE `silly_resume`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `CREATE_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人ID',
  `UPDATE_DATE` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `BUSINESS_TYPE` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务类型',
  `BUSINESS_ID` bigint NULL DEFAULT NULL COMMENT '业务表ID',
  `SEQ` int NULL DEFAULT NULL COMMENT '序号',
  `PROCESS_NODE_KEY` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程节点 跟工作流节点一致',
  `PROCESS_NODE_NAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程节点名称',
  `HANDLE_DEPT_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前处置部门',
  `HANDLE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前处置人',
  `NEXT_USER_ID` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '下一步处置人ID 多个 , 拼接',
  `HANDLE_DATE` datetime NULL DEFAULT NULL COMMENT '处置时间',
  `HANDLE_INFO` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处置内容',
  `CONSUME_TIME` bigint NULL DEFAULT NULL COMMENT '处置耗时',
  `CONSUME_DEPT_TIME` bigint NULL DEFAULT NULL COMMENT '部门耗时',
  `PROCESS_TYPE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程类型',
  `PROCESS_ID` bigint NULL DEFAULT NULL COMMENT '上一流程ID',
  `PROCESS_LEVEL` int NULL DEFAULT NULL COMMENT '流程等级',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `index_buskey`(`BUSINESS_ID` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
