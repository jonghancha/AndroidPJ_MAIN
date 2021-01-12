-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: one
-- ------------------------------------------------------
-- Server version	8.0.17

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
  `DcartNo` int(11) NOT NULL AUTO_INCREMENT,
  `cartinfo_cartNo` int(11) NOT NULL,
  `user_userEmail` varchar(45) NOT NULL,
  `cartQty` int(11) DEFAULT NULL,
  `goods_prdNo` int(11) NOT NULL,
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
  `cartNo` int(11) NOT NULL AUTO_INCREMENT,
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
  `eSeqno` int(11) NOT NULL AUTO_INCREMENT,
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
  `likeNo` int(11) NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `product_prdNo` int(11) NOT NULL,
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
  `goods_prdNo` int(11) NOT NULL,
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
  `event_eSeqno` int(11) NOT NULL,
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
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `user_userEmail` varchar(45) NOT NULL,
  `orderinfo_ordNo` int(11) NOT NULL,
  `goods_prdNo` int(11) NOT NULL,
  `ordQty` int(11) DEFAULT NULL,
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
  `ordNo` int(11) NOT NULL AUTO_INCREMENT,
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
  `event_eSeqno` int(11) NOT NULL,
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
  `prdNo` int(11) NOT NULL AUTO_INCREMENT,
  `prdName` varchar(100) NOT NULL,
  `prdColor` varchar(10) DEFAULT NULL,
  `ctgType` varchar(25) NOT NULL,
  `prdBrand` varchar(20) NOT NULL,
  `prdPrice` int(11) NOT NULL,
  `prdFilename` varchar(30) DEFAULT NULL,
  `prdDFilename` varchar(30) DEFAULT NULL,
  `prdNFilename` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`prdNo`),
  UNIQUE KEY `prdNo_UNIQUE` (`prdNo`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'잉크 더 에어리 벨벳 스틱 [004 매력보스]','쿨톤','립스틱','페리페라',11000,'Spe_04.jpeg',NULL,NULL),(2,'제로 매트 립스틱 [13호 레드카펫]','웜톤','립스틱','롬앤',12000,'Srom_13.jpeg',NULL,NULL),(3,'제로 매트 립스틱 [16호 대즐레드]','웜톤','립스틱','롬앤',12000,'Srom_16.jpeg',NULL,NULL),(4,'제로 벨벳 틴트 [08 아이시]','쿨톤','립틴트','롬앤',13000,'Trom_08.jpeg',NULL,NULL),(5,'베일 틴트 듀이 [006 로즈밀크티]','쿨톤','립틴트','클리오',16000,'Tclio_06.jpeg',NULL,NULL),(6,'무드라이어 벨벳틴트 [6호 치명적인석류인척]','웜톤','립틴트','릴리바이레드',10000,'Tlily_06.jpeg',NULL,NULL),(7,'글래스팅 워터 글로스 [01 산호크러쉬]','쿨톤','립글로즈','롬앤',13000,'Rrom_01.jpeg',NULL,NULL),(8,'C컵 딥볼륨 립톡스 [말괄량이체리]','웜톤','립글로즈','네이크업페이스',18000,'Rnake_01.jpeg',NULL,NULL),(9,'C컵 딥볼륨 립톡스 [발레리나핑크]','쿨톤','립글로즈','네이크업페이스',18000,'Rnake_02.jpeg',NULL,NULL),(10,'오늘밤 립밤','웜톤','립밤','코스노리',12000,'Bcos_01.jpeg',NULL,NULL),(11,'불가리안 로즈 립 트리트먼트 밤 [퓨어레드]','웜톤','립밤','아이소이',19800,'Bisoi_01.jpeg',NULL,NULL),(12,'핑크 그레이프 후르츠 모이스춰라이징 립 밤','쿨톤','립밤','버츠비',6000,'Bburts_01.jpeg',NULL,NULL);
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
INSERT INTO `user` VALUES ('qkrwldms011@naver.com','qwer1234','jieun','010',NULL,'여','쿨톤',NULL,'2021-01-11'),('wldms@naver.com','1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `youtube`
--

DROP TABLE IF EXISTS `youtube`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `youtube` (
  `ytNo` varchar(5) NOT NULL,
  `ytCtg` varchar(45) DEFAULT NULL,
  `ytName` varchar(35) DEFAULT NULL,
  `ytContent` text,
  `ytUrl` varchar(100) DEFAULT NULL,
  `tyImg` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ytNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `youtube`
--

LOCK TABLES `youtube` WRITE;
/*!40000 ALTER TABLE `youtube` DISABLE KEYS */;
INSERT INTO `youtube` VALUES ('1','holiday','특별한 날',NULL,'Vrysx6m3Iw4',NULL),('2','season','계절','으아아아아','xnA-HnNyXXs',NULL),('3','star','연예인',NULL,'Vrysx6m3Iw4',NULL),('4','basic','기초',NULL,'wqvs8vUpmLs',NULL),('5','color','색깔',NULL,'gYbJ-ywrKyA',NULL);
/*!40000 ALTER TABLE `youtube` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-13  1:26:07
