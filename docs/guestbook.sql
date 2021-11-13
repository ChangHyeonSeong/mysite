select no, name, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate, message
from guestbook
order by reg_date desc;


select no, name, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate, message
from guestbook
where no < 14
order by reg_date desc
limit 0, 5;

delete from guestbook where no=#{no } and password=#{password }