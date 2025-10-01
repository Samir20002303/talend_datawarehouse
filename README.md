# Data Warehouse Project  Talend

Ce projet est un **data warehouse** développé avec **Talend**. Il contient toutes les définitions de jobs, routines, schémas et métadonnées nécessaires à l’alimentation et la transformation des données.

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

## Documentation

- `documentations/projet_talend.docx` : Document Word décrivant le projet diviser en trois phases 

1. Analyse et modélisation organisationnelle à l’aide du framework i*
   Présentation de l’entreprise
   Niveau contextuel (Zachman Framework : What, How, Where, Who, When, Why)
   Niveau conceptuel (Diagrammes i*, acteurs, rôles, rationales, diagrammes de classes UML)
   Identification et modélisation de certains processus métiers (approvisionnement, vente, réparation)
   Contraintes temporelles avec règles ECA

2. Modélisation multidimensionnelle et processus ETL
   Schéma multidimensionnel et description des données
   Processus ETL : collecte, extraction, création de jobs, nettoyage, intégration, réconciliation et publication

3. Reporting
   Requêtes traitées et génération de rapports
   Analyse des ventes, chiffre d’affaires, catégories de produits

## Références

- Documentation officielle Talend : [https://www.talend.com/resources/](https://www.talend.com/resources/) 
