#include <string.h>
#include <stdlib.h>

//henter metoden distance_between:
int distance_between(char* s, char c);

//Lager funksjonen:
char* string_between(char* s, char c) {
  //Lager variabler:
  int i = 0;
  //posisjon:
  int first = -1;

  //lager en while-loekke som sjekker at setningen ikke er tom (null):
  while (s[i] != '\0') {
    //Lager en for-loekke som gaar gjennom lengden til strengen s:
    for (i = 0; i < strlen(s); i++) {
      //Gjoer akkurat samme prosess som funksjonen distance_between.c:
      if (s[i] == c) {
        if (first == -1) {
          first = i;
        }
        //Hvis else:
        else {
          //Lager variabler:
          //lengde bruker funksjonen distance_between fra distance_between.c
          //og deretter minus 1 (posisjon: foerst)
          int lengde = distance_between(s, c) - 1;
          int j = 0;

          //Lager en char variabel som skal vaere en temp (hjelpemiddel) for variabelen lengde:
          char temp[lengde];
          //lager en variabel som bruker malloc for aa heap-allokere plass til
          //den nye strengen:
          char *ma = malloc(lengde+1);

          //Lager en for-loekke for posisjonen til index: i:
          for (i; first < i - 1; first++) {
            //char temp blir hjelpemiddel til tellern j og faar streng som er mellom foerst og andre
            //forekomst i strengen s:
            temp[j] = s[first + 1];
            j++;
          }
          //utenfor for-loekken:
          //temp til lengden faar \0 (NULL character)
          //som identifiserer karakterene i arrayen og slutten til arrayen:
          temp[lengde] = '\0';
          //returnerer en kopi av strengen, og temp som argument:
          return strcpy(ma, temp);
        }
      }
    }
  }
  //dersom c forekommer faerre enn 2 ganger i teksten: returnerer NULL:
  return NULL;
}
