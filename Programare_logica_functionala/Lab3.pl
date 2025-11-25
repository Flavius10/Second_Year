submultimi(_, 0, [[]]) :- !.

submultimi([], _, []) :- !.

submultimi([H | T], N, RezultatFinal) :-
    N > 0,
    N1 is N - 1,
    submultimi(T, N1, Rez_Partial_Cu_H),

    adauga_la_toate(H, Rez_Partial_Cu_H, Rez_Cu_H),
    submultimi(T, N, Rez_Fara_H),

    append(Rez_Cu_H, Rez_Fara_H, RezultatFinal).

adauga_la_toate(_, [], []):- !.

adauga_la_toate(Elem, [SubLista | Restul], [[Elem | SubLista] | Rez]) :-
    adauga_la_toate(Elem, Restul, Rez).
