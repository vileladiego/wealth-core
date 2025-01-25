INSERT INTO tb_roles (role_id, name)
SELECT 1, 'TENANT_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM tb_roles WHERE role_id = 1);

INSERT INTO tb_roles (role_id, name)
SELECT 2, 'TEAM_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM tb_roles WHERE role_id = 2);

INSERT INTO tb_roles (role_id, name)
SELECT 3, 'FINANCIAL_ADVISOR'
WHERE NOT EXISTS (SELECT 1 FROM tb_roles WHERE role_id = 3);
