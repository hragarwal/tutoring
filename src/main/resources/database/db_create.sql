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
insert into lesson_status (id, name) values (1, 'Available');
insert into lesson_status (id, name) values (2, 'Accepted');
insert into lesson_status (id, name) values (8, 'In Progress');
insert into lesson_status (id, name) values (16, 'Waiting Payment');
insert into lesson_status (id, name) values (32, 'Submitted');
insert into lesson_status (id, name) values (64, 'Completed');
insert into lesson_status (id, name) values (128, 'Cancelled');
insert into lesson_status (id, name) values (256, 'Expired');


