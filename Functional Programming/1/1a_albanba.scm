;; Obligatorisk Innlevering 1a (IN2040)
;; Albanba

;; Oppgave 1

;; a)
(display "a)\n")
(* (+ 4 2) 5)
#| Først blir 4 multiplisert med 2 innenfor indre parantesen på grunn av
prosedyren "+". Deretter blir svaret fra den parantesen (= 6) multiplisert
med tallet 5 som ligger i den ytre parantesen med prosedyren "*". Vi får da 30. |#

;; b)
;;(* (+ 4 2) (5))
#| Vi får feilmelding på grunn av tallet 5.
"Application: not a procedure; expected a procedure that can be applied to arguments.
Vi får denne feilen fordi programmet forventer en prosedyre etter parantesen, men istedenfor
det er bare en tall, altså 5, som blir nevnt. (5) er ikke en prosedyre. |#


;; c)
;;(* (4 + 2) 5)
#| Vi får feilmelding på grunn av tallet 4.
Samme feilmelding som oppgave b).
Programmet forventer en prosedyre bli nevnt etter parantesen, men
det blir gitt en tall før prosedyren "+" blir nevnt. |#

;; d)
(display "d)\n")
(define bar (/ 44 2))
bar
#| Vi får resultatet 22. Grunnen til det er at vi definerer bar som har en parameter av 44.
44 blir delt med 2 på grunn av prosedyren "/" og vi får 22.
Siden bar blir definert, dette fører til at når man definerer bar igjen
som for eksempel i neste funksjon, så vil den videreføre resultatet fra denne funksjonen
til den andre funksjonen. |#

;; e)
(display "e)\n")
(- bar 11)
#| Den kaller på bar fra oppgave d), og bar fra d) er 22.
Vi får 22 og funksjonen kaller på prosedyren "-" og vi får 22 - 11 = 11. |#

;; f)
(display "f)\n")
(/ (* bar 3 4 1) bar)
#| Det som skjer her er at bar blir definert fra forrige oppgave (22)
Prosedyren "*" blir kalt på av funksjonen og adderer 22 med 3 4 1. Vi får 264.
Deretter blir bar kalt på i ytre parantes (22), og prosedyren "/" blir kalt på av funksjonen.
Vi får da 264 / 22, der svaret blir 12. |#

;;-----------------------------------------------------------------------------------
;; Oppgave 2

;; a)
(or (= 1 2)
    "paff!"
    "piff!"
    (zero? (1 - 1)))
#| Her ser vi at vi får "paff!" som resultat. Denne inneholder syntaksfeil, men
 koden kjører og printer ut "paff!". Grunnen til det er at and, or og if
 er special forms. I dette tilfellet har vi logiske uttrykket "or".
 Special forms unngår evalueringsregelen. Siden "or", en special form,
 blir brukt, så blir syntaksfeilen (1 - 1) ikke evaluert, og "or" evaluerer
 første verdien som er true, altså "paff!". |#

(and (= 1 2)
     "paff!"
     "piff!"
     (zero? (1 - 1)))
#| Her får vi #f fordi uttrykket som blir brukt her er "and". Hvis en av argumentene
 er false, så vil uttrykket evaluere til #f. Og siden "and" er en special form,
 så vil den ikke evaluere siste linjen som har syntaksfeil. |#

(if (positive? 42)
    "poff!"
    (i-am-undefined))
#| Her får vi resultatet "poff!". Grunnen til det er uttrykket evaluerer det som
 er #t. Og uttrykket "if" som blir brukt her, er også en special form. Dette
 betyr at siste linjen, som kaller på en prosedyre (ikke definert), blir ikke evaluert. |#

;; b)
;; if:
(define (sign x)
  (if (< x 0)
      -1
      (if (> x 0) 1 0)))

;; cond:
(define (sign x)
  (cond ((< x 0) -1)
        ((> x 0) 1)
        ((= x 0) 0)))

;; c)
;;logisk predikat (and, or ..)
(define (sign x)
  (or
   (and (< x 0) -1)
   (and (> x 0) 1)
   0))

;;-----------------------------------------------------------------------------------
;; Oppgave 3

;; a)
;; Addisjon:
(define (add1 x)
  (+ x 1))

;; Subtraksjon:
(define (sub1 x)
  (- x 1))

;; b)
(define (plus a b)
  (if (zero? b)
      a
      (plus (add1 a) (sub1 b))))

;; c)

#| Prosedyren i 3b er en rekursiv prosedyre fordi den kaller seg selv
 for å gjenta. I dette tilfellet ser vi at plus blir kalt på i siste
 linje, altså at den anvender seg selv i sin egen definisjon. |#

;; d)
#| Original (før blokkstruktur):
(define (power-close-to b n)
  (power-iter b n 1))

(define (power-iter b n e)
  (if (> (expt b e) n)
      e
      (power-iter b n (+ 1 e))))
|#

;;Endring (m/ blokkstruktur)
(define (power-close-to b n)
  (define (power-iter e)
    (if (> (expt b e) n)
        e
        (power-iter (+ 1 e))))

(power-iter 1))

#| Her blir blokkstruktur brukt. Dette ser vi ved at power-iter er definert
 internt i definisjonen til power-close-to. Vi fjerner argumentene "b" og "n" i
 power-iter fordi det trengs ikke fordi "b" og "n" er allerede argumenter i
 definisjonen power-close-to, og hjelpe-prosedyren kommer til å få tilgang til dem
 uansett. |#
    

;; e)
#| Nei, det er ikke mulig å forenkle den interne definisjonen av fib-iter
 når man skriver om til å bruke blokkstruktur. Grunnen til dette er at hvis
vi ser på prosedyren, ser vi at argumentene får nye verdier. |#