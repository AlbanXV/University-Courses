(load "prekode3a.scm")

;; Innlevering 3a
;; Av: Alban Bajrami (albanba) og Atahan Caliskan (atahanc)
;; ---------------------------------------------------------

;; Oppgave 1
;; a) og b)

(display "1a-b)\n")

(define (mem msg? proc)
  (cond ((equal? msg? 'memoize)
         (let ((cache (make-table)))
           (lambda args
             (if (null? args)
                 proc
                 (let ((prev (lookup args cache)))
                   (or prev
                       (let ((result (apply proc args)))
                         (insert! args result cache)
                         result)))))))
        ((equal? msg? 'unmemoize) (proc))))

#|
 Det som skjer her er at vi skriver prosedyren mem som gitt beskjeden
 'memoize og en prosedyre som returnerer en memoisert versjon av denne.
 Prosedyren er utvidet slik at den også støtter beskjeden 'unmemoize slik
 at vi kan gjenopprette opprinnelige ikke-memoiserte versjonen av en prosedyre.
 definisjonen mem har 2 argumenter: msg? og proc. Vi bruker condition for å
 sjekke hvis meldingen er equal til 'memoize, oppretter en variabel og sjekker
 hvis verdien er tom, hvis ikke, programmet fortsetter som gjør at programmet
 husker cachen fra tidligere kall (utførte beregninger) ved at prosedyren
 kalles med samme argument flere ganger.
 
|#

(set! fib (mem 'memoize fib))
(fib 3)
(fib 3)
(fib 2)
(fib 4)
(set! fib (mem 'unmemoize fib))
(fib 3)

;; c)

#|
 Måten vi bruker mem på over med hvordan det blir gjort (beskrevet i oppgave c)
 er at i fib (1a-1b) blir det brukt set! mens i mem-fib (1c) blir det brukt define.
 mem-fib blir altså definert istedenfor.
 I fib så er det slik at set! fører til at fib får ny verdi etter kall, altså
 vi får returnert verdiene etter kallene som den lagrer på, mens i mem-fib, det
 skjer ikke som fører til at den greier ikke å memoisert prosedyren.
 Den lager en ny prosedyre hver gang den blir kalt som gjør at den ikke lagrer
 verdiene den får i cachen.
|#

;; ---------------------------------------------------------

;; Oppgave 2
;; a)

(display "2a)\n")

(define (list-to-stream list)
  (if (null? list)
      the-empty-stream
      (cons-stream (car list) (list-to-stream (cdr list)))))

#|
 Definerer list-to-stream som tar en liste som argument. Sjekker
 hvis lista er tom, hvis tom: kaller på the-empty-stream fra prekode3a.scm
 som oppretter en tom liste. Hvis ikke tom, returnerer en liste med elementene
 i strømmene.
|#

(define (stream-to-list stream . x)
  (if (stream-null? stream)
      the-empty-stream
      (if (null? x)
          (cons (stream-car stream) (stream-to-list (stream-cdr stream)))
          (if (= (car x) 0)
              '()
              (cons (stream-car stream)
                    (stream-to-list (stream-cdr stream) (- (car x) 1)))))))

#|
 Definerer stream-to-list som tar en strøm som argument + et valgfritt argument
 som angir hvor mange elementer fra strømmen som skal tas inn (x). Sjekker
 hvis strømmen er tom ved å bruke 'stream-null?' fra prekode3a.scm. Hvis tom:
 kaller på the-empty-stream som oppretter en tom liste.
 Deretter sjekker hvis det valgfrie argumentet (x) er tom:
 Hvis den er tom: returnerer lista  med elementene i strømmen.
 Deretter sjekker jeg hvis første verdien i valgfrie argumentet (x) er lik verdien 0:
 Hvis ja: '() Liste
 Hvis nei: prosedyren returnerer lista i strømmen med argument verdien det er blitt
 gitt.
|#

(list-to-stream '(1 2 3 4 5)) ;; --> (1 . #<promise>)
(stream-to-list (stream-interval 10 20)) ;; --> (10 11 12 13 14 15 16 17 18 19 20)
(show-stream nats 15) ;; --> 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ...
(stream-to-list nats 10) ;; --> (1 2 3 4 5 6 7 8 9 10)

;; b)

(display "2b)\n")

(define (stream-take n stream)
  (if (stream-null? stream)
      the-empty-stream
      (if (= n 0)
          the-empty-stream
          (cons-stream (stream-car stream) (stream-take (- n 1) (stream-cdr stream))))))

#|
 Definerer stream-take som tar 2 argumenter: strøm (stream) og et heltall (n).
 Sjekker hvis strømmen er tom: hvis ja: the-empty-stream: oppretter tom liste
 Hvis nei: sjekker hvis argumentet (n) er 0:
 Hvis ja (0):
 Liste '()
 Hvis nei (ikke 0):
 returnerer lista med ny strøm av de n første elementene i stream.
|#

(define foo (stream-take 10 nats))
foo ;; --> (1. #<promise>)
(show-stream foo 5) ;; --> 1 2 3 4 5 ...
(show-stream foo 20) ;; --> 1 2 3 4 5 6 7 8 9 10
(show-stream (stream-take 15 nats) 10)
;; --> 1 2 3 4 5 6 7 8 9 10 ...

;; c)

#|
 Et potensielt problem med Petter Smarts forslag er at strømmer kan være uendelige
 lister, og dette vil føre til at prosedyren kommer til å kjøre uendelig og
 kommer ikke til å bli ferdig med å gå gjennom strømmen og fjerne duplikater
 fordi remove-duplikates sjekker om car av lista er et element i cdr av lista.
|#

;; d)

(display "2d)\n")

(define (remove-duplicates stream)
  (if (stream-null? stream)
      the-empty-stream
      (cons-stream (stream-car stream)
                   (remove-duplicates
                    (stream-filter
                     (lambda (x)
                       (not (equal? x (stream-car stream))))
                     (stream-cdr stream))))))

#|
 Fullførerer definisjonen remove-duplicates ved at vi gir et predikat og en
 strøm som returnerer stream-filter en ny strøm som består av de elementene
 fra input-strømmen som oppfyller predikatet, altså som returnerer sant.
 Dette gjør vi ved at vi bruker lambda med argument x og sjekker hvis
 argumentet er ikke equal.
|#

;; TEST:
(show-stream (remove-duplicates (list-to-stream '(1 2 3 3 5 7 2 3 9 9 5))))
;; --> 1 2 3 5 7 9
                       