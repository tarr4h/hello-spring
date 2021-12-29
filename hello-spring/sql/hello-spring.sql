--==========================================================
-- 관리자 계정 - spring 계정 생성
--==========================================================
alter session set "_oracle_script" = true; -- 일반 사용자 c## 접두어 없이 계정생성

create user spring
identified by spring
default tablespace users;

alter user spring quota unlimited on users;

grant connect, resource to spring;


--==============================
-- spring 계정
--==============================

-- dev 테이블
create table dev(
    no number,
    name varchar2(50) not null,
    career number not null,
    email varchar2(250) not null,
    gender char(1),
    lang varchar2(100) not null,
    constraint pk_dev_no primary key(no),
    constraint ck_dev_gender check(gender in ('M', 'F'))
);

create sequence seq_dev_no;

select * from dev;


-- 회원테이블 생성
-- member_role 권한정보는 별도의 테이블에서 관리
create table member(
    id varchar2(15),
    password varchar2(300) not null,
    name varchar2(30) not null,
    gender char(1),
    birthday date,
    email varchar2(256),
    phone char(11) not null,
    address varchar2 (512),
    hobby varchar2(256),
    enroll_date date default sysdate,
    enabled number default 1,
    constraint pk_member_id primary key(id),
    constraint ck_member_gender check(gender in ('M', 'F')),
    constraint ck_member_enabled check(enabled in (1, 0))
);


select * from member;

delete from member where id = 'aaaba';
commit;






