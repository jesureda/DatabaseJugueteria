-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: jugueteria
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) COLLATE utf8_spanish2_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `dni` varchar(9) COLLATE utf8_spanish2_ci NOT NULL,
  `telefono` int(9) DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `idCliente` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Miguel','Escudero','37466475k',635876598),(2,'Marta','Díaz','27384905w',656887778),(3,'Pablo','Merino','25748508w',611293854),(4,'Ismael','Peralta','18279406z',600887788),(5,'Israel','Alcalá','49879406t',712887788),(6,'Paula','Baena','43339406h',612884444),(7,'Jesús','Balón','10010011c',600100100),(8,'Iñigo','Errejón','22234445k',609589814);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleados` (
  `idEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) COLLATE utf8_spanish2_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `dni` varchar(9) COLLATE utf8_spanish2_ci NOT NULL,
  `telefono` int(9) DEFAULT NULL,
  `turno` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`idEmpleado`),
  UNIQUE KEY `idEmpleado` (`idEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'Alfredo','Ferrete','37466475k',635876598,NULL),(2,'Atanasio','Cantero','21002938a',610290908,'tarde'),(3,'Irene','Zamora','25748508w',611293854,'mañana'),(4,'Elena','Cabezas','18279096z',600887788,NULL);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juguetes`
--

DROP TABLE IF EXISTS `juguetes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `juguetes` (
  `idJuguete` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) COLLATE utf8_spanish2_ci NOT NULL,
  `descripcion` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `precio` decimal(6,2) NOT NULL,
  `cantidad` int(3) NOT NULL,
  `edadRecomendada` varchar(5) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `idProveedorFK` int(11) DEFAULT NULL,
  PRIMARY KEY (`idJuguete`),
  UNIQUE KEY `idJuguete` (`idJuguete`),
  KEY `idProveedorFK` (`idProveedorFK`),
  CONSTRAINT `juguetes_ibfk_1` FOREIGN KEY (`idProveedorFK`) REFERENCES `proveedores` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juguetes`
--

LOCK TABLES `juguetes` WRITE;
/*!40000 ALTER TABLE `juguetes` DISABLE KEYS */;
INSERT INTO `juguetes` VALUES (1,'Trampas ocultas de Spiderman','Juego infantil con accesorios',30.00,15,'+7a',1),(2,'Granja de Peppa Pig','Juego infantil',20.00,20,'+3a',1),(3,'Halcón Milenario Lego StarWars','Juego construcciones',50.00,5,'+11a',4),(4,'T-Rex a piezas motorizado','Juego construcciones',40.00,10,'+11a',6),(5,'Heladería clandestina de Play-Doh','Juego de manualidades',60.00,5,'+7a',3),(6,'Batcueva Lego','Juego construcciones',80.00,15,'+7a',4),(7,'Vehículo Monster Jam','Coche radiocontrol',35.00,8,'+11a',5),(8,'Pista tiburón devorador','Pista carreras',45.00,5,'+7a',5),(9,'Transformers Optimus Prime','Figura de acción convertible',40.00,10,'+7a',2),(10,'Nerf Elite','Arma de juguete',70.00,10,'+11a',2);
/*!40000 ALTER TABLE `juguetes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidos` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `urgente` varchar(2) COLLATE utf8_spanish2_ci NOT NULL,
  `idClienteFK` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPedido`),
  UNIQUE KEY `idPedido` (`idPedido`),
  KEY `idClienteFK` (`idClienteFK`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`idClienteFK`) REFERENCES `clientes` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,'2016-12-10','no',1),(2,'2016-12-20','si',2),(3,'2017-01-03','si',3),(4,'2017-01-03','si',4),(5,'2017-01-11','no',5),(6,'2017-01-13','no',6),(7,'2017-01-13','no',4),(8,'2017-02-01','no',7),(9,'2017-02-11','no',8);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidosjuguetes`
--

DROP TABLE IF EXISTS `pedidosjuguetes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidosjuguetes` (
  `idPedidoFK` int(11) NOT NULL,
  `idJugueteFK` int(11) NOT NULL,
  PRIMARY KEY (`idPedidoFK`,`idJugueteFK`),
  KEY `idJugueteFK` (`idJugueteFK`),
  CONSTRAINT `pedidosjuguetes_ibfk_1` FOREIGN KEY (`idPedidoFK`) REFERENCES `pedidos` (`idPedido`),
  CONSTRAINT `pedidosjuguetes_ibfk_2` FOREIGN KEY (`idJugueteFK`) REFERENCES `juguetes` (`idJuguete`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidosjuguetes`
--

LOCK TABLES `pedidosjuguetes` WRITE;
/*!40000 ALTER TABLE `pedidosjuguetes` DISABLE KEYS */;
INSERT INTO `pedidosjuguetes` VALUES (1,1),(2,2),(1,3),(3,4),(4,4),(8,5),(7,6),(3,7),(5,8),(6,9),(3,10),(9,10);
/*!40000 ALTER TABLE `pedidosjuguetes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedores` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nEmpresa` varchar(80) COLLATE utf8_spanish2_ci NOT NULL,
  `direccion` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `cif` varchar(9) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`idProveedor`),
  UNIQUE KEY `idProveedor` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'Mattel S.A','Calle Espiocha 17 (Madrid)',919811635,'H23344453'),(2,'Hasbro Inc.','Calle Arteta 87 (Bilbao)',942223635,'R87888979'),(3,'Play Doh Inc.','Calle Argantonio 24 (Sevilla)',955874635,'I66554478'),(4,'Lego','Rambla Nova 63 (Tarragona)',977898635,'F77646483'),(5,'Hot Wheels','Avda. Ramón y Cajal(Badajoz)',900098635,'J71116483'),(6,'K-nex','Calle Johann Strauss 7 (Alicante)',960074635,'B56644453');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `idVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fechaEmision` date NOT NULL,
  `importe` decimal(6,2) NOT NULL,
  `idEmpleadoFK` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVenta`),
  UNIQUE KEY `idVenta` (`idVenta`),
  KEY `idEmpleadoFK` (`idEmpleadoFK`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`idEmpleadoFK`) REFERENCES `empleados` (`idEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,'2016-12-20',80.00,2),(2,'2016-12-22',40.00,4),(3,'2016-12-24',20.00,4),(4,'2016-01-02',50.00,3),(5,'2016-01-04',60.00,1),(6,'2016-01-05',75.00,4),(7,'2016-01-05',40.00,1),(8,'2016-01-16',45.00,3),(9,'2016-01-20',40.00,3),(10,'2016-01-21',80.00,2),(11,'2016-02-10',60.00,3),(12,'2016-02-20',70.00,2);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventasjuguetes`
--

DROP TABLE IF EXISTS `ventasjuguetes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventasjuguetes` (
  `idVentaFK` int(11) NOT NULL,
  `idJugueteFK` int(11) NOT NULL,
  PRIMARY KEY (`idVentaFK`,`idJugueteFK`),
  KEY `idJugueteFK` (`idJugueteFK`),
  CONSTRAINT `ventasjuguetes_ibfk_1` FOREIGN KEY (`idVentaFK`) REFERENCES `ventas` (`idVenta`),
  CONSTRAINT `ventasjuguetes_ibfk_2` FOREIGN KEY (`idJugueteFK`) REFERENCES `juguetes` (`idJuguete`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventasjuguetes`
--

LOCK TABLES `ventasjuguetes` WRITE;
/*!40000 ALTER TABLE `ventasjuguetes` DISABLE KEYS */;
INSERT INTO `ventasjuguetes` VALUES (1,1),(4,1),(3,2),(4,2),(1,3),(6,4),(7,4),(5,5),(11,5),(10,6),(6,7),(8,8),(2,9),(9,9),(6,10),(12,10);
/*!40000 ALTER TABLE `ventasjuguetes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-03 14:23:01
