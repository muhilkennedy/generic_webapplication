create table if not exists destination (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, name varchar(124), parentid bigint, gmap varchar(1024), description TEXT, rating int, customerrating int, picture varchar(1024), CONSTRAINT FOREIGN KEY(parentid) REFERENCES destination(rootid));
create table if not exists destinationfileblob (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, blobinfo blob, mediaurl varchar(1024), storetype varchar(64), timecreated bigint, timeupdated bigint, active BOOL DEFAULT TRUE, modifiedby bigint, createdby bigint, version bigint DEFAULT 0, destinationid bigint, CONSTRAINT FOREIGN KEY(destinationid) REFERENCES destination(rootid));
create table if not exists destinationseason (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, destinationid bigint, month int, scale int, CONSTRAINT FOREIGN KEY(destinationid) REFERENCES destination(rootid), UNIQUE KEY `con_destination_scale` (`destinationid`,`month`));
create table if not exists destinationdetail (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, destinationid bigint, attributes JSON, CONSTRAINT FOREIGN KEY(destinationid) REFERENCES destination(rootid)); /*store reasons to visit destination*/

create table if not exists attraction (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, destinationid bigint, name varchar(256), description TEXT, customerrating int, picture varchar(1024), gmap varchar(1024), CONSTRAINT FOREIGN KEY(destinationid) REFERENCES destination(rootid));
create table if not exists attractiondetail (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, attractionid bigint, attributes JSON, CONSTRAINT FOREIGN KEY(attractionid) REFERENCES attraction(rootid)); /*is this attraction for you/not?*/
create table if not exists attractionseason (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, attractionid bigint,  month int, scale int, CONSTRAINT FOREIGN KEY(attractionid) REFERENCES attraction(rootid),  UNIQUE KEY `con_attraction_scale` (`attractionid`,`month`));
create table if not exists attractionfileblob (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, blobinfo blob, mediaurl varchar(1024), storetype varchar(64), timecreated bigint, timeupdated bigint, active BOOL DEFAULT TRUE, modifiedby bigint, createdby bigint, version bigint DEFAULT 0, attractionid bigint, CONSTRAINT FOREIGN KEY(attractionid) REFERENCES attraction(rootid));

create table if not exists featureddestination (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, destinationid bigint, CONSTRAINT FOREIGN KEY(destinationid) REFERENCES destination(rootid));

create table if not exists travelstyle (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, version bigint DEFAULT 0, createdby bigint DEFAULT 0, uniquename varchar(64) unique);

/*Initial data load*/
insert into travelstyle (uniquename) value ("SOLO");
insert into travelstyle (uniquename) value ("COUPLE");
insert into travelstyle (uniquename) value ("FAMILY");
insert into travelstyle (uniquename) value ("FRIENDS");
insert into travelstyle (uniquename) value ("GROUPS");
insert into travelstyle (uniquename) value ("LUXURY");
insert into travelstyle (uniquename) value ("ADVENTURE");
insert into travelstyle (uniquename) value ("CORPORATE");