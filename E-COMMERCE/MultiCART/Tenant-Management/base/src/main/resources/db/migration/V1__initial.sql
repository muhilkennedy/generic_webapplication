/*This table is for storing file location only*/
create table if not exists filestore (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, tenantid bigint NOT NULL, blobinfo blob, mediaurl varchar(1024), storetype varchar(64), extension varchar(64), timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, active BOOL DEFAULT TRUE, modifiedby bigint DEFAULT 0, createdby bigint DEFAULT 0, version bigint DEFAULT 0);