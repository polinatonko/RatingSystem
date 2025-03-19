insert into user_details (id, first_name, last_name, email, password, role)
values ('4c1799d2-bb4b-41f5-b14d-a949f69798f5', 'admin', 'admin', 'tonkopolina@gmail.com', '$2a$10$F0N/3RrvDlDzhQC1ao0W0er2WsV63c6cLYCGntAhqbhfVTgsmuydK', 'ROLE_ADMIN');
insert into users (id, is_enabled) values ('4c1799d2-bb4b-41f5-b14d-a949f69798f5', true);

insert into user_details (id, first_name, last_name, email, password, role)
values ('698bd8fd-be76-465d-8267-7b7e99015e65', 'seller1', 'seller1', 'polina.tonko@gmail.com', '$2a$10$F0N/3RrvDlDzhQC1ao0W0er2WsV63c6cLYCGntAhqbhfVTgsmuydK', 'ROLE_SELLER');
insert into users (id, is_enabled) values ('698bd8fd-be76-465d-8267-7b7e99015e65', true);

insert into games (id, title, text) values ('2cb37de9-94cd-41a4-882b-244212d74b0e', 'Super Mario', 'Platform game series created by Nintendo');
insert into game_objects (id, title, text, game_id, user_id) values ('52695f84-33d7-4e47-a24a-c801c31780e9', 'Block', 'Regular object', '2cb37de9-94cd-41a4-882b-244212d74b0e', '698bd8fd-be76-465d-8267-7b7e99015e65');

insert into comment_details (id, rating, message) values ('138c8df1-5dbf-4b52-bb9d-d977ce6a2c51', 3, 'Example comment.');
insert into comments (id, seller_id) values ('138c8df1-5dbf-4b52-bb9d-d977ce6a2c51', '698bd8fd-be76-465d-8267-7b7e99015e65');