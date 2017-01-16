CREATE DATABASE  IF NOT EXISTS `elective` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `elective`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: elective
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Account id',
  `name` varchar(100) NOT NULL COMMENT 'Account name',
  `surname` varchar(100) NOT NULL COMMENT 'Account surname',
  `login` varchar(32) NOT NULL COMMENT 'Account login',
  `password` varchar(64) NOT NULL,
  `user_type_id` int(11) NOT NULL COMMENT 'User type',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  KEY `idx_account` (`user_type_id`),
  CONSTRAINT `fk_account` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='Account table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Administrator','admin','admin','1a1dc91c907325c69271ddf0c944bc72',1),(4,'Ivan','Ivanov','teacher1','1a1dc91c907325c69271ddf0c944bc72',2),(5,'Petro','Petrov','teacher2','1a1dc91c907325c69271ddf0c944bc72',2),(6,'Vasyl','Vasylev','student1','1a1dc91c907325c69271ddf0c944bc72',3),(7,'Anton','Select','student2','1a1dc91c907325c69271ddf0c944bc72',3),(8,'Vladislav','Where','student3','1a1dc91c907325c69271ddf0c944bc72',1),(9,'Irina','Logs','teacher3','1a1dc91c907325c69271ddf0c944bc72',2),(11,'Vladislav','Nadtoka','nadtoka','1a1dc91c907325c69271ddf0c944bc72',1),(12,'Student','First','1student','1a1dc91c907325c69271ddf0c944bc72',3),(13,'Student','Second','2student','1a1dc91c907325c69271ddf0c944bc72',3),(15,'Student','Third','3student','1a1dc91c907325c69271ddf0c944bc72',3),(16,'Sergey','Bondarenko','sb','1a1dc91c907325c69271ddf0c944bc72',3);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Course id',
  `name` varchar(128) NOT NULL COMMENT 'Course name',
  `description` varchar(512) DEFAULT NULL COMMENT 'Course description',
  `status` int(11) NOT NULL COMMENT 'Course status',
  `teacher` int(11) NOT NULL COMMENT 'Course teacher id',
  `max_listeners` int(11) NOT NULL COMMENT 'Max students',
  PRIMARY KEY (`id`),
  KEY `idx_course` (`status`),
  KEY `idx_course_0` (`teacher`),
  CONSTRAINT `fk_course` FOREIGN KEY (`status`) REFERENCES `status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_0` FOREIGN KEY (`teacher`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='Courses table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Java SE','In this introductory course, you\'ll learn and practice essential computer science concepts using the Java programming language. You\'ll learn about Object Oriented Programming, a technique that allows you to use code written by other programmers in your own programs. You\'ll put your new Java programming skills to the test by solving real-world problems faced by software engineers.',1,4,40),(2,'Javascript','This course will teach what the most fundamental programming concepts are and how to use them. You\'ll learn about data types, functions, loops, control flow, and objects. JavaScript is the programming language of the web. It\'s one of the most popular and in demand skills in today\'s job market for good reason.',1,5,25),(4,'QA',' This software testing QA training course is designed by working professionals in a way that, course it will progress from introducing you to the basics of software testing to advanced topics like Software configuration management, creating a test plan, test estimations etc. along with introduction and familiarity with Automation testing and test management tools.',1,4,2),(5,'Python','This course is a great introduction to both fundamental programming concepts and the Python programming language. Python is a general-purpose, versatile and popular programming language. It\'s great as a first language because it is concise and easy to read, and it is also a good language to have in any programmer\'s stack as it can be used for everything from web development to software development and scientific applications.',3,4,5);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `student` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `rating` varchar(64) DEFAULT NULL,
  `comment` varchar(512) DEFAULT NULL COMMENT 'Rating comment',
  UNIQUE KEY `idx_rating_0` (`student`,`course`),
  KEY `fk_course-student_0` (`course`),
  KEY `idx_rating` (`student`),
  CONSTRAINT `fk_course-student_0` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating` FOREIGN KEY (`student`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Course-student table (many-to-many)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (6,1,NULL,NULL),(7,5,'123','123'),(12,4,'nive','gj'),(13,4,'nive','gj'),(15,1,'1','2');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_status` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Course status';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Open'),(2,'Started'),(3,'Ended');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User id',
  `user_type` varchar(32) NOT NULL COMMENT 'User type',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='User types.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'admin'),(2,'teacher'),(3,'student');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-16 15:21:32
