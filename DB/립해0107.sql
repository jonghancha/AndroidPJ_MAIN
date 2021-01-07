CREATE DATABASE  IF NOT EXISTS `one` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `one`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: one
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `cartdetail`
--

DROP TABLE IF EXISTS `cartdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetail` (
  `DcartNo` int NOT NULL AUTO_INCREMENT,
  `cartinfo_cartNo` int NOT NULL,
  `user_userEmail` varchar(45) NOT NULL,
  `cartQty` int DEFAULT NULL,
  `goods_prdNo` int NOT NULL,
  PRIMARY KEY (`DcartNo`),
  KEY `goods_prdNo` (`goods_prdNo`),
  KEY `fk_orderdetail_copy1_cartinfo1_idx` (`cartinfo_cartNo`),
  KEY `fk_cartdetail_user1_idx` (`user_userEmail`),
  CONSTRAINT `fk_cartdetail_user1` FOREIGN KEY (`user_userEmail`) REFERENCES `user` (`userEmail`),
  CONSTRAINT `fk_orderdetail_copy1_cartinfo1` FOREIGN KEY (`cartinfo_cartNo`) REFERENCES `cartinfo` (`cartNo`),
  CONSTRAINT `orderdetail_ibfk_20` FOREIGN KEY (`goods_prdNo`) REFERENCES `product` (`prdNo`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetail`
--

LOCK TABLES `cartdetail` WRITE;
/*!40000 ALTER TABLE `cartdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartinfo`
--

DROP TABLE IF EXISTS `cartinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartinfo` (
  `cartNo` int NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `cartDate` datetime DEFAULT NULL,
  `cartTotalPrice` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cartNo`,`user_userEmail`),
  KEY `fk_cartinfo_user1_idx` (`user_userEmail`),
  CONSTRAINT `fk_cartinfo_user1` FOREIGN KEY (`user_userEmail`) REFERENCES `user` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartinfo`
--

LOCK TABLES `cartinfo` WRITE;
/*!40000 ALTER TABLE `cartinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `eSeqno` int NOT NULL AUTO_INCREMENT,
  `eTitle` varchar(45) DEFAULT NULL,
  `eContent` varchar(45) DEFAULT NULL,
  `eFilename` varchar(45) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  PRIMARY KEY (`eSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `likeNo` int NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `product_prdNo` int NOT NULL,
  PRIMARY KEY (`likeNo`),
  UNIQUE KEY `likeNo_UNIQUE` (`likeNo`),
  KEY `fk_like_user1_idx` (`user_userEmail`),
  KEY `fk_like_product1_idx` (`product_prdNo`),
  CONSTRAINT `fk_like_product1` FOREIGN KEY (`product_prdNo`) REFERENCES `product` (`prdNo`),
  CONSTRAINT `fk_like_user1` FOREIGN KEY (`user_userEmail`) REFERENCES `user` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
/*!40000 ALTER TABLE `like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `management`
--

DROP TABLE IF EXISTS `management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `management` (
  `goods_prdNo` int NOT NULL,
  `admin_adminId` varchar(15) NOT NULL,
  PRIMARY KEY (`goods_prdNo`,`admin_adminId`),
  CONSTRAINT `fk_management_goods1` FOREIGN KEY (`goods_prdNo`) REFERENCES `product` (`prdNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `management`
--

LOCK TABLES `management` WRITE;
/*!40000 ALTER TABLE `management` DISABLE KEYS */;
/*!40000 ALTER TABLE `management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `open`
--

DROP TABLE IF EXISTS `open`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `open` (
  `admin_adminId` varchar(15) NOT NULL,
  `event_eSeqno` int NOT NULL,
  KEY `fk_open_admin1_idx` (`admin_adminId`),
  KEY `open_ibfk_1` (`event_eSeqno`),
  CONSTRAINT `open_ibfk_1` FOREIGN KEY (`event_eSeqno`) REFERENCES `event` (`eSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `open`
--

LOCK TABLES `open` WRITE;
/*!40000 ALTER TABLE `open` DISABLE KEYS */;
/*!40000 ALTER TABLE `open` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `orderNo` int NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `orderinfo_ordNo` int NOT NULL,
  `goods_prdNo` int NOT NULL,
  `ordQty` int DEFAULT NULL,
  `ordRefund` datetime DEFAULT NULL,
  `ordReview` text,
  `ordStar` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`orderNo`),
  KEY `fk_orderdetail_orderinfo1_idx` (`orderinfo_ordNo`),
  KEY `goods_prdNo` (`goods_prdNo`),
  KEY `fk_orderdetail_user1_idx` (`user_userEmail`),
  CONSTRAINT `fk_orderdetail_user1` FOREIGN KEY (`user_userEmail`) REFERENCES `user` (`userEmail`),
  CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`orderinfo_ordNo`) REFERENCES `orderinfo` (`ordNo`),
  CONSTRAINT `orderdetail_ibfk_2` FOREIGN KEY (`goods_prdNo`) REFERENCES `product` (`prdNo`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderinfo`
--

DROP TABLE IF EXISTS `orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderinfo` (
  `ordNo` int NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `ordDate` datetime DEFAULT NULL,
  `ordReceiver` varchar(15) DEFAULT NULL,
  `ordRcvAddress` varchar(45) DEFAULT NULL,
  `ordRcvPhone` varchar(45) DEFAULT NULL,
  `orderRequest` varchar(30) DEFAULT NULL,
  `ordPay` varchar(45) DEFAULT NULL,
  `ordBank` varchar(25) DEFAULT NULL,
  `ordCardNo` varchar(20) DEFAULT NULL,
  `ordCardPass` varchar(10) DEFAULT NULL,
  `ordDelivery` varchar(45) DEFAULT '상품 준비중',
  `ordDeliveryDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ordNo`,`user_userEmail`),
  KEY `fk_orderinfo_user1_idx` (`user_userEmail`),
  CONSTRAINT `fk_orderinfo_user1` FOREIGN KEY (`user_userEmail`) REFERENCES `user` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderinfo`
--

LOCK TABLES `orderinfo` WRITE;
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participate`
--

DROP TABLE IF EXISTS `participate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participate` (
  `user_userId` varchar(15) NOT NULL,
  `win` varchar(45) DEFAULT 'X',
  `event_eSeqno` int NOT NULL,
  PRIMARY KEY (`user_userId`,`event_eSeqno`),
  KEY `participate_ibfk_1` (`event_eSeqno`),
  CONSTRAINT `fk_participate_user1` FOREIGN KEY (`user_userId`) REFERENCES `user` (`userEmail`),
  CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`event_eSeqno`) REFERENCES `event` (`eSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participate`
--

LOCK TABLES `participate` WRITE;
/*!40000 ALTER TABLE `participate` DISABLE KEYS */;
/*!40000 ALTER TABLE `participate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `prdNo` int NOT NULL AUTO_INCREMENT,
  `prdName` varchar(45) NOT NULL,
  `ctgType` varchar(25) NOT NULL,
  `prdBrand` varchar(20) NOT NULL,
  `prdPrice` int NOT NULL,
  `prdFilename` varchar(30) DEFAULT NULL,
  `prdDFilename` varchar(30) DEFAULT NULL,
  `prdNFilename` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`prdNo`),
  UNIQUE KEY `prdNo_UNIQUE` (`prdNo`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userEmail` varchar(45) NOT NULL,
  `userPw` varchar(25) NOT NULL,
  `userName` varchar(10) DEFAULT NULL,
  `userTel` varchar(15) DEFAULT NULL,
  `userFilename` varchar(45) DEFAULT NULL,
  `userGender` varchar(10) DEFAULT NULL,
  `userColor` varchar(10) DEFAULT NULL,
  `insertDate` date DEFAULT NULL,
  `deleteDate` date DEFAULT NULL,
  PRIMARY KEY (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
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

-- Dump completed on 2021-01-07 10:39:40
