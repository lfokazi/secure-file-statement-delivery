-- Create sequences following naming convention
create sequence customer_seq start with 1000000 increment by 50;
create sequence statement_seq start with 1000000 increment by 50;
create sequence statement_download_seq start with 1000000 increment by 50;

-- Customer table
create table customer
(
    id         bigint       not null,
    version    integer      not null,
    created    timestamp(6),
    user_id    varchar(255),
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    primary key (id)
);

-- Statement table
create table statement
(
    id              bigint       not null,
    version         integer      not null,
    created         timestamp(6),
    user_created_id varchar(255),
    customer_id     bigint       not null,
    bucket_name     varchar(255),
    object_key      varchar(500),
    primary key (id)
);

-- Statement Download table
create table statement_download
(
    id                bigint       not null,
    version           integer      not null,
    date_requested    timestamp(6),
    user_requested_id varchar(255) not null,
    statement_id      bigint       not null,
    url               varchar(1000) not null,
    duration_ms       bigint,
    primary key (id)
);

-- Foreign key constraints
alter table statement
    add constraint statement_customer_fk foreign key (customer_id) references customer;

alter table statement_download
    add constraint statement_download_statement_fk foreign key (statement_id) references statement;

-- Create indexes for foreign keys and frequently queried columns
create index idx_statement_customer_id on statement (customer_id);
create index idx_statement_download_statement_id on statement_download (statement_id);
create index idx_customer_user_id on customer (user_id);
create index idx_statement_created on statement (created);
create index idx_statement_download_date_requested on statement_download (date_requested);

-- ============================================
-- Insert test data
-- ============================================

-- Insert customers
INSERT INTO customer (id, version, created, user_id, first_name, last_name)
VALUES (2000, 0, '2025-01-15 09:00:00', 'auth0:customer001', 'John', 'Doe'),
       (2001, 0, '2025-02-20 10:30:00', 'auth0:customer002', 'Jane', 'Smith'),
       (2002, 0, '2025-03-10 14:15:00', 'auth0:customer003', 'Robert', 'Johnson'),
       (2003, 0, '2025-04-05 11:45:00', 'auth0:customer004', 'Emily', 'Williams'),
       (2004, 0, '2025-05-12 16:20:00', 'auth0:customer005', 'Michael', 'Brown');

-- Insert statements for customers
-- John Doe's statements (customer 2000)
INSERT INTO statement (id, version, created, user_created_id, customer_id, bucket_name, object_key)
VALUES (3000, 0, '2025-08-01 08:00:00', 'auth0:admin001', 2000, 'statements-prod',
        'statements/2025/08/customer001_statement_202508.pdf'),
       (3001, 0, '2025-07-01 08:00:00', 'auth0:admin001', 2000, 'statements-prod',
        'statements/2025/07/customer001_statement_202507.pdf'),
       (3002, 0, '2025-06-01 08:00:00', 'auth0:admin002', 2000, 'statements-prod',
        'statements/2025/06/customer001_statement_202506.pdf');

-- Jane Smith's statements (customer 2001)
INSERT INTO statement (id, version, created, user_created_id, customer_id, bucket_name, object_key)
VALUES (3003, 0, '2025-08-01 08:15:00', 'auth0:admin001', 2001, 'statements-prod',
        'statements/2025/08/customer002_statement_202508.pdf'),
       (3004, 0, '2025-07-01 08:15:00', 'auth0:admin002', 2001, 'statements-prod',
        'statements/2025/07/customer002_statement_202507.pdf');

-- Robert Johnson's statements (customer 2002)
INSERT INTO statement (id, version, created, user_created_id, customer_id, bucket_name, object_key)
VALUES (3005, 0, '2025-08-01 08:30:00', 'auth0:admin001', 2002, 'statements-prod',
        'statements/2025/08/customer003_statement_202508.pdf'),
       (3006, 0, '2025-07-01 08:30:00', 'auth0:admin001', 2002, 'statements-prod',
        'statements/2025/07/customer003_statement_202507.pdf'),
       (3007, 0, '2025-06-01 08:30:00', 'auth0:admin002', 2002, 'statements-prod',
        'statements/2025/06/customer003_statement_202506.pdf'),
       (3008, 0, '2025-05-01 08:30:00', 'auth0:admin002', 2002, 'statements-prod',
        'statements/2025/05/customer003_statement_202505.pdf');

-- Emily Williams's statements (customer 2003)
INSERT INTO statement (id, version, created, user_created_id, customer_id, bucket_name, object_key)
VALUES (3009, 0, '2025-08-01 08:45:00', 'auth0:admin002', 2003, 'statements-prod',
        'statements/2025/08/customer004_statement_202508.pdf');

-- Michael Brown has no statements yet (customer 2004)

-- Insert statement downloads (audit log)
-- John Doe's downloads (multiple downloads of same statement)
INSERT INTO statement_download (id, version, date_requested, user_requested_id, statement_id, url, duration_ms)
VALUES (4000, 0, '2025-08-05 10:30:00', 'auth0:customer001', 3000,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer001_statement_202508.pdf?X-Amz-Expires=900&...',
        1250),
       (4001, 0, '2025-08-07 14:15:00', 'auth0:customer001', 3000,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer001_statement_202508.pdf?X-Amz-Expires=900&...',
        980),
       (4002, 0, '2025-07-10 09:45:00', 'auth0:customer001', 3001,
        'https://s3.amazonaws.com/statements-prod/statements/2025/07/customer001_statement_202507.pdf?X-Amz-Expires=900&...',
        1100);

-- Jane Smith's downloads
INSERT INTO statement_download (id, version, date_requested, user_requested_id, statement_id, url, duration_ms)
VALUES (4003, 0, '2025-08-02 11:20:00', 'auth0:customer002', 3003,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer002_statement_202508.pdf?X-Amz-Expires=900&...',
        890),
       (4004, 0, '2025-07-15 16:30:00', 'auth0:customer002', 3004,
        'https://s3.amazonaws.com/statements-prod/statements/2025/07/customer002_statement_202507.pdf?X-Amz-Expires=900&...',
        1450);

-- Robert Johnson's downloads (active user with multiple downloads)
INSERT INTO statement_download (id, version, date_requested, user_requested_id, statement_id, url, duration_ms)
VALUES (4005, 0, '2025-08-03 08:00:00', 'auth0:customer003', 3005,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer003_statement_202508.pdf?X-Amz-Expires=900&...',
        750),
       (4006, 0, '2025-07-05 12:30:00', 'auth0:customer003', 3006,
        'https://s3.amazonaws.com/statements-prod/statements/2025/07/customer003_statement_202507.pdf?X-Amz-Expires=900&...',
        920),
       (4007, 0, '2025-06-12 15:45:00', 'auth0:customer003', 3007,
        'https://s3.amazonaws.com/statements-prod/statements/2025/06/customer003_statement_202506.pdf?X-Amz-Expires=900&...',
        1050),
       (4008, 0, '2025-08-10 10:10:00', 'auth0:customer003', 3005,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer003_statement_202508.pdf?X-Amz-Expires=900&...',
        680),
       (4009, 0, '2025-05-20 09:30:00', 'auth0:customer003', 3008,
        'https://s3.amazonaws.com/statements-prod/statements/2025/05/customer003_statement_202505.pdf?X-Amz-Expires=900&...',
        1200);

-- Emily Williams's download
INSERT INTO statement_download (id, version, date_requested, user_requested_id, statement_id, url, duration_ms)
VALUES (4010, 0, '2025-08-06 13:25:00', 'auth0:customer004', 3009,
        'https://s3.amazonaws.com/statements-prod/statements/2025/08/customer004_statement_202508.pdf?X-Amz-Expires=900&...',
        1350);

-- Michael Brown has no downloads (no statements yet)
