/* BASE TRANSACTION TABLES */
CREATE TABLE IF NOT EXISTS tenant (rootid varchar(64) NOT NULL PRIMARY KEY, tenantuniquename varchar(64) NOT NULL UNIQUE, tenantname varchar(100) NOT NULL, active BOOL DEFAULT TRUE, purgetenant BOOL DEFAULT FALSE, timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, modifiedby varchar(64), createdby varchar(64), locale varchar(12) default 'en_US', timezone varchar(64) default 'GMT');
CREATE TABLE IF NOT EXISTS tenantdetails (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL UNIQUE, tenantcontact varchar(15), tenantemail varchar(100), tenantstreet varchar(200), tenantcity varchar(100), tenantpin varchar(10), tenantlogo MEDIUMBLOB, tenantfacebook varchar(100), tenanttwitter varchar(100), tenantinsta varchar(100), gstin varchar(50), fssai varchar(50), tagline varchar(256), gmaplocation varchar(1000), timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
CREATE TABLE IF NOT EXISTS tenantorigins (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, origin varchar(256), timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
CREATE TABLE IF NOT EXISTS subscriptionhistory (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, origin varchar(256), timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), renewedon datetime, expiry datetime, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
CREATE TABLE IF NOT EXISTS tenantbusinessemail (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, origin varchar(256), timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), host varchar(255), port varchar(10), emailid varchar(100), emailpassword varchar(255), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid), UNIQUE KEY `con_tenant_email` (`tenantid`,`emailid`));

CREATE TABLE IF NOT EXISTS featuretoggle (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, featurename varchar(30) NOT NULL UNIQUE, timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
CREATE TABLE IF NOT EXISTS taskaudit (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, taskname varchar(75) NOT NULL, starttime DATETIME DEFAULT CURRENT_TIMESTAMP, endtime DATETIME , status varchar(25), failureinfo varchar(1000), timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));

CREATE TABLE IF NOT EXISTS sequencenumber (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid varchar(64) NOT NULL, classname varchar(100), nextvalue bigint, incrementvalue bigint, timecreated bigint, timeupdated bigint, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), version bigint DEFAULT 0, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));

/* SAMPLE DATA */
INSERT INTO tenant (rootid, tenantuniquename, tenantname, active, timeupdated, timecreated, timezone) values ("tenant1", "devTenant", "DevTenant", true, 0, 0, 'IST');
INSERT INTO tenant (rootid, tenantuniquename, tenantname, active, timeupdated, timecreated, timezone) values ("tenant2", "prodTenant", "prodTenant", true, 1996, 1996, 'IST');
INSERT INTO tenantdetails (rootid, tenantid, tenantemail, tenantcontact, tagline, timeupdated, timecreated) values ("1:td", "tenant1", "noreplyeventemail@gmail.com", "898989889", "vazhka oru vattam", 0, 0);
INSERT INTO tenantdetails (rootid, tenantid, tenantemail, tenantcontact, tagline, timeupdated, timecreated) values ("2:td", "tenant2", "noreplyeventemail@gmail.com", "898989889", "vazhka oru vattam", 0, 0);
/* Default Data */
INSERT INTO tenant (rootid, tenantuniquename, tenantname, active, timeupdated, timecreated, timezone) values ("ADMINTENANT", "mken", "ADMIN SUPPORT", true, 0, 0, 'IST');
INSERT INTO tenantdetails (rootid, tenantid, tenantemail, tenantcontact, tagline, timeupdated, timecreated) values ("ADMINTENANT:td", "ADMINTENANT", "noreplyeventemail@gmail.com", "0000000000", "Support Admin", 0, 0);
