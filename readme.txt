Updated 2020/03/01
-----------------

jes
---
java -cp lib/commons-codec-1.3.jar:lib/commons-logging.jar:lib/dnsjava.jar:lib/log4j.jar:lib/jes-1.6.1.jar com.ericdaugherty.mail.server.Mail


tomcat https
------------
https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html#Configuration

mkdir jks
cd jks

keytool -genkey -alias tomcat -keyalg RSA -keystore localhost-rsa.jks

password: changeit


cp localhost-rsa.jks ~/java/apache-tomcat-9.0.30/conf/


Mysql
-----
mysql: root/root

systemctl status mysql.service mysql.service
https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04
https://www.cnblogs.com/lesliehe/p/7978847.html


sudo apt-get remove --purge mysql-\*

sudo mysql_secure_installation

sudo mysql -u root -p
update mysql.user set authentication_string=PASSWORD('Root@1234'), plugin='mysql_native_password' where user='root';
grant all on *.* to root@'%' identified by 'Root@1234' with grant option;
flush privileges;

mysql -u root -p



Hibernate Jpetstore 2.0 (hjpetstore2)
-------------------------------------
https://hjpetstore.dev.java.net/

1. INTRODUCTION
Re-architect my hjpetstore1.0 Spring Framework Sample project
(https://hjpetstore.dev.java.net/source/browse/hjpetstore/hibernateJpetstore1/)
by using the latest open-source frameworks and tools:
    * JQuery fisheye
    * kaptcha
    * Spring MVC 3
    * Spring Security
    * Hibernate 3.5
    * JBoss Cache 3 (alternative infinispan)
    * JMS External Integration
    * GlassFish 3 cluster
    * Mysql fail-over and cluster
    * Zabbix / Zapcat

The intension is to guide younger architects to design a java EE application by making use of
latest open-source techniques at hand, with the serious flexibility and scalability in mind
as well as reasonable performance and security.


2. Setup before run
    1) Download NetBeans with GlassFish and install, if you still have not
    2) Download Mysql and install, if you still have not
    3) Mysql intializaton, see ${hjpetstore2.0}/src/main/resources/conf/db/mysql/mysql_readme.txt
    4) install maven, if you still have not
    5) several artifacts missed in community maven repository, we need to import them into your local repository,
        see ${hjpetstore2.0}/src/main/resources/conf/maven-readme.txt

3. Glassfish configurations
    1) JGroup ip v6 issue: （only linux) change glassfish to disable ipv6, otherwise jgroup will be failed.
        a. start up glassfish admin console if still not
        b. JVM setting -> JVM options -> add JVM option： -Djava.net.preferIPv4Stack=true

    2) Mysql connectorJ
        a) Download Connector/J, the MySQL JDBC driver (e.g., Connector/J 5.1.x), which
            can be found here: http://dev.mysql.com/downloads/connector/j/
        b) added mysql-connector-java-xxx.jar into glassfish-3.0.1/glassfish/lib for datasource setup

    3) Create XA datasource 'jdbc/hjpetstore', see here: http://hi.baidu.com/quest2run/blog/item/4cc48eff5e6e368fb801a009.html

    4) Create JMS resource, see here: http://hi.baidu.com/quest2run/blog/item/4cc48eff5e6e368fb801a009.html

4. Launch the project
    (assume you downloaded the hjpetstore 2.0)
    In Netbeans, right click the project -> run


Let me know if you have questions

Pprun
quest.run@gmail.com
http://hi.baidu.com/quest2run
