USE simple_k_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE districts;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO districts (name, state_district_id, image, url)
VALUES ('ALAMO HEIGHTS ISD', 'TX-015901', '/img/district_logo/alamo_heights_isd.png', 'http://www.ahisd.net/'),
       ('EAST CENTRAL ISD', 'TX-015911', '/img/district_logo/east_central_isd.png', 'http://www.ecisd.net/'),
       ('EDGEWOOD ISD', 'TX-015905', '/img/district_logo/edgewood_isd.png', 'https://www.eisd.net/'),
       ('FLORESVILLE ISD', 'TX-247901', '/img/district_logo/floresville_isd.jpg', 'http://www.fisd.us/'),
       ('FORT SAM HOUSTON ISD', 'TX-015914', '/img/district_logo/fort_sam_houston_isd.jpg', 'http://www.fshisd.net/'),
       ('HARLANDALE ISD', 'TX-015904', '/img/district_logo/harlandale_isd.jpg', 'http://www.harlandale.net/'),
       ('JUDSON ISD', 'TX-015916', '/img/district_logo/judson_isd.png', 'https://www.judsonisd.org/'),
       ('LACKLAND ISD', 'TX-015913', '/img/district_logo/lackland_isd.png', 'http://www.lacklandisd.net/'),
       ('MEDINA VALLEY ISD', 'TX-163908', '/img/district_logo/medina_valley_isd.png', 'https://www.mvisd.com/'),
       ('NORTH EAST ISD', 'TX-015910', '/img/district_logo/north_east_isd.jpg', 'https://www.neisd.net/'),
       ('NORTHSIDE ISD', 'TX-015915', '/img/district_logo/northside_isd.png', 'https://nisd.net/'),
       ('RANDOLPH FIELD ISD', 'TX-015906' ,'/img/district_logo/randolph_field_isd.png', 'https://www.rfisd.net/'),
       ('SAN ANTONIO ISD', 'TX-015907', '/img/district_logo/san_antonio_isd.png', 'http://www.saisd.net/main/'),
       ('SCHERTZ CIBOLO UNIVERSAL CITY ISD', 'TX-ROCK', '/img/district_logo/schertz-cibolo-universal_city_isd.png', 'http://www.scuc.txed.net/'),
       ('SOUTH SAN ANTONIO ISD', 'TX-015908', '/img/district_logo/south_san_isd.png', 'https://www.southsanisd.net/'),
       ('SOUTHSIDE ISD', 'TX-015917', '/img/district_logo/southside_isd.png', 'https://southsideisd.org/'),
       ('SOUTHWEST ISD', 'TX-015912', '/img/district_logo/southwest_isd.jpg', 'http://www.swisd.net/');