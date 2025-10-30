create database cx_shop;
use cx_shop;

create table users
(
    id                  int primary key auto_increment,
    full_name           varchar(100)          default '',
    phone_number        varchar(10)  not null,
    address             varchar(200)          default '',
    password            varchar(100) not null default '',
    created_at          datetime,
    updated_at          datetime,
    is_active           tinyint(1)            default 1,
    date_of_birth       date,
    facebook_account_id int                   default 0,
    google_account_id   int                   default 0
);

alter table users
    add column role_id int;

create table roles
(
    id   int primary key auto_increment,
    name varchar(20) not null
);

alter table users
    add foreign key (role_id) references roles (id);

create table tokens
(
    id              int primary key auto_increment,
    token           varchar(256) unique not null,
    token_type      varchar(50)         not null,
    expiration_date datetime,
    revoked         tinyint(1)          not null,
    expired         tinyint(1)          not null,
    user_id         int,
    foreign key (user_id) references users (id)
);

create table social_accounts
(
    id          int primary key auto_increment,
    provider    varchar(20)  not null comment 'Tên nhà social network',
    provider_id varchar(50)  not null,
    email       varchar(150) not null comment 'Email tài khoản',
    name        varchar(100) not null comment 'Tên người dùng',
    user_id     int,
    foreign key (user_id) references users (id)
);

create table categories
(
    id   int primary key auto_increment,
    name varchar(100) not null default '' comment 'Tên danh mục, ví dụ: đồ điện tử'
);

create table products
(
    id          int primary key auto_increment,
    name        varchar(350) comment 'Tên sản phẩm',
    price       float not null check ( price >= 0 ),
    thumbnail   varchar(300) default '',
    description longtext,
    created_at  datetime,
    updated_at  datetime,
    category_id int,
    foreign key (category_id) references categories (id)
);

create table product_images
(
    id          int primary key auto_increment,
    product_id  int,
    foreign key (product_id) references products (id),
    image_url   varchar(300) default ''
);

create table orders
(
    id               int primary key auto_increment,
    user_id          int,
    foreign key (user_id) references users (id),
    full_name        varchar(100) default '',
    email            varchar(100) default '',
    phone_number     varchar(20)  not null,
    address          varchar(200) not null,
    note             varchar(100) default '',
    order_date       datetime     default current_timestamp,
    status           varchar(20),
    total_money      float check ( total_money >= 0 ),
    shipping_method  varchar(100),
    shipping_address varchar(200),
    shipping_date    date,
    tracking_number  varchar(100),
    payment_method   varchar(100),
    is_active        tinyint(1)
);

alter table orders
    modify column phone_number nvarchar(10);

alter table orders
    modify column status enum ('pending', 'processing', 'shipped', 'delivered', 'canceled')
        comment 'Trạng thái đơn hàng';

create table order_details
(
    id                 int primary key auto_increment,
    order_id           int,
    foreign key (order_id) references orders (id),
    product_id         int,
    foreign key (product_id) references products (id),
    price              float check ( price >= 0 ),
    number_of_products int check ( number_of_products > 0 ),
    total_money        float check ( total_money >= 0 ),
    color              varchar(20) default ''
);

update products
set thumbnail = (select image_url
                 from product_images
                 where products.id = product_images.product_id
                 limit 1)
where (thumbnail is null or thumbnail = '')
  and exists (select 1 from product_images where product_id = products.id);