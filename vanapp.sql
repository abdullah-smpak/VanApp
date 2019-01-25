-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 20, 2018 at 11:35 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vanapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `driver_reg`
--

CREATE TABLE IF NOT EXISTS `driver_reg` (
`id` int(100) NOT NULL,
  `van_no` varchar(100) NOT NULL,
  `make` varchar(100) NOT NULL,
  `dri_nam` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `mob_no` varchar(100) NOT NULL,
  `dri_address` varchar(100) NOT NULL,
  `school_nam` varchar(100) NOT NULL,
  `school_loc` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `dri_img` varchar(100) NOT NULL,
  `dri_route` varchar(100) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Dumping data for table `driver_reg`
--

INSERT INTO `driver_reg` (`id`, `van_no`, `make`, `dri_nam`, `model`, `mob_no`, `dri_address`, `school_nam`, `school_loc`, `password`, `dri_img`, `dri_route`) VALUES
(6, '4290', 'list 2', 'Munif', 'f3f3f', 'aa', 'wcwcwwc', 'list 2', 'list 2', 'aa', 'iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAIAAAAiOjnJAAAAA3NCSVQICAjb4U/gAAAgAElEQVR4\nnOy9eWxc13k2fs7dZt+Hw52', '1'),
(9, '1234', 'list 2', 'Rehman', 'f3f3f', 'dqd2', 'wcwcwwc', 'list 2', 'list 2', 'ac s,', 'drivers/726899.jpg', '1'),
(10, '1122', 'list 2', 'Noman', '2f3ge', 'fefe', 'scdhr', 'list 2', 'list 3', 's  d', 'drivers/179169.jpg', '1'),
(11, '2255', 'list 2', 'Faheem', 'xxc', 'xxx', 'xxx', 'list 2', 'list 2', 'cc', 'drivers/161683.jpg', '2'),
(12, '3366', 'list 1', 'Ahsan', 'ss', 'ss', 'sss', 'list 2', 'list 2', 'zz', 'drivers/636902.jpg', '5'),
(13, '2192', 'Toyota', 'Immad', '2005', '034529933554', 'Lyari', 'The Generation School', 'KDA', 'allah', 'drivers/550.jpg', '6'),
(14, '2121', 'Honda', 'Zuhaib', '2009', '0213456', 'nagan', 'Happy Palace School', 'Nazimabad Branch', 'aa', 'drivers/847168.jpg', ''),
(15, 'Ab212', 'Toyota', 'waqar', '2004', '0213', 'sss', 'Happy Palace School', 'Nazimabad Branch', 'aa', 'drivers/474671.jpg', ''),
(16, '', '', '', '', '', '', '', '', '', 'drivers/283173.jpg', ''),
(17, '', '', '', '', '', '', '', '', '', 'drivers/773865.jpg', ''),
(18, 'azd', 'Suzuki', 'mm', 'tvh', 'mmm', 'mm', 'Usman Public School', 'North Karachi Branch', 'mm', 'drivers/923340.jpg', ''),
(19, '12', 'Toyota', 'ddf', '99', '22', 'hhh', 'The Generation School', 'KDA Branch', 'qq', 'drivers/541871.jpg', ''),
(20, '', '', '', '', '', '', '', '', '', 'drivers/416443.jpg', ''),
(21, 'Ab4290', 'Honda', 'Abdullah', '2009', '03432101520', 'H.No 101,Shadman', 'The Generation School', 'KDA Branch', 'aa', 'drivers/690247.jpg', ''),
(22, '', 'Toyota', 'dggf', '2008', '+923352689828', 'sfv', 'Happy Palace School', '5 Start Branch', 'aa', 'drivers/966584.jpg', '');

-- --------------------------------------------------------

--
-- Table structure for table `drop_attandance`
--

CREATE TABLE IF NOT EXISTS `drop_attandance` (
`drop_id` int(100) NOT NULL,
  `date` varchar(100) DEFAULT NULL,
  `kid` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `van_no` varchar(100) DEFAULT NULL,
  `drop_time` varchar(100) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `drop_attandance`
--

INSERT INTO `drop_attandance` (`drop_id`, `date`, `kid`, `time`, `status`, `van_no`, `drop_time`) VALUES
(4, '29 / 10 / 2018 ', 'Waqas', '17:35:11', 'Present', '', NULL),
(5, '29 / 10 / 2018 ', 'Waqas', '17:54:35', 'Present', '1r2d', NULL),
(6, '29 / 10 / 2018 ', 'Waqas', '17:54:43', 'Present', '1r2d', NULL),
(7, '30 / 10 / 2018 ', 'Ali', '11:51:55', 'Present', '4290', NULL),
(8, '01 / 11 / 2018 ', 'John', '14:16:08', 'Present', '2192', NULL),
(9, '01 / 11 / 2018 ', 'Ali', '16:26:22', 'Present', '2192', NULL),
(10, '19 / 11 / 2018 ', 'John', '15:47:11', 'Present', '2192', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `parent_reg`
--

CREATE TABLE IF NOT EXISTS `parent_reg` (
`id` int(100) NOT NULL,
  `van_route` varchar(100) NOT NULL,
  `kid_nam` varchar(100) NOT NULL,
  `school_name` varchar(100) NOT NULL,
  `school_location` varchar(100) NOT NULL,
  `class` varchar(100) NOT NULL,
  `fee` varchar(100) NOT NULL,
  `mother_nam` varchar(100) NOT NULL,
  `mother_no` varchar(100) NOT NULL,
  `father_nam` varchar(100) NOT NULL,
  `father_no` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `pro_img` varchar(100) NOT NULL,
  `van_no` varchar(100) DEFAULT NULL,
  `token` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `parent_reg`
--

INSERT INTO `parent_reg` (`id`, `van_route`, `kid_nam`, `school_name`, `school_location`, `class`, `fee`, `mother_nam`, `mother_no`, `father_nam`, `father_no`, `pass`, `pro_img`, `van_no`, `token`) VALUES
(4, 'list 2', 'Ali', 'list 2', 'list 1', 'list 1', 'zxc', 'sdc', 'zxc', 'ssd', 'xxx', 'asd', 'parents/578064.jpg', '4290', NULL),
(5, 'BufferZone', 'Zubair', 'list 2', 'list 1', 'list 1', 'zxc', 'sdc', 'zxc', 'ssd', 'xxx', 'asd', 'parents/613892.jpg', '4290', NULL),
(7, 'BufferZone', 'Waqas', 'The Generation School', 'KDA Branch', 'Class 1', '12', 'asd', '123', 'dsa', '321', 'asd', 'parents/891602.jpg', '2192', 'e8VLEiheoh8:APA91bG9wKK4f0yZcXTzS_N80ztU1L1odaHMh_WMBMokhzpy5x7DRqM0B3fcbtAtpxMXk4tH9bcA4H31JQ-vSb0HtCjQ17q01OUn3hWis9l5W6o5pwp0Y66fZE-50c1ItpgR3KnWBAKq'),
(12, 'BufferZone', 'Waqar', 'Happy Palace School', 'Nazimabad Branch', 'KG 1', '4500', ' erw', '123', 'sdf', '321', 'aa', 'parents/759919.jpg', '2192', 'e8VLEiheoh8:APA91bG9wKK4f0yZcXTzS_N80ztU1L1odaHMh_WMBMokhzpy5x7DRqM0B3fcbtAtpxMXk4tH9bcA4H31JQ-vSb0HtCjQ17q01OUn3hWis9l5W6o5pwp0Y66fZE-50c1ItpgR3KnWBAKq'),
(13, 'North Karachi', 'John', 'The Generation School', 'KDA Branch', 'Class 2', '4500', 'asd', '111', 'asd', '222', 'aa', 'parents/254395.jpg', '2192', 'flfP3bC2jOQ:APA91bHO5OATLwQ3KyBAcDlz9nIuA58ntQjPKUZscIiKM3hde8ZPwBqwQyHqYfMV1LZdgty6qFxUv6g68zivk4vJhez30_VNDY68mqbaPNb6y3AlAmB0ibyOPINGLF0YQzMZH2I9J7gE'),
(14, 'North Karachi', 'Najaf', 'Happy Palace School', 'KDA Branch', 'Class 2', '8700', 'chill', '212', 'aa', 'sdf', 'aa', 'parents/393219.jpg', '4290', 'flfP3bC2jOQ:APA91bHO5OATLwQ3KyBAcDlz9nIuA58ntQjPKUZscIiKM3hde8ZPwBqwQyHqYfMV1LZdgty6qFxUv6g68zivk4vJhez30_VNDY68mqbaPNb6y3AlAmB0ibyOPINGLF0YQzMZH2I9J7gE'),
(15, '', '', '', '', '', '', '', '', '', '', '', 'parents/813477.jpg', '', ''),
(16, 'BufferZone', 'Ali', 'The Generation School', 'KDA Branch', 'Class 3', '1200', 'Maham', '090', 'Abaa', '080', 'aa', 'parents/156373.jpg', '2192', 'flfP3bC2jOQ:APA91bHO5OATLwQ3KyBAcDlz9nIuA58ntQjPKUZscIiKM3hde8ZPwBqwQyHqYfMV1LZdgty6qFxUv6g68zivk4vJhez30_VNDY68mqbaPNb6y3AlAmB0ibyOPINGLF0YQzMZH2I9J7gE'),
(17, 'North Karachi', 'xvh', 'Happy Palace School', 'Nazimabad Branch', 'Class 10', '5600', 'ami', '11', 'abu', '22', 'aa', 'parents/225678.jpg', '4290', 'flfP3bC2jOQ:APA91bHO5OATLwQ3KyBAcDlz9nIuA58ntQjPKUZscIiKM3hde8ZPwBqwQyHqYfMV1LZdgty6qFxUv6g68zivk4vJhez30_VNDY68mqbaPNb6y3AlAmB0ibyOPINGLF0YQzMZH2I9J7gE'),
(20, '', '', '', '', '', '', '', '03432933953', '', '', '', 'parents/671174.jpg', '', ''),
(21, '', '', '', '', '', '', '', '03432933953', '', '', '', 'parents/819855.jpg', '', ''),
(22, '', '', '', '', '', '', '', '03432933953', '', '', '', 'parents/809723.jpg', '', ''),
(23, '', '', '', '', '', '', '', '', '', '', '', 'parents/274811.jpg', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `pick_attendance`
--

CREATE TABLE IF NOT EXISTS `pick_attendance` (
`pick_id` int(100) NOT NULL,
  `date` varchar(100) DEFAULT NULL,
  `kid` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `van_no` varchar(100) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=46 ;

--
-- Dumping data for table `pick_attendance`
--

INSERT INTO `pick_attendance` (`pick_id`, `date`, `kid`, `time`, `status`, `van_no`) VALUES
(27, '30 / 10 / 2018 ', 'Faisal', '11:43:29', 'Present', '1r2d'),
(28, '30 / 10 / 2018 ', 'Waqas', '11:45:33', 'Absent', '1r2d'),
(29, '30 / 10 / 2018 ', 'Zubair', '11:52:28', 'Present', '4290'),
(30, '31 / 10 / 2018 ', 'John', '00:47:44', 'Present', '2192'),
(31, '31 / 10 / 2018 ', 'Waqar', '00:48:02', 'Absent', '2192'),
(32, '31 / 10 / 2018 ', 'John', '13:06:11', 'Absent', '2192'),
(33, '01 / 11 / 2018 ', 'Waqar', '00:36:20', 'Absent', '2192'),
(34, '01 / 11 / 2018 ', 'Waqar', '00:37:57', 'Absent', '2192'),
(35, '01 / 11 / 2018 ', 'John', '00:56:56', 'Present', '2192'),
(36, '01 / 11 / 2018 ', 'Waqar', '14:15:20', 'Present', '2192'),
(37, '01 / 11 / 2018 ', 'John', '14:15:27', 'Present', '2192'),
(38, '01 / 11 / 2018 ', 'John', '16:24:44', 'Present', '2192'),
(39, '01 / 11 / 2018 ', 'Waqar', '16:24:51', 'Present', '2192'),
(40, '01 / 11 / 2018 ', 'Waqar', '16:25:57', 'Present', '2192'),
(41, '01 / 11 / 2018 ', 'Waqas', '16:26:02', 'Absent', '2192'),
(42, '19 / 11 / 2018 ', 'John', '12:38:15', 'Absent', '2192'),
(43, '19 / 11 / 2018 ', 'John', '12:38:19', 'Absent', '2192'),
(44, '19 / 11 / 2018 ', 'Waqar', '15:46:33', 'Present', '2192'),
(45, '19 / 11 / 2018 ', 'Ali', '15:46:42', 'Absent', '2192');

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
`route_id` int(100) NOT NULL,
  `routes_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`route_id`, `routes_name`) VALUES
(1, 'North Karachi'),
(2, 'North Nazimabad'),
(3, 'Nazimabad'),
(5, 'Gulshan'),
(6, 'BufferZone');

-- --------------------------------------------------------

--
-- Table structure for table `schools`
--

CREATE TABLE IF NOT EXISTS `schools` (
`school_id` int(100) NOT NULL,
  `school_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `schools`
--

INSERT INTO `schools` (`school_id`, `school_name`) VALUES
(1, 'Usman Public School'),
(2, 'Happy Palace School'),
(3, 'The Generation School');

-- --------------------------------------------------------

--
-- Table structure for table `school_branches`
--

CREATE TABLE IF NOT EXISTS `school_branches` (
`branch_id` int(100) NOT NULL,
  `school_id` int(100) DEFAULT NULL,
  `branch` varchar(100) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `school_branches`
--

INSERT INTO `school_branches` (`branch_id`, `school_id`, `branch`) VALUES
(1, 1, 'North Karachi Branch'),
(2, 1, 'Gulshan Branch'),
(3, 2, '5 Start Branch'),
(4, 2, 'KDA Branch'),
(5, 2, 'Nazimabad Branch'),
(6, 3, 'KDA Branch');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `driver_reg`
--
ALTER TABLE `driver_reg`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `drop_attandance`
--
ALTER TABLE `drop_attandance`
 ADD PRIMARY KEY (`drop_id`);

--
-- Indexes for table `parent_reg`
--
ALTER TABLE `parent_reg`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pick_attendance`
--
ALTER TABLE `pick_attendance`
 ADD PRIMARY KEY (`pick_id`);

--
-- Indexes for table `routes`
--
ALTER TABLE `routes`
 ADD PRIMARY KEY (`route_id`);

--
-- Indexes for table `schools`
--
ALTER TABLE `schools`
 ADD PRIMARY KEY (`school_id`);

--
-- Indexes for table `school_branches`
--
ALTER TABLE `school_branches`
 ADD PRIMARY KEY (`branch_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `driver_reg`
--
ALTER TABLE `driver_reg`
MODIFY `id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `drop_attandance`
--
ALTER TABLE `drop_attandance`
MODIFY `drop_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `parent_reg`
--
ALTER TABLE `parent_reg`
MODIFY `id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `pick_attendance`
--
ALTER TABLE `pick_attendance`
MODIFY `pick_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT for table `routes`
--
ALTER TABLE `routes`
MODIFY `route_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `schools`
--
ALTER TABLE `schools`
MODIFY `school_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `school_branches`
--
ALTER TABLE `school_branches`
MODIFY `branch_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
