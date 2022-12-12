#Hovedprogram
from spillebrett import Spillebrett

def main():

    #Lager to input-variabler ("x" og "y"):
    x = int(input("Oppgi dimensjonen til rad:\n"))
    y = int(input("Oppgi dimensjonen til kolonne:\n"))
    #Lager variabelen brett som henter inn klassen "Spillebrett"
    #som har argumentene (x,y) fra input-variablene
    brett = Spillebrett(x,y)

    #henter inn metoden tegnBrett for variabelen "brett"
    #som tegner spillebrettet
    brett.tegnBrett()

    #Lager en tom  variabel
    bruker_svar = ""

    #Lager while-løkke som skal repetere hvis brukeren ikke taster på "a" som avslutter programmet
    while bruker_svar != "q":
        #Ber brukeren svare på variabelen "bruker_svar"
        bruker_svar = input("[any key] - neste generasjon\n'q' - avslutt program\n")
        #Hvis brukerern taster alt unntatt "s", oppdatering() og tegnBrett()
        #blir aktivert
        brett.oppdatering()
        brett.tegnBrett()
        
#Aktiverer prosedyren:
main()
