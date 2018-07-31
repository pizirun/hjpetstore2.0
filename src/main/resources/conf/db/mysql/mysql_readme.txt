== MySQL Configuration

Notes:
1) ${hjpetstore2.0} means the path to the project home
2) launch mysql command console if you don't know:  mysql -h localhost -u root -p
   such as to execute the script:
   > mysql -h localhost -u root -p < ${hjpetstore2.0}/src/main/resources/conf/db/mysql/hjpetstore_mysql-reinitialize.sql
    Enter password: ********

Steps:
1 If you don't have mysql, download and install the MySQL database from here: http://dev.mysql.com/downloads/

2 Initialize DB by executing script ${hjpetstore2.0}/src/main/resources/conf/db/mysql/hjpetstore_mysql-reinitialize.sql

3 Load data into create db by executing script ${hjpetstore2.0}/src/main/resources/conf/db/mysql/hjpetstore-mysql-populate.sql
