-- database dump generated with following command:
-- mysqldump -u brian -p fr_dev -d -R

-- MySQL dump 10.11
--
-- Host: localhost    Database: fr_dev
-- ------------------------------------------------------
-- Server version       5.0.38-Ubuntu_0ubuntu1-log

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
-- Table structure for table `ExerciseUOM`
--

DROP TABLE IF EXISTS `ExerciseUOM`;
CREATE TABLE `ExerciseUOM` (
  `uom` varchar(24) NOT NULL,
  `baseUOM` varchar(24) NOT NULL,
  `exerciseType` enum('repetition','distance','duration') NOT NULL,
  `conversionFactor` smallint(5) unsigned NOT NULL,
  `isDefault` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`uom`),
  KEY `exerciseType` (`exerciseType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `RoutineSession`
--

DROP TABLE IF EXISTS `RoutineSession`;
CREATE TABLE `RoutineSession` (
  `routineSessionID` int(10) unsigned NOT NULL auto_increment,
  `userRoutineID` int(10) unsigned NOT NULL,
  `routineSessionName` varchar(128) NOT NULL,
  `orderIndex` tinyint(4) NOT NULL,
  PRIMARY KEY  (`routineSessionID`),
  KEY `routine_id` (`userRoutineID`),
  CONSTRAINT `RoutineSession_ibfk_1` FOREIGN KEY (`userRoutineID`) REFERENCES `UserRoutine` (`userRoutineID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `RoutineSessionData`
--

DROP TABLE IF EXISTS `RoutineSessionData`;
CREATE TABLE `RoutineSessionData` (
  `routineSessionDataID` int(10) unsigned NOT NULL auto_increment,
  `routineSessionID` int(10) unsigned NOT NULL,
  `recordedAt` timestamp NULL default CURRENT_TIMESTAMP,
  `status` enum('completed','skipped') NOT NULL,
  `note` varchar(255) default NULL,
  PRIMARY KEY  (`routineSessionDataID`),
  KEY `routineSessionID` (`routineSessionID`),
  KEY `timestamp` (`recordedAt`),
  CONSTRAINT `RoutineSessionData_ibfk_1` FOREIGN KEY (`routineSessionID`) REFERENCES `RoutineSession` (`routineSessionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `SessionExercise`
--

DROP TABLE IF EXISTS `SessionExercise`;
CREATE TABLE `SessionExercise` (
  `sessionExerciseID` int(10) unsigned NOT NULL auto_increment,
  `routineSessionID` int(10) unsigned NOT NULL,
  `userExerciseID` int(10) unsigned NOT NULL,
  `orderIndex` tinyint(4) unsigned NOT NULL,
  `target` int(10) unsigned NOT NULL,
  `note` varchar(32) default NULL,
  PRIMARY KEY  (`sessionExerciseID`),
  KEY `exercise_id` (`userExerciseID`),
  KEY `session_id` (`routineSessionID`),
  CONSTRAINT `SessionExercise_ibfk_1` FOREIGN KEY (`routineSessionID`) REFERENCES `RoutineSession` (`routineSessionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SessionExercise_ibfk_2` FOREIGN KEY (`userExerciseID`) REFERENCES `UserExercise` (`userExerciseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `SessionExerciseData`
--

DROP TABLE IF EXISTS `SessionExerciseData`;
CREATE TABLE `SessionExerciseData` (
  `routineSessionDataID` int(10) unsigned NOT NULL,
  `sessionExerciseID` int(10) unsigned NOT NULL,
  `status` enum('completed','skipped') NOT NULL,
  `note` varchar(128) NOT NULL,
  `actual` int(10) unsigned default NULL,
  KEY `session_exercise_id` (`sessionExerciseID`),
  KEY `routineSessionDataID` (`routineSessionDataID`),
  CONSTRAINT `SessionExerciseData_ibfk_1` FOREIGN KEY (`sessionExerciseID`) REFERENCES `SessionExercise` (`sessionExerciseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SessionExerciseData_ibfk_2` FOREIGN KEY (`routineSessionDataID`) REFERENCES `RoutineSessionData` (`routineSessionDataID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `userID` int(10) unsigned NOT NULL auto_increment,
  `userName` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `currentRoutine` int(10) unsigned default NULL,
  PRIMARY KEY  (`userID`),
  KEY `current_routine` (`currentRoutine`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `UserActivation`
--

DROP TABLE IF EXISTS `UserActivation`;
CREATE TABLE `UserActivation` (
  `activationID` varchar(32) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `created` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`activationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `UserExercise`
--

DROP TABLE IF EXISTS `UserExercise`;
CREATE TABLE `UserExercise` (
  `userExerciseID` int(10) unsigned NOT NULL auto_increment,
  `userID` int(10) unsigned NOT NULL,
  `exerciseName` varchar(30) NOT NULL,
  `exerciseType` enum('repetition','distance','duration') NOT NULL default 'repetition',
  `display` tinyint(1) NOT NULL default '1',
  `defaultUOM` varchar(24) default NULL,
  `note` varchar(32) default NULL,
  PRIMARY KEY  (`userExerciseID`),
  KEY `uid` (`userID`),
  KEY `defaultUOM` (`defaultUOM`),
  CONSTRAINT `UserExercise_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserExercise_ibfk_2` FOREIGN KEY (`defaultUOM`) REFERENCES `ExerciseUOM` (`uom`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `UserRoutine`
--

DROP TABLE IF EXISTS `UserRoutine`;
CREATE TABLE `UserRoutine` (
  `userRoutineID` int(10) unsigned NOT NULL auto_increment,
  `userID` int(10) unsigned NOT NULL,
  `routineName` varchar(32) NOT NULL,
  `locked` tinyint(1) unsigned NOT NULL default '0',
  PRIMARY KEY  (`userRoutineID`),
  KEY `user_id` (`userID`),
  CONSTRAINT `UserRoutine_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `UserToken`
--

DROP TABLE IF EXISTS `UserToken`;
CREATE TABLE `UserToken` (
  `userID` int(10) unsigned NOT NULL,
  `token` varchar(32) NOT NULL,
  `expires` tinyint(1) NOT NULL default '1',
  `lastAccess` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`userID`),
  KEY `token` (`token`),
  CONSTRAINT `UserToken_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping routines for database 'fr_dev'
--
DELIMITER ;;
/*!50003 DROP FUNCTION IF EXISTS `generate_activation_id` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `generate_activation_id`(email_address VARCHAR(255)) RETURNS varchar(32)
    DETERMINISTIC
BEGIN

SET @aid = MD5(RAND());

INSERT INTO userActivation (activationID,emailAddress) VALUES (@aid, email_address);


    RETURN @aid;
END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `new_routine_session` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `new_routine_session`(rid INT, sname VARCHAR(128), oindex TINYINT) RETURNS int(10)
    DETERMINISTIC
BEGIN

#shift any display indexes that come after the requested display index of the newly added session
UPDATE RoutineSession SET orderIndex = orderIndex + 1 WHERE userRoutineID = rid AND orderIndex >= oindex;
#insert the new display index reference for the newly added routine session into the routine session display index table
INSERT INTO RoutineSession (userRoutineID, sessionName, orderIndex) VALUES (rid, sname, oindex);

RETURN LAST_INSERT_ID();
END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `new_session_exercise` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `new_session_exercise`(rsid INT, ueid INT, tgt SMALLINT, nt VARCHAR(32),oindex TINYINT) RETURNS int(11)
    DETERMINISTIC
BEGIN

#shift any display indexes that come after the requested display index of the newly added session exercise
UPDATE SessionExercise SET orderIndex = orderIndex + 1 WHERE routineSessionID = rsid AND orderIndex >= oindex;
#do the insert
INSERT INTO SessionExercise (routineSessionID, userExerciseID, target, note, orderIndex) VALUES (rsid,ueid,tgt,nt,oindex);
RETURN LAST_INSERT_ID();
END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `new_user_exercise` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `new_user_exercise`(uid INT, ename VARCHAR(30), etype ENUM('repetition','distance','duration'), defuom varchar(16), note varchar(32), display bool) RETURNS int(11)
    DETERMINISTIC
BEGIN

INSERT INTO UserExercise (userID, exerciseName, exerciseType, defaultUOM, note, display) VALUES (uid, ename,etype,defuom,note,display);
RETURN LAST_INSERT_ID();
END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `new_user_routine` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `new_user_routine`(uid INT, rname VARCHAR(32)) RETURNS int(10)
    DETERMINISTIC
BEGIN

INSERT INTO UserRoutine (userID, routineName) VALUES (uid, rname);
RETURN LAST_INSERT_ID();
END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `next_routine_session` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbpwin`*/ /*!50003 FUNCTION `next_routine_session`(urid INT(10)) RETURNS int(10)
    DETERMINISTIC
BEGIN

DECLARE threshold TINYINT;
DECLARE old_oi TINYINT;
DECLARE new_oi TINYINT;
DECLARE retval INT(10);
DECLARE last_rs INT(10);
DECLARE last_rs_recorded_at TIMESTAMP;

SELECT MAX(orderIndex) 
FROM RoutineSession 
WHERE userRoutineID = urid 
INTO @threshold;

SELECT MAX(recordedAt) 
FROM RoutineSessionData rsd 
LEFT JOIN RoutineSession rs 
ON (rsd.routineSessionID = rs.routineSessionID) 
WHERE rs.userRoutineID = urid
INTO @last_rs_recorded_at;

SELECT rsd.routineSessionID 
FROM RoutineSessionData rsd 
LEFT JOIN RoutineSession rs 
ON (rsd.routineSessionID = rs.routineSessionID) 
WHERE rs.userRoutineID = urid
AND rsd.recordedAt = @last_rs_recorded_at
INTO @last_rs;

IF @last_rs IS NULL THEN

  SELECT routineSessionID 
  FROM RoutineSession 
  WHERE userRoutineID = urid
  AND orderIndex = 0
  INTO @retval;
  
  RETURN @retval;
  
END IF; 

SELECT orderIndex 
FROM RoutineSession 
WHERE routineSessionID = @last_rs 
INTO @old_oi;

IF (@old_oi >= @threshold) THEN
    SELECT 0 
    INTO @new_oi;
ELSE
    SELECT @old_oi + 1
    INTO @new_oi;
END IF;

SELECT routineSessionID 
FROM RoutineSession 
WHERE orderIndex = @new_oi 
AND userRoutineID = urid 
INTO @retval;

RETURN @retval;

END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `uid_from_token` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `uid_from_token`(user_token varchar(32)) RETURNS int(11)
    DETERMINISTIC
BEGIN

DECLARE uid INT;
DECLARE token_expires BOOL;
DECLARE expired BOOL;


SELECT userID, expires, (TIMESTAMPDIFF(MINUTE,lastAccess,CURRENT_TIMESTAMP) >= 30) FROM UserToken WHERE token = user_token INTO uid,token_expires, expired;

IF (token_expires AND expired) THEN
RETURN null;
ELSE
UPDATE UserToken SET lastAccess = CURRENT_TIMESTAMP;
RETURN uid;
END IF;

END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP FUNCTION IF EXISTS `user_login` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 FUNCTION `user_login`(uname VARCHAR(255), passwd VARCHAR(255), stay_logged_in BOOL) RETURNS varchar(32)
    DETERMINISTIC
BEGIN

DECLARE login_result BOOL;
DECLARE uid INT;

SELECT (MD5(passwd) = password), userID FROM User WHERE userName = uname INTO login_result, uid;

IF (login_result) THEN
DELETE FROM UserToken WHERE userID = uid;
SET @token = MD5(RAND());
INSERT INTO UserToken (userID,token, expires) VALUES (uid, @token, stay_logged_in);
RETURN @token;
END IF;

RETURN NULL;

END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP PROCEDURE IF EXISTS `delete_routine_session` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 PROCEDURE `delete_routine_session`(rsid INT)
    DETERMINISTIC
BEGIN

#Declare our local variables that will hold the routine_id and display index of the to-be-deleted routine_session
DECLARE urid INT;
DECLARE oindex TINYINT;
#put values into our local vars
SELECT userRoutineID, orderIndex FROM RoutineSession WHERE routineSessionID = rsid LIMIT 1 INTO @urid, @oindex;

#delete the routine_session from the routine_session table
DELETE FROM RoutineSession WHERE routineSessionID = rsid LIMIT 1;

#shift any display indexes that come after the display index of the deleted session down
UPDATE RoutineSession SET orderIndex = orderIndex - 1 WHERE userRoutineID = @urid AND orderIndex >= @oindex;



END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
/*!50003 DROP PROCEDURE IF EXISTS `delete_session_exercise` */;;
/*!50003 SET SESSION SQL_MODE=""*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`brian`@`mbp`*/ /*!50003 PROCEDURE `delete_session_exercise`(seid INT(10))
    DETERMINISTIC
BEGIN

#Declare our local variables that will hold the routine_id and display index of the to-be-deleted routine_session
DECLARE rsid INT;
DECLARE oindex TINYINT;
#put values into our local vars
SELECT routineSessionID, orderIndex FROM SessionExercise WHERE sessionExerciseID = seid LIMIT 1 INTO @rsid, @oindex;

#delete the routine_session from the routine_session table
DELETE FROM SessionExercise WHERE sessionExerciseID = seid LIMIT 1;

#shift any display indexes that come after the display index of the deleted session down
UPDATE SessionExercise SET orderIndex = orderIndex - 1 WHERE routineSessionID = @rsid AND orderIndex >= @oindex;



END */;;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE*/;;
DELIMITER ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2007-05-05 20:19:05