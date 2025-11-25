contine(E, [E | _ ]):- !.

contine(E, [ _ | T]):-
    contine(E, T).

creeaza_set(Lista, Rez):-
    creeaza_set_helper(Lista, [], RezIntors),
    reverse(RezIntors, Rez).

creeaza_set_helper([], Acumulator , Acumulator).

creeaza_set_helper([H | T], Acumulator , Rez):-
    \+ contine(H, Acumulator),
    !,
    creeaza_set_helper(T, [H | Acumulator], Rez).

creeaza_set_helper([H | T], Acumulator ,Rez):-
    contine(H, Acumulator),
    creeaza_set_helper(T, Acumulator, Rez).


par_impar([], 0, 0, [[], []]).

par_impar([H | T], Par, Impar, [[H | ParList], ImparList]):-
    0 is H mod 2,
    par_impar(T, Par1, Impar, [ParList, ImparList]),
    Par is Par1 + 1.

par_impar([H | T], Par, Impar, [ParList, [H | ImparList]]):-
    1 is H mod 2,
    par_impar(T, Par, Impar1, [ParList, ImparList]),
    Impar is Impar1 + 1.

