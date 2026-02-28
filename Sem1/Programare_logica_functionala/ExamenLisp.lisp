;liniarizare(L: lista)
; functie care liniarizeaza lista ca sa nu numaram pe adancime(ar fi mai greu)
(defun liniarizare (L)
	(apply #'append
		(mapcar #'(lambda (elem)
			(cond
				((atom elem) (list elem))
				(T (liniarizare elem))
			)
		) L
		)
	)
)

; contor (L: lista, E: atom)
; numara de cate ori se repeta in lista aia un element dat
(defun contor (L E)
	(cond
		((NULL L) 0)
		((equal (car L) E) (+ 1 (contor (cdr L) E)))
		(T (contor (cdr L) E))
	)
)

; se_repeta(L: lista, E: atom)
; functie care verifica daca un element se repeta
(defun se_repeta (L E)
	(if (> (contor (liniarizare L) E) 1) T NIL)
)

;functia_main(L: lista, L1: lista)
; functia care merge prin fiecare element si vede daca acesta 
; se repeta pe toata lista si utilizam append ca sa legam scotand nilul
(defun functia_main (L L1)
	(apply #'append
		(mapcar #'(lambda (elem)
				(cond
					((and (atom elem) (se_repeta L1 elem)) NIL)
					((and (atom elem) (not (se_repeta L1 elem))) (list elem))
					(T (list (functia_main elem L1)))
				)
			) L 
		)
	)
)