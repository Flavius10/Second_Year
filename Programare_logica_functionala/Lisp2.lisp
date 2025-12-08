(defun parcurge_lista_fii (L)
	(cond
		((NULL L) NIL)
		(T (append (preordine(car L)) (parcurge_lista_fii(cdr L))))
	)
)

(defun preordine (L)
	(cond
		((NULL L) NIL)
		(T (cons (car L) (parcurge_lista_fii(cdr L))))
	)
)