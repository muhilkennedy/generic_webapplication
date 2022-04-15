CREATE TABLE  if not exists car
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255),
    color  VARCHAR(255)
);

CREATE TABLE if not exists DATASOURCECONFIG (
   id bigint PRIMARY KEY,
   driverclassname VARCHAR(255),
   url VARCHAR(255),
   tenantId VARCHAR(255),
   username VARCHAR(255),
   password VARCHAR(255),
   initialize BOOLEAN
);

    
INSERT INTO DATASOURCECONFIG VALUES (1, 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/platformdb1?useSSL=false&allowPublicKeyRetrieval=true', 'tenant1', 'root', 'root@123', true);
INSERT INTO DATASOURCECONFIG VALUES (3, 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/platformdb1?useSSL=false&allowPublicKeyRetrieval=true', 'tenant1-child', 'root', 'root@123', true);
INSERT INTO DATASOURCECONFIG VALUES (2, 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/platformdb2?useSSL=false&allowPublicKeyRetrieval=true', 'tenant2', 'root', 'root@123', true);

create table city(id bigint, name varchar(200), tenantid varchar(200));