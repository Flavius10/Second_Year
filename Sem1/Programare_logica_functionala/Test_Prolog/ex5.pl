sterge_atom([], _ ,[]).

sterge_atom([H | T], Elem, Rez):-
    H =:= Elem,
    !,
    sterge_atom(T, Elem, Rez).

sterge_atom([H | T], Elem, [H | Rez]):-
    sterge_atom(T, Elem, Rez).

numar(ListaIntrare, Rezultat):-
    msort(ListaIntrare, ListaSortata),
    pacheteaza(ListaSortata, Rezultat).

pacheteaza([], []).

pacheteaza([H | T], [[H, N] | RezultatRecursiv]):-

    separa(T, H, ContinueH, Rest),

    length(ContinueH, Lungime),
    N is Lungime + 1,

    pacheteaza(Rest, RezultatRecursiv).


separa([Elem | T], Elem, [Elem | Pachet], Rest):-
    !,
    separa(T, Elem, Pachet, Rest).

separa(Rest, _, [], Rest).
