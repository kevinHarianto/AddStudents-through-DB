use java3;

create table students (
  id int not null primary key,
  name varchar(20) not null,
  email varchar(100) not null,
);

insert into students values (1, 'Jack', 'jack@jack.com'),
  (2, 'Joke', 'joke@joke.com'),(3, 'Jike', 'jike@jike.com'),
  (4, 'Jake', 'jake@jake.com');
