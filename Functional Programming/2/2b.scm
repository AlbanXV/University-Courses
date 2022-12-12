;; Innlevering 2b
;; Av: Alban Bajrami (albanba) og Atahan Caliskan (atahanc)
;; ---------------------------------------------------------


;; Oppgave 1

(display "1a)\n")
(define (make-counter)
  (let ((count 0))
    (lambda ()
      (set! count (+ count 1))
      count)))

(define count 42)
(define c1 (make-counter))
(define c2 (make-counter))
(c1) ;; --> 1
(c1) ;; --> 2
(c1) ;; --> 3
count ;; --> 42
(c2) ;; --> 1

#|
 Vi lager prosedyren ved å definere make-counter som returnerer en ny prosedyre
 ved å bruke innkapsling som holder rede på hvor mange den blir kalt på.
 Vi lager privat variablen 'count' ved å bruke let og endrer hva 'count' peker på.
 Dette gjør vi ved å bruke set! og gjør slik at count økes med 1.
 'count' har verdien 0, men hver gang den blir kalt på, så vil den øke og vi får
 den oppdaterte verdien til 'count'.
|#

;; --------------------------------------------------------------------------------

;; Oppgave 2
;; a)

(display "2a)\n")
(define (make-stack list)
  
  (define (push! x)
    (set! list (append (reverse x) list)))

  (define (pop!)
    (if (not (null? list))
        (set! list (cdr list))))

  (define (dispatch message . args)
    (cond ((eq? message 'push!) (push! args))
          ((eq? message 'pop!) (pop!))
          ((eq? message 'stack) list)))
  dispatch)

#|
 Implementerer en abstrak datatype for å representere stakker som push og pop.
 Det vi gjør er at vi har en hoved definisjon som skal være en stack, og inni
 den har definerer vi to stakk-typer: push som legger til elementer, og pop som
 fjerner elementer. I begge stacks bruker vi set! for å endre hva en variabel peker på.
 Altså forandre deres verdi. I push legger vi elementene til men reverserer elementene
 for å ta i bruk LIFO: Last-In-First-Out. Deretter i pop, fjerner vi element ved å
 ta bort første elementet i lista. Til slutt definerer vi en 'message passing',
 som er slik vi skal kommunisere med prosedyreobjektene. Det den gjør er at
 den gir tre beskjeder: en for 'push! ved å destruktivt legge til element i stakken,
 den andre for 'pop! for å destruktivt fjerne det siste elementet i stakken,
 og deretter den siste for 'stack som returnerer listen av elementer som er på stakken.
|#

(define s1 (make-stack (list 'foo 'bar)))
(define s2 (make-stack '()))
(s1 'pop!)
(s1 'stack) ;; --> (bar)
(s2 'pop)
(s2 'push! 1 2 3 4)
(s2 'stack) ;; --> (4 3 2 1)
(s1 'push! 'bah)
(s1 'push! 'zap 'zip 'baz)
(s1 'stack) ;; --> (baz zip zap bah bar)

;; b)
(display "2b)\n")
(define (push! . args)
  (define (push-extra stack y)
    (if (null? y)
        '()
        (stack 'push! (car y)))
    (if (not (eq? y '()))
        (push-extra stack (cdr y))))
  (push-extra (car args) (cdr args)))
  

(define (pop! stack)
  (stack 'pop!))

(define (stack x)
  (x 'stack))

#|
 Vi definerer prosedyrene push!, pop! og stack der alle av dem tar et stakk-objekt
 som argument. Hos pop! og stack, er det ganske likt, men i push! gjør vi slik
 at den skal godta et vilkårlig antall elementer som skal settes inn ved at
 vi definerer push-ekstra som har to argumenter i seg: stack og y. Hvis y ikke
 er null, blir en tom liste opprettet. Hvis ikke tom:
 stack blir kalt på som skal legge til første element i lista.
 Deretter sjekker vi hvis y ikke er eq? med
 en tom liste, altså hvis argumentene ikke angir samme objekt
 i programmet da gjør vi slik at den "pusher" argumentet y ved at den for tak i andre
 del av paret i lista. Deretter utenfor if-setningen, utenfor definisjonen av
 push-extra, altså på slutten av programmet, kaller vi på push-extra som tar tak i
 første elementet i lista, og deretter andre delen etter første elementet i lista.
|#

(pop! s1)
(stack s1) ;; --> (zip zap bah bar)
(push! s1 'foo 'faa)
(stack s1) ;; --> (faa foo zip zap bah bar)

;; --------------------------------------------------------------------------------

;; Oppgave 3
;; a)
(display "3a)\n")
(define bar (list 'a 'b 'c 'd 'e))
(set-cdr! (cdddr bar) (cdr bar))

(list-ref bar 0) ;; --> a
(list-ref bar 3) ;; --> d
(list-ref bar 4) ;; --> b
(list-ref bar 5) ;; --> c

(display "sjekk 2b.pdf for svaret\n")
;; Sjekk 2b.pdf for svaret for oppgave 3a

;; b)
(display "3b)\n")

(define bah (list 'bring 'a 'towel))
(set-car! bah (cdr bah))

bah ;; --> ((a towel) a towel)

(set-car! (car bah) 42)

bah ;; --> ((42 towel) 42 towel)

(display "sjekk 2b.pdf for svaret\n")
;; Sjekk 2b.pdf for svaret for oppgave 3b

;; c)
(display "3c)\n")

(define (cycle? liste)
  (define (cycle-iter x y)
    (cond
      ((null? y) #f)
      ((null? (cdr y)) #f)
      ((eq? x y) #t)
      (else (cycle-iter (cdr x) (cddr y)))))
  (cycle-iter liste (cdr liste)))

(cycle? '(hey ho)) ;; --> #f
(cycle? '(la la la)) ;; --> #f
(cycle? bah) ;; --> #f
(cycle? bar) ;; --> #t

#|
 Det vi gjør her er at vi lager predikatet 'cycle?' som skal sjekke
 om en listestruktur er syklisk. Vi definerer cycle-iter som tar imot 2
 argumenter: x og y. Dette er basert på Floyd's Cycle-Finding-algoritme
 der den tar imot 2 argumenter som skal traversere i en liste.
 Først vi sjekker om y er null. Hvis ja: dette gir oss false.
 Deretter sjekker hvis hvis andre elementet i y også er null.
 Hvis ja: dette gir oss også false.
 Deretter sjekker vi om begge argumentene angir samme verdi i minnet,
 hvis ja: Dette gir oss True.
 Det algoritmen gjør er at hvis argumentene x og y møter hverandre i samme verdi,
 så betyr det at listen er en loop, at det er en cycle.
|#

;; d)
(display "3d)\n")
(list? bar)
(list? bah)
(display "bar: ")
bar
(display "bah: ")
bah

#|
 Grunnen til at vi får #f på bar og #t på bah er at i bah så får vi:
 ((42 towel) 42 towel) som er en liste. I tillegg er at sirkulære lister
 er egentlig ikke lister og i bar så får vi:
 (a . #0=(b c d . #0#))
 Siden bar er en sirkulær liste så er den egentlig ikke en ekte liste og
 predikatet list? evaluerer til at bar er False. Naturlige definisjonen
 av en liste er at det må enten være en tom liste: () eller en par
 der cdr peker på en liste. Tilfellet til bar er at den ikke
 oppfyller kravene til naturlige definisjonen til hvordan lister skal være.
 Samme gjelder cons. En "liste" som inneholder cons blir ikke sett på som
 en ekte liste.
|#