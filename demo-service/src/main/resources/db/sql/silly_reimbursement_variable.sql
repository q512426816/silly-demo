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

 Date: 02/03/2022 08:52:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for silly_reimbursement_variable
-- ----------------------------
DROP TABLE IF EXISTS `silly_reimbursement_variable`;
CREATE TABLE `silly_reimbursement_variable`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `CREATE_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人ID',
  `UPDATE_DATE` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `STATUS` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '10: 当前数据 20：历史数据',
  `NODE_ID` bigint NULL DEFAULT NULL COMMENT '节点数据ID',
  `MASTER_ID` bigint NULL DEFAULT NULL COMMENT '主数据ID',
  `TASK_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务ID',
  `NODE_KEY` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '节点Key',
  `ACTIVITI_HANDLER` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程变量类型',
  `VARIABLE_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '变量类型',
  `VARIABLE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '变量名称',
  `VARIABLE_TEXT` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '变量内容',
  `BELONG` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `index_MASTER_ID`(`MASTER_ID` ASC) USING BTREE,
  INDEX `index_NODE_ID`(`NODE_ID` ASC) USING BTREE,
  INDEX `index_NODE_KEY`(`NODE_KEY` ASC) USING BTREE,
  INDEX `index_VARIABLE_NAME`(`VARIABLE_NAME` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 189 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
