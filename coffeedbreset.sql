drop table if exists beans;
create table beans (id int, name varchar(50), origin varchar(50), process varchar(50), 
	notes varchar(100), price float, quantity int);
drop table if exists gear;
create table gear (id int, name varchar(50), type varchar(50), brand varchar(100), 
	price float, quantity int);
drop table if exists users;
create table users (userID int, username varchar(50), email varchar(100), password varchar(100),
	shippingAddress varchar(250), firstName varchar(50), lastName varchar(50), cardNo char(16),
	dateOfExpiry char(4), CVV char(3));
drop table if exists currentuser;
create table currentuser (userID int);
drop table if exists bag;
create table bag (id int, bagqty int);
drop table if exists orders;
create table orders (orderID int, userID int, orderDate timestamp, totalPrice float,
	orderStatus enum('PENDING', 'PROCESSING', 'SHIPPED'));

insert into beans values (1001, "Atilano Giraldo", "Colombia", "Natural", "Fruity",
 23.90, 300);
insert into beans values (1002, "La Coipa", "Peru", "Washed", "Fruity",
 27.90, 300);
insert into beans values (1003, "Rafael Amaya CM Pink Bourbon", "Colombia", "Washed", "Winey",
 33.90, 150);
insert into beans values (1004, "Hamasho", "Ethiopia", "Anaerobic", "Winey",
 23.90, 500);
insert into gear values (2001, "ORIGAMI Dripper Red", "Dripper", "ORIGAMI", 22.90, 100);
insert into gear values (2002, "ORIGAMI Dripper Yellow", "Dripper", "ORIGAMI", 22.90, 100);
insert into gear values (2003, "ORIGAMI Dripper Pink", "Dripper", "ORIGAMI", 22.90, 100);
insert into gear values (2004, "ORIGAMI Dripper White", "Dripper", "ORIGAMI", 22.90, 100);
insert into gear values (2005, "Hario Immersion Dripper Switch", "Dripper", "Hario", 35.90, 100);
insert into gear values (2006, "Comandante C40 MKII", "Grinder", "Comandante", 300.00, 20);
insert into gear values (2007, "Miyako Single Drip kettle", "Kettle", "Miyako", 39.90, 50);
insert into users values (9001, "profchua", "adm@ntu.eee.sg", "xxxx", "IEM Lab", "Prof.",
	"Chua", "1234123412341234", 1230, 123);