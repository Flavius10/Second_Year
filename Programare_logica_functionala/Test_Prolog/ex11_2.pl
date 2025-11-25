e_prim(2).

e_prim(N):-
    N > 2,
    Start is N - 1,
    verifica(N, Start).

verifica(_, 1) :- !.

verifica(N, Iterator):-
    N mod Iterator =\= 0,
    Iterator1 is Iterator - 1,
    verifica(N, Iterator1).


dubleaza_prim([], []).

dubleaza_prim([H | T], [H , H | Rez]):-
    e_prim(H),
    !,
    dubleaza_prim(T, Rez).

dubleaza_prim([H | T], [H | Rez]):-
    dubleaza_prim(T, Rez).
