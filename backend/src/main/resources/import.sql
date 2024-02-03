delete from academicYears;
delete from classrooms;
delete from departments;
delete from departmentParticipations;
delete from examinations;
delete from examinationPeriods;
delete from courses;
delete from supervisions;
delete from supervisionRules;
delete from supervisors;
delete from examinations_classrooms;

insert into academicYears (id, name, isActive, previousYear_id) values (1001, '2021-2022', false, null);
insert into academicYears (id, name, isActive, previousYear_id) values (1002, '2022-2023', false, 1001);
insert into academicYears (id, name, isActive, previousYear_id) values (1003, '2023-2024', true, 1002);

insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2001, 'A21', 'Central', 1, 200, 120, 70, 5);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2002, 'E2', 'Second', 5, 70, 40, 25, 2);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2003, 'B3', 'Central', 2, 115, 65, 30, 3);
insert into classrooms (id, name, building, floor, general_capacity, exam_capacity, covid_capacity, max_num_supervisors) values (2004, 'C46', 'Third', 3, 50, 30, 20, 1);

insert into departments (id, name) values (3001, 'CS');
insert into departments (id, name) values (3002, 'Business');
insert into departments (id, name) values (3003, 'Economics');
insert into departments (id, name) values (3004, 'Accounting');
insert into departments (id, name) values (3005, 'Marketing');

insert into courses (id, title, course_code, department_id) values(4001, 'Intro to Programming', 'CS105', 3001);
insert into courses (id, title, course_code, department_id) values(4002, 'Accounting and Finance 101', 'AF101', 3004);

insert into examinationPeriods (id, semester, start_date, period, academicYear_id) values (5001, 'FIRST', '2023-01-01', 'WINTER', 1002);
insert into examinationPeriods (id, semester, start_date, period, academicYear_id) values (5002, 'FOURTH', '2024-06-10', 'SPRING', 1003);
insert into examinationPeriods (id, semester, start_date, period, academicYear_id) values (5003, 'FOURTH', '2024-09-03', 'SEPTEMBER', 1003);

insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6001, 6, 'EDIP', 3001, 5001);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6002, 4, 'ETEP', 3001, 5001);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6003, 5, 'DISPATCHED', 3003, 5002);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6004, 7, 'EDIP', 3002, 5002);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6005, 6, 'ETEP', 3001, 5003);
insert into supervisionRules (id, num_of_supervisions, supervisor_category, department_id, examinationPeriod_id) values (6006, 8, 'PHD', 3004, 5003);

insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7001, 'John', 'Doe', 'Dr. K', '123456789', 'jd@email.com', 'EDIP', 3001);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7002, 'Mark', 'White', 'Dr. H', '123456789', 'mw@email.com', 'EXTERNAL', 3004);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7003, 'Bill', 'Brown', 'Dr. J', '123456789', 'bb@email.com', 'DISPATCHED', 3005);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7004, 'George', 'Black', 'Dr. E', '123456789', 'gb@email.com', 'EDIP', 3003);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7005, 'Phin', 'Stark', 'Dr. L', '123456789', 'ps@email.com', 'ETEP', 3002);
insert into supervisors (id, name, surname, supervisor, telephone, email, supervisor_category, department_id) values (7006, 'Dennis', 'Parker', 'Dr. M', '123456789', 'dp@email.com', 'PHD', 3001);

insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8001, '2023-01-03', '2023-01-03', 10, 4001, 5001);
insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8002, '2023-01-05', '2023-01-05', 8, 4002, 5001);
insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8003, '2024-06-10', '2024-06-10', 0, 4001, 5002);
insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8004, '2024-06-15', '2024-06-15', 0, 4002, 5002);
insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8005, '2024-09-03', '2024-09-03', 0, 4001, 5003);
insert into examinations (id, start_date, end_date, required_supervisors, course_id, examinationPeriod_id) values (8006, '2024-09-07', '2024-09-07', 0, 4002, 5003);

insert into examinations_classrooms (examination_id, classroom_id) values (8001, 2001);
insert into examinations_classrooms (examination_id, classroom_id) values (8001, 2002);
insert into examinations_classrooms (examination_id, classroom_id) values (8002, 2003);
insert into examinations_classrooms (examination_id, classroom_id) values (8002, 2004);
insert into examinations_classrooms (examination_id, classroom_id) values (8003, 2002);
insert into examinations_classrooms (examination_id, classroom_id) values (8004, 2001);
insert into examinations_classrooms (examination_id, classroom_id) values (8005, 2003);
insert into examinations_classrooms (examination_id, classroom_id) values (8005, 2004);
insert into examinations_classrooms (examination_id, classroom_id) values (8006, 2003);

insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9001, 120, 100, true, 4001, 3001, 5001);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9002, 30, 20, false, 4001, 3003, 5001);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9003, 50, 38, true, 4002, 3002, 5001);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9004, 40, 27, false, 4002, 3004, 5001);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9005, 20, 0, true, 4001, 3001, 5002);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9006, 20, 0, false, 4001, 3003, 5002);
insert into departmentParticipations (id, declaration, attendance, isLeadDepartment, course_id, department_id, examinationPeriod_id) values (9007, 110, 0, true, 4002, 3005, 5002);

insert into supervisions (id, isPresent, isLead, examination_id, supervisor_id) values (10001, true, true, 8001, 7001);
insert into supervisions (id, isPresent, isLead, examination_id, supervisor_id) values (10002, false, false, 8003, 7004);
