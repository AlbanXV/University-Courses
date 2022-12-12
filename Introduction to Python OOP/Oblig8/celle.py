#Celle

class Celle:

    #Konstruktør
    def __init__(self):
        #Lager instansvariabler:
        #setter statusen til en celle til: 0
        #0 = "Død" | 1 = "levende"
        self._celle_status = 0

    #Endre status
    #Metode for å sette inn døde:
    def settDoed(self):
        #Celle status til 0
        self._celle_status = 0

    #Metode for å sette levende:
    def settLevende(self):
        #Cellens status blir til 1 (Levende)
        self._celle_status = 1

    #Hente status
    def erLevende(self):
        #Hvis celle er levende, skal den returnere True
        if self._celle_status == 1:
            return True
        #Hvis celle ikke er levende, returneres False
        else:
            return False

    #Metode for å hente inn status-tegnet til celle:
    def henteStatusTegn(self):
        #Hvis cellens status er 1 (levende):
        if self._celle_status == 1:
            #Cellen får "O" som tegn
            self._tegnRep = "O"
        #Hvis cellen er ikke levende (død):
        else:
            #Cellen får tegnet "."
            self._tegnRep = "."
        #_tegnRep blir returnert
        return self._tegnRep
