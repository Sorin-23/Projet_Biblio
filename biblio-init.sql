-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : mar. 25 nov. 2025 à 15:25
-- Version du serveur : 8.0.40
-- Version de PHP : 8.3.14


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bibliotheque`
--
CREATE DATABASE IF NOT EXISTS projet_biblio;
USE projet_biblio;

-- --------------------------------------------------------

--
-- Structure de la table `auteur`
--

CREATE TABLE `auteur` (
  `id` int NOT NULL,
  `nationalite` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `auteur`
--

INSERT INTO `auteur` (`id`, `nationalite`, `nom`, `prenom`) VALUES
(1, 'francais', 'auteur', 'prenom'),
(2, 'nationalite2', 'nom1', 'prenom');

-- --------------------------------------------------------

--
-- Structure de la table `collection`
--

CREATE TABLE `collection` (
  `id` int NOT NULL,
  `nom` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `collection`
--

INSERT INTO `collection` (`id`, `nom`) VALUES
(2, 'test 2322');

-- --------------------------------------------------------

--
-- Structure de la table `editeur`
--

CREATE TABLE `editeur` (
  `id` int NOT NULL,
  `nom` varchar(100) NOT NULL,
  `pays` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `editeur`
--

INSERT INTO `editeur` (`id`, `nom`, `pays`) VALUES
(2, 'editeur 1', 'France');

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE `livre` (
  `annee` int DEFAULT NULL,
  `auteur` int DEFAULT NULL,
  `collection` int DEFAULT NULL,
  `editeur` int DEFAULT NULL,
  `id` int NOT NULL,
  `titre` varchar(50) NOT NULL,
  `resume` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `livre`
--

INSERT INTO `livre` (`annee`, `auteur`, `collection`, `editeur`, `id`, `titre`, `resume`) VALUES
(3, 1, 2, 2, 2, 'aaaaz', ''),
(4, 2, 2, 2, 3, 'qzzzz', '');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id` int NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id`, `login`, `password`, `role`) VALUES
(1, 'user', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'ADMIN');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `auteur`
--
ALTER TABLE `auteur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `collection`
--
ALTER TABLE `collection`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `editeur`
--
ALTER TABLE `editeur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK797gishit1th7hqugsf4njqsh` (`auteur`),
  ADD KEY `FKj1xtsdn4tnyotrhq5gapi4dh4` (`collection`),
  ADD KEY `FK3dry7ch7spjpm8o9r3gvqbah` (`editeur`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK8l3j8smom1kapeu3y4f4d6g3g` (`login`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `auteur`
--
ALTER TABLE `auteur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `collection`
--
ALTER TABLE `collection`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `editeur`
--
ALTER TABLE `editeur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `livre`
--
ALTER TABLE `livre`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `FK3dry7ch7spjpm8o9r3gvqbah` FOREIGN KEY (`editeur`) REFERENCES `editeur` (`id`),
  ADD CONSTRAINT `FK797gishit1th7hqugsf4njqsh` FOREIGN KEY (`auteur`) REFERENCES `auteur` (`id`),
  ADD CONSTRAINT `FKj1xtsdn4tnyotrhq5gapi4dh4` FOREIGN KEY (`collection`) REFERENCES `collection` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
