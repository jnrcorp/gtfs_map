CREATE DATABASE  IF NOT EXISTS `gtfs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gtfs`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gtfs
-- ------------------------------------------------------
-- Server version	5.7.10-log

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
-- Table structure for table `agency`
--

DROP TABLE IF EXISTS `agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agency` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `agency_id` varchar(10) DEFAULT NULL,
  `agency_name` varchar(60) DEFAULT NULL,
  `agency_url` varchar(300) DEFAULT NULL,
  `agency_timezone` varchar(30) DEFAULT NULL,
  `agency_lang` varchar(4) DEFAULT NULL,
  `agency_phone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `agency_id_idx` (`agency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `calendar_dates`
--

DROP TABLE IF EXISTS `calendar_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_dates` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `exception_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_idx` (`service_id`),
  KEY `date_idx` (`date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routes` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) DEFAULT NULL,
  `agency_id` varchar(10) DEFAULT NULL,
  `route_short_name` varchar(10) DEFAULT NULL,
  `route_long_name` varchar(60) DEFAULT NULL,
  `route_type` int(11) DEFAULT NULL,
  `route_url` varchar(300) DEFAULT NULL,
  `route_color` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `route_id_idx` (`route_id`),
  KEY `agency_id_idx` (`agency_id`),
  CONSTRAINT `rt_agency_fk` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`agency_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shapes`
--

DROP TABLE IF EXISTS `shapes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shapes` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `shape_id` int(11) DEFAULT NULL,
  `shape_pt_lat` double DEFAULT NULL,
  `shape_pt_lon` double DEFAULT NULL,
  `shape_pt_sequence` int(11) DEFAULT NULL,
  `shape_dist_traveled` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `shape_id_idx` (`shape_id`),
  KEY `shape_location_idx` (`shape_pt_lat`,`shape_pt_lon`),
  KEY `shape_sequence_idx` (`shape_pt_sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stop_times`
--

DROP TABLE IF EXISTS `stop_times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stop_times` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `trip_id` int(11) DEFAULT NULL,
  `arrival_time` datetime DEFAULT NULL,
  `departure_time` datetime DEFAULT NULL,
  `stop_id` int(11) DEFAULT NULL,
  `stop_sequence` int(11) DEFAULT NULL,
  `pickup_type` int(11) DEFAULT NULL,
  `drop_off_type` int(11) DEFAULT NULL,
  `shape_dist_traveled` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stop_id_idx` (`stop_id`),
  KEY `departure_time_idx` (`departure_time`),
  KEY `trip_id_idx` (`trip_id`,`stop_sequence`),
  CONSTRAINT `st_stop_fk` FOREIGN KEY (`stop_id`) REFERENCES `stops` (`stop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `st_trip_fk` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stops`
--

DROP TABLE IF EXISTS `stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stops` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `stop_id` int(11) DEFAULT NULL,
  `stop_code` varchar(20) DEFAULT NULL,
  `stop_name` varchar(60) DEFAULT NULL,
  `stop_desc` varchar(60) DEFAULT NULL,
  `stop_lat` double DEFAULT NULL,
  `stop_lon` double DEFAULT NULL,
  `zone_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stop_id_idx` (`stop_id`),
  KEY `stop_name_idx` (`stop_name`),
  KEY `stop_location_idx` (`stop_lat`,`stop_lon`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trips`
--

DROP TABLE IF EXISTS `trips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trips` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  `trip_headsign` varchar(120) DEFAULT NULL,
  `direction_id` int(11) DEFAULT NULL,
  `block_id` varchar(20) DEFAULT NULL,
  `shape_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `route_id_idx` (`route_id`),
  KEY `trip_id_idx` (`trip_id`),
  KEY `shape_id_idx` (`shape_id`),
  KEY `direction_id_idx` (`trip_id`, `direction_id`),
  KEY `tr_service_id_fk_idx` (`service_id`),
  CONSTRAINT `tr_route_id_fk` FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tr_service_id_fk` FOREIGN KEY (`service_id`) REFERENCES `calendar_dates` (`service_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tr_shape_id_fk` FOREIGN KEY (`shape_id`) REFERENCES `shapes` (`shape_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `travel_times`
--

DROP TABLE IF EXISTS `travel_times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gtfs`.`travel_times` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from_stop_id` INT(11) NOT NULL,
  `to_stop_id` INT(11) NOT NULL,
  `travel_time_minutes` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `from_stop_id_fk_idx` (`from_stop_id` ASC),
  INDEX `to_stop_id_fk_idx` (`to_stop_id` ASC),
  CONSTRAINT `from_stop_id_fk`
    FOREIGN KEY (`from_stop_id`)
    REFERENCES `gtfs`.`stops` (`stop_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `to_stop_id_fk`
    FOREIGN KEY (`to_stop_id`)
    REFERENCES `gtfs`.`stops` (`stop_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-14 21:18:46
