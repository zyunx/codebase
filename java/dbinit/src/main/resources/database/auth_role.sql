/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  zyun
 * Created: Aug 20, 2016
 */

create table auth_role (
role varchar(32) primary key
);

insert into auth_role (role) values ('admin');
insert into auth_role (role) values ('staff');
