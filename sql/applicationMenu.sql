-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请', '3', '1', 'application', 'poor/application/index', 1, 0, 'C', '0', '0', 'poor:application:list', '#', 'admin', sysdate(), '', null, '贫困申请菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'poor:application:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'poor:application:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'poor:application:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'poor:application:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困申请导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'poor:application:export',       '#', 'admin', sysdate(), '', null, '');