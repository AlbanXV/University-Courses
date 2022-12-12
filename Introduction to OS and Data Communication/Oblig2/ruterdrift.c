#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "ruterdrift.h"

/* RUTER */

//Strukturerer en router:
struct Router {
  int id;
  unsigned char flagg;
  unsigned char mLengde;
  unsigned char tellerK; //teller for koblinger
  char model[249];

  //Oppretter en struct som skal inneholde koblinger:
  struct Router* koblinger[10];
};

//Initialiserer Router (peker):
struct Router** rts;

//Global variabel (N):
int N;

//Metode for aa finne ruter basert paa deres id:
struct Router* hentRuter(int id) {
  int i;

  for (i = 0; i < N; i++) {
    if (rts[i] != NULL && (rts[i] -> id == id)) {
      return rts[i];
    }
  }
  return NULL;
}

/* MAIN */

int main(int argc, char *argv[]) {
  //Hvis input ikke er 3 ([kodefil] [.dat] [.txt]):
  //feilmelding:
  if (argc != 3) {
    printf("Skriv <topology-fil> <kommando-fil>\n");
    return -1;
  }

  //Kaller paa metodene:
  int n = lesFil(argv[1]); //1. Leser ruter
  lesKommando(argv[2]); //2. Leser kommando
  skrivFil(argv[1]); //3. Skriver inn i ny fil
  fri_minne(&n); //4. Frigjor minne

  return 0;
}

//Metode for aa lese filen "commands.txt" (KOMMANDO):
int lesKommando(char *filnavn) {

  //Leser fil:
  FILE *fil = fopen(filnavn, "r");

  //Sjekker om fil eksisterer:
  //Hvis ikke:
  if (fil == NULL) {
    perror("Error - fil (Kommando)");
    return -1;
  }

  //Gir buffer en valgfri char verdi som er nok for string
  //som skal printes (slik at det er ikke mer characters enn det buffer har (som kan foere til crash)):
  char buffer[522];
  char *string;

  //Gaa gjennom data (lese linjene) og hente dem:
  while (fgets(buffer, 522, fil) != NULL) {
    string = strtok(buffer, " ");

    //print()
    if (strcmp(string, "print") == 0) {
      string = strtok(NULL, "\n");
      //Executer:
      print(atoi(string));
    }

    //Samme prosess for de ulike metodene:

    //sett_flagg()
    else if (strcmp(string, "sett_flagg") == 0) {
      string = strtok(NULL, " ");
      int id = atoi(string);

      string = strtok(NULL, " ");
      unsigned char flagg = (unsigned char) atoi(string);

      string = strtok(NULL, "\n");
      unsigned char verdi = (unsigned char) atoi(string);

      sett_flagg(id, flagg, verdi);
    }

    //sett_modell()
    else if (strcmp(string, "sett_modell") == 0) {
      string = strtok(NULL, " ");
      int id = atoi(string);

      string = strtok(NULL, "\n");
      sett_modell(id, string);
    }

    //finnes_rute()
    else if (strcmp(string, "finnes_rute") == 0) {
      string = strtok(NULL, " ");
      int r1 = atoi(string);

      string = strtok(NULL, "\n");
      int r2 = atoi(string);

      //sjekker om det eksisterer rute:
      if (finnes_rute(r1, r2) == 1) {
        printf("Eksisterer rute mellom %u og %u\n", r1, r2);
      }
      //Hvis ikke:
      else {
        printf("Eksisterer ikke rute mellom %u og %u\n", r1, r2);
      }
    }

    //legg_til_kobling()
    else if (strcmp(string, "legg_til_kobling") == 0) {
      string = strtok(NULL, " ");
      int id = atoi(string);

      string = strtok(NULL, "\n");
      unsigned char kobling = (unsigned char) atoi(string);

      legg_til_kobling(id, kobling);
    }
    //slett_ruter()
    else if (strcmp(string, "slett_ruter") == 0) {
      string = strtok(NULL, " ");
      int id = atoi(string);

      slett_ruter(id);
    }
  }
  fclose(fil);
  return 0;
}

//Metode for aa lese filen "topology.dat" (RUTER):
int lesFil(char *filnavn) {

  //Leser fil:
  FILE *fil = fopen(filnavn, "rb");

  //Sjekker om fil eksisterer:
  //Hvis ikke:
  if (fil == NULL) {
    perror("Error - fil (Ruter)");
    return -1;
  }

  //Leser N:
  int n = 0;
  fread(&n, sizeof(int), 1, fil);
  printf("\n******************");
  printf("\nTotalt rutere:%d\n", n);
  N = n;

  //Allokerer:
  rts = malloc(sizeof(struct Router*)*n);

  //Leser topology (data fra ruter-fil):
  int i;

  //Gaar gjennom n:
  for (i = 0; i < n; i++) {
    int id;
    unsigned char flagg;
    unsigned char lengde;
    char modell[249] = {0};

    fread(&id, sizeof(int), 1, fil);
    fread(&flagg, sizeof(unsigned char), 1, fil);
    fread(&lengde, sizeof(unsigned char), 1, fil);
    fread(&modell, 1, lengde + 1, fil);
    modell[lengde] = '\0';

    //Allokerer:
    rts[i] = malloc(sizeof(struct Router));
    rts[i] -> id = id;
    rts[i] -> flagg = flagg;
    rts[i] -> mLengde = lengde;
    rts[i] -> tellerK = 0;

    strcpy(rts[i] -> model, modell);
  }

  //Leser koblinger til ruter:
  int id1;
  int id2;

  while(fread(&id1, sizeof(int), 1, fil) && fread(&id2, sizeof(int), 1, fil)) {
    struct Router* r1 = hentRuter(id1);
    struct Router* r2 = hentRuter(id2);

    r1 -> koblinger[r1 -> tellerK] = r2;
    r1 -> tellerK++;
  }

  fclose(fil);
  return n;
}

//Metode for aa skrive inn ny fil (NY-RUTER):
int skrivFil() {

  //Leser ny fil med w+ som skal gi tilgang til aa skrives i:
  FILE *fil = fopen("new-topology.dat", "w+");

  //Sjekker om fil eksisterer:
  //Hvis ikke:
  if (fil == NULL) {
    perror("Error - fil (Write)");
    return -1;
  }

  //Skriver:
  fwrite(&N, 4, 1, fil);
  //fwrite("\0", sizeof(char), 1, fil);

  int i;

  //Skriver ruter data:
  for (i = 0; i < N; i++) {
    if (rts[i] != NULL) {
      struct Router* ny = rts[i];

      fwrite(&ny -> id, sizeof(unsigned int), 1, fil);
      fwrite(&ny -> flagg, sizeof(unsigned char), 1, fil);
      fwrite(&ny -> mLengde, sizeof(unsigned char), 1, fil);
      fwrite(&ny -> model, rts[i] -> mLengde + sizeof(char), 1, fil);
      fwrite("\0", sizeof(char), 0, fil);
    }
  }

  //Skriver ruter koblinger:
  for (i = 0; i < N; i++) {
    if (rts[i] != NULL) {
      int j;
      struct Router* ny = rts[i];

      //Gaar gjennom telleren bak koblingene:
      for (j = 0; j < ny -> tellerK; j++) {
        fwrite(&ny -> id, sizeof(unsigned int), 1, fil);
        fwrite(&ny -> koblinger[j] -> id, sizeof(unsigned int), 1, fil);
        fwrite("\0", sizeof(char), 0, fil);
      }
    }
  }
  fclose(fil);
  return 0;
}

//Metode for aa frigjoere minne (allokering):
void fri_minne() {
  int i;

  for (i = 0; i < N; i++) {
    struct Router* ruter = rts[i];

    //Hvis ikke tom, frigjore minne:
    if (ruter != NULL) {
      free(ruter);
      ruter = NULL;
    }
  }
  free(rts);
  return;
}

/* KOMMANDO */

//Metode som skriver ut informasjon om ruteren:
void print(int id) {
  //Sjekker om ruteren finnes:
  //Hvis ikke:
  if (hentRuter(id) == NULL) {
    printf("Router - print: %d eksisterer ikke.\n", id);
    return;
  }

  struct Router* ruter = hentRuter(id);

  //Printer informasjon om ruteren som id og model:
  printf("\n***********************************");
  printf("\nRuter ID: %u\n", ruter -> id);
  printf("Ruter Model: %s\n", ruter -> model);

  //Printer informasjon om ruterens flagg:
  printf("Aktiv: %u\n", ruter -> flagg & (1 << 0));
  printf("Wireless: %u\n", ruter -> flagg & (1 << 1));
  printf("5GHz: %u\n", ruter -> flagg & (1 << 2));
  printf("Ubrukt: %u\n", (ruter -> flagg & 0x8));
  printf("Endringsnummer: %u\n", (ruter -> flagg & (0x15 << 4)));

  //Printer informasjon om rutere som er koblet sammen:
  printf("\nKoblinger:\n");

  int i;
  for (i = 0; i < ruter -> tellerK; i++) {
    printf("Ruter ID og Model: %u %s\n", ruter -> koblinger[i] -> id, ruter -> koblinger[i] -> model);
  }
  printf("\n***********************************\n");

}

//Metode for aa sette inn flagg for ruteren:
void sett_flagg(int id, unsigned char flag, unsigned char verdi) {
  //Sjekker om ruteren finnes:
  //Hvis ikke:
  if (hentRuter(id) == NULL) {
    printf("Router - sett_flagg: %d eksisterer ikke.\n", id);
    return;
  }

  //Ugyldig - Feilmelding:
  if (flag == 3) {
    printf("Flagg 3 er ugyldig.");
    return;
  }

  //Ugyldig:
  if (flag <= 2 && verdi > 1) {
    printf("Flagg er ugyldig. Flagg 0,1,2 skal ha verdi 0 eller 1.");
  }

  //Ugyldig:
  if (flag == 4 && verdi > 15) {
    printf("Flagg har ugyldig verdi. Flagg 4 skal ha verdi mellom 0 og 15.");
  }

  struct Router* ruter = hentRuter(id);

  //FLAGG 0:
  if (flag == 0) {
    //Hvis verdien er 0
    if (verdi == 0) {
      //setter aktivbiten til verdi 0:
      ruter -> flagg &= verdi << flag;
    }
    //Hvis verdien ikke er 0:
    else {
      //Setter aktivbiten til verdi 1:
      ruter -> flagg |= verdi << flag;
    }
  }

  //Samme prosess som forrige if setning:
  //FLAGG 1:
  else if (flag == 1) {
    if (verdi == 0) {
      ruter -> flagg &= verdi << flag;
    }
    else {
      ruter -> flagg |= verdi << flag;
    }
  }

  //FLAGG 2:
  else if (flag == 2) {
    if (verdi == 0) {
      ruter -> flagg &= verdi << flag;
    }
    else {
      ruter -> flagg |= verdi << flag;
    }
  }

  //FLAGG 3:
  else if (flag == 4) {
    if (verdi == 0) {
      ruter -> flagg &= verdi << flag;
    }
    else {
      ruter -> flagg |= verdi << flag;
    }
  }
}

//Metode for aa sette navn til modell:
void sett_modell(int id, char* navn) {
  //Sjekker om ruteren finnes:
  //Hvis ikke:
  if (hentRuter(id) == NULL) {
    printf("Router - sett_modell: %d eksisterer ikke.\n", id);
    return;
  }

  struct Router* ruter = hentRuter(id);

  //Kopierer string som peker på ruteren's modell:
  strcpy(ruter -> model, navn);
  //Henter lengden til modellen:
  ruter -> mLengde = strlen(navn) + 1;
}

//Metode for aa legge til kobling fra id1 til id2:
void legg_til_kobling(int id1, int id2) {
  //Sjekker hvis ruter 1 eksisterer:
  //Hvis ikke:
  struct Router* r1 = hentRuter(id1);

  if (r1 == NULL) {
    printf("Router - kobling: %d eksisterer ikke.\n", id1);
    return;
  }

  //Sjekker hvis ruter 2 eksisterer:
  //Hvis ikke:
  struct Router* r2 = hentRuter(id2);

  if (r2 == NULL) {
    printf("Router - kobling: %d eksisterer ikke.\n", id2);
    return;
  }

  r1 -> koblinger[r1 -> tellerK] = r2;
  r1 -> tellerK++;
}

//Metode for aa slette ruterinformasjon:
void slett_ruter(int id) {
  //Sjekker hvis ruter eksisterer:
  //Hvis ikke:
  if (hentRuter(id) == NULL) {
    printf("Router - slett_ruter: %d eksisterer ikke.\n", id);
    return;
  }

  struct Router* ruter = hentRuter(id);

  //fjerner koblingene:
  int i;
  //gaar gjennom globale N:
  for (i = 0; i < N; i++) {
    int j;
    //enda en for-loekke som gaar gjennom tellerK
    for (j = 0; j < rts[i] -> tellerK; j++) {
      //Hvis verdien id til koblinger er lik id fra strukturen ruter:
      //Bruker deres id for aa fjerne dem:
      if (rts[i] -> koblinger[j] -> id == ruter -> id) {
        rts[i] -> koblinger[j] = NULL;
        rts[i] -> tellerK--;
      }
    }
  }

  //gaar gjennom globale N
  for (i = 0; i < N; i++) {
    //hvis pekeren ikke er null og iden fra pekeren
    //samsvarer med argumentens id
    if (rts[i] != NULL && rts[i] -> id == id) {
      //hoppover:
      break;
    }
  }
  //pekeren faar verdien null og allokkerer minne:
  rts[i] == NULL;
  free(ruter);

  //Lager en for-loekke som gaar gjennom telleren N:
  //bruker dette for aa justere ruterne:
  for (i = 0; i < N; i++) {
    if (rts[i] == NULL) {
      int l;
      for (l = i + 1; l < N; l++) {
        rts[i] = rts[l];
        i++;
      }
      rts[i] = NULL;
    }
  }
  (N)--;
}

//Metode som sjekker om en rute finnes fra ruter 1 til ruter 2:
int finnes_rute(int r1, int r2) {
  //Sjekker om r1 og/eller r2 eksisterer:
  if (hentRuter(r1) == NULL) {
    printf("Ruter 1 - finnes: %u eksisterer ikke\n", r1);
    return 0;
  }
  if (hentRuter(r2) == NULL) {
    printf("Ruter 2 - finnes: %u eksisterer ikke\n", r2);
    return 0;
  }

  //Gjoer dybde-foerst-soek:
  unsigned char besokt[N];
  int i;

  for (i = 0; i < N; i++) {
    besokt[i] = 0;
  }
  return sjekker(r1, r2, besokt);

}

//Metode som soeker etter koblinger:
int sjekker(int a, int b, unsigned char* besokt) {
  int i;
  int a1;

  //Gaar gjennom for aa hente id fra a:
  for (i = 0; i < N; i++) {
    if(rts[i] != NULL && rts[i] -> id == a) {
      a1 = i;
      break;
    }
  }

  if (besokt[a1] == 1) {
    return 0;
  }

  if (a == b) {
    return 1;
  }
  besokt[a] = 1;

  //Gaar gjennom for aa sjekke koblinger til ruterne:
  for (i = 0; i < rts[a1] -> tellerK; i++) {
    if (sjekker(rts[a1] -> koblinger[i] -> id, b, besokt) == 1) {
      return 1;
    }
  }
  return 0;
}
