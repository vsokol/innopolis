insert into checklist_item_type (id, code, name, descr) values (1, 'radiobutton', 'Радиокнопка', 'Одиночный выбор из нескольких элементов');
insert into checklist_item_type (id, code, name, descr) values (2, 'checkbox', 'Чекбокс', 'Множественный выбор из нескольких элементов');
insert into checklist_item_type (id, code, name, descr) values (3, 'combobox', 'Выпадающий список', 'Одиночный выбор из выпадающего списка');
insert into checklist_item_type (id, code, name, descr) values (4, 'number', 'Числовое поле', 'Поле для ввода числовых значений');
insert into checklist_item_type (id, code, name, descr) values (5, 'text', 'Текстовое поле', 'Текстовое поле');

insert into users (id, login, password, name, is_lock, full_name) values(1, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Admin', false, 'Главный админ (pwd: 12345)');
insert into users (id, login, password, name, is_lock, full_name) values(10, 'guest', null, 'Guest незванный', true, null);
commit;