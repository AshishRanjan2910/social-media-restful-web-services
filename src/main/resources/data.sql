insert into user_details (id, name, dob)
values (10001, 'Ranjan', current_date()),
       (10002, 'Rajjo', current_date()),
       (10003, 'Athul', current_date());

insert into post (id, description, user_id)
values (20001, 'Visiting Dal lake', 10001),
       (20002, 'Ruminating about old sufi', 10001),
       (20003, 'Mountains are adventurous so are you', 10002),
       (20004, 'Hello World! This is my first post.', 10003);