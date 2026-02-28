substituire_helper([], _, _, []).

substituire_helper([Elem | T], Inlocuire, Elem, [Inlocuire | Rez]):-
    !,
    substituire_helper(T, Inlocuire, Elem, Rez).


substituire_helper([H | T], Inlocuire, Elem, [H | Rez]):-
    substituire_helper(T, Inlocuire, Elem, Rez).


sublista(Lista, M, N, Rez):-
    sublista_helper(Lista, 1, M, N, Rez).

sublista_helper([], _, _, _, []).

sublista_helper([H | T], Poz, M, N, [H | Rez]):-
    Poz >= M,
    Poz =< N,
    !,
    Poz1 is Poz + 1,
    sublista_helper(T, Poz1, M, N, Rez).

sublista_helper([_ | T], Poz, M, N, Rez):-
    Poz1 is Poz + 1,
    sublista_helper(T, Poz1, M, N, Rez).
