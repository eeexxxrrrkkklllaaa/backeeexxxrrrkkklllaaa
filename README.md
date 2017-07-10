# REST API

What do you need to start a server:
  - MongoDB
  - Edit .profile
  - Have maven
  
# INSTALL

install mongodb
install maven

# CONFIGURE your mongodb

- <a href = "https://www.tutorialspoint.com/mongodb/mongodb_create_database.htm"> create db </a>
- <a href = "https://docs.mongodb.com/manual/reference/method/db.createUser/"> create user with role: "userAdmin" </a>

# EDIT .profile( you are running from IDEA ) or .bashrc ( if u are running from bash )!

- export DB_HOST=127.0.0.1
- export DB_PORT= PORT in which MONGO listen(27017 by default)
- export DB_NAME= Data Base Name
- export DB_USER= Username for access to database
- export DB_PASS= Password for access to database

- export TEST_NAME= Test database name
- export TEST_USER= Username for access to test database
- export TEST_PASS= Password for access to test databaset

# RUN

```
$ mvn spring-boot:run

```

