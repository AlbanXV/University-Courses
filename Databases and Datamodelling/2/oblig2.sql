/*
Oblig 2

Oppgave 2
*/
-- a)
SELECT * FROM timelistelinje WHERE timelistelinje.timelistenr = 3;
-- b)
SELECT COUNT(*) FROM timeliste;
--bruker COUNT(*) for å vise hvor mange timelister det finnes.
-- c)
SELECT * FROM timeliste WHERE timeliste.utbetalt IS null;
-- d)
SELECT COUNT(*) AS antall, COUNT(pause) AS antallmedpause FROM timelistelinje;
-- e)
SELECT * FROM timelistelinje WHERE pause IS null;

/*
Oppgave 3
*/
-- a)
SELECT SUM(varighet)/60 AS ikkeUtbetalt FROM timeliste AS t
INNER JOIN Varighet AS v ON t.timelistenr = v.timelistenr
WHERE t.utbetalt IS null;
--Gir SUM(...)/60 tabellnavn, og deler på 60 fordi varigheten er i minutter
--Bruker INNER JOIN for aa koble Varighet (v) mellom begge timelistenr som finnes i t og v (returnere matchende verdier fra begge tabeller)
-- b)
SELECT DISTINCT t.timelistenr, t.beskrivelse FROM timeliste AS t
INNER JOIN timelistelinje AS l
ON (t.timelistenr = l.timelistenr)
WHERE l.beskrivelse LIKE '%Test%' or l.beskrivelse LIKE '%test%';
--Bruker DISTINCT for aa fjerne duplikat
--INNER JOIN for aa koble mellom timeliste og timelistenr
--LIKE for aa sjekke om det stemmer beskrivelsen med det jeg har gitt 'test' eller 'Test'

--ALTERNATIV (UTEN JOIN):
SELECT DISTINCT t.timelistenr, t.beskrivelse FROM timeliste AS t
WHERE t.beskrivelse LIKE '%Test%' or t.beskrivelse LIKE '%test%';
-- c)
SELECT SUM(varighet)/60*200 AS utbetalt FROM timeliste AS t
INNER JOIN Varighet AS v
ON t.timelistenr = v.timelistenr
WHERE t.utbetalt IS NOT null;

/*
Oppgave 4
*/
-- a)
/*
Disse to spørringene gir ikke likt svar. Grunnen til det er på grunn av ulike JOINS som blir brukt.
Hos NATURAL JOIN er det slik at kolonner med samme navn vil kun dukke opp engang som er grunnen til at vi får 'count: 1'.
I dette tilfellet: timelistenr har samme navn på begge tabeller, 
mens hos INNER JOIN så er det slik at den returnerer matchende verdier fra begge tabellene, og vi får 'count: 34'.
/*
-- b)
/*
Disse to spørringene gir likt svar.
Hos NATURAL JOIN er det slik at den legger sammen kolonner som har samme navn, i dette tilfellet: timelistenr hos begge
tabellene (timeliste og varighet),
og hos INNER JOIN returnerer den identiske verdier fra begge kolonnene (timeliste og varighet) som er blitt koblet med INNER JOIN.
Dette fører til at disse to spørringene gir likt svar: 'count: 34'.
*/