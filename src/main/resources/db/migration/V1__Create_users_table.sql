CREATE TABLE users (
	id serial PRIMARY KEY,
	username VARCHAR (50) UNIQUE NOT NULL,
	password VARCHAR (50) NOT NULL,
	first_name VARCHAR (30) NOT NULL,
  last_name VARCHAR (30) NOT NULL
);