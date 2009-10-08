drop table customer;

create table customer
(
id integer PRIMARY KEY,
name varchar(50),
abbr varchar(5)
);

drop table country;

create table country
(
id integer primary key,
name varchar(30),
iso_code varchar(2)
);

drop table state;

create table state
(
id integer primary key,
name varchar(30),
abbr varchar(10),
country_id integer references country(id)
);

drop table address;

create table address
(
id integer primary key,
suite varchar(20),
street1 varchar(50),
street2 varchar(50),
city varchar(50),
state_id integer references state(id),
zip varchar(10),
country_id integer references country(id)
);

drop table site;

create table site
(
id integer PRIMARY KEY,
cust_id integer REFERENCES customer(id),
name varchar(30),
geo_lat varchar(20),
geo_long varchar(20),
address_id integer REFERENCES address(id)
);

drop table flow_collector;

create table flow_collector
(
id INTEGER NOT NULL PRIMARY KEY,
cust_id INTEGER REFERENCES customer(id),
site_id INTEGER REFERENCES site(id),
src_ip VARCHAR(15),
name VARCHAR(30),
vendor VARCHAR(20),
version VARCHAR(20)
);

drop table flow_collector_intf;

create table flow_collector_intf
(
id integer PRIMARY KEY,
collector_id integer REFERENCES flow_collector(id),
intf_ifindex integer,
intf_name varchar(20)
);

drop table collector_bandwidth;

create table collector_bandwidth
(
id integer PRIMARY KEY,
collector_id integer REFERENCES flow_collector(id),
wan_max_bw varchar(15),
wan_danger_bw varchar(15),
wan_warning_bw varchar(15)
);

--network and mask fields are sized to optionally support ipv6 eventually

drop table flow_collector_routes;

create table flow_collector_routes
(
id integer PRIMARY KEY,
collector_id integer REFERENCES flow_collector(id),
network varchar(40),
netmask varchar(4)
);

drop table  appl_prof;

create table appl_prof
(
id integer PRIMARY KEY,
cust_id integer REFERENCES customer(id),
state integer
);

drop table appl_tag;

create table appl_tag
(
id integer PRIMARY KEY,
app_prof_id integer references appl_prof(id),
name varchar(20),
descr varchar(50),
priority integer,
rule varchar(100)
);


--Stuff below here is not stable. 
--We have some interim structures, which will change over time

    create table `netview`.`flowrecord`(
        `ID` BIGINT not null auto_increment,
       `APP_ID` BIGINT,
       `APP_NAME` VARCHAR(256),
       `BYTE_COUNT` BIGINT not null,
       `DEST_AS` INT not null,
       `DEST_IP` VARCHAR(256),
       `DEST_PORT` INT not null,
       `IP_PROTO` INT not null,
       `IP_TOS` INT not null,
       `PACKET_COUNT` BIGINT not null,
       `SITE_ID` BIGINT,
       `SRC_INTF` VARCHAR(256),
       `SRC_IP` VARCHAR(256),
       `SRC_PORT` INT not null,
       `TCP_FLAGS` INT not null,
       `TIMECODE` INT not null,
       `TIMESTAMP_END` VARCHAR(256),
       `TIMESTAMP_START` VARCHAR(256),
       `VLAN_NAME` VARCHAR(256),
       `VLAN_TAG` INT not null,
        primary key (`ID`)
    );


    create index `SITE_ID_IDX` on `netview`.`flowrecord`(`SITE_ID`);
    create index `SRC_INTF_IDX` on `netview`.`flowrecord`(`SRC_INTF`);
    create index `TIMECODE_IDX` on `netview`.`flowrecord`(`TIMECODE`);

create table flow_record
(
id , 
timecode,
duration,
Timestamp_start,
Timestamp_stop,
flow_sign_id,
packets,
bytes
)

create table flow_sign
(
id
flow_collector_id REFERENCES flow_collector(id)
source_intf,
app_id REFERENCES application(id),
src_ip
dest_ip
src_port
dest_port
ip_proto
ip_tos
tcp_flags
source_as
dest_as
source_netmask
dest_netmask
)

[optional v6]
ip_ver

create table ip_site_match
id PRIMARY KEY,
ip_addr,
collector_id REFERENCES flow_collector(id),
site_id REFERENCES site(id)

create table ip_geo_match
id PRIMARY KEY,
ip_addr,
lat,
long,
city,
state_id,
country_id,
zip





