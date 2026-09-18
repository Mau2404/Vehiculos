-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 18, 2026 at 05:40 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_vehiculos_poo2`
--

-- --------------------------------------------------------

--
-- Table structure for table `documentos`
--

CREATE TABLE `documentos` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo_vehiculo_aplica` varchar(5) NOT NULL,
  `obligatorio_segun_tipo` varchar(5) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `persona`
--

CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL,
  `identificacion` varchar(20) NOT NULL,
  `tipo_identificacion` varchar(5) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `tipo_persona` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `login` varchar(255) NOT NULL,
  `persona` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `apikey` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vehiculos`
--

CREATE TABLE `vehiculos` (
  `id` bigint(20) NOT NULL,
  `placa` varchar(6) NOT NULL COMMENT 'Placa del vehículo (ej: ABC123)',
  `tipo_vehiculo` varchar(20) NOT NULL,
  `tipo_servicio` varchar(20) NOT NULL,
  `tipo_combustible` varchar(20) NOT NULL,
  `capacidad_pasajeros` int(11) NOT NULL COMMENT 'Número de pasajeros',
  `color` varchar(7) NOT NULL COMMENT 'Color en formato hexadecimal o texto',
  `modelo` int(11) NOT NULL COMMENT 'Año del modelo',
  `marca` varchar(50) NOT NULL COMMENT 'Marca del vehículo',
  `linea` varchar(50) NOT NULL COMMENT 'Línea o modelo específico'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vehiculo_documentos`
--

CREATE TABLE `vehiculo_documentos` (
  `id` bigint(20) NOT NULL,
  `vehiculo_id` bigint(20) NOT NULL COMMENT 'ID del vehículo',
  `documento_id` bigint(20) NOT NULL COMMENT 'ID del documento',
  `fecha_expedicion` date NOT NULL COMMENT 'Fecha de expedición del documento',
  `fecha_vencimiento` date NOT NULL COMMENT 'Fecha de vencimiento del documento',
  `estado` varchar(20) NOT NULL,
  `archivo_pdf` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vehiculo_persona`
--

CREATE TABLE `vehiculo_persona` (
  `id` bigint(20) NOT NULL,
  `vehiculo_id` bigint(20) NOT NULL,
  `persona_id` bigint(20) NOT NULL,
  `fecha_asociacion` date NOT NULL,
  `estado_conductor` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `documentos`
--
ALTER TABLE `documentos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`);

--
-- Indexes for table `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `identificacion` (`identificacion`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`login`,`persona`),
  ADD KEY `usuario_persona_FK` (`persona`);

--
-- Indexes for table `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `placa` (`placa`);

--
-- Indexes for table `vehiculo_documentos`
--
ALTER TABLE `vehiculo_documentos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_veh_doc_vehiculo` (`vehiculo_id`),
  ADD KEY `fk_veh_doc_documento` (`documento_id`);

--
-- Indexes for table `vehiculo_persona`
--
ALTER TABLE `vehiculo_persona`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_veh_per_vehiculo` (`vehiculo_id`),
  ADD KEY `fk_veh_per_persona` (`persona_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `documentos`
--
ALTER TABLE `documentos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `persona`
--
ALTER TABLE `persona`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vehiculos`
--
ALTER TABLE `vehiculos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `vehiculo_documentos`
--
ALTER TABLE `vehiculo_documentos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `vehiculo_persona`
--
ALTER TABLE `vehiculo_persona`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_persona_FK` FOREIGN KEY (`persona`) REFERENCES `persona` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `vehiculo_documentos`
--
ALTER TABLE `vehiculo_documentos`
  ADD CONSTRAINT `fk_veh_doc_documento` FOREIGN KEY (`documento_id`) REFERENCES `documentos` (`id`),
  ADD CONSTRAINT `fk_veh_doc_vehiculo` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `vehiculo_persona`
--
ALTER TABLE `vehiculo_persona`
  ADD CONSTRAINT `fk_veh_per_persona` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_veh_per_vehiculo` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
