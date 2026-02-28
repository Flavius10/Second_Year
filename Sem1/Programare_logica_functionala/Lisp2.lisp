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

(defun procesare (L E NIV)
	(cons
		(if (oddp NIV) E (car L))
		(mapcar #'(lambda(subarbore) (procesare subarbore E (+ NIV 1))) (cdr L))
	)
)

(defun procesare_3 (L K NIV)
	(append 
		(if (equal NIV K) (list (car L)) NIL)
		(mapcan #'(lambda(subarbore) (procesare_3 subarbore K (+ 1 NIV))) (cdr L))
	)
)	

(defun Fct (F L)
    (cond
        ((NULL L) NIL)
        (T 
            ((lambda (rezultat)       
                (if rezultat         
                    (cons rezultat (Fct F (cdr L)))
                    NIL        
                )
            )
            
            (funcall F (car L))) 
        )
    )
)

(defun F (L)
	(cond
		((NULL L) NIL)
		(T 
			((lambda (fct)
				(cond
					((> fct 2) (+ (car L) (F (cdr L))))
					(T fct)
				)
				) (F (car L))
			)
		)
	)
)

(defun inlocuire (L K Niv)
	(mapcar #'(lambda (elem)
				(cond
					((atom elem)
						(if (equal Niv K) 0 elem)
					)
					
					(T
						(inlocuire elem K (+ 1 Niv))
					)
				)
			)
		L)
)

(defun exista (L)
	(cond 
		((not (numberp L)) 0)
		((numberp L) 1)
		(T (apply '+ (mapcar #' exista L)))
	)
)

(defun schimbare (Elem K Niv)
	(cond
		((atom Elem) (if (equal Niv K) 0 Elem))
		(T
			(mapcar #'(lambda (x)
				(schimbare x K (+ 1 Niv))		
						) Elem
			)
		)
	)
)

(defun nr_pare (L)
    (cond
        ((and (numberp L) (evenp L)) (list L))
        ((atom L) NIL)
        (T (mapcan #'nr_pare L))
    )
)

(defun nr_impare (L)
    (cond
        ((and (numberp L) (oddp L)) (list L))
        ((atom L) NIL)
        (T (mapcan #'nr_impare L))
    )
)

(defun combina (P I)
	(cond
		((and (NULL P) (NULL I)) NIL)
		(T (cons 
			(car P) (cons (list (car I)) (combina (cdr P) (cdr I)))))
	)
)

(defun run_problem (L)
	(combina (nr_pare L) (nr_impare L))
)

(defun elimina_div_3 (L)
	(cond 
		((atom L) (if (= (mod L 3) 0) NIL (list L)))
		(T
			(mapcan #' (lambda (x)
					(cond
						((= (mod x 3) 0) NIL)
						((listp x) (list elimina_div_3 x))
						(T (list x))
					)
				)
			L)
		)
	)
)