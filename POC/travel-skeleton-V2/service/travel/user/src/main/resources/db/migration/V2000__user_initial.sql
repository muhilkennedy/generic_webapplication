create table if not exists employee (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, fname varchar(50) NOT NULL, lname varchar(50) NOT NULL, mobile varchar(15) NOT NULL unique, emailid varchar(50) NOT NULL unique, password varchar(255), profilepic varchar(1024), timecreated bigint, timeupdated bigint, modifiedby bigint, version bigint DEFAULT 0, createdby bigint, locale varchar(12) default 'en_US', timezone varchar(64), lastactive bigint, lastlogin bigint, designation varchar(64), reportsto varchar(64));
create table if not exists customer (rootid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, active BOOL DEFAULT TRUE, fname varchar(50) NOT NULL, lname varchar(50), mobile varchar(15), emailid varchar(50) unique, password varchar(255), origin varchar(10), profilepic varchar(1024), lastactive bigint, lastlogin bigint, timecreated bigint, timeupdated bigint, modifiedby bigint, createdby bigint, version bigint DEFAULT 0, locale varchar(12) default 'en_US', timezone varchar(64));

/*Intial data load*/
insert into employee (rootid, active, fname, lname, mobile, emailid, password, timecreated, timeupdated, designation, createdby, modifiedby) values (1, true, "Support", "admin", "1234567890", "superuser", "$2a$05$MhdU7apzW37pNyu77/1xt.ICQdYmri/q4e.606JZQFFGaYNDyiUXG", 0, 0, "Admin", 0, 0);