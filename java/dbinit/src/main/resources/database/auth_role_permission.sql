/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  zyun
 * Created: Aug 20, 2016
 */

create table auth_role_permission (
    role varchar(32),
    permission varchar(64),
    primary key(role, permission)
);