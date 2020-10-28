-- phpMyAdmin SQL Dump
-- version 4.4.15.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2020-10-24 19:14:04
-- 服务器版本： 8.0.12
-- PHP Version: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `game_1`
--

-- --------------------------------------------------------

--
-- 表的结构 `g_user`
--

CREATE TABLE IF NOT EXISTS `g_user` (
  `id` int(11) NOT NULL,
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `is_state` int(1) NOT NULL DEFAULT '0' COMMENT '0允许登录',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `create_time` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='帐号表';

--
-- 转存表中的数据 `g_user`
--

INSERT INTO `g_user` (`id`, `user`, `password`, `is_state`, `name`, `id_card`, `create_time`) VALUES
(1, '123', '123', 0, '', NULL, 1602823858),
(2, '18516721096', '123456', 0, '', '421124199104076015', 1602823922),
(3, '18516721097', '123456', 0, '', NULL, 1602824014),
(4, '1234', '1234', 0, '', NULL, 1602824642);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `g_user`
--
ALTER TABLE `g_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `g_user`
--
ALTER TABLE `g_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
