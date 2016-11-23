-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 23-Nov-2016 às 02:36
-- Versão do servidor: 10.1.16-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sys_sugestao`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aso_user_categoria`
--

CREATE TABLE `aso_user_categoria` (
  `id_aso` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `aso_user_categoria`
--

INSERT INTO `aso_user_categoria` (`id_aso`, `id_user`, `id_categoria`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_categoria`
--

CREATE TABLE `cad_categoria` (
  `id_categoria` int(11) NOT NULL,
  `nome_categoria` varchar(45) NOT NULL,
  `datacad_categoria` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cad_categoria`
--

INSERT INTO `cad_categoria` (`id_categoria`, `nome_categoria`, `datacad_categoria`) VALUES
(1, 'Estrutura', '2016-11-15'),
(2, 'Ensino', '2016-11-17');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_comentarios`
--

CREATE TABLE `cad_comentarios` (
  `id_comentario` int(11) NOT NULL,
  `txt_comentario` text NOT NULL,
  `id_sugestao` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  `cad_comentarioscol` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_sugestao`
--

CREATE TABLE `cad_sugestao` (
  `id_sugestao` int(11) NOT NULL,
  `datacad_sugestao` date NOT NULL,
  `dataupdate_sugestao` date DEFAULT NULL,
  `titulo_sugestao` varchar(150) NOT NULL,
  `txt_sugestao` text NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cad_sugestao`
--

INSERT INTO `cad_sugestao` (`id_sugestao`, `datacad_sugestao`, `dataupdate_sugestao`, `titulo_sugestao`, `txt_sugestao`, `id_user`, `id_categoria`, `id_status`) VALUES
(1, '2016-11-15', NULL, 'Meu Primeiro UPDATE', 'Essa é a primeira sugestão do sistema de Sugestões e Devolutivas', 1, 1, 1),
(2, '2016-11-15', NULL, 'Hello Word', 'Essa é a primeira sugestão do sistema de Sugestões e Devolutivas', 1, 2, 1),
(15, '2016-11-19', NULL, 'Ar condicionado', 'Colocar Ar condicionado nas salas', 1, 1, 2),
(16, '2016-11-21', NULL, 'Aula Legal', 'as auals do professor rodrigo vaz são bem interativas.', 2, 2, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_usuario`
--

CREATE TABLE `cad_usuario` (
  `id_user` int(11) NOT NULL,
  `name_user` varchar(45) NOT NULL,
  `email_user` varchar(45) NOT NULL,
  `ra_user` varchar(45) NOT NULL,
  `senha_user` varchar(45) NOT NULL,
  `datanasc_user` date NOT NULL,
  `tipo_user` varchar(45) NOT NULL,
  `datacad_user` date NOT NULL,
  `sexo_user` varchar(54) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cad_usuario`
--

INSERT INTO `cad_usuario` (`id_user`, `name_user`, `email_user`, `ra_user`, `senha_user`, `datanasc_user`, `tipo_user`, `datacad_user`, `sexo_user`) VALUES
(1, 'Alex Oliveira', 'admin', '81616584', 'admin', '1996-09-30', 'Avaliador', '2016-11-15', 'Masculino'),
(2, 'David Souza', 'david.sa@outlook.com', '201314824', 'admin', '1995-04-24', 'Colaborador', '2016-11-21', 'Masculino');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tab_status`
--

CREATE TABLE `tab_status` (
  `id_status` int(11) NOT NULL,
  `nome_status` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tab_status`
--

INSERT INTO `tab_status` (`id_status`, `nome_status`) VALUES
(1, 'Ativo'),
(2, 'Pendente'),
(3, 'Desativado');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aso_user_categoria`
--
ALTER TABLE `aso_user_categoria`
  ADD PRIMARY KEY (`id_aso`),
  ADD KEY `fk_aso_user_categoria_cad_usuario_idx` (`id_user`),
  ADD KEY `fk_aso_user_categoria_cad_categoria1_idx` (`id_categoria`);

--
-- Indexes for table `cad_categoria`
--
ALTER TABLE `cad_categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indexes for table `cad_comentarios`
--
ALTER TABLE `cad_comentarios`
  ADD PRIMARY KEY (`id_comentario`,`cad_comentarioscol`),
  ADD KEY `fk_cad_comentarios_cad_sugestao1_idx` (`id_sugestao`),
  ADD KEY `fk_cad_comentarios_cad_usuario1_idx` (`id_user`),
  ADD KEY `fk_cad_comentarios_tab_status1_idx` (`id_status`);

--
-- Indexes for table `cad_sugestao`
--
ALTER TABLE `cad_sugestao`
  ADD PRIMARY KEY (`id_sugestao`),
  ADD KEY `fk_cad_sugestao_cad_usuario1_idx` (`id_user`),
  ADD KEY `fk_cad_sugestao_cad_categoria1_idx` (`id_categoria`),
  ADD KEY `fk_cad_sugestao_tab_status1_idx` (`id_status`);

--
-- Indexes for table `cad_usuario`
--
ALTER TABLE `cad_usuario`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email_user_UNIQUE` (`email_user`),
  ADD UNIQUE KEY `ra_user_UNIQUE` (`ra_user`);

--
-- Indexes for table `tab_status`
--
ALTER TABLE `tab_status`
  ADD PRIMARY KEY (`id_status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `aso_user_categoria`
--
ALTER TABLE `aso_user_categoria`
  MODIFY `id_aso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `cad_categoria`
--
ALTER TABLE `cad_categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `cad_comentarios`
--
ALTER TABLE `cad_comentarios`
  MODIFY `id_comentario` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `cad_sugestao`
--
ALTER TABLE `cad_sugestao`
  MODIFY `id_sugestao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `cad_usuario`
--
ALTER TABLE `cad_usuario`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tab_status`
--
ALTER TABLE `tab_status`
  MODIFY `id_status` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `aso_user_categoria`
--
ALTER TABLE `aso_user_categoria`
  ADD CONSTRAINT `fk_aso_user_categoria_cad_categoria1` FOREIGN KEY (`id_categoria`) REFERENCES `cad_categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_aso_user_categoria_cad_usuario` FOREIGN KEY (`id_user`) REFERENCES `cad_usuario` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `cad_comentarios`
--
ALTER TABLE `cad_comentarios`
  ADD CONSTRAINT `fk_cad_comentarios_cad_sugestao1` FOREIGN KEY (`id_sugestao`) REFERENCES `cad_sugestao` (`id_sugestao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cad_comentarios_cad_usuario1` FOREIGN KEY (`id_user`) REFERENCES `cad_usuario` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cad_comentarios_tab_status1` FOREIGN KEY (`id_status`) REFERENCES `tab_status` (`id_status`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `cad_sugestao`
--
ALTER TABLE `cad_sugestao`
  ADD CONSTRAINT `fk_cad_sugestao_cad_categoria1` FOREIGN KEY (`id_categoria`) REFERENCES `cad_categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cad_sugestao_cad_usuario1` FOREIGN KEY (`id_user`) REFERENCES `cad_usuario` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cad_sugestao_tab_status1` FOREIGN KEY (`id_status`) REFERENCES `tab_status` (`id_status`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
