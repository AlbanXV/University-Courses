;; Innlevering 2b
;; Av: Alban Bajrami (albanba) og Atahan Caliskan (atahanc)
;; ---------------------------------------------------------

(load "huffman.scm")

;; Oppgave 1

;; a)
(display "1a)\n")
(define (p-cons x y)
  (lambda (proc) (proc x y)))

#|
 Definerer p-car og p-cdr ved å gi dem parameter 'proc'
 og deretter definerer lambda som lager prosedyrer.
 Definerer lambda for parameterene 'x' og 'y'.
 For p-car, kaller på x etter (x y) som skal hente første verdi i paret.
 For p-cdr, kaller på y etter (x y) som skal hente verdien etter første verdi i paret.
|#
(define (p-car proc)
  (proc (lambda (x y) x)))

(define (p-cdr proc)
  (proc (lambda (x y) y)))

(p-cons "foo" "bar")

(p-car (p-cons "foo" "bar"))

(p-cdr (p-cons "foo" "bar"))

(p-car (p-cdr (p-cons "zoo" (p-cons "foo" "bar"))))

;; b)

(define foo 42)

#|
let-uttrykk 1.1:

(let ((foo 5)
      (x foo))
  (if (= x foo)
      'same
      'different))
|#

(display "1b.1)\n")
;; lambda-uttrykk:
((lambda (foo x)
   (if (= x foo)
       'same
       'different))
 foo 5)

#|
 Erstatter let med lambda, måten vi gjør det på er slik:
 Vi ser på den generelle formen for let og forandrer den
 med en ekvivalent lambda uttrykk:
 istedenfor (let ((foo 5 ...) vi erstatter let med
 lambda som lager prosedyre. Prosedyren tar imot 2 argumenter:
 'foo' og 'x'.
 Deretter på slutten blir 'foo' kalt på med en verdi: 5,
 istedenfor at den får verdien på starten som i let-uttrykket.
|#

#|
let-uttrykk 1.2:

(let ((bar foo)
      (baz 'towel))
  (let ((bar (list bar baz))
        (foo baz))
    (list foo bar)))
|#

(display "1b.2)\n")
;; lambda-uttrykk:
((lambda (bar baz)
   ((lambda (bar foo)
      (list foo bar))
      (list bar baz) baz))
    foo 'towel)

#|
 Erstatter let med lambda ved å forandre på generelle formen
 for let med en lambda-uttrykk. Lager en prosedyre med 2 args: 'bar' og 'baz'
 deretter enda en lambda som lager en prosedyre med enda 2 args: 'bar' og 'foo'
 Oppretter en liste som tar verdi av foo og verdi av bar for 2. lambda.
 Deretter en liste blir opprettet for 1. lambda som tar verdi av 'bar' og 'baz'
 og utenfor list-parantesen, blir 'baz' kalt på. På slutten av definisjonen blir
 foo kalt på, og 'towel, og vi får: (towel (42 towel)).
|#

;; 1c)

#|
 Definerer en prosedyre 'infix-eval som tar ett argument 'exp'
 og den er foventet å være en liste av 3 elementer: operand (verdi), operator og operand (verdi) igjen.
 cadr, car og caddr blir brukt:
 Henter andre element i lista, deretter første element, og til slutt tredje elementet i lista.
 I dette tilfellet, la oss se på foo: '+' (operator) blir henta først,
 deretter 21 (verdi) i første indeksen,
 og til slutt 21 (verdi) i siste indeksen og vi får: (+ 21 21) = 42
|#
(display "1c)\n")

(define (infix-eval exp)
  ((cadr exp) (car exp) (caddr exp)))

(define foo (list 21 + 21))

(define baz (list 21 list 21))

(define bar (list 84 / 2))

(infix-eval foo)

(infix-eval baz)

(infix-eval baz)

;; 1d)

;;(define bah '(84 / 2))

;;(infix-eval bah)

#|
 Resultatet av følgende kall av definisjonen 'bah' blir:
 "application: not a procedure; expected a procedure that can be applied
 to arguments given: /".
 Resultatet blir altså en feilmelding. Grunnen til det er at det blir brukt
 en anførselstegn (') før (84 / 2) så programmet tolker (/) ikke som en prosedyre, men
 som en symbol istedenfor.
|#

;; ---------------------------------------------------------

;; Oppgave 2

;; a)
#|
(define (decode bits tree)
  (define (decode-1 bits current-branch)
    (if (null? bits)
        '()
        (let ((next-branch
               (choose-branch (car bits) current-branch)))
          (if (leaf? next-branch)
              (cons (symbol-leaf next-branch)
                    (decode-1 (cdr bits) tree))
              (decode-1 (cdr bits) next-branch)))))
  (decode-1 bits tree))
|#

;; Halerekursiv versjon av decode:

(define (decode bits tree)
  (define (decode-1 bits current-branch x)
    (if (null? bits)
        (reverse x)
        (let ((next-branch
               (choose-branch (car bits) current-branch)))
          (if (leaf? next-branch)
              (decode-1 (cdr bits) tree (cons (symbol-leaf next-branch) x))
              (decode-1 (cdr bits) next-branch x)))))
  (decode-1 bits tree '()))

;; b)
(display "2b)\n")
(decode sample-code sample-tree)
;; Resultatet av å kalle prosedyren decode fra oppgaven over gir oss:
;; (samurais fight ninjas by night).

;; c)
(display "2c)\n")

(define (encode message tree)
  (define (encode-1 message current-branch x)
    (if (null? message)
        (reverse x)
        (if (leaf? current-branch)
            (if (eq? (car message) (symbol-leaf current-branch))
                (encode-1 (cdr message) tree x)
                '())
            (append (encode-1 message (left-branch current-branch) (cons 0 x))
                    (encode-1 message (right-branch current-branch) (cons 1 x))))))
  (encode-1 message tree '()))

#|
 Skriver prosedyren encode som transformerer en sekvens av symboler til en sekvens av
 bits. 'encode' tar inspirasjon fra 'decode' (oppgaven over) med at den tar imot
 2 argumenter: 'message' og 'tree'. Deretter definerer vi også 'encode-1'.
 De to definisjonene følger samme struktur som forrige prosedyre: 'encode'.
 Hvis løvnåden er i nåværende gren OG hvis argumentene angir samme objekt i 'message'
 så blir ordene funnet og bli gitt en verdi der 'melding' i venstre-gren får verdien
 0, mens 'melding' i høyre-gren får verdien 1, også blir det encode-1 kalt hale-rekursivt.
|#

(decode (encode '(ninjas fight ninjas) sample-tree) sample-tree)
;; Resultat: (ninjas fight ninjas)

;; d)
(display "2d)\n")

(define (grow-huffman-tree par)
  (define (iter x)
    (if (null? (cdr x))
        (car x)
        (iter (adjoin-set (make-code-tree (car x) (cadr x)) (cddr x)))))
  (iter (make-leaf-set par)))

(define freqs '((a 2) (b 5) (c 1) (d 3) (e 1) (f 3)))
(define codebook (grow-huffman-tree freqs))
(decode (encode '(a b c) codebook) codebook) ;; -> (a b c)

;; e)
(display "2e)\n")

(define alfabet '((samurais 57) (ninjas 20) (fight 45) (night 12) (hide 3) (in 2)
                  (ambush 2) (defeat 1) (the 5) (sword 4) (by 12) (assassin 1)
                  (river 2) (wait 1) (poison 1)))

;; Genererer et Huffman-tre for alfabetet:
(define huff-tre (grow-huffman-tree alfabet))

(define msg '(ninjas fight
              ninjas fight ninjas
              ninjas fight samurais
              samurais fight
              samurais fight ninjas
              ninjas fight by night))

;; Encoder meldingen i treet slik at jeg får meldingen i bits:
(encode msg huff-tre)

;; Svar til spørsmål:

#|
 1. 43 bits blir det brukt på å kode meldingen.
 2. Gjennomsnittlige lengden på hvert kodeord er 2.5
    fordi vi dividerer 43 bits med 17 kodeord som blir brukt.
 3. Minste antall bits man ville trengt for å kode meldingen med fast lengde
   (fixed-length code) over det samme alfabet er: 4 fordi log2(16) = 4.
|#

;; f)
(display "2f)\n")
(define (huffman-leaves tree)
  (if (leaf? tree)
      (list (cdr tree))
      (append (huffman-leaves (left-branch tree))
              (huffman-leaves (right-branch tree)))))

#|
 Skriver prosedyren 'huffman-leaves' som tar imot en argument (en tre som input),
 og deretter returnerer en liste med par av symboler og frekvenser ved at vi
 først sjekker om det er løvnode i treet, hvis ja: oppretter en liste som
 henter verdiene etter første par i 'tree' (ved bruk av cdr) og deretter
 binder sammen listene fra begge sider: left og right.
|#

(huffman-leaves sample-tree)
    

