sortare_cu_dubluri([], []).

sortare_cu_dubluri([H | T], Result):-
    sortare_cu_dubluri(T, SortedT),
    insereaza(H, SortedT, Result).


insereaza(X, [], [X]):- !.


insereaza(X, [H | T], [X, H | T]):-
    X =< H,
    !.

insereaza(X, [H | T], [H | Result]):-
    X > H,
    insereaza(X, T, Result).
