desc user;


-- insert
insert into user values(null, '둘리', 'dooly@gmail.com', '1234', 'male', now());

-- select01
select no, name  
from user
where email='dooly@gmail.com' and password='1234';

-- select02
select * from user;

delete from user where no = 2;

-- updateform 
select name, email, password
from user
where no = 3;

-- update user
update user
set name = '또치', email = 'ddochi@naver.com', password = '1234', gender='female'
where no = 4;


desc board;

insert into board values(null,'test', 'this is test', 0, now(), 3, 0, 0, 3);
insert into board values(null,'hi test', 'hi this is test', 0, now(), 1, 1, 1, 3);
insert into board values(null,'REST API', 'GET PUT DELETE ~~~', 0, now(), 2, 0, 0, 3);
insert into board values(null,'질문이요', 'GET에서 블라블라', 0, now(), 2, 1, 1, 4);
insert into board values(null,'그건요', 'GET 으로 요청이 블라블라', 0, now(), 2, 2, 2, 3);
insert into board values(null,'또 다른 질문이요', 'Post에서 블라블라', 0, now(), 2, 3, 1, 5);

insert into board values(null,'test', 'this is test', 0, now(), ifnull((select max(b.group_no) from board b),0) + 1, 0, 0, 3);

select * from user;
select * from board order by group_no desc;
select * from guestbook;

select count(*)
from board;

delete from board where no = 4;

select b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y/%m/%d %H:%i:%s'), b.group_no, b.order_no, b.depth, u.name , b.user_no
from board b join user u on b.user_no = u.no
order by group_no desc, order_no asc;


select b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y/%m/%d %H:%i:%s'), b.group_no, b.order_no, b.depth, u.name , u.no
from board b join user u on b.user_no = u.no
order by group_no desc, order_no asc
limit 7,5;

select count(*)
from board b join user u on b.user_no = u.no
order by group_no desc, order_no asc;

select b.no, b.title, b.contents, u.no
from board b join user u on b.user_no = u.no
where b.no=95;

update board
set hit = hit + 1
where no = 95;

update board
set title = 'a', contents = 'b'
where no = 94;

update board 
set order_no = order_no + 1 
where group_no = 2 and order_no >= 1;

-- 안전모드 일시 중단
set sql_safe_updates=0;


select b.no, b.title, b.contents, u.no, b.group_no, b.order_no, b.depth
from board b join user u on b.user_no = u.no
where b.no=3;

update board 
set title = 'aaa', contents='aaa' 
where no=1;
select * from board order by group_no desc;


desc gallery;

insert into gallery values(null, 'test', 'test');

select * from gallery;

select url, comments from gallery;

delete from gallery where no = 6;


alter table user add column role enum('USER', 'ADMIN') not null default 'USER';

select * from user;

insert into user values(null, '관리자','admin@mysite.com', '1234','male',now(),'ADMIN');

desc site;

insert into site values(null, 'Mysite', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.
메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.', 'gallery/images/20219221314524.jpg', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.
메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.');

select * from site;

delete from user where no = 7;

 select b.no as no, b.title as title, b.contents as contents,
		        u.no as userNo, b.group_no as groupNo, 
		        b.order_no as orderNo, b.depth as depth
		 from board b join user u on b.user_no = u.no
		 where b.no=50;
         
select * from board;
select * from guestbook;
select * from user;
select * from gallery;
select no, title, welcom, profile, description from site;
desc site;
select * from site;
