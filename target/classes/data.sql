Drop table if exists cars;

create table cars(
	id                  int auto_increment NOT NULL primary key,
	manufacturer 		varchar(50),
	model				varchar(50),
	color 	   			varchar(50),
	release_year		int,
	creation_time       datetime default current_timestamp not null
);

insert into cars (manufacturer, model, color, release_year) values
	('Ford', 'Focus', 'black', 1995),
	('Nissan', 'Almera', 'gray', 1992),
	('Toyota', 'Camry', 'green', 1998);