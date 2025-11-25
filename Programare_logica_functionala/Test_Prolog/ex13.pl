contine(E, [E | _]).

contine(E, [ _ | T]):-
    contine(E, T).


multime(Lista, Rez):-
    reverse(Lista, ListaReverse),
    multime_helper(ListaReverse, [], Rez).

multime_helper([], Acumulator, Acumulator).

multime_helper([H | T], Acumulator, Rez):-
    \+ contine(H, Acumulator),
    !,
    multime_helper(T, [H | Acumulator], Rez).

multime_helper([_ | T], Acumulator, Rez):-
    multime_helper(T, Acumulator, Rez).


cmmdc(A, 0, A):- !.

cmmdc(A, B, Rez):-
    B > 0,
    Res is A mod B,
    cmmdc(B, Res, Rez).


cmmdc_multime([X], X):- !.

cmmdc_multime([H | T], Rez):-
    cmmdc_multime(T, Rez1),
    cmmdc(H, Rez1, Rez).


ultima_aparitie([], []).

ultima_aparitie([H | T], [H | Rez]):-
    \+ contine(H, T),
    !,
    ultima_aparitie(T, Rez).

ultima_aparitie([ _ | T], Rez):-
    ultima_aparitie(T, Rez).

prima_aparitie_principal(Lista, Rez):-
    prima_aparitie(Lista, [], Rez).

prima_aparitie([], _, []).

prima_aparitie([H | T], Acumulator, [H | Rez]):-
    \+ contine(H, Acumulator),
    !,
    prima_aparitie(T, [H | Acumulator], Rez).

prima_aparitie([_ | T], Acumulator, Rez):-
    prima_aparitie(T, Acumulator, Rez).
