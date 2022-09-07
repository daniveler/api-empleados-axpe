/*	Database Schema: 	
 * 		employee_id bigint NOT NULL,
 * 		first_name varchar(50) NOT NULL,
 * 		surname1 varchar(50),
 * 		surname2 varchar(50),
 * 		email varchar(255),
 * 		phone_number varchar(15),
 * 		nif varchar(11) NOT NULL,
 * 		nickname varchar(255) NOT NULL,
 * 		password varchar(255) NOT NULL,
 * 		department varchar(50),
 * 		contract_status varchar(255),
 * 		date_of_birth timestamp without time zone,
 * 		is_email_verified boolean NOT NULL,
 * 		entry_date timestamp without time zone NOT NULL,
 * 		cancel_date timestamp without time zone,
 * 		modified_date timestamp without time zone
 * */

INSERT INTO employees (employee_id, first_name, surname1, surname2, email, phone_number, identification_document_value, identification_document_type, nickname, department, contract_status, date_of_birth, email_verified, entry_date, password)
VALUES
	(nextval('sq_employees'), 
	'Pepe', 
	'González', 
	'Pérez', 
	'pepe@axpe.es', 
	'+34689456123', 
	'123456789J', 
	'NIF',
	'admin', 
	'DEVELOPMENT',
	'INDEFINITE', 
	'1995-06-29',
	FALSE,
	CURRENT_TIMESTAMP, 
	'$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe');

INSERT INTO employees (employee_id, first_name, surname1, surname2, email, phone_number, identification_document_value, identification_document_type, nickname, department, contract_status, date_of_birth, email_verified, entry_date, password)
VALUES
	(nextval('sq_employees'), 
	'Sandra', 
	'Guzman', 
	'Herrera', 
	'sandra@axpe.es', 
	NULL, 
	'223456789J', 
	'NIF',
	'sandra', 
	'DEVELOPMENT',
	'INDEFINITE', 
	'1982-03-13',
	FALSE,
	CURRENT_TIMESTAMP, 
	'$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe');

INSERT INTO employees (employee_id, first_name, surname1, surname2, email, phone_number, identification_document_value, identification_document_type, nickname, department, contract_status, date_of_birth, email_verified, entry_date, password)
VALUES
	(nextval('sq_employees'), 
	'Antonio', 
	'Losada', 
	'Hernández', 
	'antonio@axpe.es', 
	'+34689456153', 
	'323456789J', 
	'NIF',
	'antonio', 
	'DEVELOPMENT',
	'INDEFINITE', 
	'2000-10-01',
	FALSE,
	CURRENT_TIMESTAMP, 
	'$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe');

INSERT INTO employees (employee_id, first_name, surname1, surname2, email, phone_number, identification_document_value, identification_document_type, nickname, department, contract_status, date_of_birth, email_verified, entry_date, password)
VALUES
	(nextval('sq_employees'), 
	'Sara', 
	'Bonilla', 
	'Cuadrado', 
	'sara@axpe.es', 
	'+34689456126', 
	'423456789J',
	'NIF',
	'sara', 
	'DEVELOPMENT',
	'INDEFINITE', 
	'1998-01-30',
	FALSE,
	CURRENT_TIMESTAMP, 
	'$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe');    
    