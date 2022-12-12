#include <stdio.h>

//Lager funksjonen med char og en peker (*) som paramenter:
int stringsum(char *s) {
	//Lager tomme variabler:
	int i = 0;
	int j = 0;

	//Lager en while-loekke som sjekker hvis en char sin innhold ikke er tom (null terminator):
	while(s[i] != '\0') {
		//Bruker ASCII-tabell for aa finne verdiene i char som DEC og Symbol.
		//Lager if-setninger som sjekker verdier som inneholder bokstaver:
		//Hvis indexen til char *s inneholder smae bokstaver:
		if (96 < s[i] && s[i] < 123) {
			//j faar summen av den innsendte strengen:
			j = j + (int) s[i] - 96;
		}
		//Enda en if-setning som sjekker for bokstaver:
		//Samme prosess som forrige if-setning, men bare for store bokstaver:
		else if (64 < s[i] && s[i] < 91) {
			//j faar summen av den innsendte strengen:
			j = j + (int) s[i] - 64;
		}
		//Hvis strengen inneholder symboler (ikke bokstaver):
		else {
			//returnerer -1
			return -1;
		}
		i++;
	}
	//returnerer summen av den innsendte strengen:
	return j;
}
