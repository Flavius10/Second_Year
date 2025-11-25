select_n(Lista, N, Rez):-
    select(Lista, 1, N, Rez).

select([], _, _, 0):- !.

select([H | _], N, N, H):-
    !.

select([_ | T], Poz, N, Rez):-
    Poz1 is Poz + 1,
    select(T, Poz1, N, Rez).

contine(E, [E | _]).

contine(E, [_ | T]):-
    contine(E, T).

submultime([], _):- !.

submultime([H | T], ListaDoi):-
    contine(H, ListaDoi),
    !,
    submultime(T, ListaDoi).


egalitate_multimi(A, B):-
    submultime(A, B),
    submultime(B, A).
