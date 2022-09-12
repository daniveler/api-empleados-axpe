-- Tabla employees
DROP SEQUENCE sq_employees;
DROP TABLE employees;

CREATE SEQUENCE sq_employees START WITH 1;

CREATE TABLE employees (
	employee_id bigint NOT NULL,
    first_name varchar(50) NOT NULL,
    surname1 varchar(50),
    surname2 varchar(50),
    email varchar(255),
    phone_number varchar(15),
    identification_document_value varchar(50) NOT NULL,
    identification_document_type enum('NIF', 'NIE', 'PASSPORT') NOT NULL,
    nickname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    department enum('DEVELOPMENT', 'TESTING', 'DESIGN', 'MARKETING', 'MAINTENANCE'),
    contract_status enum('INDEFINITE', 'TRAINEE', 'TEMPORAL'),
    date_of_birth date,
    email_verified boolean NOT NULL,
    
--    entry_date bigint,
--    cancel_date bigint,
--    modified_date bigint
    
    entry_date timestamp without time zone,
    cancel_date timestamp without time zone,
    modified_date timestamp without time zone
);

ALTER TABLE employees
    ADD CONSTRAINT employees_id_pk PRIMARY KEY (employee_id);