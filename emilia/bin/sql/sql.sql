create table account(id varchar2(20),password varchar2(20),constraint pk_account primary key (id));

create table account_tenshu(id varchar2(20), win number(4),defeat number(4),constraint fk_account_tenshu foreign key (id) references account (id));