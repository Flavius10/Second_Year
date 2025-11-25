cea_mai_lunga_pare(Lista, Rezultat):-
    scanare(Lista, [], [], Rezultat).

actualizare_maxim(L1, L2, L1):-
    length(L1, Len1),
    length(L2, Len2),
    Len1 > Len2,
    !.

actualizare_maxim(_, L2, L2).


scanare([], Curent, Maxim, Rez):-
    actualizare_maxim(Curent, Maxim, ListaMaxima),
    reverse(ListaMaxima, Rez).

scanare([H | T], Curent, Maxim, Rez):-
    0 is H mod 2,
    !,
    scanare(T, [H | Curent], Maxim, Rez).

scanare([_ | T], Curent, Maxim, Rez):-
    actualizare_maxim(Curent, Maxim, ListaCastigatoare),
    scanare(T, [], ListaCastigatoare, Rez).
