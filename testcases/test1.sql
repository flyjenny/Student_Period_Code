CREATE  TABLE student (
	id INT PRIMARY KEY,
	name VARCHAR(10),
	sex char(1),
	age INT,
	gpa float
)
Insert INTO student values (1, 'ChenBin', 'M', 21, 4.0)
SELECT * FROM student ORDER BY id DESC
ALTER TABLE student ADD address VARCHAR(100)
SELECT * FROM student WHERE id = 1 OR age < 18
drop table student

create table Product(maker char(1), model int, type char(8) NOT NULL DEFAULT 'SB100')
create index idx on Product(maker, model)
create view vw as (select model, speed as gigahertz, hd as gigabytes from pc where price < 1000)
select avg(speed)*tab+4 as hi from laptop where price > 1000 group by hh having avg(speeed) > 10 Or avg(speeed) = 9
select model from pc where NOT (speed > 2.88 and price < 5000)
