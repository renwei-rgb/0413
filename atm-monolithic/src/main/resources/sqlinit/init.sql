-- 创建数据库
CREATE DATABASE IF NOT EXISTS atm_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE atm_db;

-- 部门表
CREATE TABLE IF NOT EXISTS `departments` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '部门名称',
    `code` VARCHAR(50) NOT NULL COMMENT '部门编码',
    `parent_id` BIGINT COMMENT '父部门ID',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 用户表
CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `employee_id` VARCHAR(50) COMMENT '员工编号',
    `department_id` BIGINT COMMENT '部门ID',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_employee_id` (`employee_id`),
    KEY `idx_department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `roles` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `code` VARCHAR(50) NOT NULL COMMENT '角色编码',
    `description` VARCHAR(200) COMMENT '描述',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE IF NOT EXISTS `permissions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '权限名称',
    `code` VARCHAR(50) NOT NULL COMMENT '权限编码',
    `type` TINYINT NOT NULL COMMENT '类型：1-菜单，2-按钮，3-接口',
    `parent_id` BIGINT COMMENT '父权限ID',
    `path` VARCHAR(200) COMMENT '路径',
    `component` VARCHAR(200) COMMENT '组件',
    `icon` VARCHAR(100) COMMENT '图标',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permissions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `user_roles` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 考勤记录表
CREATE TABLE IF NOT EXISTS `sys_attendance` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `check_in_time` DATETIME COMMENT '上班打卡时间',
    `check_out_time` DATETIME COMMENT '下班打卡时间',
    `check_in_status` TINYINT COMMENT '上班打卡状态：0-正常，1-迟到，2-缺卡',
    `check_out_status` TINYINT COMMENT '下班打卡状态：0-正常，1-早退，2-缺卡',
    `date` DATE NOT NULL COMMENT '考勤日期',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_date` (`user_id`, `date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤记录表';

-- 考勤报表表
CREATE TABLE IF NOT EXISTS `sys_attendance_report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `year` INT NOT NULL COMMENT '年份',
    `month` INT NOT NULL COMMENT '月份',
    `normal_days` INT DEFAULT 0 COMMENT '正常出勤天数',
    `late_days` INT DEFAULT 0 COMMENT '迟到天数',
    `early_leave_days` INT DEFAULT 0 COMMENT '早退天数',
    `absent_days` INT DEFAULT 0 COMMENT '缺勤天数',
    `leave_days` DECIMAL(5,1) DEFAULT 0 COMMENT '请假天数',
    `overtime_hours` DECIMAL(5,1) DEFAULT 0 COMMENT '加班小时数',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_year_month` (`user_id`, `year`, `month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤报表表';

-- 员工信息表
CREATE TABLE IF NOT EXISTS `sys_employee` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `employee_id` VARCHAR(50) NOT NULL COMMENT '员工编号',
    `department_id` BIGINT COMMENT '部门ID',
    `position` VARCHAR(50) COMMENT '职位',
    `entry_date` DATE COMMENT '入职日期',
    `leave_date` DATE COMMENT '离职日期',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-离职，1-在职',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_id` (`employee_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工信息表';

-- 插入初始数据
INSERT INTO `departments` (`name`, `code`, `status`) VALUES 
('管理部', 'ADMIN', 1),
('技术部', 'TECH', 1),
('人事部', 'HR', 1),
('财务部', 'FINANCE', 1);

INSERT INTO `roles` (`name`, `code`, `description`, `status`) VALUES 
('系统管理员', 'ROLE_ADMIN', '系统管理员，拥有所有权限', 1),
('普通用户', 'ROLE_USER', '普通用户，拥有基本权限', 1);

INSERT INTO `users` (`username`, `password`, `real_name`, `email`, `phone`, `employee_id`, `department_id`, `status`)
VALUES 
('admin', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '系统管理员', 'admin@example.com', '13800138000', 'EMP001', 1, 1),
('test', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '测试用户', 'test@example.com', '13800138001', 'EMP002', 2, 1);

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES 
(1, 1), -- admin -> ROLE_ADMIN
(2, 2); -- test -> ROLE_USER 

-- 插入测试数据
-- 1. 部门表
INSERT INTO `departments` (`name`, `code`, `status`) VALUES
('市场部', 'MARKETING', 1),
('研发部', 'RESEARCH', 1),
('销售部', 'SALES', 1),
('人事部', 'HR', 1),
('财务部', 'FINANCE', 1),
('行政部', 'ADMIN', 1),
('客服部', 'CUSTOMER', 1),
('采购部', 'PURCHASE', 1),
('物流部', 'LOGISTICS', 1),
('IT部', 'IT', 1);

-- 2. 角色表
INSERT INTO `roles` (`name`, `code`, `description`, `status`) VALUES
('系统管理员', 'ROLE_ADMIN', '系统管理员', 1),
('普通用户', 'ROLE_USER', '普通用户', 1),
('销售经理', 'ROLE_SALES_MANAGER', '销售团队管理', 1),
('技术支持', 'ROLE_TECH_SUPPORT', '技术支持', 1),
('人事专员', 'ROLE_HR', '人事管理', 1);

-- 3. 权限表
INSERT INTO `permissions` (`name`, `code`, `type`, `status`) VALUES
('查看报表', 'PERM_VIEW_REPORT', 3, 1),
('编辑用户', 'PERM_EDIT_USER', 2, 1),
('考勤打卡', 'PERM_ATTENDANCE', 2, 1),
('部门管理', 'PERM_DEPT_MANAGE', 1, 1),
('角色分配', 'PERM_ROLE_ASSIGN', 2, 1),
('工资管理', 'PERM_SALARY', 2, 1);

-- 4. 用户表
INSERT INTO `users` (`username`, `password`, `real_name`, `email`, `phone`, `employee_id`, `department_id`, `status`) VALUES
('admin', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '系统管理员', 'admin@example.com', '13800000000', 'EMP001', 1, 1),
('user1', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '张三', 'user1@example.com', '13800000001', 'EMP002', 2, 1),
('user2', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '李四', 'user2@example.com', '13800000002', 'EMP003', 3, 1),
('user3', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '王五', 'user3@example.com', '13800000003', 'EMP004', 4, 1),
('user4', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '赵六', 'user4@example.com', '13800000004', 'EMP005', 5, 1),
('user5', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '钱七', 'user5@example.com', '13800000005', 'EMP006', 6, 1),
('user6', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '孙八', 'user6@example.com', '13800000006', 'EMP007', 7, 1),
('user7', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '周九', 'user7@example.com', '13800000007', 'EMP008', 8, 1),
('user8', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '吴十', 'user8@example.com', '13800000008', 'EMP009', 9, 1),
('user9', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '郑十一', 'user9@example.com', '13800000009', 'EMP010', 10, 1),
('user10', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '王十二', 'user10@example.com', '13800000010', 'EMP011', 1, 1),
('user11', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '冯十三', 'user11@example.com', '13800000011', 'EMP012', 2, 1),
('user12', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '陈十四', 'user12@example.com', '13800000012', 'EMP013', 3, 1),
('user13', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '褚十五', 'user13@example.com', '13800000013', 'EMP014', 4, 1),
('user14', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '卫十六', 'user14@example.com', '13800000014', 'EMP015', 5, 1),
('user15', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '蒋十七', 'user15@example.com', '13800000015', 'EMP016', 6, 1),
('user16', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '沈十八', 'user16@example.com', '13800000016', 'EMP017', 7, 1),
('user17', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '韩十九', 'user17@example.com', '13800000017', 'EMP018', 8, 1),
('user18', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '杨二十', 'user18@example.com', '13800000018', 'EMP019', 9, 1),
('user19', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '朱二一', 'user19@example.com', '13800000019', 'EMP020', 10, 1),
('user20', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '秦二二', 'user20@example.com', '13800000020', 'EMP021', 1, 1),
('user21', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '尤二三', 'user21@example.com', '13800000021', 'EMP022', 2, 1),
('user22', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '许二四', 'user22@example.com', '13800000022', 'EMP023', 3, 1),
('user23', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '何二五', 'user23@example.com', '13800000023', 'EMP024', 4, 1),
('user24', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '吕二六', 'user24@example.com', '13800000024', 'EMP025', 5, 1),
('user25', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '施二七', 'user25@example.com', '13800000025', 'EMP026', 6, 1),
('user26', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '张二八', 'user26@example.com', '13800000026', 'EMP027', 7, 1),
('user27', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '孔二九', 'user27@example.com', '13800000027', 'EMP028', 8, 1),
('user28', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '曹三十', 'user28@example.com', '13800000028', 'EMP029', 9, 1),
('user29', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '严三一', 'user29@example.com', '13800000029', 'EMP030', 10, 1),
('user30', '$2a$10$X/hX4Jz7UxXz7UxXz7UxX.7UxXz7UxXz7UxXz7UxXz7UxXz7UxX', '华三二', 'user30@example.com', '13800000030', 'EMP031', 1, 1);

-- 5. 用户角色关联表
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2),
(13, 2),
(14, 2),
(15, 2),
(16, 2),
(17, 2),
(18, 2),
(19, 2),
(20, 2),
(21, 2),
(22, 2),
(23, 2),
(24, 2),
(25, 2),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2);

-- 6. 员工信息表
INSERT INTO `sys_employee` (`user_id`, `employee_id`, `department_id`, `position`, `entry_date`, `status`) VALUES
(1, 'EMP001', 1, '系统管理员', '2023-01-01', 1),
(2, 'EMP002', 2, '研发工程师', '2023-01-02', 1),
(3, 'EMP003', 3, '销售经理', '2023-01-03', 1),
(4, 'EMP004', 4, '人事专员', '2023-01-04', 1),
(5, 'EMP005', 5, '财务专员', '2023-01-05', 1),
(6, 'EMP006', 6, '行政专员', '2023-01-06', 1),
(7, 'EMP007', 7, '客服', '2023-01-07', 1),
(8, 'EMP008', 8, '采购', '2023-01-08', 1),
(9, 'EMP009', 9, '物流', '2023-01-09', 1),
(10, 'EMP010', 10, 'IT专员', '2023-01-10', 1),
(11, 'EMP011', 1, '市场专员', '2023-01-11', 1),
(12, 'EMP012', 2, '研发工程师', '2023-01-12', 1),
(13, 'EMP013', 3, '销售', '2023-01-13', 1),
(14, 'EMP014', 4, '人事', '2023-01-14', 1),
(15, 'EMP015', 5, '财务', '2023-01-15', 1),
(16, 'EMP016', 6, '行政', '2023-01-16', 1),
(17, 'EMP017', 7, '客服', '2023-01-17', 1),
(18, 'EMP018', 8, '采购', '2023-01-18', 1),
(19, 'EMP019', 9, '物流', '2023-01-19', 1),
(20, 'EMP020', 10, 'IT', '2023-01-20', 1),
(21, 'EMP021', 1, '市场', '2023-01-21', 1),
(22, 'EMP022', 2, '研发', '2023-01-22', 1),
(23, 'EMP023', 3, '销售', '2023-01-23', 1),
(24, 'EMP024', 4, '人事', '2023-01-24', 1),
(25, 'EMP025', 5, '财务', '2023-01-25', 1),
(26, 'EMP026', 6, '行政', '2023-01-26', 1),
(27, 'EMP027', 7, '客服', '2023-01-27', 1),
(28, 'EMP028', 8, '采购', '2023-01-28', 1),
(29, 'EMP029', 9, '物流', '2023-01-29', 1),
(30, 'EMP030', 10, 'IT', '2023-01-30', 1);

-- 7. 考勤记录表
INSERT INTO `sys_attendance` (`user_id`, `check_in_time`, `check_out_time`, `check_in_status`, `check_out_status`, `date`) VALUES
(1, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(1, '2025-05-02 09:05:00', '2025-05-02 18:10:00', 1, 0, '2025-05-02'),
(2, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(2, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(3, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(3, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(4, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(4, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(5, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(5, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(6, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(6, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(7, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(7, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(8, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(8, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(9, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(9, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(10, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(10, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(11, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(11, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(12, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(12, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(13, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(13, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(14, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(14, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(15, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(15, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(16, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(16, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(17, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(17, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(18, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(18, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(19, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(19, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(20, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(20, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(21, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(21, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(22, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(22, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(23, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(23, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(24, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(24, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(25, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(25, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(26, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(26, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(27, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(27, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(28, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(28, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(29, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(29, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02'),
(30, '2025-05-01 09:00:00', '2025-05-01 18:00:00', 0, 0, '2025-05-01'),
(30, '2025-05-02 09:10:00', '2025-05-02 18:00:00', 1, 0, '2025-05-02');

-- 8. 考勤报表表
INSERT INTO `sys_attendance_report` (`user_id`, `year`, `month`, `normal_days`, `late_days`, `early_leave_days`, `absent_days`, `leave_days`, `overtime_hours`) VALUES
(1, 2025, 5, 20, 2, 0, 0, 0, 5),
(2, 2025, 5, 19, 3, 0, 0, 0, 4),
(3, 2025, 5, 18, 4, 0, 0, 0, 3),
(4, 2025, 5, 21, 1, 0, 0, 0, 2),
(5, 2025, 5, 20, 2, 0, 0, 0, 1),
(6, 2025, 5, 20, 2, 0, 0, 0, 0),
(7, 2025, 5, 20, 2, 0, 0, 0, 0),
(8, 2025, 5, 20, 2, 0, 0, 0, 0),
(9, 2025, 5, 20, 2, 0, 0, 0, 0),
(10, 2025, 5, 20, 2, 0, 0, 0, 0),
(11, 2025, 5, 20, 2, 0, 0, 0, 0),
(12, 2025, 5, 20, 2, 0, 0, 0, 0),
(13, 2025, 5, 20, 2, 0, 0, 0, 0),
(14, 2025, 5, 20, 2, 0, 0, 0, 0),
(15, 2025, 5, 20, 2, 0, 0, 0, 0),
(16, 2025, 5, 20, 2, 0, 0, 0, 0),
(17, 2025, 5, 20, 2, 0, 0, 0, 0),
(18, 2025, 5, 20, 2, 0, 0, 0, 0),
(19, 2025, 5, 20, 2, 0, 0, 0, 0),
(20, 2025, 5, 20, 2, 0, 0, 0, 0),
(21, 2025, 5, 20, 2, 0, 0, 0, 0),
(22, 2025, 5, 20, 2, 0, 0, 0, 0),
(23, 2025, 5, 20, 2, 0, 0, 0, 0),
(24, 2025, 5, 20, 2, 0, 0, 0, 0),
(25, 2025, 5, 20, 2, 0, 0, 0, 0),
(26, 2025, 5, 20, 2, 0, 0, 0, 0),
(27, 2025, 5, 20, 2, 0, 0, 0, 0),
(28, 2025, 5, 20, 2, 0, 0, 0, 0),
(29, 2025, 5, 20, 2, 0, 0, 0, 0),
(30, 2025, 5, 20, 2, 0, 0, 0, 0);

-- 9. 角色权限关联表
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 1), (4, 3),
(5, 2), (5, 4); 