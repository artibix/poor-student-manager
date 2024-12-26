-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助', '3', '1', 'financial', 'poor/financial/index', 1, 0, 'C', '0', '0', 'poor:financial:list', '#', 'admin', sysdate(), '', null, '贫困资助菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'poor:financial:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'poor:financial:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'poor:financial:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'poor:financial:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('贫困资助导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'poor:financial:export',       '#', 'admin', sysdate(), '', null, '');