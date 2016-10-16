-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2016 at 02:54 PM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `taxipooling`
--
CREATE DATABASE IF NOT EXISTS `taxipooling` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `taxipooling`;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `feedbackID` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(50) NOT NULL,
  `fromID` varchar(30) NOT NULL,
  `toID` varchar(30) NOT NULL,
  `rate` int(11) NOT NULL,
  PRIMARY KEY (`feedbackID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `messageID` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `fromID` varchar(30) NOT NULL,
  `toID` varchar(30) NOT NULL,
  `readBit` tinyint(1) NOT NULL DEFAULT '0',
  `dateTime` datetime NOT NULL,
  PRIMARY KEY (`messageID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notificationID` int(11) NOT NULL AUTO_INCREMENT,
  `notification` varchar(50) NOT NULL,
  `fromID` varchar(30) NOT NULL,
  `toID` varchar(30) NOT NULL,
  `readBit` tinyint(1) NOT NULL DEFAULT '0',
  `dateTime` datetime NOT NULL,
  PRIMARY KEY (`notificationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `poolingcreated`
--

CREATE TABLE IF NOT EXISTS `poolingcreated` (
  `poolingID` int(11) NOT NULL AUTO_INCREMENT,
  `createrID` varchar(30) NOT NULL,
  `acceptorID` varchar(30) NOT NULL,
  `dateTime` datetime NOT NULL,
  PRIMARY KEY (`poolingID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userID` varchar(30) NOT NULL,
  `locLat` varchar(256) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` text NOT NULL,
  `userType` enum('d','p') NOT NULL DEFAULT 'p',
  `taxiNo` varchar(10) NOT NULL,
  `location` varchar(30) NOT NULL,
  `blockBit` tinyint(1) NOT NULL DEFAULT '0',
  `totalRating` int(11) NOT NULL DEFAULT '0',
  `poolingCreated` int(11) NOT NULL DEFAULT '0',
  `locLng` varchar(256) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `locLat`, `username`, `password`, `userType`, `taxiNo`, `location`, `blockBit`, `totalRating`, `poolingCreated`, `locLng`) VALUES
('ali@ali.com', '0.0', 'ali', '1234567', 'p', '', 'darwin', 0, 0, 0, '0.0'),
('alig', '0.0', 'shan', '123456', 'p', '', '', 0, 0, 0, '0.0'),
('dummy@gm.com', '-12.3810818', 'suraj', '12Â³45678', 'p', '', 'darwin', 0, 0, 0, '130.8692875'),
('shahab.rana@gmail.com ', '0.0', 'shahab', '123r5678', 'p', '', 'darwin', 0, 0, 0, '0.0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
