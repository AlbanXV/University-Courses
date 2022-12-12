#Oppgave 1: Klassen Sang

#Lager klasse
class Sang:

    #lager instansmetode med parametrene "tittel" og "artist"
    def __init__(self, tittel, artist):
        #Lager variablene fra parametrene
        self._tittel = tittel
        #I artist blir setningene splittet hvis det er flere artister enn bare en
        self._artist = artist

    #Lager en metode som spiller informasjon om tittel og artisten
    def spill(self):
        print("Spiller", self._tittel, "av", self._artist)

    #Lager en metode som sjekker om artisten stemmer:
    def sjekkArtist(self, navn):
        #setter verdien til j = False
        j = False

        #Lager en for-løkke som leter gjennom self._artist
        for i in self._artist.split(" "):
            #Hvis verdien i "navn" stemmer med informasjonen fra
            #self._artist, blir verdien til j = True
            if i in navn:
                j = True
        #Hvis j blir True, blir True returnert
        if j == True:
            return j

    #lager en metode som sjekker om tittelen stemmer
    def sjekkTittel(self, tittel):
        #Bruker if-setning: hvis tittelen (verdien) stemmer
        #med self._tittel (med lower()), blir True returnert
        if tittel.lower() == self._tittel.lower():
            return True
        #Hvis det ikke stemmer: blir False returnert
        else:
            return False


    #Lager en metode som sjekker begge om de stemmer
    def sjekkArtistOgTittel(self, tittel, artist):

        #j får verdien False
        j = False
        #for-løkke som går gjennom både self._tittel og self._artist
        for i in self._tittel and self._artist.split(" "):
            #hvis verdien i tittel og artist stemmer, blir j til True
            if i in tittel and artist:
                j = True
        #hvis j blir til true, returneres True
        if j == True:
            return True
        return False
