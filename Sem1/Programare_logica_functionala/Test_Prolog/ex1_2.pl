suma_lista_principal(Lista1, Lista2, RezultatFinal):-
    length(Lista1, Lungime1),
    length(Lista2, Lungime2),

    egalizeaza(Lista1, Lungime1, Lista2, Lungime2, L1_Gata, L2_Gata),

    suma_lista(L1_Gata, L2_Gata, CarryOut, RezPartial),


    formateaza_rezultat(CarryOut, RezPartial, RezultatFinal).

egalizeaza(L1, Len1, L2, Len2, L1, L2_Modificat) :-
    Len1 > Len2,
    !,
    Diferenta is Len1 - Len2,
    adauga_zero_principal(L2, Diferenta, L2_Modificat).

egalizeaza(L1, Len1, L2, Len2, L1_Modificat, L2) :-
    Len2 > Len1,
    !,
    Diferenta is Len2 - Len1,
    adauga_zero_principal(L1, Diferenta, L1_Modificat).

egalizeaza(L1, _, L2, _, L1, L2).



suma_lista([], [], 0, []):- !.

suma_lista([H | T], [H1 | T1], CarryOut, [Digit | Rez]):-
   suma_lista(T, T1, CarryIn, Rez),

   Valoare is H + H1 + CarryIn,
   Digit is Valoare mod 10,
   CarryOut is Valoare div 10.

adauga_zero_principal(Lista, Lungime, Rez):-
    genereaza_lista_de_zero(Lungime, Zero),
    concateneaza_liste(Zero, Lista, Rez).




genereaza_lista_de_zero(0, []):- !.

genereaza_lista_de_zero(Lungime, [0 | Rez]):-
    Lungime1 is Lungime - 1,
    genereaza_lista_de_zero(Lungime1, Rez).

concateneaza_liste([], L, L).

concateneaza_liste([H | T], L, [H | Rez]):-
    concateneaza_liste(T, L, Rez).

formateaza_rezultat(CarryOut, RezInitial, [CarryOut | RezInitial]):-
    CarryOut > 0.



