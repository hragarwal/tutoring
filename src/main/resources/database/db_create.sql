CREATE DATABASE tutoring;

-- data for the table 'role'
insert into role (id,name) values
(1,'Super Admin'),
(2,'Admin'),
(4,'Support'),
(8,'Tutor'),
(16,'Student');

-- data for the table subject
insert  into subject (id,is_active,name) values
(1,1,'Computer Science'),
(2,1,'Android Programming'),
(3,1,'J2EE Programming'),
(4,1,'C Programming'),
(5,1,'C++ Programming');

-- lesson status data 
insert into lesson_status (id, allowed_roles, name) values (1, 0, 'Available');  -- default set when it is created
insert into lesson_status (id, allowed_roles, name) values (2, 15, 'Accepted');
insert into lesson_status (id, allowed_roles, name) values (8, 15, 'In Progress');
insert into lesson_status (id, allowed_roles, name) values (16, 15, 'Waiting Payment');
insert into lesson_status (id, allowed_roles, name) values (32, 7, 'Payment Made');
insert into lesson_status (id, allowed_roles, name) values (64, 15, 'Submitted');
insert into lesson_status (id, allowed_roles, name) values (128, 23, 'Completed');
insert into lesson_status (id, allowed_roles, name) values (256, 23, 'Cancelled');
insert into lesson_status (id, allowed_roles, name) values (512, 0, 'Expired');   -- as expired will be automatically


