SET FOREIGN_KEY_CHECKS = 0;

truncate table pet;
truncate table store;

INSERT into store(`id`, `name`, `location`, `contact_no`)

VALUES(21, 'Super Store', 'Herbert Macauley', '08052765428');

INSERT into pet(`id`, `name`, `color`, `breed`, `age`, `pet_sex`, `store_id`)

VALUES(31, 'jill', 'blue', 'parrot', 6, 'MALE', 21),
(30, 'jack', 'black', 'dog', 4, 'MALE', 21),
(21, 'sill', 'brown', 'cat', 5, 'FEMALE', 21),
(22, 'mill', 'red', 'dog', 6, 'FEMALE', 21),
(02, 'ross', 'pink', 'cat', 8, 'MALE', 21),
(25, 'gem', 'green', 'parrot', 2, 'MALE', 21);

SET FOREIGN_KEY_CHECKS = 1;