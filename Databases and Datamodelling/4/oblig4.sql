-- Oppgave 0

SELECT COUNT(*) FROM film;

-- Oppgave 1
SELECT filmcharacter as rollefigurnavn, COUNT(*) as antall FROM filmcharacter
GROUP BY filmcharacter HAVING COUNT(*) > 2000 ORDER BY COUNT(*) DESC;
/*
Henter alle filmkarakterer som rollefigurnavn og deretter hvor mange rollefigurer det finnes (count(*)) som antall.
Deretter grupperer disse to kolonnene. Henter bare de som forekommer over 2000 ganger og bruker ORDER BY COUNT(*) DESC, for at
tabellen skal gå i synkende rekkefølge (fra høy til lav)
*/

-- Oppgave 2
-- a)
SELECT film.title, film.prodyear FROM film
INNER JOIN filmparticipation AS fp ON film.filmid = fp.filmid
INNER JOIN person ON fp.personid = person.personid
WHERE fp.parttype = 'director' 
AND person.firstname = 'Stanley' 
AND person.lastname = 'Kubrick';
/*
Henter film tittel og prodyear fra film og bruker innerjoin på filmparticipation mellom filmid fra film og filmparticipation
deretter enda en inner join men på person mellom personid fra filmparticipation og person og finner ut hvem som har rollen som
direktør og som har navnet Stanley Kubrick.
*/

-- b)
SELECT film.title, film.prodyear FROM film
NATURAL JOIN filmparticipation AS fp
NATURAL JOIN person
WHERE fp.parttype = 'director' 
AND person.firstname = 'Stanley' 
AND person.lastname = 'Kubrick';
/*
Henter film tittel og prodyear fra film. Bruker natural join som joiner begge filmparticipation og person og deretter finner
hvem som er director og har navnet Stanley Kubrick i databasen.
*/

-- c)
SELECT film.title, film.prodyear FROM film, filmparticipation AS fp, person
WHERE film.filmid = fp.filmid
AND fp.personid = person.personid
AND fp.parttype = 'director'
AND person.firstname = 'Stanley'
AND person.lastname = 'Kubrick';
/*
På en implissit join så bruker jeg IKKE noen typer JOINS og erstatter JOINS med WHERE og fortsetter med AND istedenfor.
*/

-- Oppgave 3
SELECT person.firstname ||' '|| person.lastname AS fullnavn, person.personid, film.title, fchar.filmcharacter, filmcountry.country
FROM filmparticipation AS fp
NATURAL JOIN person
NATURAL JOIN filmcharacter AS fchar
NATURAL JOIN filmcountry
NATURAL JOIN film
WHERE person.firstname = 'Ingrid' AND fchar.filmcharacter = 'Ingrid';
/*
Henter fornavn og etternavn og konkatenerer begge slik at det blir fulltnavn. Henter personid, filmtittel, filmkarakter og hvilket land 
filmen ble produsert i. Henter dem fra filmparticipation. Deretter bruker jeg natural join på de som jeg har hentet fra
første linjen: person, filmcharacter, filmcountry og film. Skal koble dem sammen.
Til slutt bruker jeg WHERE for å finne en skuespiller som heter Ingrid og som har spilt en rolle til en karakter som
heter 'Ingrid'
*/

-- Oppgave 4
SELECT film.filmid, film.title, COUNT(fg.genre) AS Sjanger FROM film
LEFT JOIN filmgenre fg ON film.filmid = fg.filmid
WHERE film.title LIKE '%Antoine %'
GROUP BY film.filmid, film.title;
/*
Henter filmid, tittel og antall sjangre (inkludert filmer uten sjanger) fra film. Bruker LEFT JOIN for å returnere alle verdier
fra film og som matcher verdier fra andre tabellen: filmgenre, og returnerer verdiene som matcher hverandre fra filmgenre.
Vi får 17 rader hvor 4 av dem er større enn 0. Du hadde fått 4 rader HVIS man bruker f.eks. NATURAL JOIN pga. sjangre som er registrert
*/

-- Oppgave 5
SELECT film.title, fp.parttype, COUNT(fp.parttype) AS antall_deltagere FROM filmparticipation fp
NATURAL JOIN film
NATURAL JOIN filmitem
WHERE film.title LIKE '%Lord of the Rings%' 
AND filmitem.filmtype LIKE 'C'
GROUP BY film.title, fp.parttype;
/*
Henter filmtittel, typer deltagelser i filmen og antall sjangere fra filmparticipation.
Bruker NATURAL JOIN som joiner fp med film og deretter filmitem som skal inneholde filmtypen "C" i tabellen.
Deretter bruker jeg WHERE for å finne filmen "Lord of The Rings" fra kino, deretter grupperer jeg dem ved film tittel
og film deltagelsestype.
*/

-- Oppgave 6
SELECT film.title, film.prodyear FROM film
WHERE prodyear = (SELECT MIN(prodyear) FROM film)
GROUP BY film.title, film.prodyear;
/*
Henter filmtittel og produksjonsår fra film. Bruker WHERE på prodyear og bruker SELECT MIN som skal finne filmer som ble
minst utgitt i løpet av de årene. Deretter grupperer jeg dem ved filmtittel og film.prodyear. Minst filmer ble utgitt i: 1888.
*/

-- Oppgave 7
SELECT film.title, film.prodyear FROM film, filmgenre AS genre1, filmgenre AS genre2
WHERE film.filmid = genre1.filmid 
AND genre1.filmid = genre2.filmid
AND genre1.genre = 'Film-Noir' 
AND genre2.genre = 'Comedy';
/*
Henter filmtittel og produksjonsår fra film og deretter filmgenre men to ganger som skal brukes for to ulike sjanger.
Bruker WHERE når en filmid har en filmsjanger1 som også har en filmsjanger 2 der sjangrene er "Film-Noir" og "Comedy".
*/

-- Oppgave 8
SELECT film.title, film.prodyear FROM film
WHERE prodyear = (SELECT MIN(prodyear) FROM film)
GROUP BY film.title, film.prodyear
UNION ALL
SELECT film.title, film.prodyear FROM film, filmgenre AS genre1, filmgenre AS genre2
WHERE film.filmid = genre1.filmid 
AND genre1.filmid = genre2.filmid
AND genre1.genre = 'Film-Noir' 
AND genre2.genre = 'Comedy';
/*
Kopierer fra oppgave 6 og 7, men mellom 6 og 7 blir UNION ALL brukt. UNION ALL kombinerer begge og gir 5 rader (2+3)
*/

-- Oppgave 9
SELECT film.title, film.prodyear FROM film
NATURAL JOIN filmparticipation AS fp
NATURAL JOIN person
WHERE person.firstname = 'Stanley' 
AND person.lastname = 'Kubrick' 
AND fp.parttype = 'director'
INTERSECT
SELECT film.title, film.prodyear FROM film
NATURAL JOIN filmparticipation AS fp
NATURAL JOIN person
WHERE person.firstname = 'Stanley' 
AND person.lastname = 'Kubrick' 
AND fp.parttype = 'cast';
/*
Velger filmtittel og produkjsonsår fra film og bruker NATURAL JOIN på filmparticipation og deretter person.
Bruker WHERE for å finne Stanley Kubrick der han er en 'director' i filmer.
Deretter bruker jeg INTERSECT operatøren som kombinerer begge SELECT spørringene, men returnerer bare rader fra den første
SELECT som er identisk med rader fra andre SELECT spørringen. I andre SELECT-spørringen: eneste forskjell er parttype som er
forandret til 'cast'.
*/

-- Oppgave 10
SELECT series.maintitle, filmrating.votes FROM filmrating
INNER JOIN series ON filmrating.filmid = series.seriesid
WHERE filmrating.votes > 1000 AND filmrating.rank = (SELECT MAX(filmrating.rank) FROM filmrating
WHERE filmrating.votes > 1000)
GROUP BY series.maintitle, filmrating.votes;
/*
Velger serie navn og deres brukerstemmer fra filmrating. Bruker INNER JOIN mellom filmrating og series.
Bruker WHERE for å finne brukerstemmer flere enn 1000 og der scoren (rank) er høyest ved å bruke SELECT MAX som
skal finne de største verdiene. Til slutt grupperer jeg series.maintitle og filmrating.votes.
*/

-- Oppgave 11
SELECT fc.country FROM filmcountry AS fc
GROUP BY fc.country HAVING COUNT(fc.country) = 1;
/*
Velger land fra tabellen filmcountry. Grupperer fc.country og bruker HAVING COUNT på country der verdien er satt til 1.
Dette skal finne land som forekommer bare en gang i tabellen filmcountry.
*/

-- Oppgave 12
WITH unik AS (
SELECT fchar.partid, u.filmcharacter FROM ( SELECT filmcharacter, COUNT(*) FROM filmcharacter
GROUP BY filmcharacter HAVING COUNT(*) = 1) AS u, filmcharacter AS fchar
WHERE u.filmcharacter = fchar.filmcharacter)
SELECT person.firstname ||' '|| person.lastname AS fullname, count(*) AS antall FROM person
NATURAL JOIN filmparticipation
NATURAL JOIN unik
GROUP BY fullname HAVING COUNT(*) > 199 ORDER BY antall DESC;
/*
For å finne skuespillere som har spilt figurer med unikt rollenavn i mer enn 199 filmer: velger jeg id-en til
filmcharacter og verdi til filmcharacter via u: som er ved å velge filmcharacter og teller til filmcharacter
som er gruppert og som skal være unik COUNT(*) = 1, altså et unikt navn som forekommer en gang. Deretter bruker jeg
WHERE etter å ha forklart hva "u" og filmcharacter, og skriver at u.filmcharacter = fchar.filmcharacter.
Deretter lager jeg en ny spørring som velger personens fornavn og etternavn og konkatenerer dem. Deretter bruker COUNT(*) som skal
bli brukt som antall ganger den har blitt brukt.
Bruker NATURAL JOIN på både filmparticipation og unik: hele første spørringen.
Deretter grupperer jeg fullname og bruker COUNT(*) som skal være større enn 199.
Til slutt bruker jeg ORDER BY antall DESC som skal vise tabellene i synkende rekkefølge (fra størst til minst).
*/

-- Oppgave 13
SELECT person.firstname ||' '|| person.lastname AS fullname FROM person
INNER JOIN filmparticipation as fp ON fp.personid = person.personid
INNER JOIN filmrating ON filmrating.filmid = fp.filmid
WHERE filmrating.votes > 60000 AND fp.parttype = 'director'
GROUP BY fullname, filmrating.rank HAVING filmrating.rank >= 8
EXCEPT
SELECT person.firstname ||' '|| person.lastname AS fullname FROM person
INNER JOIN filmparticipation as fp ON fp.personid = person.personid
INNER JOIN filmrating ON filmrating.filmid = fp.filmid
WHERE filmrating.votes > 60000 AND fp.parttype = 'director'
GROUP BY fullname, filmrating.rank HAVING filmrating.rank < 8;

/*
Velger personens fornavn og etternavn fra tabellen person der disse to blir konkatenert
Bruker INNER JOIN på både filmparticipation og filmrating.
Bruker WHERE som skal finne personer som har over 60 000 stemmer og personen er en "director".
Grupperer deretter personen med deres score (rank) der scoren er lik eller større enn 8.
Bruker deretter EXCEPT operatøren som gjør at den henter alle verdier fra første spørringen og returnerer dem i andre spørringen som ikke
dukker opp en gang til. Den fjerner også duplikater som EXCEPT ALL ikke gjør.
Deretter kopierer jeg første spørringen i den andre spørringen og forandrer bare på siste kodelinjen: filmrating.rank < 8.
Jeg gjør dette slik at ikke alle som har fått 8+ scores (rank) ikke tas med. Resultat blir at jeg får 49 rows.
*/