
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  zyun
 * Created: Aug 20, 2016
 */
create  table if not exists auth_user
(
username varchar(32) primary key, 
password varchar(64)
);


insert into auth_user(username, password) values('zyun', 'zyun');