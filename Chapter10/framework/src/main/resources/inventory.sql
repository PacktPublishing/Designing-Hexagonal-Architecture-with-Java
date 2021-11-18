CREATE TABLE routers (
    router_id UUID PRIMARY KEY NOT NULL,
    router_parent_core_id UUID NULL,
    router_type VARCHAR(255),
    router_vendor VARCHAR(255),
    router_model VARCHAR(255),
    router_ip_protocol VARCHAR(255),
    router_ip_address VARCHAR(255),
    location_id int NULL
);

CREATE TABLE location (
                          location_id int NULL PRIMARY KEY,
                          address     VARCHAR(255),
                          city        VARCHAR(255),
                          state       VARCHAR(255),
                          zipcode     VARCHAR(255),
                          country     VARCHAR(255),
                          latitude    FLOAT,
                          longitude   FLOAT,
                          PRIMARY KEY (location_id),
                          FOREIGN KEY (location_id) REFERENCES location(location_id)
);

CREATE TABLE switches (
    switch_id UUID PRIMARY KEY NOT NULL,
    router_id UUID,
    switch_type VARCHAR(255),
    switch_vendor VARCHAR(255),
    switch_model VARCHAR(255),
    switch_ip_protocol VARCHAR(255),
    switch_ip_address VARCHAR(255),
    location_id int NULL,
    PRIMARY KEY (switch_id),
    FOREIGN KEY (location_id) REFERENCES location(location_id),
    FOREIGN KEY (router_id) REFERENCES routers(router_id)
);

CREATE TABLE networks (
    network_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    switch_id UUID,
    network_protocol VARCHAR(255),
    network_address VARCHAR(255),
    network_name VARCHAR(255),
    network_cidr VARCHAR(255),
    PRIMARY KEY (network_id),
    FOREIGN KEY (switch_id) REFERENCES switches(switch_id)
);

INSERT INTO location(location_id, address, city, state, zipcode, country, latitude, longitude)
VALUES(1, 'Amos Ln', 'Tully', 'NY', '13159', 'United States', '42.797310', '-76.130750');

INSERT INTO location(location_id, address, city, state, zipcode, country, latitude, longitude)
VALUES(2, '104 N Wolcott St', 'Casper', 'WY', '82601', 'United States', '42.850840', '-106.324150');

INSERT INTO routers(router_id, router_parent_core_id, router_type,
                    router_vendor, router_model,
                    router_ip_protocol, router_ip_address,
                    location_id)
VALUES('b832ef4f-f894-4194-8feb-a99c2cd4be0c', null,
       'CORE', 'CISCO', 'XYZ0001', 'IPV4', '1.0.0.1', 1);

INSERT INTO routers(router_id, router_parent_core_id, router_type,
                    router_vendor, router_model,
                    router_ip_protocol, router_ip_address,
                    location_id)
VALUES('b832ef4f-f894-4194-8feb-a99c2cd4be0a', 'b832ef4f-f894-4194-8feb-a99c2cd4be0c',
       'EDGE', 'JUNIPER', 'XYZ0001', 'IPV4', '5.0.0.5', 1);

INSERT INTO routers(router_id, router_parent_core_id, router_type,
                    router_vendor, router_model,
                    router_ip_protocol, router_ip_address,
                    location_id)
VALUES('b832ef4f-f894-4194-8feb-a99c2cd4be0b', 'b832ef4f-f894-4194-8feb-a99c2cd4be0c',
       'EDGE', 'JUNIPER', 'XYZ0001', 'IPV4', '6.0.0.6', 1);

INSERT INTO routers(router_id,
                    router_parent_core_id,
                    router_type, router_vendor,
                    router_model,router_ip_protocol,
                    router_ip_address, location_id)
VALUES('b07f5187-2d82-4975-a14b-bdbad9a8ad46',
       'b832ef4f-f894-4194-8feb-a99c2cd4be0c',
       'EDGE', 'HP', 'XYZ0002', 'IPV4', '2.0.0.2', 2);

INSERT INTO switches(switch_id, router_id, switch_type, switch_vendor, switch_model, switch_ip_protocol, switch_ip_address, location_id)
VALUES('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'b07f5187-2d82-4975-a14b-bdbad9a8ad46',
       'LAYER3','JUNIPER','XYZ0004', 'IPV4', '9.0.0.9', 1);

INSERT INTO switches(switch_id, router_id, switch_type, switch_vendor, switch_model, switch_ip_protocol, switch_ip_address, location_id)
VALUES('922dbcd5-d071-41bd-920b-00f83eb4bb47', 'b07f5187-2d82-4975-a14b-bdbad9a8ad46',
       'LAYER3','CISCO','XYZ0002', 'IPV4', '10.0.0.10', 1);

INSERT INTO networks(switch_id, network_protocol, network_address, network_name, network_cidr)
VALUES('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '10.0.0.0', 'HR', '8');
INSERT INTO networks(switch_id, network_protocol, network_address, network_name, network_cidr)
VALUES('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '20.0.0.0', 'Marketing', '8');
INSERT INTO networks(switch_id, network_protocol, network_address, network_name, network_cidr)
VALUES('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '30.0.0.0', 'Engineering', '8');