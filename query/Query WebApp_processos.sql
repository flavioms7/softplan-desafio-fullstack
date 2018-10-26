-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.5.42 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for webapp_processos
CREATE DATABASE IF NOT EXISTS `webapp_processos` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `webapp_processos`;

-- Dumping structure for table webapp_processos.grupo
CREATE TABLE IF NOT EXISTS `grupo` (
  `id_grupo` int(11) NOT NULL AUTO_INCREMENT,
  `nm_grupo` varchar(50) NOT NULL,
  `ds_grupo` varchar(200) NOT NULL,
  PRIMARY KEY (`id_grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='Classifica os grupos de usuários.';

-- Dumping data for table webapp_processos.grupo: ~3 rows (approximately)
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` (`id_grupo`, `nm_grupo`, `ds_grupo`) VALUES
	(1, 'ADMINISTRADOR', 'Visão de Administrador'),
	(2, 'TRIADOR', 'Visão de Usuario Triador'),
	(3, 'FINALIZADOR', 'Visão de Usuario Finalizador');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;

-- Dumping structure for table webapp_processos.permissao
CREATE TABLE IF NOT EXISTS `permissao` (
  `id_permissao` int(11) NOT NULL AUTO_INCREMENT,
  `nm_permissao` varchar(50) NOT NULL,
  `ds_permissao` varchar(100) NOT NULL,
  PRIMARY KEY (`id_permissao`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COMMENT='Possui descrição de permissões de acesso.';

-- Dumping data for table webapp_processos.permissao: ~6 rows (approximately)
/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` (`id_permissao`, `nm_permissao`, `ds_permissao`) VALUES
	(1, 'MANTER_USUARIOS', 'Mantem Usuarios'),
	(2, 'INCLUIR_PROCESSOS', 'Inclusao de Processos'),
	(3, 'VIZUALIZAR_PROCESSOS', 'Vizualização de Processos'),
	(4, 'VINCULAR_PARECER', 'Vincula Usuário ao Parecer do processo'),
	(5, 'VIZUALIZAR_PROCESSOS_PENDENTES', 'Vizualiza processos Pendentes'),
	(6, 'INCLUI_PARECER', 'Inclui parecer do processo');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;

-- Dumping structure for table webapp_processos.permissao_grupo
CREATE TABLE IF NOT EXISTS `permissao_grupo` (
  `id_permissao` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  PRIMARY KEY (`id_permissao`,`id_grupo`),
  KEY `FK__grupo` (`id_grupo`,`id_permissao`),
  CONSTRAINT `FK__grupo` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__permissao` FOREIGN KEY (`id_permissao`) REFERENCES `permissao` (`id_permissao`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela de relacionamento entre Permissão e Grupo';

-- Dumping data for table webapp_processos.permissao_grupo: ~3 rows (approximately)
/*!40000 ALTER TABLE `permissao_grupo` DISABLE KEYS */;
INSERT INTO `permissao_grupo` (`id_permissao`, `id_grupo`) VALUES
	(1, 1),
	(2, 2),
	(3, 2),
	(4, 2),
	(5, 3),
	(6, 3);
/*!40000 ALTER TABLE `permissao_grupo` ENABLE KEYS */;

-- Dumping structure for table webapp_processos.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nm_usuario` varchar(100) NOT NULL,
  `login_usuario` varchar(50) NOT NULL,
  `senha_usuario` varchar(300) NOT NULL,
  `status_usuario` int(1) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='Contém informações de usuário';

-- Dumping data for table webapp_processos.usuario: ~1 rows (approximately)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id_usuario`, `nm_usuario`, `login_usuario`, `senha_usuario`, `status_usuario`) VALUES
	(1, 'Flavio Miranda', 'flavio', '$2a$10$YYe9VtFGZoWvrNSZNV/AeuVSTOMQLxcGia4IQEl/yVaxrfAnPDcuO', 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Dumping structure for table webapp_processos.usuario_grupo
CREATE TABLE IF NOT EXISTS `usuario_grupo` (
  `id_usuario` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_grupo`),
  KEY `FK_usuario_grupo_grupo` (`id_grupo`),
  CONSTRAINT `FK_usuario_grupo_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_usuario_grupo_grupo` FOREIGN KEY (`id_grupo`) REFERENCES `grupo` (`id_grupo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Possúi relacionamento entre usuários e grupos.';

-- Dumping data for table webapp_processos.usuario_grupo: ~1 rows (approximately)
/*!40000 ALTER TABLE `usuario_grupo` DISABLE KEYS */;
INSERT INTO `usuario_grupo` (`id_usuario`, `id_grupo`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `usuario_grupo` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
