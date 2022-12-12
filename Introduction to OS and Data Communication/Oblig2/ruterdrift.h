//Deklarerer metoder:
//RUTER:
struct Router* hentRuter(int id);
//KOMMANDOER:
void print(int id);
void sett_flagg(int id, unsigned char flag, unsigned char verdi);
void sett_modell(int id, char* navn);
void legg_til_kobling(int id1, int id2);
void slett_ruter(int id);
int finnes_rute(int r1, int r2);
int sjekker(int a, int b, unsigned char* besokt);
//MAIN:
int lesKommando(char *filnavn);
int lesFil(char *filnavn);
int skrivFil();
void fri_minne();
