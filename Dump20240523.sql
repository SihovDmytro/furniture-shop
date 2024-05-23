CREATE DATABASE  IF NOT EXISTS `furniture_shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `furniture_shop`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: furniture_shop
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'bed'),(2,'chair'),(3,'desk'),(4,'sofa'),(5,'table lamp'),(6,'floor lamp'),(7,'dining table'),(8,'vase'),(9,'rug');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` enum('ACCEPTED','CONFIRMED','FORMED','PROCESSING','SENT','COMPLETED','CANCELED') NOT NULL,
  `status_description` varchar(45) DEFAULT NULL,
  `date` datetime NOT NULL,
  `user_ID` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_user_idx` (`user_ID`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`user_ID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'CONFIRMED',NULL,'2020-01-01 00:00:00',14),(2,'CANCELED',NULL,'2020-01-01 00:00:00',14),(4,'ACCEPTED','Your order was accepted','2022-05-22 11:17:56',14),(8,'ACCEPTED','Your order was accepted','2022-05-22 12:02:03',1),(9,'ACCEPTED','Your order was accepted','2022-06-14 23:31:14',1),(10,'ACCEPTED','Your order was accepted','2022-06-14 23:35:02',1),(11,'ACCEPTED','Your order was accepted','2022-06-28 11:10:17',17),(12,'ACCEPTED','Your order was accepted','2022-06-28 11:11:46',17),(13,'ACCEPTED','Your order was accepted','2022-06-28 11:14:53',1),(14,'ACCEPTED','Your order was accepted','2022-07-21 19:09:29',1),(15,'ACCEPTED','Your order was accepted','2022-08-31 21:39:01',1),(16,'ACCEPTED','Your order was accepted','2022-11-01 17:41:10',18),(17,'ACCEPTED','Your order was accepted','2022-11-13 22:56:16',1),(18,'ACCEPTED','Your order was accepted','2022-11-13 23:00:13',1),(19,'ACCEPTED','Your order was accepted','2022-11-13 23:01:57',1),(20,'ACCEPTED','Your order was accepted','2022-11-13 23:06:00',1),(21,'FORMED',NULL,'2022-12-23 20:59:01',1),(22,'ACCEPTED','Your order was accepted','2022-12-23 22:01:38',1),(23,'ACCEPTED','Your order was accepted','2022-12-24 18:48:36',20),(25,'ACCEPTED','Your order was accepted','2022-12-25 19:05:58',1),(27,'CONFIRMED','123','2022-12-27 14:25:02',1),(30,'ACCEPTED','Your order was accepted','2022-12-27 15:08:30',1),(504,'FORMED','','2023-05-07 15:36:36',503),(506,'FORMED','','2023-05-07 15:46:26',503),(508,'FORMED','','2023-05-07 15:51:26',503),(509,'FORMED','','2023-05-07 16:13:20',503),(510,'FORMED','','2023-05-08 16:56:04',501),(511,'FORMED','','2023-05-08 17:18:15',501);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_product_info`
--

DROP TABLE IF EXISTS `orders_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_product_info` (
  `order_ID` int NOT NULL,
  `product_info_ID` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`order_ID`,`product_info_ID`),
  KEY `FK_List_Orders_ProductInfo_idx` (`product_info_ID`),
  CONSTRAINT `fk_orders_orders_product_Info` FOREIGN KEY (`order_ID`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_product_info_orders_product_info` FOREIGN KEY (`product_info_ID`) REFERENCES `product_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_product_info`
--

LOCK TABLES `orders_product_info` WRITE;
/*!40000 ALTER TABLE `orders_product_info` DISABLE KEYS */;
INSERT INTO `orders_product_info` VALUES (1,1,1),(1,2,1),(1,3,2),(2,2,3),(4,4,4),(4,5,1),(8,1,1),(8,7,1),(21,11,1),(21,12,1),(22,13,1),(23,14,1),(23,15,1),(25,16,1),(25,17,1),(30,18,4),(506,2,1),(506,5,1),(506,7,1),(506,507,2),(508,2,1),(508,7,10),(509,1,1),(509,15,2),(510,5,5),(510,15,3),(510,507,2),(511,3,1),(511,4,2);
/*!40000 ALTER TABLE `orders_product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producer`
--

DROP TABLE IF EXISTS `producer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producer`
--

LOCK TABLES `producer` WRITE;
/*!40000 ALTER TABLE `producer` DISABLE KEYS */;
INSERT INTO `producer` VALUES (1,'Dmytro'),(2,'Sihov'),(3,'A company'),(4,'B company'),(5,'C company');
/*!40000 ALTER TABLE `producer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `category_id` int NOT NULL,
  `producer_id` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Product_Category_idx` (`category_id`),
  KEY `FK_Product_Producer_idx` (`producer_id`),
  CONSTRAINT `FK_Product_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_Product_Producer` FOREIGN KEY (`producer_id`) REFERENCES `producer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Throne',9999.00,2,1,' Oak chair','chair.png'),(2,'Abra',10000.00,1,2,'Single bad frame','SingleBed.png'),(3,'Kadabra',12000.00,1,3,'Double bad frame with storage','DoubleBed.png'),(4,'Piece of wood',6555.55,7,4,'Table, 140x60 cm','diningTable.png'),(5,'Qwerty',5999.99,3,5,'Desk, 155x55 cm','desk.png'),(6,'Asdf',8880.00,4,1,'Two-seat sofa','sofa.png'),(7,'Zxcv',350.00,5,2,'Work lamp','tableLamp.png'),(8,'Uiop',950.00,6,3,'Floor lamp, 150 cm','floorLamp.png'),(9,'Ghjk',1000.00,8,4,'Vase, 40 cm','vase.png');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_info`
--

DROP TABLE IF EXISTS `product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `category_id` int NOT NULL,
  `producer_id` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ProductInfo_Category_idx` (`category_id`),
  KEY `FK_ProductInfo_Producer_idx` (`producer_id`),
  CONSTRAINT `FK_ProductInfo_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_ProductInfo_Producer` FOREIGN KEY (`producer_id`) REFERENCES `producer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info`
--

LOCK TABLES `product_info` WRITE;
/*!40000 ALTER TABLE `product_info` DISABLE KEYS */;
INSERT INTO `product_info` VALUES (1,'Throne',9999.00,2,1,' Oak chair'),(2,'Abra',10000.00,1,2,'Single bad frame'),(3,'Kadabra',12000.00,1,3,'Double bad frame with storage'),(4,'Piece of wood',6555.55,7,4,'Table, 140x60 cm'),(5,'Qwerty',5999.99,3,5,'Desk, 155x55 cm'),(6,'Asdf',8880.00,4,1,'Two-seat sofa'),(7,'Zxcv',350.00,5,2,'Work lamp'),(11,'Throne',10000.00,2,1,' Oak chair'),(12,'Abra',11000.00,1,2,'Single bad frame'),(13,'Abra',9999.00,1,2,'Single bad frame'),(14,'Abra',80000.00,1,2,'Single bad frame'),(15,'Ghjk',1000.00,8,4,'Vase, 40 cm'),(16,'Abra',8888.00,1,2,'Single bad frame'),(17,'Piece of wood',5555.55,7,4,'Table, 140x60 cm'),(18,'Piece of wood',5500.55,7,4,'Table, 140x60 cm'),(507,'Uiop',950.00,6,3,'Floor lamp, 150 cm');
/*!40000 ALTER TABLE `product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_table`
--

DROP TABLE IF EXISTS `sequence_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sequence_table` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_table`
--

LOCK TABLES `sequence_table` WRITE;
/*!40000 ALTER TABLE `sequence_table` DISABLE KEYS */;
INSERT INTO `sequence_table` VALUES (512);
/*!40000 ALTER TABLE `sequence_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `password` varchar(60) NOT NULL,
  `email` varchar(30) NOT NULL,
  `send_mail` tinyint(1) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `avatar` varchar(25) NOT NULL DEFAULT 'default.png',
  `attempts` int NOT NULL DEFAULT '0',
  `unban` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'dmytro','Dmytro','Sihov','abrakadabra','Dmytro_Sihov@epam.com',0,'ADMIN','default.png',0,NULL),(2,'vasya','Vas','Vass','123456','Vasya@epam.com',1,'USER','default.png',0,NULL),(3,'petya','Petya','Pet','qwerty','Petya@epam.com',0,'USER','default.png',0,NULL),(4,'dlasdasd','dasda','asdasda','dasdasdasdada','mail',1,'USER','default.png',0,NULL),(5,'dmytro2','dmytro','dmytro','dmytro','dima.sigov1@gmail.com',1,'USER','default.png',0,NULL),(7,'testuser1','testuser1','testuser1','testuser1','dima.sigov1@gmail.com',0,'USER','default.png',0,NULL),(8,'testuser2','testuser2','testuser2','testuser2','dima.sigov3@gmail.com',0,'USER','default.png',0,NULL),(9,'testuser3','testuser3','testuser3','testuser3','dima.sigov3@gmail.com',0,'USER','default.png',0,NULL),(10,'testuser4','testuser4','testuser4','testuser4','dima.sigov3@gmail.com',0,'USER','default.png',0,NULL),(11,'testuser5','testuser5','testuser5','testuser5','dima.sigov1@gmail.com',0,'USER','default.png',0,NULL),(12,'testuser6','testuser6','testuser6','testuser6','dima.sigov7@gmail.com',0,'USER','default.png',0,NULL),(13,'testuser9','testuser9','testuser9','testuser9','dima.sigov3@gmail.com',0,'USER','default.png',0,NULL),(14,'testuser10','testuser10','testuser10','$2a$10$13Iy5PPB4GHWZREZ6shokO6DDL3Vlgmdl6cdiQQOvG0VtA12f9HN6','dima.sigov7@gmail.com',0,'USER','default.png',0,NULL),(15,'test_User13','test_User13','test_User13','test_User13','dima.sigov7@gmail.com',0,'USER','default.png',0,NULL),(16,'testuser-32131','testuser-32131','testuser-32131','testuser-32131','dima.sigov1@gmail.com',1,'USER','default.png',0,NULL),(17,'testuser-321311','testuser-321311','testuser-321311','testuser-321311','dima.sigov3@gmail.com',0,'USER','default.png',0,NULL),(18,'fsdfsdf','fsdfsdf','fsdfsdf','fsdfsdf','dima.sigov1@gmail.com',0,'USER','default.png',0,NULL),(19,'testuser11','testuser11','testuser11','testuser11','testuser11@gmail.com',1,'USER','default.png',0,NULL),(20,'testuser12','testuser12','testuser12','testuser12','testuser12@gmail.com',1,'USER','default.png',0,NULL),(500,'testuser13','testuser13','testuser13','$2a$10$gK/RKOdAoPPcZsQeQ1Yj3ul8t7IR7vXGoLLKhoZfuIIasKA6lm5.W','dima.sigov1@gmail.com',0,'USER','default.png',0,NULL),(501,'testuser14','testuser14','testuser14','$2a$10$v.SZQNRqa5ewQ0U0KUOd9OVQMM/zrdqh7KI5ht0v22ap3YJXV2/6G','dima.sigov1@gmail.com',0,'USER','default.png',0,'2023-05-28 22:02:57'),(502,'testuser15','testuser15','testuser15','$2a$10$YgaHFHQiqObGMMrCemq5e.P00A9jCN07QZiPfHfy7R.xIaiFcAaiC','dima.sigov1@gmail.com',0,'USER','testuser15.png',0,'2023-05-28 22:02:57'),(503,'testuser16','testuser16','testuser16','$2a$10$YuTHZdY863n2SDA4SDvLKeyM7hueQHh2nFRSHLuIfqbczbrLt/cFG','dima.sigov1@gmail.com',0,'USER','testuser16.png',0,'2023-04-29 18:06:38');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23  9:57:26
