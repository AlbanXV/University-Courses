from sang import Sang

class Spilleliste:

    #Lager instansmetode med parameteren "listenavn"
    def __init__(self, listenavn):
        #Lager en variabel som inneholder en tom liste
        self._sanger = []
        #Lager variabelen fra parameteren
        self._navn = listenavn

    #Lager en metode som leser musikkfilen
    def lesFraFil(self, filnavn):
        #Lager en variabel som åpner "filnavn"
        fil = open(filnavn)
        #Bruker for-løkke som leter gjennom filen
        for i in fil:
            #Lager en variabel som splitter musikklisten i to
            #etter ";"-tegnet
            allData = i.strip().split(";")
            #den foerste delen:
            tittel = allData[0]
            #den andre delen:
            artist = allData[1]
            #Lager en variabel som setter disse delene sammen
            #men ved å koble dem med klassen Sang
            playlist = Sang(tittel, artist)
            #Legger playlist-variabelen i listen self._sanger
            self._sanger.append(playlist)

        #Lager en variabel som starter metoden spillAlle()
        spill = self.spillAlle()

        #Lukker filen til slutt
        fil.close()

    #Lager en metode som legger til ny sang
    def leggTilSang(self, nySang):
        #Legger til ny sang ved å bruke "append": "nySang" i listen
        self._sanger.append(nySang)

    #Lager en metode for å fjerne sang
    def fjernSang(self, sang):
        #Bruker .remove for å fjerne "sang"
        self._sanger.remove(sang)

    #def __repr__(self):
    #    return self._navn + "\n" + str(self._sanger)

    #Lager en metode som spiller enkelt-sang
    def spillSang(self, sang):
        #Printer ut tittelen og artisten ved å bruke parametret "sang" i dem
        sang.spill()

    #Lager en metode som spiller alle sanger
    def spillAlle(self):
        #Bruker for-løkke som leter gjennom listen "self._sanger"
        for sang in self._sanger:
            #Printer ut alle sangene: tittel + artist
            sang.spill()

    #Lager en metode som finner sang
    def finnSang(self, tittel):
        #lager variabelen "j" som har verdien "False"
        j = False
        #bruker for-løkke som går gjennom self._sanger (listen)
        for i in self._sanger:
            #Hvis "tittel" er lik tittelen som finnes i lista
            if i.sjekkTittel(tittel):
                #j blir til true
                j = True
        #Hvis j blir til true, blir "j" returnert
        if j == True:
            return j
        #Det blir returnert "None" hvis j = False
        return None

    #Lager en metode som henter utvalgt artist
    def hentArtistUtvalg(self, artistnavn):
        #Lager en tom liste der navnet til variabelen er "liste"
        liste = []

        #Bruker for-løkke som leter gjennom alle sangene i lista
        for i in self._sanger:
            #Hvis "artistnavn" stemmer med navnet til artisten i listen:
            if i.sjekkArtist(artistnavn):
                #alt av artisten fra listen "self._sanger" blir lagt til
                #i listen "liste"
                liste.append(i)
        #Hvis ikke:
        else:
            #dette blir printet ut:
            print("Ikke funnet.")
