delete from academicYears;
delete from classrooms;
delete from departments;
delete from courseAttendances;
delete from examinations;
delete from examinationPeriods;
delete from courses;
delete from supervisions;
delete from supervisionRules;
delete from supervisors;
delete from examinations_classrooms;
delete from supervisorPreferences;
delete from supervisor_preference_exclude_dates;

insert into academicYears (id, name, isActive, previousYear_id) values (1001, '2021-2022', false, null);
insert into academicYears (id, name, isActive, previousYear_id) values (1002, '2022-2023', true, 1001);
insert into academicYears (id, name, isActive, previousYear_id) values (1003, '2023-2024', false, 1002);

insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2001, 'A', 'Central', 1, 360, 103, 70, 2);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2002, 'B', 'Central', 5, 198, 57, 25, 1);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2003, 'C', 'Central', 2, 192, 55, 30, 1);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2004, 'X', 'Central', 3, 270, 77, 20, 2);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2005, 'D', 'Central', 1, 192, 55, 70, 2);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2006, 'Amf. Derigny', 'Central', 5, 210, 60, 25, 2);

insert into departments (id, name) values (3001, 'CS');
insert into departments (id, name) values (3002, 'ME');
insert into departments (id, name) values (3003, 'LOHRI');
insert into departments (id, name) values (3004, 'ODE');
insert into departments (id, name) values (3005, 'STATISTICS');
insert into departments (id, name) values (3006, 'DET');
insert into departments (id, name) values (3007, 'OIK');
insert into departments (id, name) values (3008, 'DEOS');

insert into courses (id, title, course_code, department_id) values(4001, 'Analisi kai Sxediasi Plir. Systimaton', '3541', 3001);
insert into courses (id, title, course_code, department_id) values(4002, 'Efarmosmenes Pithanotites', '3614', 3001);
insert into courses (id, title, course_code, department_id) values(4003, 'Psyxologia', '5721', 3002);
insert into courses (id, title, course_code, department_id) values(4004, 'Eisagogi stin Didaktiki', '3076', 3001);

insert into examinationPeriods (id, start_date, period, academicYear_id) values (5001, '2023-01-01', 'WINTER', 1002);
insert into examinationPeriods (id, start_date, period, academicYear_id) values (5002, '2023-06-10', 'SPRING', 1002);
insert into examinationPeriods (id, start_date, period, academicYear_id) values (5003, '2023-09-03', 'SEPTEMBER', 1002);
insert into examinationPeriods (id, start_date, period, academicYear_id) values (5004, '2024-02-01', 'WINTER', 1003);

insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6001, 6, 'EDIP', 3001, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6002, 4, 'ETEP', 3001, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6003, 6, 'PHD', 3001, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6004, 4, 'DISPATCHED', 3001, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6005, 6, 'EXTERNAL', 3001, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6006, 4, 'EDIP', 3002, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6007, 3, 'ETEP', 3002, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6008, 5, 'PHD', 3002, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6009, 2, 'DISPATCHED', 3002, 5004);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6010, 3, 'EXTERNAL', 3002, 5004);

insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7001, 'ATHANASIOS', 'Asurname', 'Someone', '123456', 'athanasios.random1@example.com', 'EDIP', 3001);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7002, 'VASILIS', 'Asurnamef', 'Someone', '123456', 'vasilis.random2@example.com', 'EDIP', 3002);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7003, 'CHRISTOS', 'Asurnamed', 'Someone', '123456', 'christos.random3@example.com', 'EDIP', 3001);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7004, 'MAKIS', 'Asurnamer', 'Someone', '123456', 'makis.random4@example.com', 'EDIP', 3002);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7005, 'ANNA', 'Asurnames', 'Someone', '123456', 'anna.random5@example.com', 'EDIP', 3001);

insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8001, '2023-01-03', '11:30', '13:30', 4001, 5001);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8002, '2023-01-05', '12:30', '14:30', 4002, 5001);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8003, '2024-06-10', '13:30', '15:30', 4001, 5002);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8004, '2024-06-15', '14:30', '16:30', 4002, 5002);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8005, '2024-09-03', '15:30', '17:30', 4001, 5003);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8006, '2024-09-07', '16:30', '18:30', 4002, 5003);
insert into examinations (id, date, start_time, end_time, course_id, examinationPeriod_id) values (8007, '2024-06-10', '18:30', '12:30', 4001, 5001);

insert into examinations_classrooms (examination_id, classroom_id) values (8001, 2001);
insert into examinations_classrooms (examination_id, classroom_id) values (8002, 2003);
insert into examinations_classrooms (examination_id, classroom_id) values (8002, 2004);
insert into examinations_classrooms (examination_id, classroom_id) values (8003, 2002);
insert into examinations_classrooms (examination_id, classroom_id) values (8004, 2001);
insert into examinations_classrooms (examination_id, classroom_id) values (8005, 2003);
insert into examinations_classrooms (examination_id, classroom_id) values (8005, 2004);
insert into examinations_classrooms (examination_id, classroom_id) values (8006, 2003);

insert into courseAttendances (id, attendance, course_id, examinationPeriod_id) values (9001, 200, 4001, 5001);
insert into courseAttendances (id, attendance, course_id, examinationPeriod_id) values (9002, 250, 4002, 5001);
insert into courseAttendances (id, attendance, course_id, examinationPeriod_id) values (9003, 138, 4001, 5003);
insert into courseAttendances (id, attendance, course_id, examinationPeriod_id) values (9004, 127, 4002, 5003);

insert into supervisions (id, isPresent, isLead, examination_id, supervisor_id) values (10001, true, true, 8001, 7001);
insert into supervisions (id, isPresent, isLead, examination_id, supervisor_id) values (10002, false, false, 8003, 7004);
insert into supervisions (id, isPresent, isLead, examination_id, supervisor_id) values (10003, false, false, 8007, 7004);

insert into courseDeclarations (id, declaration, course_id, academicYear_id) values (11001, 400, 4001, 1002);
insert into courseDeclarations (id, declaration, course_id, academicYear_id) values (11002, 450, 4002, 1002);

insert into supervisorPreferences (id, start_time, end_time, supervisor_id, examinationPeriod_id) values (12001, '11:30', '13:30', 7001, 5004);
insert into supervisorPreferences (id, start_time, end_time, supervisor_id, examinationPeriod_id) values (12002, '17:00', '19:30', 7002, 5004);

insert into supervisor_preference_exclude_dates (supervisor_preference_id, exclude_date) values (12001, '2024-02-03');
insert into supervisor_preference_exclude_dates (supervisor_preference_id, exclude_date) values (12001, '2024-02-10');
insert into supervisor_preference_exclude_dates (supervisor_preference_id, exclude_date) values (12002, '2024-02-15');
