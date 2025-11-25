scadere_liste(Lista1, Lista2, RezultatFinal):-
    length(Lista1, L1),
    length(Lista2, L2),

    egalizeaza_scadere(Lista1, L1, Lista2, L2, L1_Gata, L2_Gata),

    scadere_recursiva(L1_Gata, L2_Gata, _, RezultatBrut),

    elimina_zerouri(RezultatBrut, RezultatFinal).


egalizeaza_scadere(L1, Len1, L2, Len2, L1, L2_Nou) :-
    Len1 > Len2, !,
    Dif is Len1 - Len2,
    adauga_zerouri(L2, Dif, L2_Nou).

egalizeaza_scadere(L1, Len1, L2, Len2, L1_Nou, L2) :-
    Len2 > Len1, !,
    Dif is Len2 - Len1,
    adauga_zerouri(L1, Dif, L1_Nou).

egalizeaza_scadere(L1, _, L2, _, L1, L2).


scadere_recursiva([], [], 0, []).

scadere_recursiva([H1|T1], [H2|T2], BorrowOut, [Digit|Rez]) :-
    scadere_recursiva(T1, T2, BorrowFromRight, Rez),

    Valoare is H1 - H2 - BorrowFromRight,

    determina_imprumut(Valoare, Digit, BorrowOut).


determina_imprumut(Val, Val, 0) :-
    Val >= 0, !.

determina_imprumut(Val, Digit, 1) :-
    Val < 0,
    Digit is Val + 10.

adauga_zerouri(L, 0, L) :- !.
adauga_zerouri(L, N, [0|Res]) :-
    N > 0, N1 is N - 1, adauga_zerouri(L, N1, Res).

elimina_zerouri([0], [0]) :- !.
elimina_zerouri([0|T], R) :- !, elimina_zerouri(T, R).
elimina_zerouri(L, L).
