from heapq import heappush, heappop
from collections import defaultdict, deque
import time


# Oppretter metode som leser inn filnavn:
def lesFil(filnavn):
    # Oppretter tom liste
    liste = []
    # Åpner filen som er encoda som UTF-8
    with open(filnavn, "r", encoding='UTF-8') as rd:
        #teller = 0
        # For-løkke som går gjennom filen:
        for i in rd:
            #teller += 1
            # splitter dem i \t (fordi det er .tsv fil) for hvert individuelt element
            # og legger dem i lista 'liste'
            liste.append(i.strip().split("\t"))
    # print(liste)
    # print(teller)
    return liste

# Oppretter klassen Movie med parametre:


class Movie:
    def __init__(self, tt_id, title, rating):
        self.tt_id = tt_id
        self.title = title
        self.rating = float(rating)

        # Lager en tom liste som skal inneholde skuespillere i filmer de har vært med i:
        self.actors = []

    # Metode for å legge til skuespiller:
    def leggTilActor(self, i):
        self.actors.append(i)

    def hentTittel(self):
        print(self.title)

    def hentRating(self):
        print(self.rating)

    # Metode som returnerer vekt til film ratings (10 - (rating fra spesifikk film)):
    @property
    def vekt(self):
        return 10 - self.rating

    def __str__(self):
        return self.title


# Oppretter Actor klasse med parametre
class Actor:
    def __init__(self, filmer, nm_id, name, *movies):
        self.nm_id = nm_id
        self.name = name
        self.movies = [
            filmer[tt_id]
            for tt_id in movies
            if tt_id in filmer
        ]

        # Lager en tom dictionary for oppgave 3 om beste filmer:
        self.bestmovies = {}

    def hentActor(self):
        print(self.name)

    def hentFilmer(self):
        print(self.movies)

    def __str__(self):
        return self.name

    # Gjør nm_id hashable:
    def __hash__(self):
        return self.nm_id.__hash__()

    # Returnerer True for heap som blir brukt i oppgave 3:
    def __lt__(self, x):
        return True

    # Oppretter metoden nabo som finner:
    @property
    def nabo(self):
        # Returnerer _nabo hvis den er i __dict__:
        if "_nabo" in self.__dict__:
            return self._nabo

        # _nabo blir lagt tom dictionary
        self._nabo = {}

        # For-løkke som går gjennom alle filmer:
        for i in self.movies:
            # Variabler som sjekker gjennom actors i i.actors:
            ns = (n_actor
                  for n_actor in i.actors
                  if n_actor != self)

            # For-løkke som sjekker n-elementer i ns:
            for n in ns:
                # Hvis den er ikke i _nabo:
                if n not in self._nabo:
                    # elementene i 'i'-dict er i dictionary i n som er i _nabo:
                    self._nabo[n] = {i}
                # Legger til i i _nabo:
                self._nabo[n].add(i)
        return self._nabo

    # Metode som sjekker best rating for filmer:

    def best_rating(self, x):
        # Variabler som har telleren 10:
        teller = i = 10

        # Hvis x er i dictonarien bestmovies: returner x verdier i bestmovies:
        if x in self.bestmovies:
            return self.bestmovies[x]

        # Hvis x er i nabo:
        if x in self.nabo:
            # For-løkke gjennom x verdier i nabo:
            for film in self.nabo[x]:
                # tellern returnerer minste verdien via vekten til filmen og tellern:
                teller = min(film.vekt, teller)

                # Hvis tellern er mindre enn i:
                if teller < i:
                    # film-verdier blir lagt til i bestmovies dict:
                    self.bestmovies[x] = film
                    i = teller
        return self.bestmovies[x]


# Metode som bruker bredde først søk strategi til å besøke naboer:
def bfs(start, slutt):
    # x <-- start
    x = {start: None}

    # Oppretter en kø:
    queue = deque([start])

    result = []

    # While-løkke gjennom køen:
    while queue:
        # Fjerner element fra venstresiden fra køen og legger til den i lista 'result':
        actor = deque.popleft(queue)
        result.append(actor)

        # For-løkke gjennom naboer i actor (kø):
        for n in actor.nabo:
            # Hvis n er ikke med x (start)
            if n not in x:
                # actor <-- n som er i x
                x[n] = actor
                # Putter n deretter i køen:
                queue.append(n)

                # Returnerer x hvis n har samme verdi som slutt altså nådd slutten:
                if n == slutt:
                    return x
    return x


# Metode som henter fra bfs og oppretter en vei fra start til slutt:
def path(sok, start, slutt):

    # Vei-variabel som har tom liste:
    vei = []
    # x <-- slutt
    x = slutt

    # En while-løkke som sjekker hvis x ikke er tom:
    while x is not None:
        # Legger x til i vei-liste:
        vei.append(x)
        # x-verdier i sok-argumentet i x:
        x = sok[x]
    # Returnerer lista i reverse for å få riktig rekkefølge (start-slutt, ikke slutt-start):
    liste = list(reversed(vei))
    return liste


# Metode som tar inspirasjon fra forelesningen om Dijkstra:
# Finner korteste stier for vektede grafer:
def chill_sti(start, slutt):
    Q = [(0, start)]
    D = defaultdict(lambda: float('inf'))
    D1 = {start: []}
    D[start] = 0

    while Q:
        cost, v = heappop(Q)

        if cost >= D[slutt]:
            break

        for u in v.nabo:
            c = cost + v.best_rating(u).vekt
            if c < D[u]:
                D[u] = c
                heappush(Q, (c, u))
                D1[u] = D1[v] + [v]
    return D1[slutt] + [slutt], D[slutt]

# Metode som teller noder i komponenter:


def komponentTeller(s, x):

    # Teller som starter på 1 (min. size er 1):
    teller = 1
    # Variabel som oppretter en kø via deque():
    queue = deque([s])

    result = []

    # Deretter blir alle s i x fjernet:
    x.remove(s)

    # En while-løkke som går gjennom størrelsen av køen ved å bruke qsize():
    while len(queue):
        # Fjerner element fra venstresiden fra køen og legger til den i lista 'result':
        s = deque.popleft(queue)
        result.append(s)

        # For-løkke som går gjennom naboer i s:
        for i in s.nabo:
            # Hvis i er i x:
            if i in x:
                # i blir fjernet i x og blir lagt til i køen (telleren økes også):
                x.remove(i)
                queue.append(i)
                teller += 1
    return teller

# Metode som finner komponenter:


def finn_komponenter(x):
    # Lager en variabel som lager en uordret collection (set) av verdiene og returnerer en ny list via copy() av x:
    ikkeBesokt = set(x.copy().values())
    # Lager en dictionary som konverterer til int eller returnerer alltid 0 hvis ingen argument er gitt:
    komponenter = defaultdict(int)

    # While-løkke som går gjennom og finner komponeneter
    # Deretter også teller hvor mange komponenter det finnes:
    while ikkeBesokt:
        s = next(iter(ikkeBesokt))
        teller = komponentTeller(s, ikkeBesokt)
        komponenter[teller] += 1
    return komponenter


def chilleste_vei():
    six_degree = [
        ("nm2255973", "nm0000460"),
        ("nm0424060", "nm0000243"),
        ("nm4689420", "nm0000365"),
        ("nm0000288", "nm0001401"),
        ("nm0031483", "nm0931324")
    ]

    for start, slutt in six_degree:
        fra = actorD[start]
        til = actorD[slutt]

        # Kaller på chill_sti som finner den "chilleste stien" med 2 argumenter: fra og til
        vei = chill_sti(fra, til)[0]
        vekt = chill_sti(fra, til)[1]

        for i, j in zip(vei[:-1], vei[1:]):
            # film-variablen får j-verdier fra best_rating som er i 'i'
            film = i.best_rating(j)

            print(i.name, "\n===[", film.title,
                  " (", film.rating, ") ] ===>", end=" ")
        print(vei[-1].name)
        print("Total weight:", vekt, "\n")


def korteste_vei():
    # Liste over start og slutt skuespillere:
    six_degree = [
        ("nm2255973", "nm0000460"),
        ("nm0424060", "nm0000243"),
        ("nm4689420", "nm0000365"),
        ("nm0000288", "nm0001401"),
        ("nm0031483", "nm0931324")
    ]

    # For-løkke som går gjennom start og slutt skuespillere i lista over:
    for start, slutt in six_degree:
        # lager to variabler (fra, til) der de blir koblet med start, slutt i actorD:
        fra = actorD[start]
        til = actorD[slutt]

        # Lager en søk-variabel som kaller på bfs med argumentene: fra og til:
        sok = bfs(fra, til)
        # Deretter en vei-variabel med 3 argumenter som søker de korteste veiene:
        vei = path(sok, fra, til)

        # For-løkke gjennom i og j, altså skuespillere ved å bruke zip som yielder tuplene:
        for i, j in zip(vei[:-1], vei[1:]):
            # Henter en iterator fra objektet og returnerer det neste elementet som er i film nabo:
            film = next(iter(i.nabo[j]))

            # Printer ut navn til skuespiller, film-tittel (film de har vært med i), deres film rating
            # og printer ut navn til neste skuespillerne i stien (helt til den når slutten):
            # --> Start-skuespiller ===[Film (rating)] ===> Neste skuespiller
            print(i.name, "\n===[", film.title,
                  " (", film.rating, ") ] ===>", end=" ")
        print(vei[-1].name, "\n")

# Kall på metoden for å printe ut alle komponenter:


def komponenter():
    kmp = finn_komponenter(actorD)

    for i, j in reversed(sorted(dict(kmp).items())):
        print("There are", j, "components of size", i)


# Leser filene:
filmer = lesFil("movies.tsv")
actors = lesFil("actors.tsv")

# Variabeler som lager en dictionary ut av .tsv filene:
filmD = {
    linje[0]: Movie(*linje[:-1])
    for linje in filmer
}
actorD = {
    linje[0]: Actor(filmD, *linje)
    for linje in actors
}

# Metode for å sjekke tiden:


def tid(x):
    present = time.time()
    print("\nTotalt tid brukt:", present - x, "sekunder")


# Main-metode:
def main():

    # Går gjennom skuespillere i actorD
    # og legger alle skuespillerne som har vært med i filmer i movies:
    for actor in actorD.values():
        for movie in actor.movies:
            movie.leggTilActor(actor)

    # Tom variabel (0):
    kanter = 0
    # En for-løkke som går gjennom filmene i filmD:
    # og plusser til verdiene med lengden av skuespillere ganget med skuespillere subtrahert med 1 og delt på 2 på slutten:
    for film in filmD.values():
        kanter += len(film.actors)*(len(film.actors) - 1)/2

    # Oppgave 1:
    print("**OPPGAVE 1**")
    print("Noder:", len(actorD), "\nKanter:", int(kanter))
    print()

    # Starter tiden:
    startTid = time.time()

    # Oppgave 2:
    print("**OPPGAVE 2**")
    korteste_vei()

    # Oppgave 3:
    print("**OPPGAVE 3**")
    chilleste_vei()

    # Oppgave 4:
    print("**OPPGAVE 4**")
    komponenter()

    # Kaller på metoden tid for å sjekke hvor langt tid programmet brukte fra start til nå:
    tid(startTid)


if __name__ == "__main__":
    main()
