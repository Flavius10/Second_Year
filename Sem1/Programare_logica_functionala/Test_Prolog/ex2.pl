cmmdc(A, 0, A):- !.

cmmdc(A, B, Rez):-
     Rest is A mod B,
     cmmdc(B, Rest, Rez).


cmmmc(A, _, 0):- A = 0, !.
cmmmc(_, B, 0):- B = 0, !.

cmmmc(A, B, Rez):-
     cmmdc(A, B, DivizorComun),
     Rez is  (A * B) // DivizorComun.

cmmmc_lista_final(Lista, Rez):-
     cmmmc_lista_helper(Lista, 1, Rez).

cmmmc_lista_helper([], Acumulator, Acumulator).

cmmmc_lista_helper([H | T], Acumulator, Rezultat):-
     cmmmc(Acumulator, H, CMMMC),
     cmmmc_lista_helper(T, CMMMC, Rezultat).


adaugare(Lista, Val, Rez):-
     adaugare_helper(Lista, 1, 1, Val, Rez).

adaugare_helper([], _, _, _, []).

adaugare_helper([H | T], PozCurenta, PozUrm, Val, [H, Val | Rez]):-
     PozCurenta =:= PozUrm,
     !,
     NewPoz is PozCurenta + 1,
     NewPozUrm is PozUrm * 2,

     adaugare_helper(T, NewPoz, NewPozUrm, Val, Rez).


adaugare_helper([H | T], PozCurenta, PozUrm, Val, [H| Rez]):-
     NewPoz is PozCurenta + 1,
     adaugare_helper(T, NewPoz, PozUrm, Val, Rez).
