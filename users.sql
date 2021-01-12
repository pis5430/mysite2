

--users 테이블 만들기
create table users (
    no number,
    id varchar2(20) unique not null,
    password varchar2(20) not null,
    name varchar2(20) ,
    gender varchar2(10),
    primary key(no)
);

--시퀀스 생성
--(nocache:재시작시 번호가 점프되는걸 막아주는것, start with가 숫자를 미리 잡아놔서)
create sequence seq_users_no
increment by 1
start with 1
nocache;

--시퀀스 삭제(잘못 건드렸을경우 삭제하고 다시만드는 용도)
drop sequence seq_users_no;

--데이터 입력
insert into users
values (seq_users_no.nextval, 
        'aaa', 
        '1234' , 
        '아무개' , 
        'mail');

--테이블 삭제
drop table users;

--데이터 출력
select *
from users;

select *
from guestbook;

commit;
