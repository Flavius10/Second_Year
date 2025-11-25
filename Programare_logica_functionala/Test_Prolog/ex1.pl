contine(E, [E | _ ]):- !.

contine(E, [ _ | T]) :-
    contine(E, T).

% duferenta(Lista: lista initiala, L1: a doua lista initiala, Rez:
% rezultatul)
% Subpunctul a.)
diferenta([], _ , []).

diferenta([H | T], L1, [H | Rez]):-
    \+ contine(H, L1),
    !,
    diferenta(T, L1, Rez).

diferenta([_ | T], L1, Rez):-
    diferenta(T, L1, Rez).

%Subpunctul b.)

adaugare([], []).

adaugare([H | T], [H, 1 | Rez]):-
    0 is H mod 2,
    !,
    adaugare(T, Rez).

adaugare([H | T], [H | Rez]):-
    adaugare(T, Rez).

