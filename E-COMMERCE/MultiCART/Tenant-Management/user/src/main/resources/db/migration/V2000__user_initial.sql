/*Employee*/
create table if not exists employee (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, locale varchar(12) default 'en_US', timezone varchar(64), uniquename varchar(50) NOT NULL, fname varchar(50), lname varchar(50), emailid varchar(50), mobile varchar(512), mobilehash varchar(300), password varchar(512), designation varchar(64), reportsto bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_tenant_emp_email` (`tenantid`,`emailid`), UNIQUE KEY `con_tenant_emp_uniquename` (`tenantid`,`uniquename`), UNIQUE KEY `con_tenant_emp_mobile` (`tenantid`,`mobilehash`));
create table if not exists employeeinfo (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, locale varchar(12) default 'en_US', timezone varchar(64), dob varchar(16), gender varchar(12), proofblobid bigint, CONSTRAINT FOREIGN KEY(proofblobid) REFERENCES filestore(rootid), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
/*create table if not exists employeeactivity (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, lastactive bigint, lastlogin bigint, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));*/

/*Customer*/
create table if not exists customer (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0, locale varchar(12) default 'en_US', timezone varchar(64), uniquename varchar(50) NOT NULL, fname varchar(50), lname varchar(50), emailid varchar(50), mobile varchar(512), mobilehash varchar(300), password varchar(512), logintype varchar(15), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_tenant_cus_email` (`tenantid`,`emailid`), UNIQUE KEY `con_tenant_cus_uniquename` (`tenantid`,`uniquename`), UNIQUE KEY `con_tenant_cus_mobile` (`tenantid`,`mobilehash`));

/*Intial data load*/
insert into employee (rootid, tenantid, active, fname, lname, mobile, mobilehash, emailid, password, timecreated, timeupdated, designation, createdby, modifiedby, uniquename) values (-1, -1, true, "Support", "admin", "plwXfAGRbjziUITXD/BZSA==", "x3Xnt1ft5jDNCqERO9ECZhqziCnKUqZCKreChi8mhkY=", "superuser", "$2a$05$MhdU7apzW37pNyu77/1xt.ICQdYmri/q4e.606JZQFFGaYNDyiUXG", 0, 0, "Admin", 0, 0, "emp1");
insert into employee (rootid, tenantid, active, fname, lname, mobile, mobilehash, emailid, password, timecreated, timeupdated, designation, createdby, modifiedby, uniquename) values (-2, -2, true, "Support", "admin", "plwXfAGRbjziUITXD/BZSA==", "x3Xnt1ft5jDNCqERO9ECZhqziCnKUqZCKreChi8mhkY=", "superuserprod", "$2a$05$MhdU7apzW37pNyu77/1xt.ICQdYmri/q4e.606JZQFFGaYNDyiUXG", 0, 0, "Admin", 0, 0, "empprod1");