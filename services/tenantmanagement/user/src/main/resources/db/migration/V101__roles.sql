CREATE TABLE IF NOT EXISTS role (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby varchar(64), createdby varchar(64), version bigint DEFAULT 0, rolename varchar(32) NOT NULL, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid),  UNIQUE KEY `con_role` (`tenantid`,`rolename`));
CREATE TABLE IF NOT EXISTS permission (rootid int NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, permission varchar(32) UNIQUE);
CREATE TABLE IF NOT EXISTS rolepermission (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby varchar(64), createdby varchar(64), version bigint DEFAULT 0, roleid varchar(32) NOT NULL, permissionid int NOT NULL, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(permissionid) REFERENCES permission(rootid));
CREATE TABLE IF NOT EXISTS employeerole (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby varchar(64), createdby varchar(64), version bigint DEFAULT 0, roleid varchar(32) NOT NULL, employeeid varchar(64) NOT NULL, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), CONSTRAINT FOREIGN KEY(roleid) REFERENCES role(rootid), CONSTRAINT FOREIGN KEY(employeeid) REFERENCES employee(rootid));

/* Default Data */
INSERT INTO permission (rootid, permission) values(1, "Master");
INSERT INTO permission (rootid, permission) values(2, "Admin");
INSERT INTO permission (rootid, permission) values(3, "CustomerSupport");
INSERT INTO permission (rootid, permission) values(4, "ManageUsers");
INSERT INTO permission (rootid, permission) values(5, "EditUsers");
INSERT INTO permission (rootid, permission) values(6, "EditOrders");
INSERT INTO permission (rootid, permission) values(7, "MangeOrders");
INSERT INTO permission (rootid, permission) values(8, "EditPromotions");
INSERT INTO permission (rootid, permission) values(9, "ManageProducts");
INSERT INTO permission (rootid, permission) values(10, "EditProducts");
INSERT INTO permission (rootid, permission) values(11, "ManageCategories");
INSERT INTO permission (rootid, permission) values(12, "EditCategories");
INSERT INTO permission (rootid, permission) values(13, "PointOfSale");
INSERT INTO permission (rootid, permission) values(14, "ManageReporting");
INSERT INTO permission (rootid, permission) values(15, "ManagePromotions");
INSERT INTO permission (rootid, permission) values(16, "ManageCoupons");
INSERT INTO permission (rootid, permission) values(17, "EditCoupons");

INSERT INTO role (rootid, tenantid, timecreated, timeupdated, rolename) values ("1:ROLE", "tenant1", 0, 0, "CustomerSupportAdmin");
INSERT INTO rolepermission (rootid, tenantid, timecreated, timeupdated, roleid, permissionid) values ("1:RPM1", "tenant1", 0, 0, "1:ROLE", 1);
INSERT INTO rolepermission (rootid, tenantid, timecreated, timeupdated, roleid, permissionid) values ("1:RPM2", "tenant1", 0, 0, "1:ROLE", 2);
INSERT INTO employeerole (rootid, tenantid, timecreated, timeupdated, roleid, employeeid) values ("1:ERM1", "tenant1", 0, 0, "1:ROLE", "1:USER");

INSERT INTO role (rootid, tenantid, timecreated, timeupdated, rolename) values ("ADMINTENANT:ROLE", "ADMINTENANT", 0, 0, "CustomerSupportAdmin");
INSERT INTO rolepermission (rootid, tenantid, timecreated, timeupdated, roleid, permissionid) values ("ADMINTENANT:RPM1", "ADMINTENANT", 0, 0, "ADMINTENANT:ROLE", 1);
INSERT INTO rolepermission (rootid, tenantid, timecreated, timeupdated, roleid, permissionid) values ("ADMINTENANT:RPM2", "ADMINTENANT", 0, 0, "ADMINTENANT:ROLE", 2);
INSERT INTO employeerole (rootid, tenantid, timecreated, timeupdated, roleid, employeeid) values ("ADMINTENANT:ERM1", "ADMINTENANT", 0, 0, "ADMINTENANT:ROLE", "ADMINTENANT:USER");