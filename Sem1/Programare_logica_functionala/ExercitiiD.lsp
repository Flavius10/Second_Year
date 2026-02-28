;Înlocuirea unui element e cu e1 în toată lista (la orice nivel).

(defun inlocuire1 (L E E1)
	(cond
		((NULL L) NIL)
		((equal E (car L)) (cons E1 (inlocuire1 (cdr L) E E1)))
		((atom (car L)) (cons (car L) (inlocuire1 (cdr L) E E1)))
		(T (cons (inlocuire1 (car L) E E1) (inlocuire1 (cdr L) E E1)))
	)
)

(defun inlocuire1Map (L E E1)
	(mapcar #'(lambda (elem)
		(cond
			((and (atom elem)(equal elem E)) E1)
			((and (atom elem)(not (equal elem E))) elem)
			(T (inlocuire1Map elem E E1))
		)
	) L )
)

;Substituirea unui element e cu e1 doar la nivelurile impare

(defun substituire (L E E1 Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (and (atom elem) (equal elem E)) (oddp Niv)) E1)
			((atom elem) elem)
			(T (substituire elem E E1 (+ 1 Niv)))
		)
	) L )
)

;Înlocuirea tuturor valorilor numerice cu o valoare e.

(defun inlocuieste_numere (L E)
	(mapcar #'(lambda (elem)
		(cond
			((and (atom elem) (numberp elem)) E)
			((atom elem) elem)
			(T (inlocuieste_numere elem E))
		)
	) L )
)

;Înlocuirea atomilor de pe nivelul k cu 0.

(defun atomi_nivel (L K Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (atom elem)(equal K Niv)) 0)
			((atom elem) elem)
			(T (atomi_nivel elem K (+ 1 Niv)))
		)
	) L )
)

;Înlocuirea atomilor de pe nivelurile pare cu 0.

(defun inlocuire_pare (L Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (atom elem) (evenp Niv)) 0)
			((atom elem) elem)
			(T (inlocuire_pare elem (+ 1 Niv)))
		)
	) L )
)

;Înlocuirea valorilor numerice impare situate pe un nivel par cu succesorul lor.

(defun inlocuire_impare (L Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (and (numberp elem) (evenp Niv)) (oddp elem)) (+ 1 elem))
			((atom elem) elem)
			(T (inlocuire_impare elem (+ 1 Niv)))
		)
	) L )
)

;Înlocuirea valorilor numerice > k situate pe un nivel impar cu predecesorul lor.
			
(defun problema1 (L K Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (and (numberp elem)(> elem k)) (oddp Niv)) (- elem 1))
			((atom elem) elem)
			(T (problema1 elem K (+ 1 Niv)))
		)
	) L )
)

;Înlocuirea valorilor numerice pare cu succesorul.

(defun problema2 (L)
	(mapcar #'(lambda (elem)
		(cond
			((and (numberp elem) (evenp elem)) (+ 1 elem))
			((atom elem) elem)
			(T (problema2 elem))
		)
	) L )
)

;Înlocuirea fiecărui atom nenumeric cu numărul total de
;apariții ale acelui atom la nivelul respectiv.

(defun nrAtom (L E)
	(cond 
		((NULL L) 0)
		((equal (car L) E) (+ 1 (nrAtom (cdr L) E)))
		(T (nrAtom (cdr L) E)) 
	)
)

(defun inlocuieste_per_nivel (L Niv)
	(mapcar #'(lambda (elem)
		(cond
			((and (atom elem)(not (numberp elem))) (nrAtom L elem))
			((atom elem) elem)
			(T (inlocuieste_per_nivel elem (+ 1 Niv)))
		)
	) L )
)

;Eliminarea atomilor nenumerici de pe nivelurile pare.

(defun eliminare (L Niv)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((and (and (atom elem)(not (numberp elem)))(evenp Niv)) NIL)
				((atom elem) (list elem))
				(T (list (eliminare elem (+ 1 Niv))))
			)
		) L )
	)
)

;Eliminarea atomilor numerici multipli de 3 de la orice nivel.

(defun eliminare2(L Niv)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((and (numberp elem)(equal (mod elem 3) 0)) NIL)
				((atom elem) (list elem))
				(T (list (eliminare2 elem (+ 1 Niv))))
			)
		) L )
	)
)

;Eliminarea atomilor numerici pari situați pe un nivel impar.

(defun eliminare3 (L Niv)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((and(and(numberp elem)(evenp elem))(oddp Niv)) NIL)
				((atom elem) (list elem))
				(T (list (eliminare3 elem (+ 1 Niv))))
			)
		) L )
	)
)

;Eliminarea tuturor atomilor de pe nivelul k.
(defun eliminare4 (L Niv K)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((and(atom elem)(equal Niv K)) NIL)
				((atom elem) (list elem))
				(T (list (eliminare4 elem (+ 1 Niv) K)))
			)
		) L )
	)
)
	
;Eliminarea tuturor aparițiilor unui element e.

(defun eliminare5 (L E)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond 
				((and (atom elem)(equal elem E)) NIL)
				((atom elem) (list elem))
				(T (list (eliminare5 elem E)))
			)
		) L )
	)
)

;Calculul CMMDC al numerelor impare de la nivelurile pare.

(defun numereImpare (L Niv)
	(apply #'append	
		(mapcar #'(lambda (elem)
			(cond
				((and(and (atom elem)(oddp elem)) (oddp Niv)) (list elem))
				((atom elem) NIL)
				(T (numereImpare elem (+ 1 Niv)))
			)
		)L
		)
	)
)

(defun cmmdc (A B)
	(cond
		((equal B 0) A)
		(T (cmmdc B (mod A B)))
	)
)

(defun cmmdc_lista (L C)
	(cond
		((NULL L) C)
		(T (cmmdc_lista (cdr L) (cmmdc (car L) C)))
	)
)

(defun main (L)
	(cmmdc_lista (numereImpare L 0) 0)
)


;Construirea unei liste liniare 
;cu atomii nenumerici care apar de un număr par de ori.

(defun nrElem (L E)
	(apply #'+
		(mapcar #'(lambda (elem)
			(cond
				((and (atom elem)(equal elem E)) 1)
				((atom elem) 0)
				(T (nrElem elem E))
			)
		) L )
	)
)

(defun lista_liniara(L L1)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((and (and (atom elem) (not (numberp elem)))(evenp (nrElem L1 elem))) (list elem))
				((atom elem) NIL)
				(T (lista_liniara elem L1))
			)
		)L )
	)
)

(defun membru (L E)
	(cond 
		((NULL L) NIL)
		((equal (car L)) T)
		(T (membru (cdr L) E))
	)
)

(defun setPersonal (L A)
	(cond
		((not (membru A (car L))) (cons (car L) A))
		(T (setPersonal (cdr L) A))
	)
)

(defun main_problema(L)
	(setPersonal (lista_liniara L L) ())
)

;Înlocuirea nodurilor de pe nivelurile impare cu o valoare e.

(defun niveluriImpare (L E Niv)
    (cons 
        
        (if (oddp Niv) E (car L))

        (mapcar #'(lambda (sub) 
                    (niveluriImpare sub E (+ 1 Niv))) 
                (cdr L))
    )
)

(defun nImpare (L E Niv)
	(cons 
		(if (oddp Niv) E (car L))
	
		(mapcar #'(lambda (sub)
			(nImpare sub E (+ 1 Niv))
		) (cdr L))
	)
)


; Înlocuirea nodurilor de pe nivelul k cu o valoare e.

(defun niveluriK (L E K Niv)
  (cons 
   
    (if (equal Niv K) E (car L))
    
    (mapcar #'(lambda (subarbore)
                (niveluriK subarbore E K (+ 1 Niv))
              )
            (cdr L))
  )
)

;Numărarea nodurilor de pe nivelul k.

(defun numaraNiveluri (L Niv K)
	(apply #'+
		(if (equal Niv K) 1 0)
		
		(mapcar #'(lambda (sub)
			(numaraNiveluri sub (+ 1 Niv) K)
		) (cdr L))
	)
)

; Construirea listei nodurilor de pe nivelul k.

(defun lista_noduri (L K Niv)
	(cond
	
		((equal Niv K)(list (car L)))
	
		(T (apply #'append 
			(mapcar #' (lambda (subarbore)
				(lista_noduri subarbore K (+ 1 Niv))
			) (cdr L))
		))
	)
)

;Construirea listei nodurilor de pe nivelurile pare.
(defun noduri (L Niv)
	(append
		
		(if (evenp Niv) (list (car L)) NIL)
		
		(apply #'append
			(mapcar #'(lambda (subarbore)
				(noduri subarbore (+ 1 Niv))
			)(cdr L))
		)
	)
)

;Verificarea dacă un nod X apare pe un nivel par.
(defun verifica (L X Niv)
    (append
        
        (if (and (equal (car L) X) (evenp Niv)) 
            (list T) 
            NIL)
        
        (apply #'append 
            (mapcar #'(lambda (sub)
                        (verifica sub X (+ 1 Niv))
                      ) 
                    (cdr L))
        )
    )
)

;Determinarea înălțimii unui nod (sau a arborelui).
(defun inaltime (L)
    (if (null (cdr L))
		0
		
		(+ 1 (apply #'max
				(mapcar #'inaltime (cdr L))
			)
		)
	)
)

;Determinarea căii de la rădăcină către un nod X dat.
(defun cale (L X)
    (if (equal (car L) X)
        (list X)

        (if (apply #'append (mapcar #'(lambda (s) (cale s X)) (cdr L)))
            
            (cons (car L) 
                  (apply #'append (mapcar #'(lambda (s) (cale s X)) (cdr L)))
            )
            
            NIL
        )
    )
)

(defun cale (L X)
	(cond	
		((equal (car L) X) (list (car L)))
		(T (cauta_in_copii (car L) (cdr L) X))
	)
)

(defun cauta_in_copii (Tata ListaCopii X)
	(cond
		((NULL ListaCopii) NIL)
		((cale (car ListaCopii) X)
			(cons Tata (cale (car ListaCopii) X)))
		(T (cauta_in_copii Tata (cdr ListaCopii) X))
	)
)
		