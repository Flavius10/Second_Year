urmator_numar_principal(Lista, [CarryOut | Rez]):-
    urmator_numar(Lista, CarryOut, Rez),
    CarryOut > 0,
    !.

urmator_numar_principal(Lista, Rez):-
    urmator_numar(Lista, _, Rez).


urmator_numar([], 1, []).

urmator_numar([H | T], CarryOut, [Digit | Rez]):-
    urmator_numar(T, CarryIn, Rez),

    Valoare is H + CarryIn,

    Digit is Valoare mod 10,
    CarryOut is Valoare div 10.
