(defun produs (L F)
	(cond 
		((or (NULL L) (NULL F)) 0)
		(T (+ (produs (cdr L) (cdr F)) (* (car L) (car F))))
	)
)

(defun maxim (L)
    (cond
	
        ((null L) -999999)
        ((listp (car L))(max (maxim (car L)) (maxim (cdr L))))
        (T (max (car L) (maxim (cdr L))))
    )
)


(defun nr_par (L)
	(cond
		((NULL L) T)
		((and L (NULL (cdr L))) NIL)
		(T (nr_par (cdr (cdr L))))
	)
)

(defun elimina (E L)
	(cond
		((NULL L) NIL)
		((equal E (car L)) (cdr L))
		(t (cons (car L) (elimina E (cdr L))))
  )
)

(defun adauga (E L)
	(cond
		((NULL L) NIL)
		(T (cons (cons E (car L)) (adauga E (cdr L))))
	)
)

;; deci facem asa
;; luam primul element din L_curenta si dupa il stergem din lista curenta
;; din Lista_originala stergem elementul ala si dupa facem permutarile la
;; Lista_curenta si adaugam elementul in fata ei
 
(defun permutari_aux (L_curenta L_originala)
	(cond
		((NULL L_curenta) NIL)
		(T (append
          (adauga (car L_curenta) (permutari (elimina (car L_curenta) L_originala)))
          (permutari_aux (cdr L_curenta) L_originala)
      ))
  )
)

(defun permutari (L)
	(cond
		((NULL L) (list NIL))
		(t (permutari_aux L L))
  )
)