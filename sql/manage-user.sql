create table if not exists brand
(
    id int auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists color
(
    id int auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists person
(
    id int auto_increment
        primary key,
    firstname varchar(255) charset utf8 null,
    lastname varchar(255) charset utf8 null,
    dob bigint null,
    address longtext null,
    email varchar(255) charset utf8 null,
    phoneNumber varchar(255) not null,
    extras longtext null comment '""',
    roleId int default 2 null
);

create table if not exists orders
(
    id int auto_increment
        primary key,
    name varchar(255) null,
    personId int null,
    extras text null,
    products json null,
    constraint orders_person_id_fk
        foreign key (personId) references person (id)
);

create index person_role_id_fk
    on person (roleId);

create table if not exists products
(
    id int auto_increment
        primary key,
    name varchar(255) null,
    colorId int null,
    price double null,
    brandId int null,
    description text null,
    constraint products_brand_id_fk
        foreign key (brandId) references brand (id),
    constraint products_color_id_fk
        foreign key (colorId) references color (id)
);

create table if not exists role
(
    id int auto_increment
        primary key,
    name varchar(255) charset utf8 null,
    description longtext null
);

create table if not exists sub_products
(
    id int auto_increment
        primary key,
    productId int null,
    isCell tinyint(1) default 0 null,
    constraint sub_products_products_id_fk
        foreign key (productId) references products (id)
);

INSERT INTO `manage-user`.brand (id, name) VALUES (1, 'LV');
INSERT INTO `manage-user`.brand (id, name) VALUES (2, 'Gucci');
INSERT INTO `manage-user`.brand (id, name) VALUES (3, 'Lacoste');


INSERT INTO `manage-user`.color (id, name) VALUES (1, 'red');
INSERT INTO `manage-user`.color (id, name) VALUES (2, 'grow');
INSERT INTO `manage-user`.color (id, name) VALUES (3, 'violet');
INSERT INTO `manage-user`.color (id, name) VALUES (4, 'blue');
INSERT INTO `manage-user`.color (id, name) VALUES (5, 'yellow');


INSERT INTO `manage-user`.orders (id, name, personId, extras, products) VALUES (1, 'Order by: null', 13, '', '[{"name": "Banana Bottom", "price": 560, "subId": 36, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 37, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 38, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 39, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 40, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}]');
INSERT INTO `manage-user`.orders (id, name, personId, extras, products) VALUES (2, 'Order by: null', 13, '', '[{"name": "Banana Bottom", "price": 560, "subId": 36, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 37, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 38, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 39, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}, {"name": "Banana Bottom", "price": 560, "subId": 40, "isCell": false, "brandId": 1, "colorId": 3, "brandName": "LV", "colorName": "violet", "productId": 4}]');


INSERT INTO `manage-user`.person (id, firstname, lastname, dob, address, email, phoneNumber, extras, roleId) VALUES (1, 'Nhat Nam', 'Nguyen Trung', null, '73 Ca Van Thinh, p13, Tan Binh', 'nhatnam@gmail.com', '0939382758', null, 1);
INSERT INTO `manage-user`.person (id, firstname, lastname, dob, address, email, phoneNumber, extras, roleId) VALUES (7, 'Van A', 'Nguyen', 0, '128 Le Van Khuong, P13, Binh Thanh', 'vana@gmail.com', '0939382799', '', 2);
INSERT INTO `manage-user`.person (id, firstname, lastname, dob, address, email, phoneNumber, extras, roleId) VALUES (12, 'Van B', 'Nguyen', 0, '72 Hoxton, Los Angel Les', 'vanb@gmail.com', '0919131722', '', 2);
INSERT INTO `manage-user`.person (id, firstname, lastname, dob, address, email, phoneNumber, extras, roleId) VALUES (13, 'Van B', 'Nguyen', 0, '128 Los Angel Les, P13, Binh Thanh', 'vanb@gmail.com', '0939382755', '', 2);

INSERT INTO `manage-user`.products (id, name, colorId, price, brandId, description) VALUES (1, 'Bag LV', 2, 800, 1, null);
INSERT INTO `manage-user`.products (id, name, colorId, price, brandId, description) VALUES (3, 'Mantantu Top', 1, 280, 2, 'This is Mantantu top update update');
INSERT INTO `manage-user`.products (id, name, colorId, price, brandId, description) VALUES (4, 'Banana Bottom', 3, 560, 1, 'This is Banana Bottom');
INSERT INTO `manage-user`.products (id, name, colorId, price, brandId, description) VALUES (7, 'Mantantu Top', 1, 280, 2, 'This is Mantantu top update update');

INSERT INTO `manage-user`.role (id, name, description) VALUES (1, 'ADMIN', null);
INSERT INTO `manage-user`.role (id, name, description) VALUES (2, 'USER', null);

INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (24, 3, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (25, 3, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (26, 3, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (27, 3, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (28, 3, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (29, 3, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (30, 3, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (31, 3, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (34, 4, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (35, 4, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (36, 4, 1);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (37, 4, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (38, 4, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (39, 4, 0);
INSERT INTO `manage-user`.sub_products (id, productId, isCell) VALUES (40, 4, 0);
