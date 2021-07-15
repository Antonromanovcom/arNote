CREATE SEQUENCE salary_id_seq;

CREATE SEQUENCE wishes_id_seq;

CREATE SEQUENCE users_id_seq;

CREATE TABLE salary
(
  id                BIGINT NOT NULL
    CONSTRAINT salary_pkey
    PRIMARY KEY,
  fullslary         INTEGER,
  residualsalary    INTEGER,
  salary_time_stamp TIMESTAMP,
  date              DATE,
  user_id           BIGINT
);

CREATE TABLE users
(
  id                  BIGINT                NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  creation_date       DATE,
  email               VARCHAR(255),
  fullname            VARCHAR(255),
  last_operation      VARCHAR(255),
  last_operation_time TIMESTAMP,
  login               VARCHAR(255),
  pwd                 VARCHAR(255),
  user_crypto_mode    BOOLEAN DEFAULT FALSE NOT NULL,
  user_role           VARCHAR(255),
  view_mode           VARCHAR(255)
);

ALTER TABLE salary
  ADD CONSTRAINT fkas3bpxv37w9nu9pm98rxre2av
FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE wishes
(
  id                   BIGINT NOT NULL
    CONSTRAINT wishes_pkey
    PRIMARY KEY,
  archive              BOOLEAN,
  creation_date        DATE,
  description          VARCHAR(1024),
  price                INTEGER,
  priority             INTEGER,
  priority_group       INTEGER,
  priority_group_order INTEGER,
  realization_date     DATE,
  realized             BOOLEAN,
  url                  VARCHAR(1024),
  wish                 VARCHAR(255),
  user_id              BIGINT
    CONSTRAINT fkh4fwumji30i8f8lt9gnhqxjy7
    REFERENCES users
);


