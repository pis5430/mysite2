

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
        '2222', 
        '2222' , 
        '2222' , 
        'mail');

--테이블 삭제
drop table users;

--데이터 출력
select *
from users;

select *
from guestbook;


--update문
update users
set password = '1234',
    name = '테스트',
    gender = 'female'
where no = 1;

--아이디와 비밀번호가 일치하는 사람 출력 
select no,
        id,
        password,
        name,
        gender
from users
where id='1111'
and password ='1111';

--board 테이블 생성 fk
create table board (
    no number,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number,
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint user_fk foreign key (user_no)
    references users(no)
);

select *
from board;

--게시물 식별 번호
--(nocache:재시작시 번호가 점프되는걸 막아주는것, start with가 숫자를 미리 잡아놔서)
create sequence seq_board_no
increment by 1
start with 1
nocache;

--시퀀스 삭제(잘못 건드렸을경우 삭제하고 다시만드는 용도)
drop sequence seq_board_no;

--게시물 데이터 입력(게시물식별번호,제목,내용,조회수,등록일,회원식별번호)
-- 조회수 초기값 0 , 글 확인할때마다 hit+1로 1씩 추가되게
insert into board
values (seq_board_no.nextval, 
        '타이틀', 
        '내용' , 
        0 , 
        sysdate,
        1);
        
--게시물 조회수 업데이트
--update문
update board
set hit = hit+1
where no = 1;

--게시물식별번호,제목,내용,조회수,등록일,회원식별번호)
select  no 게시물번호,
        title 제목,
        content 내용,
        hit 조회수,
        reg_date 등록일,
        user_no 회원번호
from board;

select  b.no 게시물번호,
        u.name 이름,
        b.title 제목,
        b.content 내용,
        b.hit 조회수,
        b.reg_date 등록일,
        b.user_no 회원번호
from board b , users u
where b.user_no = u.no;


commit;
