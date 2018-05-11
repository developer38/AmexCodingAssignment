DROP TABLE IF EXISTS person; 

create table person (
id integer NOT NULL,
name varchar(25) NOT NULL,
age integer(3) NOT NULL,
dateOfBirth date not null,
email varchar(50) NOT NULL
);

INSERT INTO person VALUES (1, 'person1', 31, '1981-01-01', 'person1@gmail.com');
INSERT INTO person VALUES (2, 'person2', 32, '1982-02-02', 'person2@gmail.com');
INSERT INTO person VALUES (3, 'person1', 33, '1983-03-03', 'person3@gmail.com');
INSERT INTO person VALUES (4, 'person1', 34, '1984-04-04', 'person4@gmail.com');
