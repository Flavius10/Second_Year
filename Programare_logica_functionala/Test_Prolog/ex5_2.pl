maxim([X], X):- !.

maxim([H | T], H):-
    maxim(T, MaxT),
    H >= MaxT,
    !.

maxim([ _ | T], MaxT):-
    maxim(T, MaxT).



gaseste_poz(Lista, Rez):-
    maxim(Lista, MaximGlobal),
    gaseste_poz_helper(Lista, 1, MaximGlobal, Rez).

gaseste_poz_helper([], _, _, []).

gaseste_poz_helper([H | T], Poz, MaximGlobal, [Poz | Rez]):-
    H =:= MaximGlobal,
    !,
    Poz1 is Poz + 1,
    gaseste_poz_helper(T, Poz1, MaximGlobal, Rez).

gaseste_poz_helper([ _ | T], Poz, MaximGlobal, Rez):-
    Poz1 is Poz + 1,
    gaseste_poz_helper(T, Poz1, MaximGlobal, Rez).
