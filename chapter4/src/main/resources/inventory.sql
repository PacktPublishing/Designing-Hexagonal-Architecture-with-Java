CREATE TABLE routers(
    router_id UUID PRIMARY KEY NOT NULL,
    router_type VARCHAR(255)
);
CREATE TABLE switches (
    switch_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    router_id UUID,
    switch_type VARCHAR(255),
    switch_ip_protocol VARCHAR(255),
    switch_ip_address VARCHAR(255),
    PRIMARY KEY (switch_id),
    FOREIGN KEY (router_id) REFERENCES routers(router_id)
);
CREATE TABLE networks (
    network_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    router_id UUID,
    network_protocol VARCHAR(255),
    network_address VARCHAR(255),
    network_name VARCHAR(255),
    network_cidr VARCHAR(255),
    PRIMARY KEY (network_id),
    FOREIGN KEY (router_id) REFERENCES switches(router_id)
);

INSERT INTO routers(router_id, router_type) VALUES('ca23800e-9b5a-11eb-a8b3-0242ac130003', 'EDGE');

INSERT INTO switches(router_id, switch_type, switch_ip_protocol, switch_ip_address)
VALUES('ca23800e-9b5a-11eb-a8b3-0242ac130003', 'LAYER3', 'IPV4', '9.0.0.9');

INSERT INTO networks(router_id, network_protocol, network_address, network_name, network_cidr)
VALUES('ca23800e-9b5a-11eb-a8b3-0242ac130003', 'IPV4', '10.0.0.0', 'HR', '8');
INSERT INTO networks(router_id, network_protocol, network_address, network_name, network_cidr)
VALUES('ca23800e-9b5a-11eb-a8b3-0242ac130003', 'IPV4', '20.0.0.0', 'Marketing', '8');
INSERT INTO networks(router_id, network_protocol, network_address, network_name, network_cidr)
VALUES('ca23800e-9b5a-11eb-a8b3-0242ac130003', 'IPV4', '30.0.0.0', 'Engineering', '8');

