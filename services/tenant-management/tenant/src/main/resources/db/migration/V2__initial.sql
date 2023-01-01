/* File details */
CREATE TABLE IF NOT EXISTS fileblob (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid varchar(64) NOT NULL, blobinfo blob, mediaurl varchar(512), storetype varchar(64), timecreated bigint, timeupdated bigint, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), version bigint DEFAULT 0, CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));

/* feature enablement status */
CREATE TABLE IF NOT EXISTS featuretoggle (rootid varchar(64) NOT NULL PRIMARY KEY, tenantid varchar(64) NOT NULL, featurename varchar(30) NOT NULL UNIQUE, timecreated bigint, timeupdated bigint, version bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby varchar(64), createdby varchar(64), CONSTRAINT FOREIGN KEY(tenantid) REFERENCES tenant(rootid));
