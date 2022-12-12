; Oblig 1b
; Alban Bajrami (albanba)

; Oppgave 1

; f)
; (0 42 #t bar)
(display "1f)\n")
(car(cdr '(0 42 #t bar)))
; Henter først andre del av paret, deretter første del av paret etter cdr: 42

; g)
; ((0 42) (#t bar))
(display "1g)\n")
(car (cdr (car '((0 42) (#t bar)))))
; Henter første del av paret (car), deretter andre del av paret etter det (cdr)
; og deretter første paret: 42

; h)
; ((0) (42 #t) (bar))
(display "1h)\n")
(car (car (cdr '((0) (42 #t) (bar)))))
; Henter andre del av paret, deretter første paret etter cdr, og deretter første paret
; etter å ha brukt car og vi får: 42

; i)
; Med cons:
;(cons (cons 0 (cons 42 '())) cons (cons #t (cons bar '())) '())

; Med list:
; (list (list 0 42) (list #t bar))

; ------------------------------------------------------------------------------------
; Oppgave 2
; a)
#|
Lager en definisjon som tar et tall n og en liste items som argumenter
og deretter returnerer en ny liste med de n første elementene av items.
Bruker if-setning for å sjekke om det er tomt, hvis ikke:
tar tak i andre del av paret i items og subtraherer n med tallet 1 i take,
og deretter får tak i første del av paret i lista.
|#
(display "2a)\n")
(define (take n items)
  (if (not (or (null? items) (zero? n)))
      (cons (car items)
            (take (- n 1) (cdr items)))
      '()))

(take 3 '(a b c d e f)) ; (a b c)
(take 1 '(a b c d e f)) ; (a)
(take 4 '(a b)) ; (a b)
(take 4 '()) ; ()

; b)
#|
Med hale-rekursjon:
Nesten akkurat det samme som oppg2a, men forskjellen her er
at jeg gjør den til en iterativ versjon fordi rekursive kallet
står i haleposisjon. Jeg definerer iter med 3 argumenter og
kaller på iter på slutten. (Bruker også reverse fordi listene
man får når man kjører prosedyrene er at man får dem i reverse,
så vi bruker reverse for å reversere lista som allerede er i reverse)
|#
(display "2b)\n")
(define (take n items)
  (define (iter n a b)
  (if (null? a)
      (reverse b)
      (if (zero? n)
          (reverse b)
          (iter (- n 1) (cdr a)
                      (cons (car a) b)))))
  (iter n items '()))
          

(take 3 '(a b c d e f)) ; (a b c)
(take 1 '(a b c d e f)) ; (a)
(take 4 '(a b)) ; (a b)
(take 4 '()) ; ()

; c)
#|
Lager en definisjon som tar et predikat pred og liste items som args
og returnerer ny liste med elementene fra items som tester sant for pred
fra starten av lista og frem til første element som tester usant.
Hvis det er tomt, bruker '() som indikerer for liste.
Bruker cdr for å hente andre del av paret i items og
kaller på take-while for predikatet.
Henter første paret i items og bruker det for predikatet
Hvis sant: fortsetter helt til det blir usant
Hvis usant fra starten: tom liste
|#
(display "2c)\n")
(define (take-while pred items)
  (cond ((pred (car items))
         (cons (car items)
               (take-while pred (cdr items))))
        ((null? items) '())
        (else '())))
        
        

(take-while even? '(2 34 42 75 88 103 250))
(take-while odd? '(2 34 42 75 88 103 250))
(take-while (lambda (x) (< x 100)) '(2 34 42 75 88 103 250))

; d)
#|
Følger map-definisjonen fra foilen i uke 3 som ga oss en
definisjon av prosedyren map. I denne oppgaven skal vi en variant
som opererer over TO lister parallelt og ikke EN som i foilen.
Det jeg gjør er at jeg lager en if-setning som sjekker hvis begge
listene er tomme. Hvis ikke, den kjører gjennom og opererer
ved å hente par fra begge lister. I dette tilfellet blir
verdiene fra de to listene med samme indeks addert sammen.
Lista stoppes når en av dem har ikke mer verdier i lista (blir tom)
|#
(display "2d)\n")
(define (map2 proc list1 list2)
  (if (not (or (null? list1) (null? list2)))
      (cons (proc (car list1) (car list2))
            (map2 proc (cdr list1) (cdr list2)))
      '()))

(map2 + '(1 2 3 4) '(3 4 5)) ; (4 6 8)

; e)
#|
Bruker lambda-uttrykk direkte som en anonym prosedyre
(og deler på 2 slik at vi får halvparten av (4 6 8), altså (2 3 4):
|#
(display "2e)\n")
(map2 (lambda (x y) (/ (+ x y) 2)) '(1 2 3 4) '(3 4 5)) ; (2 3 4)