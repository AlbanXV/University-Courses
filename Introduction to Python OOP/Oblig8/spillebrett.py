#Spillebrett

#Importerer:
from random import randint
from celle import Celle

class Spillebrett:

    #Konstruktør med 2 parametre: "rader", "kolonner"
    def __init__(self, rader, kolonner):
        self._rader = rader
        self._kolonner = kolonner
        #lager en instansvariabel som er rader ganget med kolonner
        self._rutenett = rader * kolonner #self._rader * self._kolonner
        self._generasjonNumb = 0 #Setter generasjon nummer til 0
        self._liste = [] #Lager enda en instansvariabel som blir brukt som en liste
        #self._kallInn = self.generer()


        #Lager en for-løkke som går gjennom _rader
        for i in range(self._rader):
            #Henter inn [] i _liste
            self._liste.append([])
            #Enda en for-løkke som går gjennom _kolonner
            for j in range(self._kolonner):
                #Klassen Celle blir hentet inn som verdier i _liste
                self._liste[i].append(Celle())
        #Aktiverer metoden "generer"
        self.generer()

    #Metode for å lage brettet:
    def tegnBrett(self):
        #Bruker for-løkke som går gjennom _rader
        for i in range(0, self._rader):
            #Enda en for-løkke som går gjennom _kolonner
            for j in range(0, self._kolonner):
                #Printer ut listen: _liste med [i][j] i seg (rad og kolonne)
                #som også blir hentet fra henteStatusTegn() for å gi
                #cellene tegnet deres om de er levende eller døende
                #Bruker end = "" for å unngå linjeskift etter hver utskrift
                print(self._liste[i][j].henteStatusTegn(), end = "")
            print()
        #Printer ut informasjon om hvilken generasjon brettet er i (_generasjonNumb)
        #og hvor mange celler som lever (finnAntallLevende())
        print("Generasjon:", self._generasjonNumb, "- Antall levende celler:", self.finnAntallLevende())

    #Metode som oppdaterer hvert generasjon:
    def oppdatering(self):
        #Henter instansvariabelen _generasjonNumb og legger til 1
        self._generasjonNumb += 1

        #Lager 2 tomme lister:
        doende_livlig = []
        levende_dead = []

        #lager en for-løkke som går gjennom _rader:
        for x in range(0, self._rader):
            #Lager enda en for-løkke som går gjennom _kolonner
            for y in range(0, self._kolonner):
                #Lager variabelen "a" med verdien 0
                #som skal telle celler som lever
                a = 0
                #lager en ny for-løkke som går gjennom metoden finnNabo()
                #med løkke-variablene "x" og "y":
                for i in range(len(self.finnNabo(x, y))):
                    #Hvis nabocellene fra finnNabo i rad og kolonne er levende
                    if self.finnNabo(x,y)[i].erLevende():
                        #Legger til ett ekstra verdi i variabelen "a"
                        a += 1
                #Hvis cellene _liste i rad og kolonne er i erLevende():
                if self._liste[x][y].erLevende():
                    #Og hvis "a" er mindre enn 2, eller stoerre enn 3:
                    if a < 2 or a > 3:
                        #de levende blir dead: ved å legge cellene fra rad
                        #og kolonne fra _liste inn i tomme listen: levende_dead
                        levende_dead.append(self._liste[x][y])
                else:
                    #Hvis det er motsatt, altså "a" er lik 3
                    if a == 3:
                        #De doende blir levende ved å legge cellene i rad og kollone
                        #fra _liste inn i den andre tomme listen: doende_livlig
                        doende_livlig.append(self._liste[x][y])

        #Lager en for-løkke som går gjennom levende_dead:
        for i in levende_dead:
            #Setter "i" - løkke-variabelen i metoden settDoed()
            i.settDoed()
        #Lager en for-løkke som går gjennom listen doende_livlig:
        for i in doende_livlig:
            #Setter "i" i metoden settLevende()
            i.settLevende()

        #Returnerer _generasjonNumb
        return self._generasjonNumb

    #Metode for å telle antall levende celler i et spillebrett:
    def finnAntallLevende(self):
        #Lager en tom instansvariabel, der verdien er 0
        self._levende = 0

        #Lager en for-løkke som går gjennom _rader:
        for i in range(0, self._rader):
            #Lager en for-løkke som går gjennom _kolonner:
            for j in range(0, self._kolonner):
                #Hvis løkke-variablene i _liste er levende i metoden erLevende():
                if self._liste[i][j].erLevende() is True: #"is True" kan fjernes, blir likt uansett
                    #Legger til ekstra verdi, altså 1, slik at den går ett verdi opp
                    #i _levende
                    self._levende += 1

        #Returnerer _levende
        return self._levende

    #Metode som skal generere tilfeldige celler:
    def generer(self):
        #Lager for-løkke som går gjennom _rader
        for i in range(0, self._rader):
            #Enda en for-løkke som går gjennom _kolonner
            for j in range(0, self._kolonner):
                #Lager variabelen random
                #Bruker randint(0,3) = 1/3 sjans
                random = randint(0,3)
                #hvis random er lik 3
                if random == 3:
                    #Henter inn celle status fra metoden settLevende
                    #lagrer den inn i self_liste [i] og [j] (rad og kolonne)
                    self._liste[i][j].settLevende()


    #Lager metode som finner nabo-celler:
    def finnNabo(self, rad, kolonne):
        #Lager en tom liste:
        nabo_liste = []

        #En for-løkke som går gjennom fra -1 til 2
        for i in range(-1, 2):
            #Enda en for-løkke som gjør det samme, bare annerledes variabel (j)
            for j in range(-1, 2):
                #Lager variabelen nabo_rad + nabo_kolonne
                #som legger til verdiene (i og j) med parametrene (rad og kolonne)
                nabo_rad = rad + i
                nabo_kolonne = kolonne + j

                #Setter variabelen o til True når nabo cellene er ulike
                o = True

                #Hvis nabo_rad og nabo_kolonne er lik rad og kolonne:
                if nabo_rad == rad and nabo_kolonne == kolonne:
                    #o blir til False
                    o = False
                #Hvis nabo_rad er lik eller større enn _rader eller at den er mindre enn 0
                #i brettet:
                if nabo_rad >= self._rader or nabo_rad < 0:
                    #o blir til False
                    o = False
                #Samme prosess som nabo_rad:
                if nabo_kolonne >= self._kolonner or nabo_kolonne < 0:
                    #o blir til False
                    o = False
                if o: #Hvis o er True:
                    #nabo_rad og nabo_liste i _liste blir lagt til i nabo_liste
                    nabo_liste.append(self._liste[nabo_rad][nabo_kolonne])

        #nabo_liste blir returnert
        return nabo_liste
