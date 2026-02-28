f([], []).

f([H|T], [H|Rez]) :-
    H > 0,
    !,
    f(T, Rez).

f([_|T], Rez) :-
    f(T, Rez).

f1([], 0).
f1([H|T], S) :- f1(T, S1), S is S1 + H.

p(100).
p(N) :- write(N), N1 is N - 1, p(N1), nl.

genereaza_lista(B, B, [B]):- !.

genereaza_lista(A, B, [A | Rez]):-
    A1 is A + 1,
    genereaza_lista(A1, B, Rez).

suma([], 0).
suma([H | T], Rez):-
    suma(T, Rez1),
    Rez is Rez1 + H.

suma_para(L):-
    suma(L, Suma),
    0 is Suma mod 2.

combinari(_, 0, SumaCrt, []):-
    SumaCrt mod 2 =:= 0.

combinari([H | T], N, SumaCrt, [H | Rez]):-
    N > 0,
    N1 is N - 1,
    SumaNoua is SumaCrt + H,
    combinari(T, N1, SumaNoua, Rez).

combinari([_ | T], N, SumaCrt, Rez):-
    N > 0,
    combinari(T, N, SumaCrt, Rez).

rezultat_final(A, B, N, RezultatFinal):-
    genereaza_lista(A, B, ListaNoua),
    findall(S, combinari(ListaNoua, N, 0, S), RezultatFinal).

selecteaza(E, [E | T], T).

selecteaza(E, [H | T], [H | Rez]):-
    selecteaza(E, T, Rez).


aranjamente(_, 0, PC, PD, []):-
    PC =:= PD.

aranjamente(L, K, PC, PD, [E | Rez]):-
    K > 0,
    selecteaza(E, L, Rest),
    K1 is K - 1,
    PN is PC * E,
    aranjamente(Rest, K1, PN, PD, Rez).

rezultat_final_aranj(L, K, P, Rez):-
    findall(S, aranjamente(L, K, 1, P, S), Rez).


maxim([], -1).

maxim([H | T], S):-
    H > 0,
    maxim(T, S1),
    S1 < H,
    !,
    S is H.

maxim([_ | T], S):-
    maxim(T, S1),
    S is S1.


suma1([], 0).

suma1([H | T], S):-
    suma1(T, S1),
    verificare(H, S1, Suma),
    S is S1 + Suma.

verificare(H, S1, H):-
    H < S1,
    !.

verificare(_, _, Suma):-
    Suma is 2.

p1(1).
p1(2).

q(1).
q(2).

r(1).
r(2).

s:-!, p1(X), q(Y), r(Z), write([X, Y, Z]), nl.

lungime([], 0).

lungime([_ | T], Rez):-
    lungime(T, Rez1),
    Rez is Rez1 + 1.


generare_k(Min, Max, Min):- Min =< Max.

generare_k(Min, Max, K):-
    Min < Max,
    Min1 is Min + 1,
    generare_k(Min1, Max, K).

combinari1(_, 0, SumaCrt, []):-
    SumaCrt mod 3 =:= 0.

combinari1([H | T], K, SumaCrt, [H | Rez]):-
    K > 0,
    K1 is K - 1,
    SumaNoua is SumaCrt + H,
    combinari1(T, K1, SumaNoua, Rez).

combinari1([_ | T], K, SumaCrt, Rez):-
    K > 0,
    combinari1(T, K, SumaCrt, Rez).

submultimi(L, N, Rez):-
    lungime(L, Lungime),
    generare_k(N, Lungime, K),
    combinari1(L, K, 0, Rez).

sol_final(L, N, Rez):-
    findall(S, submultimi(L, N, S), Rez).







rezolvare(A, B, Rez) :-
    findall(Sub, bkt(A, B, 0, 0, Sub), Rez).

bkt(A, B, NP, NI, []) :-
    A > B,
    NP mod 2 =:= 0,
    NI mod 2 =:= 1.

bkt(A, B, NP, NI, [A|Rez]) :-
    A =< B,
    actualizeaza(A, NP, NI, NP_Nou, NI_Nou),
    A1 is A + 1,
    bkt(A1, B, NP_Nou, NI_Nou, Rez).

bkt(A, B, NP, NI, Rez) :-
    A =< B,
    A1 is A + 1,
    bkt(A1, B, NP, NI, Rez).

actualizeaza(Nr, NP, NI, NP_Nou, NI) :-
    Nr mod 2 =:= 0, !,
    NP_Nou is NP + 1.

actualizeaza(_, NP, NI, NP, NI_Nou) :-
    NI_Nou is NI + 1.

aranj(_, 0, S, []):-
    S mod 2 =:= 1.

aranj(L, Flag, S, [E | Rez]):-
    selecteaza(E, L, Rest),
    S1 is S + E,
    FlagN is 1 - Flag,
    aranj(Rest, FlagN, S1, Rez).

rezolvare_completa(L, Rez):-
    findall(S, aranj(L, 0, 0, S), Rez).

aranjamente_cu_toate(_, K, S, []):-
    K mod 2 =:= 0,
    S mod 2 =:= 1.

aranjamente_cu_toate(L, K, S, [E | Rez]):-
    K > 0,
    selecteaza(E, L, Rest),
    S1 is S + E,
    K1 is K - 1,
    aranjamente_cu_toate(Rest, K1, S1, Rez).

rezolvare_completa_tot(L, Rez):-
    lungime(L, Lungime),
    findall(S, aranjamente_cu_toate(L, Lungime, 0, S), Rez).


combinari_sp([H | _], 1, S, [H]):-
    (S + H) mod 2 =:= 0.

combinari_sp([H | T], K, S, [H | Rez]):-
    K > 1,
    S1 is S + H,
    K1 is K - 1,
    combinari_sp(T, K1, S1, Rez).

combinari_sp([_ | T], K, S, Rez):-
    K > 0,
    combinari_sp(T, K, S, Rez).

rez_sp(L, K, Rez):-
    findall(S, combinari_sp(L, K, 0, S), Rez).

generare(A, B, []):-
    A > B,
    !.

generare(A, B, [A | Rez]):-
    A =< B,
    !,
    A1 is A + 1,
    generare(A1, B, Rez).


combinare_suma_impara([], S, []):-
    S mod 2 =:= 1.

combinare_suma_impara([H | T], S, [H | Rez]):-
    S1 is S + H,
    combinare_suma_impara(T, S1, Rez).

combinare_suma_impara([_ | T], S, Rez):-
    combinare_suma_impara(T, S, Rez).


rezultat_final_impara(A, B, Rez):-
    generare(A, B, Lista),
    findall(S, combinare_suma_impara(Lista, 0, S), Rez).

flavius([], []).

flavius([H | T], [H | S]):- flavius(T, S).

flavius([H | T], S):- H mod 2 =:= 0, flavius(T, S).


submultimi(_, 0, _, []).

sumbultimi([H | T], K, E, [H | Rez]):-
    K > 0,
    abs(H - E) mod 2 =:= 0,
    K1 is K - 1,
    sumbumltimi(T, K1, H, Rez).

submultimi([_ | T], K, E, Rez):-
    K > 0,
    submultimi(T, K, E, Rez).

rezolvare_submultimi(N, K, Rez):-
    generare(1, N, [H | T]),
    findall(S, submultimi([H | T], K, H, S), Rez).



membru(E, [E | _]):- !.

membru(E, [_ | T]):-
    membru(E, T).

lungime1([], 0).

lungime1([_ | T], Rez):-
    lungime1(T, Rez1),
    Rez is Rez1 + 1.


