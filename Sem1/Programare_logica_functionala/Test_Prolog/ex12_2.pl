inlocuire([], _, _, []).

inlocuire([Elem | T], Elem, Inlocuire, [Inlocuire | Rez]):-
    !,
    inlocuire(T, Elem, Inlocuire, Rez).

inlocuire([H | T], Elem, Inlocuire, [H | Rez]):-
    inlocuire(T, Elem, Inlocuire, Rez).
