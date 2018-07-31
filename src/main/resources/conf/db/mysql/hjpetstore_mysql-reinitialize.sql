-- for clean up purpose ---------------------
-- run as root --
-- mysql -h localhost -u root -p

-- frist drop database hjpetstore and user hjpetstore
drop database if exists hjpetstore;
create database hjpetstore;

-- create user hjpetstore and give the password  hjpetstore
grant all privileges on hjpetstore.* to hjpetstore@'%' identified by 'hjpetstore';

show databases;