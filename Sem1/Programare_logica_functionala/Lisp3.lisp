(defun lista_numere (L)
	(mapcan #'(lambda (element)
		(cond
			((numberp element) (list element))
			((atom element) NIL)
			(T (lista_numere element))
		)
	) L)
)
			
(defun maxim_lista (L)
	(apply #'max (lista_numere L))
)

(defun extrage_litere (L)
	(cond
		((NULL L) NIL)
		((numberp (car L)) (extrage_litere (cdr L)))
		((atom (car L))
			(cons (car L) (extrage_litere (cdr L))))
		(T (append (extrage_litere (car L)) (extrage_litere (cdr L))))
	)
)

(defun schimbare (L E V N)
	(mapcar #'(lambda (elem)
		(cond
			((atom elem)
				(if (and (oddp N) (equal elem E)) V elem)
			)
			
			(T (schimbare elem E V (+ 1 N)))
		)
	) L)
)


(defun verificare (L X N)


  (cond
    ((and (equal X (car L)) (evenp N)) 1)
    
    (T (APPLY #'OR
         (mapcar #'(lambda (subarbore)
                     (verificare subarbore X (+ 1 N))
                   )
                   (cdr L))
       )
    )
  )
)

(defun lungime(L)
	(cond 
		((NULL L) 0)
		(T (+ 1 (lungime (cdr L))))
	)
)

(defun genereaza (S N)
	(cond 
		((> S N) NIL)
		(T (cons S (genereaza (+ 1 S) N)))
	)
)

(defun insereaza (L P C E)
	(cond
		((equal C P) (cons E L))
		((NULL L) NIL)
		(T (cons (car L) (insereaza (cdr L) P (+ 1 C) E)))
	)
)

(defun make_move(N L E)
	(cond
	
		((NULL N) NIL)
		(T (cons (insereaza L (car N) 1 E) (make_move (cdr N) L E)))
		
	)
)

(defun rezolva (L E)
	(make_move (genereaza 1 (+ 1 (lungime L))) L E)
)