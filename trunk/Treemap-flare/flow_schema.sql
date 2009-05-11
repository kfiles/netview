create table flow_collector
id PRIMARY KEY,
cust_id REFERENCES customer(id),
site_id REFERENCES site(id),
src_ip,
name,
vendor,
version

create table flow_collector_intf
id PRIMARY KEY,
collector_id REFERENCES flow_collector(id),
intf_ifindex,
intf_name,
site_id REFERENCES site(id)

create table collector_bandwidth
id PRIMARY KEY,
collector_id REFERENCES flow_collector(id),
wan_max_bw,
wan_danger_bw,
wan_warning_bw

create table flow_record
id, 
timecode,
duration,
Timestamp_start,
Timestamp_stop,
flow_sign_id,
packets,
bytes

create table flow_sign
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

create table flow_collector_routes
id PRIMARY KEY,
collector_id REFERENCES flow_collector(id),
network,
netmask

create table application_prof
id PRIMARY KEY,
cust_id REFERENCES customer(id),
state

create table application
id PRIMARY KEY
app_prof_id references application_prof(id),
name,
descr,
priority,
rule

create table customer
id PRIMARY KEY
name,
abbr

create table site
id PRIMARY KEY,
name,
lat,
long,
addr_id REFERENCES address(id)
 
create table address
id,
suite,
street1,
street2,
city,
state_id,
zip,
country_id

