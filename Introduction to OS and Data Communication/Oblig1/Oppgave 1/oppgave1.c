#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//funksjon som tar inn argumenter: 1 setning og 2 bokstaver:
int main(int argc, char *argv[]) {

	//sjekker hvis argc er 4 som skal gjoere det mulig for aa replace bokstaver (0-3):
	if (argc == 4) {

		//Lager variabler:
		int len = strlen(argv[1]);
		//bruker malloc som allokerer minne til pointeren:
		char *ny = malloc(sizeof(char) * len + 1);

		//Lager en for-loekke som gar gjennom lengden til setningen:
		for (int i = 0; i < len + 1; i++) {
			//Hvis bokstavene som skal forandres med er like:
			if (argv[1][i] == argv[2][0]) {
				//erstatter gamle bokstaven med den nye:
				ny[i] = argv[3][0];
			}
			//Hvis ikke:
			else {
				//Gjoer ikke noe spesielt (ikke noe endring):
				ny[i] = argv[1][i];
			}
		}
		printf("%s\n", ny);

		//Frigjoer minnet for aa unngaa leak:
		free(ny);
	}
	//Hvis brukeren skriver noe feil:
	else {
		printf("Feil input.\n");
	}
	return 0;
}
