-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: localhost    Database: dbsysmodelo
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKemsnreyk6g37uoja1ngeog5sp` (`user_id`),
  CONSTRAINT `FKemsnreyk6g37uoja1ngeog5sp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Rua Território Fernando de Noronha 2112','64007250','Teresina',0,0,'Armando Soares Sousa','PI',1),(2,'Rua Júlio Cesar','60000000','Fortaleza',0,0,'Maria Joaquina de Amaral Pereira Goes','CE',2),(16,'Rua do Teste','64000000','Teste',0,0,'Teste da Silva Sauro','TE',34),(17,'End Teste','65000000','Teste',0,0,'Teste Segundo Atualizado no Ambiente de Desenvolvimento','TE',35),(18,NULL,NULL,NULL,0,0,NULL,NULL,36);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_pictures`
--

DROP TABLE IF EXISTS `person_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_pictures` (
  `person_id` bigint(20) NOT NULL,
  `pictures_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_jqqg29t07q75nwoiohvqvfyn6` (`pictures_id`),
  KEY `FKtmnbbk3jp0dit0qqr2lu7sev8` (`person_id`),
  CONSTRAINT `FK8ujewad87wn7tt46i974jbwxq` FOREIGN KEY (`pictures_id`) REFERENCES `picture` (`id`),
  CONSTRAINT `FKtmnbbk3jp0dit0qqr2lu7sev8` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_pictures`
--

LOCK TABLES `person_pictures` WRITE;
/*!40000 ALTER TABLE `person_pictures` DISABLE KEYS */;
INSERT INTO `person_pictures` VALUES (1,33),(1,34),(1,35),(1,37),(2,36);
/*!40000 ALTER TABLE `person_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41i6hfm4sdrkrm9krf2h1i9fx` (`person_id`),
  CONSTRAINT `FK41i6hfm4sdrkrm9krf2h1i9fx` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (3,'Armando no teste 3 de salva imagem no banco','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-08-10.png',1,NULL),(4,'teste 4 do armando','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-18-07.png',1,NULL),(5,'Teste 5 de imagem salva pelo usuário.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-32-00.png',1,'1-2019-03-18-12-32-00'),(13,'teste 14','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-18-12-59-48.png',1,'1-2019-03-18-12-59-48'),(22,'Explorando a Computação em Nuvem com a Amazon.','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-19-20-51-16.png',1,'1-2019-03-19-20-51-16'),(24,'teste junto ao S3 da AWS','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-23-14-59-53.png',1,'1-2019-03-23-14-59-53'),(25,'Teste de novo arquivo salvo no s3 da aos','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-23-15-17-19.png',1,'1-2019-03-23-15-17-19'),(26,'teste 3 de inserção de foto no s3 do aos','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-23-15-24-39.png',1,'1-2019-03-23-15-24-39'),(27,'teste 4 do s3 no aws','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-23-15-32-58.png',1,'1-2019-03-23-15-32-58'),(28,'teste 7 do s3','/Users/armandosoaressousa/git/tsd/systagram/uploads/pictures/1-2019-03-23-15-45-45.png',1,'1-2019-03-23-15-45-45'),(33,'teste1','/Users/armandosoaressousa/Desenvolvimento/modelos/spring-boot/sysdemo/uploads/pictures/1-2019-07-10-13-19-11.png',1,'1-2019-07-10-13-19-11'),(34,'Tempo','/Users/armandosoaressousa/Desenvolvimento/modelos/spring-boot/sysdemo/uploads/pictures/1-2019-07-10-13-20-48.png',1,'1-2019-07-10-13-20-48'),(35,'Tecnologia da Informação','/Users/armandosoaressousa/Desenvolvimento/modelos/spring-boot/sysdemo/uploads/pictures/1-2019-07-10-13-30-46.png',1,'1-2019-07-10-13-30-46'),(36,'Aplicativos móveis','/Users/armandosoaressousa/Desenvolvimento/modelos/spring-boot/sysdemo/uploads/pictures/2-2019-07-10-13-34-10.png',2,'2-2019-07-10-13-34-10'),(37,'Desenvolvimento para a Nuvem','/Users/armandosoaressousa/git/sysarm/sysdemo/uploads/pictures/1-2019-07-10-13-46-09.png',1,'1-2019-07-10-13-46-09');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER'),(4,'GUEST');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `id` (`id`),
  KEY `FKd21kkcigxa21xuby5i3va9ncs` (`person_id`),
  CONSTRAINT `FKd21kkcigxa21xuby5i3va9ncs` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('armando','$2a$10$bkmVrasmzmeK4Uq/uashx..pta97Koxhi41..rbami8RSjVDJ/zNO',1,1,'armando@ufpi.edu.br',1),('maria','$2a$10$QDZqbS8KUkaLACXFrZUpqOtDFc/wMBqC.ZaEu.XgvagmOLAfDUhoq',1,2,'maria@gmail.com',2),('teste','$2a$10$h.XG2Pr8HYIDBOXQHctKbeLWmMEZZ.tscCXDCPsDC5OF2zxcjtOyu',1,34,'teste@gmail.com',16),('teste2','$2a$10$wFAuEdSSIZqxzUOOWv47CefhsSI8j1Wrkz5TwDfgPkrXkMKv7XIGC',1,35,'teste2@gmail.com',17),('teste3','$2a$10$FspFBwmhpr8IPJJ3fLlGLuEGhcsrvrGfdgXqixfLKHDc9P/7M8yHi',1,36,'teste3@gmail.com',18);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK15d410tj6juko0sq9k4km60xq` (`roles_id`),
  KEY `FKml90kef4w2jy7oxyqv742tsfc` (`users_id`),
  CONSTRAINT `FK15d410tj6juko0sq9k4km60xq` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(1,2),(2,2),(34,2),(35,2),(36,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-10 13:50:17
