//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

/**
 * Created by daristote on 18-03-16.
 */

public class DatabaseQueries {



    public static final String[] TABLE_RESERVATION_NAMES = {
            "adresse", "salle", "artiste", "genre", "spectacle", "spectacle_artiste",
            "section", "spectacle_section", "siege", "spectacle_siege", "utilisateur",
            "reservation", "reservation_spectacle_siege", "carte_credit", "paiement"
    };


    public static final String[] CREATE_TABLES_RESERVATION = {
            "create table if not exists adresse( " +
                    "   id integer primary key autoincrement, " +
                    "   numero integer not null, " +
                    "   rue varchar(60) not null, " +
                    "   ville varchar(60) not null, " +
                    "   province varchar(2) not null, " +
                    "   code_postal varchar(7) not null, " +
                    "   longitude long default null, " +
                    "   latitude long default null, " +
                    "   constraint ck_province check (province in ('AL', 'BC', 'MB', 'NB', 'NL', 'NS', 'NT', 'NU', 'ON', 'PE', 'QC', 'SK', 'YT')))",

            "create table if not exists salle( " +
                    "   id integer primary key autoincrement, " +
                    "   nom varchar(30) not null, " +
                    "   telephone varchar(20), " +
                    "   fax varchar(20), " +
                    "   courriel varchar(60), " +
                    "   id_adresse integer not null, " +
                    "   description text, " +
                    "   foreign key(id_adresse) references adresse(id))",

            "create table if not exists artiste( " +
                    "   id integer primary key autoincrement, " +
                    "   prenom varchar(60), " +
                    "   nom varchar(60) not null)",

            "create table if not exists genre( " +
                    "   id integer primary key autoincrement, " +
                    "   nom varchar(20) not null)",

            "create table if not exists spectacle( " +
                    "   id integer primary key autoincrement, " +
                    "   titre varchar(40), " +
                    "   date_spectacle timestamp default current_timestamp, " +
                    "   id_genre integer not null, " +
                    "   id_salle integer not null, " +
                    "   foreign key(id_genre) references genre(id), " +
                    "   foreign key(id_salle) references salle(id))",

            " create table if not exists spectacle_artiste( " +
                    "   id_spectacle integer not null, " +
                    "   id_artiste integer not null, " +
                    "   primary key (id_artiste, id_spectacle) " +
                    "   foreign key (id_spectacle) references spectacle(id), " +
                    "   foreign key (id_artiste) references artiste(id) )",

            " create table if not exists section( " +
                    "   id integer primary key autoincrement, " +
                    "   nom varchar(30) not null, " +
                    "   categorie integer not null, " +
                    "   nb_sieges integer not null, " +
                    "   id_salle integer not null, " +
                    "   foreign key (id_salle) references salle(id) )",

            " create table if not exists spectacle_section( " +
                    "   id_spectacle integer not null, " +
                    "   id_section integer not null, " +
                    "   prix float default 0.0, " +
                    "   foreign key(id_spectacle) references spectacle(id), " +
                    "   foreign key(id_section) references section(id) )",

            " create table if not exists siege( " +
                    "   id integer primary key autoincrement, " +
                    "   rangee varchar(5) not null, " +
                    "   colonne tinyint not null, " +
                    "   id_section integer not null, " +
                    "   unique(rangee, colonne, id_section), " +
                    "   foreign key(id_section) references section(id) )",

            " create table if not exists spectacle_siege( " +
                    "   id_spectacle integer not null, " +
                    "   id_siege integer not null, " +
                    "   reserve tinyint(1) default 0, " +
                    "   foreign key(id_spectacle) references spectacle(id), " +
                    "   foreign key(id_siege) references siege(id) )",

            "create table if not exists utilisateur( " +
                    "   id integer primary key autoincrement, " +
                    "   prenom varchar(40) not null, " +
                    "   nom varchar(40) not null, " +
                    "   login varchar(40) not null unique, " +
                    "   mot_passe varchar(80), " +
                    "   courriel varchar(40) not null unique, " +
                    "   telephone varchar(12),  " +
                    "   id_adresse integer default null,  " +
                    "   role integer default 0,  " +
                    "   foreign key(id_adresse) references adresse(id) )",

            " create table if not exists reservation( " +
                    "   id integer primary key autoincrement, " +
                    "   num_confirmation long not null, " +
                    "   date_reservation timestamp default current_timestamp, " +
                    "   id_utilisateur integer not null, " +
                    "   foreign key(id_utilisateur) references utilisateur(id))",

            " create table if not exists reservation_spectacle_siege ( " +
                    "   id_reservation smallint unsigned not null, " +
                    "   id_spectacle smallint unsigned not null, " +
                    "   id_siege smallint unsigned not null, " +
                    "   primary key (id_reservation, id_spectacle, id_siege), " +
                    "   foreign key(id_reservation) references reservation(id), " +
                    "   foreign key(id_spectacle) references spectacle(id), " +
                    "   foreign key(id_siege) references siege(id) ) ",

            " create table if not exists carte_credit( " +
                    "   id integer primary key autoincrement, " +
                    "   id_utilisateur integer not null,  " +
                    "   nom_utilisateur varchar(40) not null, " +
                    "   nom_carte varchar(20) not null, " +
                    "   numero varchar(20) not null, " +
                    "   date_expiration timestamp default current_timestamp, " +
                    "   code integer not null, " +
                    "   unique(nom_carte, numero), " +
                    "   foreign key(id_utilisateur) references utilisateur(id) )",

            " create table if not exists paiement( " +
                    "   id integer primary key autoincrement, " +
                    "   montant float default 0.0, " +
                    "   date_paiement timestamp default current_timestamp, " +
                    "   id_reservation integer not null, " +
                    "   foreign key(id_reservation) references reservation(id) )"

    };


    public static final String[] DROP_TABLES_QUERIES = {
            "drop table if exists reservation_spectacle_siege",
            "drop table if exists spectacle_artiste",
            "drop table if exists spectacle_section",
            "drop table if exists spectacle_siege",
            "drop table if exists paiement",
            "drop table if exists carte_credit",
            "drop table if exists reservation",
            "drop table if exists siege",
            "drop table if exists section",
            "drop table if exists spectacle",
            "drop table if exists salle",
            "drop table if exists artiste",
            "drop table if exists genre",
            "drop table if exists utilisateur",
            "drop table if exists adresse"
    };

    public static final String[] CREATE_TABLE_FK_CONSTRAINTS = {
            "alter table utilisateur  add  constraint fk_user_adresse foreign key(id_adresse) references adresse(id)",

            "alter table carte_credit add  constraint fk_carte_user foreign key(id_utilisateur) references utilisateur(id)",

            "alter table paiement add  constraint fk_paiement_reservation foreign key(id_reservation) references reservation(id)",

            "alter table reservation_spectacle_siege add  constraint fk_res_spect_siege_reservation foreign key(id_reservation) references reservation(id)",

            "alter table reservation_spectacle_siege add  constraint fk_res_spect_siege_spectacle foreign key(id_spectacle) references spectacle(id)",

            "alter table reservation_spectacle_siege add  constraint fk_res_spect_siege_siege	foreign key(id_siege) references siege(id)",

            "alter table reservation add  constraint fk_reservation_user foreign key(id_utilisateur) references utilisateur(id)",

            "alter table siege add  constraint fk_siege_section foreign key(id_section) references section(id)",

            "alter table section add  constraint fk_section_salle foreign key(id_salle) references salle(id)",

            "alter table spectacle_artiste add  constraint fk_spectacle_artiste_spectacle foreign key(id_spectacle) references spectacle(id)",

            "alter table spectacle_artiste add  constraint fk_spectacle_artiste_artiste foreign key(id_artiste) references artiste(id)",

            "alter table spectacle_section add  constraint fk_spectacle_section_spectacle foreign key(id_spectacle) references spectacle(id)",

            "alter table spectacle_section add  constraint fk_spectacle_section_section foreign key(id_section) references section(id)",

            "alter table spectacle_siege add  constraint fk_spectacle_siege_spectacle foreign key(id_spectacle) references spectacle(id)",

            "alter table spectacle_siege add  constraint fk_spectacle_siege_siege foreign key(id_siege) references siege(id)",

            "alter table spectacle add constraint fk_spectacle_genre foreign key(id_genre) references genre(id)",

            "alter table spectacle add constraint fk_spectacle_salle foreign key(id_salle) references salle(id)",

            "alter table salle add constraint fk_salle_adresse foreign key(id_adresse) references adresse(id)"

    };
    //**********************************************************************************************
    //**********************************************************************************************
    //**************************************Insert queries******************************************
    public static final String[] INSERT_ADRESSE_QUERIES = {
            "insert into adresse(id, numero, rue, ville, province, code_postal, longitude, latitude) values \n" +
                    "   (null, 5468, 'De Lorimier', 'Montréal', 'QC', 'H4B 1V9', null, null), \n" +
                    "   (null, 855, 'De la Providence Avenue', 'Ste Agathe', 'QC', 'J8C 1J5', null, null), \n" +
                    "   (null, 3705, 'Roger Street',  'Parksville', 'BC', 'V9P 1R1', null, null), \n" +
                    "   (null, 4258, 'Tanner Street',  'Vancouver', 'BC', 'V5R 2T4', null, null),\n " +
                    "   (null, 3710, 'Carling Avenue',  'Ottawa', 'ON', 'K1Z 7B5', null, null), \n" +
                    "   (null, 3531, 'Jasper Avenue', 'Edmonton', 'AL', 'T5J 3N2', null, null), \n" +
                    "   (null, 889,  'Weir Crescent', 'Toronto', 'ON', 'M1E 3T8', null, null),\n" +
                    "   (null, 268, 'Chemin du Lac', 'Boucherville', 'QC', 'J4B 5B1', null, null)\n"
    };


    public static final String[] INSERT_SALLE_QUERIES = {
            "insert into salle (id, nom, telephone, fax, courriel, id_adresse, description) values \n" +
                    "   (null, 'La Momie pétrifiée', '514-555-5555', '514-666-6666', 'infos@momie.com', 1, \n" +
                    "   'La salle \"La Momie Pétrifée\" a été fondée en l''an de grâce 2517 B.C, sous le règne de Mykérinos de la IVe Dynastie.')\n"
    };
    public static final String[] INSERT_USER_QUERIES = {
            "insert into utilisateur(id, prenom, nom, login, mot_passe, courriel, telephone, id_adresse, role) values " +
                    "   (1, 'Joe', 'Administrateur', 'admin', 'admin123', 'admin@momie.com', '514-555-5555', 1, 1), " +
                    "   (2, 'Arnaud', 'Fournier', 'Enstives97', 'weWoh9zie', 'ArnaudFournier@dayrep.com', '819-217-5880', 2, 0), " +
                    "   (3, 'Victorine', 'De La Ronde', 'Nowing1991', 'UuJ6uadu8', 'victorine.delaronde@teleworm.ca', null, 3, 0), " +
                    "   (4, 'Felicienne', 'Gendron', 'Sincing', 'niQuieXu8pee', 'felicgend@dayrep.com', null, 4, 0),\n" +
                    "   (5, 'Saville', 'Lacharité', 'Scia1966', 'Eegheel9Ohy', 'SavilleLacharite@rhyta.com', '613-295-6288', null, 0),\n " +
                    "   (6, 'Claude', 'Fortin', 'Getiandre', 'ohrim2eeFee', 'ClaudeFortin@rhyta.com', '780-237-4447', 6, 0),\n" +
                    "   (7, 'Rosamonde', 'Fréchette', 'Rubligod', 'UuJ6uadu8', 'rosamonde.frechette@teleworm.ca', '416-660-1103', 7, 0),\n" +
                    "   (8, 'Ignace', 'Lemieux', 'Efuld1977', 'UuJ6uadu8', 'IgnaceLemieux@teleworm.ca', '450-645-9478', 8, 0)\n"
    };

    public static final String[] INSERT_GENRE_QUERIES = {
            "insert into genre (nom) values \n" +
                    "   ('Jazz'), \n" +
                    "   ('Blues'), \n" +
                    "   ('Classique'), \n" +
                    "   ('Rock'), \n" +
                    "   ('Hip⁻Hop'), \n" +
                    "   ('Métal Celtique'),\n" +
                    "   ('Country'), \n" +
                    "   ('Chanson française'), \n" +
                    "   ('Pop'), \n" +
                    "   ('Jazz latin'), \n" +
                    "   ('Rock alternatif')\n"
    };

    public static final String[] INSERT_ARTISTE_QUERIES = {
            "   insert into artiste (prenom, nom) values \n" +
                    "   ('Frida N.',  'Olsen' ), \n" +
                    "   ('David',  'Moore' ), \n" +
                    "   ('Paul', 'McCartney'), \n" +
                    "   ('', 'Rolling Stones'),\n " +
                    "   ('Dieter',  'Propst' ), \n" +
                    "   ('', 'Led Zeppelin'), \n" +
                    "   ('Charles', 'Aznavour'), \n" +
                    "   ('', 'Madonna'), \n" +
                    "   ('Florence',  'Briard' ), \n" +
                    "   ('Júlio',  'Martins Souza'), \n" +
                    "   ('Zdenek', 'Kováč'), \n" +
                    "   ('Antônio Carlos', 'Jobim'), \n" +
                    "   ('', 'Orchestre Symphonique de Montréal'), \n" +
                    "   ('Kent', 'Nagano') \n"

    };

    public static final String[] INSERT_SPECTACLE_QUERIES = {
            "insert into spectacle (titre, date_spectacle, id_genre, id_salle) values \n" +
                    "   ('McCartney en concert', '2018-04-10 20:00:00', 11, 1), \n" +
                    "   ('Jobim en concert', '2018-05-27 20:00:00', 10, 1), \n" +
                    "   ('Beethoven en folie', '2018-06-02 20:00:00', 3, 1), \n" +
                    "   ('Mahler: Symphonie #3', '2018-06-12 19:00:00', 3, 1), \n" +
                    "   ('Azanavour: 18e tournée d''adieu', '2018-05-22 20:00:00', 8, 1), \n" +
                    "   ('Rolling Stones: Money Tour', '2018-07-21 20:00:00', 4, 1)"

    };

    public static final String[] INSERT_SPECTACLE_ARTISTE_QUERIES = {
            "insert into spectacle_artiste (id_spectacle, id_artiste) values\n" +
                    "   (1, 3),\n" +
                    "   (2, 12),\n" +
                    "   (3, 13),\n" +
                    "   (3, 14),\n" +
                    "   (4, 13),\n" +
                    "   (4, 14),\n" +
                    "   (5, 7), \n" +
                    "   (6, 4) "

    };

    public static final String[] INSERT_SECTION_QUERIES = {
            "insert into section (nom, categorie, nb_sieges, id_salle) values\n" +
                    "   ('Parterre A', 1, 20, 1),\n" +
                    "   ('Parterre B', 2, 20, 1),\n" +
                    "   ('Mezzanine', 3, 20, 1),\n" +
                    "   ('Balcon', 4, 20, 1)"

    };


    public static final String[] INSERT_SPECTACLE_SECTION_QUERIES = {
            "insert into spectacle_section (id_spectacle, id_section, prix) values\n" +
                    //McCartney"
                    "   (1, 1, 60.0),\n" +
                    "   (1, 2, 50.0),\n" +
                    "   (1, 3, 40.0),\n" +
                    "   (1, 4, 35.0),\n" +

                    // Jobim
                    "   (2, 1, 35.0),\n" +
                    "   (2, 2, 30.0),\n" +
                    "   (2, 3, 25.0),\n" +
                    "   (2, 4, 15.0),\n" +

                    //Beethoven
                    "   (3, 1, 55.0),\n" +
                    "   (3, 2, 50.0),\n" +
                    "   (3, 3, 50.0),\n" +
                    "   (3, 4, 35.0),\n" +
                    //Mahler
                    "   (4, 1, 55.0),\n" +
                    "   (4, 2, 50.0),\n" +
                    "   (4, 3, 50.0),\n" +
                    "   (4, 4, 35.0),\n" +

                    //Aznavour
                    "   (5, 1, 40.0),\n" +
                    "   (5, 2, 40.0),\n" +
                    "   (5, 3, 40.0),\n" +
                    "   (5, 4, 40.0),\n" +

                    //Rolling Stones
                    "   (6, 1, 200.0),\n" +
                    "   (6, 2, 200.0),\n" +
                    "   (6, 3, 200.0),\n" +
                    "   (6, 4, 200.0)"

    };

    public static final String[] INSERT_SIEGE_QUERIES = {
            "insert into siege (rangee, colonne, id_section) values\n" +
                    "   ('PA', 1, 1), ('PA', 2, 1), ('PA', 3, 1), ('PA', 4, 1), ('PA', 5, 1),\n" +
                    "   ('PB', 1, 1), ('PB', 2, 1), ('PB', 3, 1), ('PB', 4, 1), ('PB', 5, 1),\n" +
                    "   ('PC', 1, 1), ('PC', 2, 1), ('PC', 3, 1), ('PC', 4, 1), ('PC', 5, 1), \n" +
                    "   ('PD', 1, 1), ('PD', 2, 1), ('PD', 3, 1), ('PD', 4, 1), ('PD', 5, 1),\n" +
                    "   ('PE', 1, 1), ('PE', 2, 1), ('PE', 3, 1), ('PE', 4, 1), ('PE', 5, 1),\n" +
                    "   ('PF', 1, 1), ('PF', 2, 1), ('PF', 3, 1), ('PF', 4, 1), ('PF', 5, 1),\n" +

                    "   ('PG', 1, 2), ('PG', 2, 2), ('PG', 3, 2), ('PG', 4, 2), ('PG', 5, 2),\n" +
                    "   ('PH', 1, 2), ('PH', 2, 2), ('PH', 3, 2), ('PH', 4, 2), ('PH', 5, 2),\n" +
                    "   ('PI', 1, 2), ('PI', 2, 2), ('PI', 3, 2), ('PI', 4, 2), ('PI', 5, 2), \n" +
                    "   ('PJ', 1, 2), ('PJ', 2, 2), ('PJ', 3, 2), ('PJ', 4, 2), ('PJ', 5, 2),\n" +
                    "   ('PK', 1, 2), ('PK', 2, 2), ('PK', 3, 2), ('PK', 4, 2), ('PK', 5, 2),\n" +
                    "   ('PL', 1, 2), ('PL', 2, 2), ('PL', 3, 2), ('PL', 4, 2), ('PL', 5, 2),\n" +

                    "   ('MA', 1, 3), ('MA', 2, 3), ('MA', 3, 3), ('MA', 4, 3), ('MA', 5, 3),\n" +
                    "   ('MB', 1, 3), ('MB', 2, 3), ('MB', 3, 3), ('MB', 4, 3), ('MB', 5, 3),\n" +
                    "   ('MC', 1, 3), ('MC', 2, 3), ('MC', 3, 3), ('MC', 4, 3), ('MC', 5, 3), \n" +
                    "   ('MD', 1, 3), ('MD', 2, 3), ('MD', 3, 3), ('MD', 4, 3), ('MD', 5, 3), \n" +
                    "   ('ME', 1, 3), ('ME', 2, 3), ('ME', 3, 3), ('ME', 4, 3), ('ME', 5, 3),\n" +
                    "   ('MF', 1, 3), ('MF', 2, 3), ('MF', 3, 3), ('MF', 4, 3), ('MF', 5, 3), \n" +

                    "   ('BA', 1, 4), ('BA', 2, 4), ('BA', 3, 4), ('BA', 4, 4), ('BA', 5, 4),\n" +
                    "   ('BB', 1, 4), ('BB', 2, 4), ('BB', 3, 4), ('BB', 4, 4), ('BB', 5, 4),\n" +
                    "   ('BC', 1, 4), ('BC', 2, 4), ('BC', 3, 4), ('BC', 4, 4), ('BC', 5, 4), \n" +
                    "   ('BD', 1, 4), ('BD', 2, 4), ('BD', 3, 4), ('BD', 4, 4), ('BD', 5, 4), \n" +
                    "   ('BE', 1, 4), ('BE', 2, 4), ('BE', 3, 4), ('BE', 4, 4), ('BE', 5, 4),\n" +
                    "   ('BF', 1, 4), ('BF', 2, 4), ('BF', 3, 4), ('BF', 4, 4), ('BF', 5, 4) "

    };

    public static final String[] INSERT_SPECTACLE_SIEGE_QUERIES = {
            "insert into spectacle_siege (id_spectacle, id_siege, reserve) values\n" +
                    "   (1, 1, 0),   (1, 2, 0), (1, 3, 0), \t(1, 4, 0),  (1, 5, 0), (1, 6, 0), (1, 7, 0), (1, 8, 0), (1, 9, 0), (1, 10, 0), (1, 11, 0), (1, 12, 0), (1, 13, 0), (1, 14, 0), (1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), \n" +
                    "   (1, 21, 0),  (1, 22, 0), (1, 23, 0), (1, 24, 0), (1, 25, 0), (1, 26, 0), (1, 27, 0), (1, 28, 0), (1, 29, 0), (1, 30, 0), (1, 31, 0), (1, 32, 0), (1, 33, 0), (1, 34, 0), (1, 35, 0), (1, 36, 0), (1, 37, 0), (1, 38, 0), (1, 39, 0), (1, 40, 0), \n" +
                    "   (1, 41, 0),  (1, 42, 0), (1, 43, 0), (1, 44, 0), (1, 45, 0), (1, 46, 0), (1, 47, 0), (1, 48, 0), (1, 49, 0), (1, 50, 0), (1, 51, 0), (1, 52, 0), (1, 53, 0), (1, 54, 0), (1, 55, 0), (1, 56, 0), (1, 57, 0), (1, 58, 0), (1, 59, 0), (1, 60, 0), \n" +
                    "   (1, 61, 0),  (1, 62, 0), (1, 63, 0), (1, 64, 0), (1, 65, 0), (1, 66, 0), (1, 67, 0), (1, 68, 0), (1, 69, 0), (1, 70, 0), (1, 71, 0), (1, 72, 0), (1, 73, 0), (1, 74, 0), (1, 75, 0), (1, 76, 0), (1, 77, 0), (1, 78, 0), (1, 79, 0), (1, 80, 0), \n" +
                    "   (1, 81, 0),  (1, 82, 0), (1, 83, 0), (1, 84, 0), (1, 85, 0), (1, 86, 0), (1, 87, 0), (1, 88, 0), (1, 89, 0), (1, 90, 0), (1, 91, 0), (1, 92, 0), (1, 93, 0), (1, 94, 0), (1, 95, 0), (1, 96, 0), (1, 97, 0), (1, 98, 0), (1, 99, 0), (1, 100, 0), \n" +
                    "   (1, 101, 0), (1, 102, 0), (1, 103, 0), (1, 104, 0), (1, 105, 0), (1, 106, 0), (1, 107, 0), (1, 108, 0), (1, 109, 0), (1, 110, 0), (1, 111, 0), (1, 112, 0), (1, 113, 0), (1, 114, 0), (1, 115, 0), (1, 116, 0), (1, 117, 0), (1, 118, 0), (1, 119, 0), (1, 120, 0),\n" +

                    "   (2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0), (2, 8, 0), (2, 9, 0), (2, 10, 0), (2, 11, 0), (2, 12, 0), (2, 13, 0), (2, 14, 0), (2, 15, 0), (2, 16, 0), (2, 17, 0), (2, 18, 0), (2, 19, 0), (2, 20, 0), \n" +
                    "   (2, 21, 0), (2, 22, 0), (2, 23, 0), (2, 24, 0), (2, 25, 0), (2, 26, 0), (2, 27, 0), (2, 28, 0), (2, 29, 0), (2, 30, 0), (2, 31, 0), (2, 32, 0), (2, 33, 0), (2, 34, 0), (2, 35, 0), (2, 36, 0), (2, 37, 0), (2, 38, 0), (2, 39, 0), (2, 40, 0), \n" +
                    "   (2, 41, 0), (2, 42, 0), (2, 43, 0), (2, 44, 0), (2, 45, 0), (2, 46, 0), (2, 47, 0), (2, 48, 0), (2, 49, 0), (2, 50, 0), (2, 51, 0), (2, 52, 0), (2, 53, 0), (2, 54, 0), (2, 55, 0), (2, 56, 0), (2, 57, 0), (2, 58, 0), (2, 59, 0), (2, 60, 0), \n" +
                    "   (2, 61, 0), (2, 62, 0), (2, 63, 0), (2, 64, 0), (2, 65, 0), (2, 66, 0), (2, 67, 0), (2, 68, 0), (2, 69, 0), (2, 70, 0), (2, 71, 0), (2, 72, 0), (2, 73, 0), (2, 74, 0), (2, 75, 0), (2, 76, 0), (2, 77, 0), (2, 78, 0), (2, 79, 0), (2, 80, 0), \n" +
                    "   (2, 81, 0), (2, 82, 0), (2, 83, 0), (2, 84, 0), (2, 85, 0), (2, 86, 0), (2, 87, 0), (2, 88, 0), (2, 89, 0), (2, 90, 0), (2, 91, 0), (2, 92, 0), (2, 93, 0), (2, 94, 0), (2, 95, 0), (2, 96, 0), (2, 97, 0), (2, 98, 0), (2, 99, 0), (2, 100, 0), \n" +
                    "   (2, 101, 0), (2, 102, 0), (2, 103, 0), (2, 104, 0), (2, 105, 0), (2, 106, 0), (2, 107, 0), (2, 108, 0), (2, 109, 0), (2, 110, 0), (2, 111, 0), (2, 112, 0), (2, 113, 0), (2, 114, 0), (2, 115, 0), (2, 116, 0), (2, 117, 0), (2, 118, 0), (2, 119, 0), (2, 120, 0),\n" +

                    "   (3, 1, 0), (3, 2, 0), (3, 3, 0), (3, 4, 0), (3, 5, 0), (3, 6, 0), (3, 7, 0), (3, 8, 0), (3, 9, 0), (3, 10, 0), (3, 11, 0), (3, 12, 0), (3, 13, 0), (3, 14, 0), (3, 15, 0), (3, 16, 0), (3, 17, 0), (3, 18, 0), (3, 19, 0), (3, 20, 0), \n" +
                    "   (3, 21, 0), (3, 22, 0), (3, 23, 0), (3, 24, 0), (3, 25, 0), (3, 26, 0), (3, 27, 0), (3, 28, 0), (3, 29, 0), (3, 30, 0), (3, 31, 0), (3, 32, 0), (3, 33, 0), (3, 34, 0), (3, 35, 0), (3, 36, 0), (3, 37, 0), (3, 38, 0), (3, 39, 0), (3, 40, 0), \n" +
                    "   (3, 41, 0), (3, 42, 0), (3, 43, 0), (3, 44, 0), (3, 45, 0), (3, 46, 0), (3, 47, 0), (3, 48, 0), (3, 49, 0), (3, 50, 0), (3, 51, 0), (3, 52, 0), (3, 53, 0), (3, 54, 0), (3, 55, 0), (3, 56, 0), (3, 57, 0), (3, 58, 0), (3, 59, 0), (3, 60, 0), \n" +
                    "   (3, 61, 0), (3, 62, 0), (3, 63, 0), (3, 64, 0), (3, 65, 0), (3, 66, 0), (3, 67, 0), (3, 68, 0), (3, 69, 0), (3, 70, 0), (3, 71, 0), (3, 72, 0), (3, 73, 0), (3, 74, 0), (3, 75, 0), (3, 76, 0), (3, 77, 0), (3, 78, 0), (3, 79, 0), (3, 80, 0), \n" +
                    "   (3, 81, 0), (3, 82, 0), (3, 83, 0), (3, 84, 0), (3, 85, 0), (3, 86, 0), (3, 87, 0), (3, 88, 0), (3, 89, 0), (3, 90, 0), (3, 91, 0), (3, 92, 0), (3, 93, 0), (3, 94, 0), (3, 95, 0), (3, 96, 0), (3, 97, 0), (3, 98, 0), (3, 99, 0), (3, 100, 0), \n" +
                    "   (3, 101, 0), (3, 102, 0), (3, 103, 0), (3, 104, 0), (3, 105, 0), (3, 106, 0), (3, 107, 0), (3, 108, 0), (3, 109, 0), (3, 110, 0), (3, 111, 0), (3, 112, 0), (3, 113, 0), (3, 114, 0), (3, 115, 0), (3, 116, 0), (3, 117, 0), (3, 118, 0), (3, 119, 0), (3, 120, 0),\n" +

                    "   (4, 1, 0), (4, 2, 0), (4, 3, 0), (4, 4, 0), (4, 5, 0), (4, 6, 0), (4, 7, 0), (4, 8, 0), (4, 9, 0), (4, 10, 0), (4, 11, 0), (4, 12, 0), (4, 13, 0), (4, 14, 0), (4, 15, 0), (4, 16, 0), (4, 17, 0), (4, 18, 0), (4, 19, 0), (4, 20, 0), \n" +
                    "   (4, 21, 0), (4, 22, 0), (4, 23, 0), (4, 24, 0), (4, 25, 0), (4, 26, 0), (4, 27, 0), (4, 28, 0), (4, 29, 0), (4, 30, 0), (4, 31, 0), (4, 32, 0), (4, 33, 0), (4, 34, 0), (4, 35, 0), (4, 36, 0), (4, 37, 0), (4, 38, 0), (4, 39, 0), (4, 40, 0), \n" +
                    "   (4, 41, 0), (4, 42, 0), (4, 43, 0), (4, 44, 0), (4, 45, 0), (4, 46, 0), (4, 47, 0), (4, 48, 0), (4, 49, 0), (4, 50, 0), (4, 51, 0), (4, 52, 0), (4, 53, 0), (4, 54, 0), (4, 55, 0), (4, 56, 0), (4, 57, 0), (4, 58, 0), (4, 59, 0), (4, 60, 0), \n" +
                    "   (4, 61, 0), (4, 62, 0), (4, 63, 0), (4, 64, 0), (4, 65, 0), (4, 66, 0), (4, 67, 0), (4, 68, 0), (4, 69, 0), (4, 70, 0), (4, 71, 0), (4, 72, 0), (4, 73, 0), (4, 74, 0), (4, 75, 0), (4, 76, 0), (4, 77, 0), (4, 78, 0), (4, 79, 0), (4, 80, 0), \n" +
                    "   (4, 81, 0), (4, 82, 0), (4, 83, 0), (4, 84, 0), (4, 85, 0), (4, 86, 0), (4, 87, 0), (4, 88, 0), (4, 89, 0), (4, 90, 0), (4, 91, 0), (4, 92, 0), (4, 93, 0), (4, 94, 0), (4, 95, 0), (4, 96, 0), (4, 97, 0), (4, 98, 0), (4, 99, 0), (4, 100, 0), \n" +
                    "   (4, 101, 0), (4, 102, 0), (4, 103, 0), (4, 104, 0), (4, 105, 0), (4, 106, 0), (4, 107, 0), (4, 108, 0), (4, 109, 0), (4, 110, 0), (4, 111, 0), (4, 112, 0), (4, 113, 0), (4, 114, 0), (4, 115, 0), (4, 116, 0), (4, 117, 0), (4, 118, 0), (4, 119, 0), (4, 120, 0),\n" +

                    "   (5, 1, 0), (5, 2, 0), (5, 3, 0), (5, 4, 0), (5, 5, 0), (5, 6, 0), (5, 7, 0), (5, 8, 0), (5, 9, 0), (5, 10, 0), (5, 11, 0), (5, 12, 0), (5, 13, 0), (5, 14, 0), (5, 15, 0), (5, 16, 0), (5, 17, 0), (5, 18, 0), (5, 19, 0), (5, 20, 0), \n" +
                    "   (5, 21, 0), (5, 22, 0), (5, 23, 0), (5, 24, 0), (5, 25, 0), (5, 26, 0), (5, 27, 0), (5, 28, 0), (5, 29, 0), (5, 30, 0), (5, 31, 0), (5, 32, 0), (5, 33, 0), (5, 34, 0), (5, 35, 0), (5, 36, 0), (5, 37, 0), (5, 38, 0), (5, 39, 0), (5, 40, 0), \n" +
                    "   (5, 41, 0), (5, 42, 0), (5, 43, 0), (5, 44, 0), (5, 45, 0), (5, 46, 0), (5, 47, 0), (5, 48, 0), (5, 49, 0), (5, 50, 0), (5, 51, 0), (5, 52, 0), (5, 53, 0), (5, 54, 0), (5, 55, 0), (5, 56, 0), (5, 57, 0), (5, 58, 0), (5, 59, 0), (5, 60, 0), \n" +
                    "   (5, 61, 0), (5, 62, 0), (5, 63, 0), (5, 64, 0), (5, 65, 0), (5, 66, 0), (5, 67, 0), (5, 68, 0), (5, 69, 0), (5, 70, 0), (5, 71, 0), (5, 72, 0), (5, 73, 0), (5, 74, 0), (5, 75, 0), (5, 76, 0), (5, 77, 0), (5, 78, 0), (5, 79, 0), (5, 80, 0), \n" +
                    "   (5, 81, 0), (5, 82, 0), (5, 83, 0), (5, 84, 0), (5, 85, 0), (5, 86, 0), (5, 87, 0), (5, 88, 0), (5, 89, 0), (5, 90, 0), (5, 91, 0), (5, 92, 0), (5, 93, 0), (5, 94, 0), (5, 95, 0), (5, 96, 0), (5, 97, 0), (5, 98, 0), (5, 99, 0), (5, 100, 0), \n" +
                    "   (5, 101, 0), (5, 102, 0), (5, 103, 0), (5, 104, 0), (5, 105, 0), (5, 106, 0), (5, 107, 0), (5, 108, 0), (5, 109, 0), (5, 110, 0), (5, 111, 0), (5, 112, 0), (5, 113, 0), (5, 114, 0), (5, 115, 0), (5, 116, 0), (5, 117, 0), (5, 118, 0), (5, 119, 0), (5, 120, 0),\n" +

                    "   (6, 1, 0), (6, 2, 0), (6, 3, 0), (6, 4, 0), (6, 5, 0), (6, 6, 0), (6, 7, 0), (6, 8, 0), (6, 9, 0), (6, 10, 0), (6, 11, 0), (6, 12, 0), (6, 13, 0), (6, 14, 0), (6, 15, 0), (6, 16, 0), (6, 17, 0), (6, 18, 0), (6, 19, 0), (6, 20, 0), \n" +
                    "   (6, 21, 0), (6, 22, 0), (6, 23, 0), (6, 24, 0), (6, 25, 0), (6, 26, 0), (6, 27, 0), (6, 28, 0), (6, 29, 0), (6, 30, 0), (6, 31, 0), (6, 32, 0), (6, 33, 0), (6, 34, 0), (6, 35, 0), (6, 36, 0), (6, 37, 0), (6, 38, 0), (6, 39, 0), (6, 40, 0), \n" +
                    "   (6, 41, 0), (6, 42, 0), (6, 43, 0), (6, 44, 0), (6, 45, 0), (6, 46, 0), (6, 47, 0), (6, 48, 0), (6, 49, 0), (6, 50, 0), (6, 51, 0), (6, 52, 0), (6, 53, 0), (6, 54, 0), (6, 55, 0), (6, 56, 0), (6, 57, 0), (6, 58, 0), (6, 59, 0), (6, 60, 0), \n" +
                    "   (6, 61, 0), (6, 62, 0), (6, 63, 0), (6, 64, 0), (6, 65, 0), (6, 66, 0), (6, 67, 0), (6, 68, 0), (6, 69, 0), (6, 70, 0), (6, 71, 0), (6, 72, 0), (6, 73, 0), (6, 74, 0), (6, 75, 0), (6, 76, 0), (6, 77, 0), (6, 78, 0), (6, 79, 0), (6, 80, 0), \n" +
                    "   (6, 81, 0), (6, 82, 0), (6, 83, 0), (6, 84, 0), (6, 85, 0), (6, 86, 0), (6, 87, 0), (6, 88, 0), (6, 89, 0), (6, 90, 0), (6, 91, 0), (6, 92, 0), (6, 93, 0), (6, 94, 0), (6, 95, 0), (6, 96, 0), (6, 97, 0), (6, 98, 0), (6, 99, 0), (6, 100, 0), \n" +
                    "   (6, 101, 0), (6, 102, 0), (6, 103, 0), (6, 104, 0), (6, 105, 0), (6, 106, 0), (6, 107, 0), (6, 108, 0), (6, 109, 0), (6, 110, 0), (6, 111, 0), (6, 112, 0), (6, 113, 0), (6, 114, 0), (6, 115, 0), (6, 116, 0), (6, 117, 0), (6, 118, 0), (6, 119, 0), (6, 120, 0)\n"

    };

    public static final String[] INSERT_RESERVATION_QUERIES = {
            "insert into reservation (id, num_confirmation, date_reservation, id_utilisateur) values\n" +
                    "   (1, 206553440898, '2018-02-21 08:12', 2),\n" +
                    "   (2, 549056770312, '2018-02-27 16:31', 1),\n" +
                    "   (3, 679397883628, '2018-03-04 17:45',5),\n" +
                    "   (4, 259410172631, '2018-03-12 19:20', 3),\n" +
                    "   (5, 841664029474, '2018-03-14 12:39', 7),\n" +
                    "   (6, 100570981564, '2018-03-21 09:03', 6),\n" +
                    "   (7, 827361872385, '2018-03-29 07:22', 4),\n" +
                    "   (8, 261203157625, '2018-04-02 19:12', 5)"

    };

    public static final String[] INSERT_CARTE_CREDIT_QUERIES = {
            "insert into carte_credit (id_utilisateur, nom_utilisateur, nom_carte, numero, date_expiration, code) values\n" +
                    "   (3, 'Felicienne Gendron', 'VISA', '7394127927901230', '2019-02-01 00:00',  664),\n" +
                    "   (5, 'Claude Fortin', 'Master Card', '3792751870812842', '2020-08-01 00:00',  194), \n" +
                    "   (1, 'Arnaud Fournier', 'Master Card', '4826802799200312', '2018-12-01 00:00',  521),\n" +
                    "   (4, 'Saville Lacharité', 'VISA', '4548992196327140', '2021-07-01 00:00', 249)\n"

    };

    public static final String[] INSERT_PAIEMENT_QUERIES = {

            "insert into paiement(montant, date_paiement, id_reservation) values " +
                    "   (115.0, '2018-02-21 08:12:00', 1),\n" +
                    "   (115.0, '2018-02-27 16:31:00', 2),\n" +
                    "   (230.0, '2018-03-04 17:45:00', 3),\n" +
                    "   (92.0, '2018-03-12 19:20:00', 4),\n" +
                    "   (92.0, '2018-03-14 12:39:00', 5),\n" +
                    "   (80.50, '2018-03-21 09:03:00', 6),\n" +
                    "   (80.50, '2018-03-29 07:22:00', 7),\n" +
                    "   (92.0, '2018-04-02 19:12:00', 8)\n"
    };


    public static final String[] UPDATE_SPECTACLE_SIEGE_QUERIES = {
            "update spectacle_siege set reserve=1 where id_siege=47 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=48 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=51 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=52 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=33 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=34 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=35 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=36 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=63 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=64 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=81 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=82 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=107 and id_spectacle=1",
            "update spectacle_siege set reserve=1 where id_siege=108 and id_spectacle=1"

    };
    public static final String[] INSERT_RESERVATION_SPECTACLE_SIEGE_QUERIES = {
            "insert into reservation_spectacle_siege (id_reservation, id_spectacle, id_siege) values\n" +
                    "   (1, 1, 47),\n" +
                    "   (1, 1, 48),\n" +
                    "   (2, 1, 51),\n" +
                    "   (2, 1, 52),\n" +
                    "   (3, 1, 33),\n" +
                    "   (3, 1, 34),\n" +
                    "   (3, 1, 35),\n" +
                    "   (3, 1, 36),\n" +
                    "   (4, 1, 63),\n" +
                    "   (4, 1, 64),\n" +
                    "   (5, 1, 81),\n" +
                    "   (5, 1, 82),\n" +
                    "   (6, 1, 107),\n" +
                    "   (6, 1, 108)"
    };


}