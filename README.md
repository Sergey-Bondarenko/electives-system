# Electives system

Final project epam java web courses

##Task

Elective system. There is a list of courses, each of which is assigned a teacher. The student is recorded on one or more courses, registration data is stored. Upon completion of training teacher to the students assessment, which is stored in the archives.

##Requirements for implementation of the project

1. Information about domain store in the database (recommended MySQL), for access use JDBC.
2. Based on the subject area create classes describing of entities.
3. In implementing the business logic use GoF patterns (FactoryMethod, Command, Singleton, Builder, Strategy) and the Model-View-Controller.
4. Using servlets and the JSP, implement the functionality offered insetting a specific task.
5. In the development of JSP tags use your own.
6. When designing business logic use filters and sessions.
7. The application must support the work with the Cyrillic alphabet, including the storage of information in the database.
8. Classes and methods should have names reflecting their functionality and they must be properly structured in packets.
9. Making the code must comply with Java Code Convention.
10. In developing the use logging events (Log4j).
11. The code must contain comments, at least partially.

##Deployment

1. Build Maven project from pom.xml
2. Create elective db from dump file.
3. Config database file for your own base.
4. Run project using Tomcat
