CREATE DATABASE  IF NOT EXISTS `BEEP` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `BEEP`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: k7a406.p.ssafy.io    Database: BEEP
-- ------------------------------------------------------
-- Server version	5.7.40

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
-- Table structure for table `block`
--

DROP TABLE IF EXISTS `block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `block` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `target_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `message_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkeh4gnvncdumm464jgf77tdn7` (`target_id`),
  KEY `FKwqvu8bn4qsys0h72a2a1p4kf` (`user_id`),
  KEY `FK4k1hueyhgg61q9m0w3gcnj7v1` (`message_id`),
  CONSTRAINT `FK4k1hueyhgg61q9m0w3gcnj7v1` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FKkeh4gnvncdumm464jgf77tdn7` FOREIGN KEY (`target_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKwqvu8bn4qsys0h72a2a1p4kf` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block`
--

LOCK TABLES `block` WRITE;
/*!40000 ALTER TABLE `block` DISABLE KEYS */;
INSERT INTO `block` VALUES (2,18,19,NULL),(3,22,18,NULL);
/*!40000 ALTER TABLE `block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  `word` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionary`
--

LOCK TABLES `dictionary` WRITE;
/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` VALUES (1,'당신은 나의 0순위','0000'),(2,'영원히 변하지 말자','0001'),(3,'외로워','0001000'),(4,'손가락 욕','00100'),(5,'good night kiss','0011'),(6,'영원히','012'),(7,'영원히 사랑해','0024'),(8,'땡땡이 치자','0027'),(9,'차에 문제가 생겼어','0049'),(10,'가고 있는 중이야','0090'),(11,'조금 늦어','0098'),(12,'응','010'),(13,'돌아와','0100'),(14,'시간 지켜','0116'),(15,'영원히 사랑해','0124'),(16,'영원한 친구','0179'),(17,'영원한 친구 사이','017942'),(18,'연인 사이','0242'),(19,'많이 아파','0285'),(20,'영원히 사랑해','04'),(21,'영원히 사랑해','0404'),(22,'영화 보러 가자','04290'),(23,'빵 사와','0455'),(24,'Hello','07734'),(25,'모든 것이 취소됐다(빵구빵구)','0909'),(26,'볼링장 가자','0929'),(27,'돌아와','100'),(28,'네가 그리워','10000001'),(29,'만세','100003'),(30,'많이 사랑해','100024'),(31,'천사','1004'),(32,'고민스러워','1008'),(33,'열렬히 사모한다','1010235'),(34,'기분 좋아','101101'),(35,'열이 펄펄','10288'),(36,'러브(LOVE)','1052'),(37,'고민이야','108'),(38,'흥','11010'),(39,'흐흐','11011101'),(40,'비상사태','1111'),(41,'긴급상황','112'),(42,'가운데에 선을 그으면 사랑해로 읽힌다','1126611'),(43,'I miss you','1177155400'),(44,'일을 빨리 해라','1182'),(45,'병원이야','119'),(46,'지금 바빠','1200'),(47,'홀짝홀짝(술 마시자)','1212'),(48,'이리 오소','1254'),(49,'이리 오시오','1255'),(50,'이리 오십사','12504'),(51,'일이 발생','1283'),(52,'난 지금 몸이 아파','129129'),(53,'기분 완전 좋아','13486'),(54,'너 없이 못 살아','1350'),(55,'정말 이상하다','13579'),(56,'1년 365일 24시간 사랑해','1365244'),(57,'식사나 함께 합시다','1414'),(58,'일사천리','1472'),(59,'사랑해','1486'),(60,'일찍 와','1717'),(61,'I LOVE YOU','17171771'),(62,'일찍 일찍 와','17175'),(63,'일찍 일찍 일어나','171715'),(64,'일찍 오렴','1750'),(65,'씨발씨발','1818'),(66,'나 너 싫어','186'),(67,'집에 급한 일','1919'),(68,'그럼 이만','20000'),(69,'천사에게','21004'),(70,'이...(불만)','2222'),(71,'둘이서 만나자','2241000045'),(72,'이상無','230'),(73,'이리 와','2255'),(74,'짝짝짝짝','2468'),(75,'잘가','258'),(76,'이륙이륙','2626'),(77,'가족 중에 축하해 줄 사람이 있어','2715'),(78,'할 얘기 있어','2828'),(79,'이제 그만 만나','2848'),(80,'이그 이그!','2929'),(81,'용기를 잃지 마','3232'),(82,'차 막혀','3255'),(83,'심심해','331'),(84,'심심하니 영화 보러 가자','3312042'),(85,'보고 싶어','3322'),(86,'힘내라','337'),(87,'사무치게 그리워','3575'),(88,'자주 연락해','4242'),(89,'사랑사랑','4343'),(90,'死死死死','4444'),(91,'사아아라아앙해애애','444888666'),(92,'죽도록 사랑해','4486'),(93,'사랑해','486'),(94,'우리 사랑은 영원할 거야','4860'),(95,'사고 났으니 빨리와','494982535'),(96,'오직 너만 사랑해','504'),(97,'S.O.S','505'),(98,'오늘 밤 전화해','5091'),(99,'조금 늦어','51055'),(100,'만났던 곳에서 기다릴게','5111'),(101,'항상 널 생각하고 있을게','5112'),(102,'오늘 술 한잔하자','5121'),(103,'하트','514709635'),(104,'기름이 떨어졌다','5151'),(105,'오늘은 잊을 수 없는 날이야','5200'),(106,'우리 만나자','521000045'),(107,'보고 싶다','522'),(108,'우리 사귀자','5222'),(109,'다 줄게','535'),(110,'카운트다운','54321'),(111,'오빠 사랑해','5454'),(112,'보고 싶어','55102'),(113,'잘 살아라','552111152'),(114,'오오사랑','5543'),(115,'증오','5555'),(116,'오~','55555555'),(117,'오빠를 사랑하는 사람','5844'),(118,'오빠 사랑해','5854'),(119,'오빠 빨리','5882'),(120,'우리 집으로 와','6288'),(121,'보고 싶다','6515'),(122,'보고 싶다','6516'),(123,'뽀뽀','660660'),(124,'삐삐 쳐','6616617'),(125,'축하해','7115'),(126,'오늘 휴강이야','7116'),(127,'친한 사이','7142'),(128,'축하해 주셔서 감사합니다','7175'),(129,'친한 친구','7179'),(130,'친한 친구 사이','717942'),(131,'오늘 미팅한다','7205'),(132,'호출해라','7272'),(133,'죽도록 사랑해','7486'),(134,'싫어','75'),(135,'착륙 착륙','7676'),(136,'드라이브 하자','7700'),(137,'우는 소리','777'),(138,'친구야 힘내','79337'),(139,'친구사이','7942'),(140,'바보','8080'),(141,'빨리와','820'),(142,'약속장소도착','8200'),(143,'빨리 와','825'),(144,'빨리 만나자','82504'),(145,'빨리 오시오','82515'),(146,'빨리 와','8255'),(147,'굿모닝','952'),(148,'빨리빨리','8282'),(149,'파이팅','827'),(150,'빨리 출발','8278'),(151,'빨리빨리 집으로','8282755'),(152,'빨리 가','8290'),(153,'바로 출발','8578'),(154,'바로바로 와요','858555'),(155,'빠이빠이','882882'),(156,'나 팔팔해','8888'),(157,'집에 가','90'),(158,'GO','9090'),(159,'구질구질','9797'),(160,'급한일','981'),(161,'굿바이','982'),(162,'할 이야기가 너무 많다(구구절절)','9977'),(163,'행운을 빌어','9999');
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `audio_uri` varchar(255) DEFAULT NULL,
  `content` varchar(11) NOT NULL,
  `distinction` int(11) DEFAULT NULL,
  `time` datetime(6) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  `tag` varchar(10) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86f0kc2mt26ifwupnivu6v8oa` (`receiver_id`),
  KEY `FKcnj2qaf5yc36v2f90jw2ipl9b` (`sender_id`),
  KEY `FKpxb0wq1oxjlpdr5hsqr4fmblp` (`owner_id`),
  CONSTRAINT `FK86f0kc2mt26ifwupnivu6v8oa` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcnj2qaf5yc36v2f90jw2ipl9b` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpxb0wq1oxjlpdr5hsqr4fmblp` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'0e8fe511-1319-402a-a510-9574d307ea25.mp3','ㅎㅇㅎㅇ',NULL,'2022-11-20 23:48:43.484000',1,21,21,'태그2',21),(2,'c38974e7-8d2a-46c0-b60c-e23b39127000.mp3','★ㅁㅎㅁㅎ★',NULL,'2022-11-21 00:10:55.438000',1,18,20,'호호아줌마',18),(3,NULL,'♥★♥★♥',NULL,'2022-11-21 00:25:15.423000',1,20,19,NULL,20),(4,NULL,'8282',NULL,'2022-11-20 23:14:53.415000',2,19,18,NULL,19),(5,NULL,'ㅊㄷㅎㄷㄷ',NULL,'2022-11-21 00:36:11.088000',2,18,22,NULL,18),(6,'ccc5d4f9-e5fe-40b1-b651-d5e3516540d1.mp3','★8282★',NULL,'2022-11-21 00:33:14.387000',1,18,20,NULL,18),(7,NULL,'ㅎㅇㅎㅇ',NULL,'2022-11-21 00:26:09.461000',1,20,18,'박사장',18),(8,NULL,'8282',NULL,'2022-11-20 23:14:53.213000',1,19,18,'김수한무',18);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_book`
--

DROP TABLE IF EXISTS `phone_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone_book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `install` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `target_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9lpbg6959wkl2gb0qwt317527` (`user_id`),
  CONSTRAINT `FK9lpbg6959wkl2gb0qwt317527` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_book`
--

LOCK TABLES `phone_book` WRITE;
/*!40000 ALTER TABLE `phone_book` DISABLE KEYS */;
INSERT INTO `phone_book` VALUES (1,0,'박사장',18,'01074794185'),(2,1,'♥내사랑♥',18,'01038964701'),(3,1,'호호아줌마',18,'01026778997'),(4,0,'떠난자★',18,'01020178439'),(5,1,'현열찌',18,'01034511945');
/*!40000 ALTER TABLE `phone_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preset`
--

DROP TABLE IF EXISTS `preset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(11) NOT NULL,
  `number` int(11) NOT NULL,
  `part` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr2lbnvf5cndc0evpmvk85k3br` (`user_id`),
  CONSTRAINT `FKr2lbnvf5cndc0evpmvk85k3br` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preset`
--

LOCK TABLES `preset` WRITE;
/*!40000 ALTER TABLE `preset` DISABLE KEYS */;
INSERT INTO `preset` VALUES (1,'01074794185',0,2,18),(2,'01026778997',1,2,18),(3,'01038964701',2,2,18),(4,'01020178439',3,2,18),(5,'ㅅㄹㅎ♥',1,1,18),(6,'1004',0,1,18),(7,'01041274185',4,2,18),(8,'01058801458',5,2,18),(9,'01066904185',6,2,18),(10,'ㅎㅇㅎㅇ',2,1,18),(11,'1255',3,1,18),(12,'ㄱㅅ',4,1,18),(13,'17171771',5,1,18),(14,'331',6,1,18),(15,'ㅂㄱㅍ',7,1,18),(16,'660660',8,1,18),(17,'ㅂ2ㅂ2',9,1,18),(18,'ㅁㅎㅁㅎ',0,1,21),(19,'01026778997',0,2,19),(20,'01050123883',0,2,20),(21,'01025802580',1,2,20),(22,'ㅎㅇㅎㅇ',0,1,20),(23,'00100',1,1,20);
/*!40000 ALTER TABLE `preset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime(6) NOT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnfnboik5lv37rehgogdfthojk` (`receiver_id`),
  KEY `FK38wce4d5fg7hv0o3t0lpfclom` (`sender_id`),
  CONSTRAINT `FK38wce4d5fg7hv0o3t0lpfclom` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnfnboik5lv37rehgogdfthojk` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sms`
--

DROP TABLE IF EXISTS `sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `audio_uri` varchar(255) NOT NULL,
  `content` varchar(11) NOT NULL,
  `time` datetime(6) NOT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfvvpdyurnvt3rs1knwt691as2` (`receiver_id`),
  KEY `FKm1t7rfky5pj2g2neiwfaes9d4` (`sender_id`),
  CONSTRAINT `FKfvvpdyurnvt3rs1knwt691as2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKm1t7rfky5pj2g2neiwfaes9d4` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sms`
--

LOCK TABLES `sms` WRITE;
/*!40000 ALTER TABLE `sms` DISABLE KEYS */;
/*!40000 ALTER TABLE `sms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alarm` int(11) NOT NULL DEFAULT '1',
  `engrave` varchar(10) DEFAULT NULL,
  `fcm_token` varchar(255) NOT NULL DEFAULT '0',
  `font` int(11) NOT NULL DEFAULT '1',
  `introduce_audio` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `theme` int(11) NOT NULL DEFAULT '1',
  `type` int(11) NOT NULL DEFAULT '1',
  `authority` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4bgmpi98dylab6qdvf9xyaxu4` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,NULL,'cIeMwTm7SV2y1l0rgoQKzk:APA91bEVL2oQjlFII23bbfK3WJCqgXSRNZJRIXvem0vGPbJRyZxNebBZgOJaREtBhknkps1uGBKI2lY2dzjaU7VjBG13wm1umohfmd6LF8rWKRsGp6yuzs_OnW7-QwKnxO3vw0xjaIEA',2,NULL,'$2a$10$2aHHsb6UZp5iQGjMKVA2xe8rGwSyRrnDLbfL3ZXKP82za3IRz5f/a','01034511945',1,1,'ROLE_USER'),(18,1,'ssafy','cxdTNC8SR4CWG-JlPj9jeE:APA91bH1SdvCTq9GqVac-NxLIQaCLUmLjovMlPi9FZU2SvPOCKIUXxmzWFvkamvvL9OUyAVMdBm-qh_0Ab-8WmPQzRbuHgwR6ki0Ycn1uFpNXmYLfTmFwOC0qYldt-RMZXt_nyF2rv67',1,NULL,'$2a$10$lRm1q2RXzG2drCR5MfT1SOacxpk/XRBtdZ7J73Ph0qmesxSr4nSda','01050123883',3,1,'ROLE_USER'),(19,1,NULL,'cxh65jQtSuGHr83BJkAa1Y:APA91bGRpMxQSLxM9iZ3XiFmsoIKEv4w-3N1ZKz_nMZYio9nCkIU3Amxch3FyfQpw2xGfzpDk0rYb4kEePWPCFVXBptwO0oeN8rJ2MEupFe0IaAr9-hNCrkKnEE1lcIklY71cwK570S2',4,NULL,'$2a$10$ebirIxmuxWcLyAKAgE18vOH7MNLMlYfQLA.88GTe9vb6fUkOvRjqq','01025802580',1,1,'ROLE_USER'),(20,1,NULL,'fxO7L2HpQViiRigTbA8ZPE:APA91bERWdyBr4u2Ey0ao19_7CQOZ8oPiIsZhylwtbUaxEGjkkxPVzOGxxwtzPa0zBsOQxVNKBncweMnPO1tFZIEpfHtpppgK0-LzYsdak2oriT5m6Mit3KqzI52SC25wZLRZBYgL2qS',1,'3b4e5169-bcf7-45c0-b896-50689b6d0e79.mp3','$2a$10$UvoOzzp63RXJHddQI1po6Ol9nNaUAVbCXBaCG7h08F8tGtFx4qTu.','01026778997',1,1,'ROLE_USER'),(21,1,'661661','cSOjGSzmR0uJXpBcKL8EOe:APA91bEOfOMV7bdcm7E6Z-CmDs_mwETklRksdBmXOGfhciTtz2Xua3H-dlLe4gGDVK1romnBxRCDjmj_DoKzJ5By9MLCcEm_aldNnOHJCFNhVGjrczZhkx72AGT4ngQ0AluOJ2QDMkEJ',1,'c0218f59-ee2b-4b54-8142-81578e59a283.mp3','$2a$10$ZucGaYeSq76T3xmru6SqBuho5GbyixdPkzX62n3C5atvBaMCjvnj6','01038964701',2,1,'ROLE_USER'),(22,1,NULL,'cxh65jQtSuGHr83BJkAa1Y:APA91bGRpMxQSLxM9iZ3XiFmsoIKEv4w-3N1ZKz_nMZYio9nCkIU3Amxch3FyfQpw2xGfzpDk0rYb4kEePWPCFVXBptwO0oeN8rJ2MEupFe0IaAr9-hNCrkKnEE1lcIklY71cwK570S2',1,NULL,'$2a$10$lpKrSmiVt8SFdnVZ6om1reMzemyFEvvK9fmLk5RQz7meXFDfxj/ye','01044444444',1,1,'ROLE_USER');
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

-- Dump completed on 2022-11-21  1:04:32
