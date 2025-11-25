%inmultire(Lista, Numar, Adaugare, Rez)
inmultire_principal(Lista, Cifra, [CarryOut | Rezultat]):-
    inmultire(Lista, Cifra, CarryOut, Rezultat),
    CarryOut > 0,
    !.

inmultire_principal(Lista, Cifra, Rez):-
    inmultire(Lista, Cifra, 0, Rez).


inmultire([], _, 0, []).

inmultire([H | T], Cifra, CarryOut, [Digit | RestulListei]):-
    inmultire(T, Cifra, CarryIn, RestulListei),

    Valoare is H * Cifra + CarryIn,

    Digit is Valoare mod 10,
    CarryOut is Valoare div 10.
