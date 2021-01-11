

--users 테이블 만들기
create table users (
    no number,
    id varchar2(20),
    password varchar2(20) unique not null,
    name varchar2(20) not null,
    getder varchar2(10),
    primary key(no)
);

--시퀀스 생성
create sequence seq_users_no
increment by 1
start with 1
nocache;

--데이터 입력
insert into users
values (seq_users_no.nextval, 'aaa', '1234' , '아무개' , 'mail');

--테이블 삭제
drop table users;

select *
from users;
