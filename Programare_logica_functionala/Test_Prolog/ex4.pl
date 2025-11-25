inlocuieste([], _, _, []).

inlocuieste([H | T], Element, ListaInloc, [ListaInloc | Rez]):-
    H =:= Element,
    !,
    inlocuieste(T, Element, ListaInloc, Rez).

inlocuieste([H | T], Element, ListaInloc, [H | Rez]):-
    inlocuieste(T, Element, ListaInloc, Rez).

elimina_poz_final(Lista, PozDorita, Rez):-
    elimina_poz_helper(Lista, 1,PozDorita, Rez).

elimina_poz_helper([], _, _, []).

elimina_poz_helper([_ | T], Poz, PozDorita, Rez):-
    Poz =:= PozDorita,
    !,
    Poz1 is Poz + 1,
    elimina_poz_helper(T, Poz1, PozDorita, Rez).

elimina_poz_helper([H | T], Poz, PozDorita, [H | Rez]):-
    Poz1 is Poz + 1,
    elimina_poz_helper(T, Poz1, PozDorita, Rez).
