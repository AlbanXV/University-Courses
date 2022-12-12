(load "evaluator.scm")

;; Innlevering 3b
;; Av: Alban Bajrami (albanba) og Atahan Caliskan (atahanc)
;; ---------------------------------------------------------

;; Oppgave 1
;; a)

(display "1a)\n")
(set! the-global-environment (setup-environment))

(mc-eval '(define (foo cond else)
  (cond ((= cond 2) 0)
        (else (else cond)))) the-global-environment) ;; --> OK

(mc-eval '(define cond 3) the-global-environment) ;; --> OK

(mc-eval '(define (else x) (/ x 2)) the-global-environment) ;; --> OK

(mc-eval '(define (square x) (* x x)) the-global-environment) ;; --> OK


(mc-eval '(foo 2 square) the-global-environment) ;; --> 0

#|
 Det som blir returnert her er 0 fordi vi får vite at cond argumentet
 er 2, og i følge foo, så evaluerer den til 0 fordi (= cond 2) --> 0,
 altså 2 = 2 fører til 0.
|#

(mc-eval '(foo 4 square) the-global-environment) ;; --> 16

#|
 Det blir returner 16 her fordi vi ser at cond parameteret er ikke 2 som forrige
 så den hopper over til else: (else (else cond)))), og foo kaller på (square x)
 som tar kvadratet av tallet gitt som argument i cond: 4, og ganger den med
 seg selv og vi får: 16.
|#

(mc-eval '(cond ((= cond 2) 0)
                (else (else 4))) the-global-environment) ;; --> 2

#|
 Det blir returnert 2 her fordi den kaller på definisjonen else som
 tar 4 og deler den på 2 og vi får 2 som resultat.
|#

;; ---------------------------------------------------------

;; Oppgave 2
(display "2a)\n")
;; a)

;; Hentet og modifisert fra evaluator.scm:
(define primitive-procedures
  (list (list 'car car)
        (list 'cdr cdr)
        (list 'cons cons)
        (list 'null? null?)
        (list 'not not)
        (list '+ +)
        (list '- -)
        (list '* *)
        (list '/ /)
        (list '= =)
        (list 'eq? eq?)
        (list 'equal? equal?)
        (list 'display 
              (lambda (x) (display x) 'ok))
        (list 'newline 
              (lambda () (newline) 'ok))
        ;; Legger til 1+ og 1-:
        (list '1+
              (lambda (x) (+ x 1)))
        (list '1-
              (lambda (x) (- x 1)))
        ))

;; INPUT-TEST:
;;(read-eval-print-loop)

;; (1+ 2) --> 3
;; (1- 2) --> 1

;; b)
(display "2b)\n")

(define (install-primitive! navn proc)
  (set! the-global-environment
        (extend-environment (list navn) (list(list 'primitive proc))
                            the-global-environment)))

#|
 Definerer install-primitve som lar oss legge til nye primitive prosedyrer
 i den globale omgivelsen under kjøring av evaluatoren ved at
 install-primitive! tar imot 2 argumenter: navn til prosedyren og selve
 prosedyren. Bruker set! for å destruktivt endre på the-global-environment
 og deretter bruker extend-environment fra evaluator.scm
 for å legge til ny primitiv prosedyre.
|#

;; Legger til ny primitiv prosedyre:

(install-primitive! 'square (lambda (x) (* x x)))

;; INPUT-TEST:
;;(read-eval-print-loop)

;; INPUT: (square 2) --> 4 (MC-Eval value OUTPUT)

;; ---------------------------------------------------------

;; Oppgave 3
;; a)

(display "3a)\n")

;; Hentet og modifisert (lagt til) and og or i evaluatoren:
(define (and? exp) (tagged-list? exp 'and))

(define (or? exp) (tagged-list? exp 'or))

;; Hentet og modifisert and og or som nye special forms i evaluatoren:
(define (special-form? exp)
  (cond ((quoted? exp) #t)
        ((assignment? exp) #t)
        ((definition? exp) #t)
        ((if? exp) #t)
        ((lambda? exp) #t)
        ((begin? exp) #t)
        ((cond? exp) #t)
        ((and? exp) #t) ;; LEGGER TIL and?
        ((or? exp) #t) ;; LEGGER TIL or?
        (else #f)))

;; Definerer eval-and i evaluatoren:

(define (eval-and exp env)
  (cond ((false? (mc-eval (cadr exp) env))
         #f)
        ((null? (cddr exp))
         (mc-eval (cadr exp) env))
        (else (eval-and (cons (car exp) (cddr exp)) env))))

#|
 Lager condition som sjekker hvis andre elementet i lista exp som blir evaluert
 er false: returnerer false.
 Deretter en annen condition som sjekker hvis elementer fra indeks 2 og oppover
 er null: evaluerer exp element i indeks 1.
 Hvis ikke (else): evaluerer lista med eval-and
|#

;; Definererer eval-or i evaluatoren:

(define (eval-or exp env)
  (cond ((null? (cddr exp)) #f)
        ((true? (mc-eval (cadr exp) env))
         (mc-eval (cadr exp) env))
        (else (eval-or (cons (car exp) (cddr exp)) env))))

#|
 Lager condition der den sjekker hvis cddr i exp, altså elementer i lista
 fra indeks 2 og oppover, er null: returnerer false.
 Deretter en annen condition som sjekker hvis andre elementet i lista i exp
 som skal bli evaluert ved mc-eval, er true: så blir den evaluert
 Hvis ikke (else): evaluerer med eval-or lista.
|#

;; Hentet og modifisert eval-special-form ved å legge til 
(define (eval-special-form exp env)
  (cond ((quoted? exp) (text-of-quotation exp))
        ((assignment? exp) (eval-assignment exp env))
        ((definition? exp) (eval-definition exp env))
        ((if? exp) (eval-if exp env))
        ((lambda? exp)
         (make-procedure (lambda-parameters exp)
                         (lambda-body exp)
                         env))
        ((begin? exp) 
         (eval-sequence (begin-actions exp) env))
        ((cond? exp) (mc-eval (cond->if exp) env))
        ((and? exp) (eval-and exp env)) ;; LEGGER TIL eval-and
        ((or? exp) (eval-or exp env)) ;; LEGGER TIL eval-or
        ))

;; INPUT-TEST:
;;(read-eval-print-loop)

;; Eksempel outputs fra test:

;; (and 3 7 5) --> 5
;; (and #f #t) --> #f
;; (and #t #t) --> #f

;; (or 3 2 1) --> 3
;; (or #t #f) --> #t
;; (or #f #t) --> #f

;; b)
(display "3b)\n")

;; Endrer på if? i evaluatoren:
;;(define (if? exp) (tagged-list? exp 'if))
(define (if? exp) (tagged-list? (cddr exp) 'then))

#|
Gammel eval-if:
(define (eval-if exp env)
  (if (true? (mc-eval (if-predicate exp) env))
      (mc-eval (if-consequent exp) env)
      (mc-eval (if-alternative exp) env)))
|#

;; Endrer på if-syntaksen (eval-if) i evaluatoren:
(define (eval-if exp env)
  (let ((x (mc-eval (if-predicate exp) env)))
    (cond ((true? x) (mc-eval (cadddr exp) env))
          ((tagged-list? exp 'else) x)
          (else (eval-if (cddddr exp) env)))))

;; INPUT-TEST:
;;(read-eval-print-loop)

;; Eksempel input fra test:
;; (if (= 1 2)
;; then 3
;; elsif (= 5 5)
;; then 1
;; else 2)

;; Resultat (output) --> MC-Eval value: 1

;; c)

;; Defienerer let? i evaluatoren:
(define (let? exp) (tagged-list? exp 'let))

;; Legger let? i special-form?:
(define (special-form? exp)
  (cond ((quoted? exp) #t)
        ((assignment? exp) #t)
        ((definition? exp) #t)
        ((if? exp) #t)
        ((lambda? exp) #t)
        ((begin? exp) #t)
        ((cond? exp) #t)
        ((and? exp) #t)
        ((or? exp) #t)
        ((let? exp) #t) ;; LEGGER TIL let?
        (else #f)))

;; Legger eval-let? i eval-special-form:
(define (eval-special-form exp env)
  (cond ((quoted? exp) (text-of-quotation exp))
        ((assignment? exp) (eval-assignment exp env))
        ((definition? exp) (eval-definition exp env))
        ((if? exp) (eval-if exp env))
        ((lambda? exp)
         (make-procedure (lambda-parameters exp)
                         (lambda-body exp)
                         env))
        ((begin? exp) 
         (eval-sequence (begin-actions exp) env))
        ((cond? exp) (mc-eval (cond->if exp) env))
        ((and? exp) (eval-and exp env))
        ((or? exp) (eval-or exp env))
        ((let? exp) (eval-let exp env)) ;; LEGGER TIL eval-let
        ))

;; DEFINERER eval-let:
(define (eval-let exp env)
  (mc-eval (let->lambda exp env) env))

;; HJELPEMIDLER FOR let (inspirert fra oppg. 4.6):
(define (let-var exp) (map car (cadr exp)))

(define (let-init exp) (map cadr (cadr exp)))

(define (let-body exp) (caddr exp))

;; Kombinerer dem til en definisjon som blir kalt på eval-let:
(define (let-combination exp)
  (list 'lambda (let-var exp) (let-body exp)))

(define (let->lambda exp env)
  (append (list (let-combination exp)) (let-init exp)))

;; INPUT-TEST:
;;(read-eval-print-loop)

;; Eksempel input fra test:

;; (let ((a 5)
;; (b 5))
;; (+ a b))

;; Resultat (output) --> MC-Eval value: 10

;; d)
