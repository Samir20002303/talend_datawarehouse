# Data Warehouse Project - Talend

Ce projet est un **data warehouse** développé avec **Talend**. Il contient toutes les définitions de jobs, routines, schémas et métadonnées nécessaires à l’alimentation et la transformation des données.

## Contenu du projet
DATAWAREHOUSE_PROJECT/
│
├─ code/ # Jobs Talend principaux
├─ documentations/ # Documentation du projet
├─ metadata/ # Métadonnées (connexions, schémas, etc.)
├─ poms/ # Fichiers Maven pour Talend
├─ process/ # Jobs Talend
├─ sqlPatterns/ # Scripts SQL utilisés
├─ temp/ # Fichiers temporaires générés
├─ .project # Fichier de configuration Talend
├─ talend.project # Projet Talend
└─ README.md # Ce fichier


## Prérequis

- Talend Open Studio ou Talend Enterprise (selon la version utilisée pour le projet)
- Java JDK compatible avec Talend
- Base de données cible pour le data warehouse

## Installation

1. Importer le projet dans Talend via **File → Import Items → Select root directory**.
2. Vérifier les connexions aux bases de données dans `metadata`.
3. Exécuter les jobs depuis Talend pour tester le pipeline.

## Notes

- Ce projet est fourni tel quel et nécessite Talend pour être exécuté.
- Tous les scripts SQL et métadonnées sont inclus pour faciliter le déploiement.
- Les dossiers `temp/` et `.settings/` sont générés automatiquement par Talend.

## Références

- Documentation officielle Talend : [https://www.talend.com/resources/](https://www.talend.com/resources/)

