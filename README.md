# ChatBox

 Le micro-blogging est un type d’application phare pour les entreprises. Chaque utilisateur peut:

    Consulter l'accueil du réseau social
    S'identifier à l'application
    Renseigner sa fiche profil pour détailler:
        Ses informations de contact (email, téléphone...)
        Sa photo
    Rechercher des utilsateurs, et lire leur fiche profil
    Afficher les tweets d'un utilisateur
    Partager du contenu sous forme d'un tweet en interne(avec gestion des hashtags)
    Rechercher du contenu sur base d'un hashtag
    Pouvoir gérer l'ensemble des données via une API de web-service REST

Le micro-blogging se doit d'être:

    Modulable : Les différents modules du réseau social doivent être interdépendants et s'appuyer sur des services distinct (possibilité d'utilisé firebase)
    Sécurisé : Les contenus ne doivent pas être accessibles aux personnes non-authentifiées
    Scalable : Le réseau social doit pouvoir absorber une forte charge, et un fort volume de données.

## Comment lancer le projet

1 - Lancer MongoDB
1.1 - Aller dans le repertoire de mongoDB 
1.2 - ./mongod ou ./mongod.exe

2 - Lancer le projet
2.1 - Aller dans le repertoire du projet
2.2 - mvn clean spring-boot:run

3 - Aller sur http://locahost:8989/

## Quelques messages REST d'exemple pour l'invocation des web-service

### Users

	- http://locahost:8989/users GET POST PUT
	- http://locahost:8989/users/connexion POST
	- http://locahost:8989/users/:iduser GET

### Messages 

	- http://locahost:8989/messages GET POST PUT DELETE
	- http://locahost:8989/messages/hashtag/:tag GET
	- http://locahost:8989/messages/user/:iduser GET
	- http://locahost:8989/messages/:idmessage GET
	
	