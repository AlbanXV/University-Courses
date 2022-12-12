#include <string.h>

int distance_between(char *s, char c) {
  //lager variabler:
  int i = 0;
  //posisjon:
  int first = -1;

  //sjekker hvis strengen ikke er tom:
  while (s[i] != '\0') {
    //Lager en for-loekke som gaar gjennom lengden til strengen:
    for (i = 0; i < strlen(s); i++) {
      //sjekker hvis karakteren c er i s
      if (s[i] == c) {
        //hvis ja:
        //sjekker hvis first er -1 (foerst i strengen):
        if (first == -1) {
          //first faar verdien til indexen (i):
          first = i;
        }
        else {
          //returnerer avstanden i antall tegn mellom foerste og andre forekomst:
          return i - first;
        }
      }
    }
  }
  //returnerer -1 hvis c ikke dukker opp 2 ganger i strengen:
  return -1;
}
