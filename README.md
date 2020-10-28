### Spring Test

This project contains a Maven project about the Spring Test for the Units Test and Integration 
Tests. 

For the Unit Tests this project use jUnit 5, Mockito.\
For the Integration Tests it uses Spring Boot Test, JUnit 5 and H2 in-memory database.

My post about Unit Tests (English language): https://www.vincenzoracca.com/en/blog/framework/spring/unit-test/
My post about Unit Tests (Italian language): https://www.vincenzoracca.com/blog/framework/spring/unit-test/

My post about Integration Tests: https://www.vincenzoracca.com/blog/framework/spring/integration-test/

This Project use MySQL database when run the application (indeed for the tests it uses H2 database).

#### Build a MySQL Docker container

##### Step 1: container mysql

docker pull mysql:8.0.1
docker run -p 3306:3306 --name sql-container -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.1


##### Step 2: container phppmyadmin

docker pull phpmyadmin/phpmyadmin:latest docker run --name phpmyadmin-container -d --link sql-container:db -p 8081:80 phpmyadmin/phpmyadmin

console phpmyadmin: http://localhost:8081/
user: root
pass: root