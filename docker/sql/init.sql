GRANT ALL ON *.* TO 'master'@'%';
GRANT USAGE ON *.* TO 'master'@'%';

/*!40000 DROP DATABASE IF EXISTS `money`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `money` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `money`;

FLUSH PRIVILEGES;