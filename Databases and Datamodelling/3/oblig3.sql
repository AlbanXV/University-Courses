--Oppgave 1

CREATE TABLE tog (
    togNr INT PRIMARY KEY NOT NULL,
    startStasjon VARCHAR(50) NOT NULL,
    endeStasjon VARCHAR(50) NOT NULL,
    ankomstTid TIME NOT NULL
);

CREATE TABLE togTabell (
    togNr INT NOT NULL FOREIGN KEY REFERENCES tog(togNr),
    avgangsTid TIME NOT NULL,
    stasjon VARCHAR(50) NOT NULL,
    PRIMARY KEY(togNr, avgangsTid)
);

CREATE TABLE plass (
    dato DATE NOT NULL,
    togNr INT NOT NULL FOREIGN KEY REFERENCES tog(togNr),
    vognNr INT NOT NULL,
    plassNr INT NOT NULL,
    vindu boolean NOT NULL,
    ledig boolean NOT NULL,
    PRIMARY KEY(dato, togNr, vognNr, plassNr)
);


