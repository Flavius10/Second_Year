contine(E, [E | _]).

contine(E, [_ | T]):-
    contine(E, T).

multime([]).

multime([H | T]):-
    \+ contine(H, T),
    multime(T).


sterge_elem_numar(Lista, Elem, Rez):-
    sterge_element(Lista, 1, 3, Elem, Rez).

sterge_element([], _, _, _, []).

sterge_element([Elem | T], NumarCurent, NumarDorit, Elem, Rez):-
    NumarCurent =< NumarDorit,
    !,
    NumarCurent1 is NumarCurent + 1,
    sterge_element(T, NumarCurent1, NumarDorit, Elem, Rez).


sterge_element([H | T], NumarCurent, NumarDorit, Elem, [H | Rez]):-
    sterge_element(T, NumarCurent, NumarDorit, Elem, Rez).
