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
create table if not exists explorationtype (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, timecreated bigint DEFAULT 0, timeupdated bigint DEFAULT 0, modifiedby bigint DEFAULT 0, version bigint DEFAULT 0, createdby bigint DEFAULT 0, uniquename varchar(64) unique, filestoreid bigint, CONSTRAINT FOREIGN KEY(filestoreid) REFERENCES filestore(rootid));

/*Initial data load*/
insert into travelstyle (uniquename) value ("SOLO");
insert into travelstyle (uniquename) value ("COUPLE");
insert into travelstyle (uniquename) value ("FAMILY");
insert into travelstyle (uniquename) value ("FRIENDS");
insert into travelstyle (uniquename) value ("GROUPS");
insert into travelstyle (uniquename) value ("LUXURY");
insert into travelstyle (uniquename) value ("ADVENTURE");
insert into travelstyle (uniquename) value ("CORPORATE");

insert into filestore (rootid, mediaurl, storetype, extension) value (1, "/assets/explore-images/adventure.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (2, "/assets/explore-images/flora.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (3, "/assets/explore-images/mountain.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (4, "/assets/explore-images/cusines.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (5, "/assets/explore-images/islands.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (6, "/assets/explore-images/unesco.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (7, "/assets/explore-images/stars.png", "INTERNAL", "png");
insert into filestore (rootid, mediaurl, storetype, extension) value (8, "/assets/explore-images/ocean.png", "INTERNAL", "png");

insert into explorationtype (uniquename, filestoreid) value ("ADVENTURE", 1);
insert into explorationtype (uniquename, filestoreid) value ("FLORA AND FAUNA", 2);
insert into explorationtype (uniquename, filestoreid) value ("MOUNTAINS", 3);
insert into explorationtype (uniquename, filestoreid) value ("CUSINES", 4);
insert into explorationtype (uniquename, filestoreid) value ("ISLANDS", 5);
insert into explorationtype (uniquename, filestoreid) value ("UNESCO SITE", 6);
insert into explorationtype (uniquename, filestoreid) value ("STARGAZING", 7);
insert into explorationtype (uniquename, filestoreid) value ("SEA/OCEAN", 8);