-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `manage_packages`
--

DROP TABLE IF EXISTS `manage_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_packages` (
  `id` varchar(10) NOT NULL,
  `package_name` varchar(100) DEFAULT NULL,
  `description` text,
  `duration` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `availability` varchar(20) DEFAULT 'Available',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manage_packages`
--

LOCK TABLES `manage_packages` WRITE;
/*!40000 ALTER TABLE `manage_packages` DISABLE KEYS */;
INSERT INTO `manage_packages` VALUES ('2','Day out package 1','2 person+pool Access,beverage','9 a.m - 4.00 p.m',42200,'Available'),('3','Family package','10 person+ pool access+Lunch+Beverage','9 a.m - 4 p.m',15000,'Available'),('4','Package 1','person 1','2a.m - 4 p.m',1200,'Available');
/*!40000 ALTER TABLE `manage_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nic` varchar(20) DEFAULT NULL,
  `room_price` double DEFAULT NULL,
  `package_price` double DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `invoice_no` varchar(100) DEFAULT NULL,
  `ac_charge` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'12321',1000,1000,2000,'2025-05-10',NULL,NULL,0),(24,'200314911060',1000,4000,7000,'2025-05-15','Cash','INV1747290432382',2000);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nic` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `address` text,
  `checkin_date` date DEFAULT NULL,
  `checkout_date` date DEFAULT NULL,
  `room_type` varchar(20) DEFAULT NULL,
  `room_number` varchar(10) DEFAULT NULL,
  `package_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (17,'Thisara Sandeepa','200314911060','sandeepa@gmail.com','0761589697','Male','Aluthgama','2025-01-02','2025-01-02','AC','1','Day out package 1'),(18,'Lahiru Nuwanga123','200219200709','lahiru@123','0771086950','Male','Badulla','2025-07-10','2025-08-28','AC','1','Day out package 1'),(19,'Kavintha Dassanayake','200219200701','kavintha@123','0715678543','Male','Matara','2025-09-08','2026-01-02','AC','1','Day out package 1'),(21,'Thisara','200314911061','Sandeepa@gmail.com','0775856643','Male','Aluthgama','2024-09-08','2024-09-10','AC','2','Day out package 1'),(22,'Thisara','200314911062','Sandeepa@gmail.com','0775856643','Male','Aluthgama','2024-09-08','2024-09-10','AC','2','Family package'),(23,'Thisara','200314911063','Sandeepa@gmail.com','0775856643','Male','Aluthgama','2024-09-08','2024-09-10','AC','4','Family package');
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_number` varchar(10) NOT NULL,
  `bed` int DEFAULT NULL,
  `price_per_day` double DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Available',
  PRIMARY KEY (`room_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES ('1',1,1000,'Available'),('2',2,2,'Available'),('4',1,5000,'Available'),('5',1,70000,'Available'),('6',2,2000000,'Available');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nic` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `accessibility` varchar(20) DEFAULT 'Active',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (29,'Lahiru Nuwanga','lahiru@gmail.com','200219200709','lahiru@123','Active'),(30,'Pawani Sathsarani123','pawani@gmail.com','200269702162','Pawani@123','Active'),(31,'Kavintha Oshada','Kavintha@gmail.com','200233802763','Kavintha@123','Active'),(32,'supun','supun@gmail.com','200314911064','Supun@123','Active');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19 13:54:51
