-- Tabla employees
CREATE SEQUENCE sq_employees;

CREATE TABLE employees (	
    /*id bigint NOT NULL,
    name varchar(50) NOT NULL,
    surname1 varchar(50),
    surname2 varchar(50),
    email varchar(255),
    phone_number varchar(15),
    nif varchar(11) NOT NULL,
    nickname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    status varchar(255),
    entry_date timestamp without time zone NOT NULL,
    cancel_date timestamp without time zone,
    modified_date timestamp without time zone*/

	employee_id bigint NOT NULL,
    first_name varchar(50) NOT NULL,
    surname1 varchar(50),
    surname2 varchar(50),
    email varchar(255),
    phone_number varchar(15),
    nif varchar(11) NOT NULL,
    nickname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    department varchar(50),
    contract_status varchar(255),
    date_of_birth date,
    is_email_verified boolean NOT NULL,
    entry_date timestamp without time zone NOT NULL,
    cancel_date timestamp without time zone,
    modified_date timestamp without time zone
);

ALTER TABLE employees
    ADD CONSTRAINT employees_id_pk PRIMARY KEY (employee_id);