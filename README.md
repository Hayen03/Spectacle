- # Spectacle
- TP IFT2905

- Lorsqu'on lance l'application on est automatiquement dirigé sur la page de login
- la premières fois que l'application est lancée la base de données est chargées (SQLite)
- si par la suite on veut effacer la base de données et la recharger à nouveau, on peut décommenter la ligne # 53 du fichier app/src/main/java/hayen/spectacle/data/dao/DatabaseHelper.java

- 53:    //this.context.deleteDatabase(Constant.DATABASE_NAME);


- On peut utiliser l'utilisateur # 2 pour se connecter avec l'adresse de courriel et le mot de passe suivants: 
- login: 'ArnaudFournier@dayrep.com'
- mot de passe: 'weWoh9zie'

- les entrées de la BD sont insérées dans le fichier:  app/src/main/java/hayen/spectacle/data/dao/DatabaseQueries.java
- 8 utilisateurs ont été insérés dans la table 'utilisateur' à la ligne 224

- on peut modifier/insérer des entrées et effacer la base de données en déommentant la ligne #53 pour recharger les tables 

