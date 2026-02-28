%Subpunctul a.)
%Sa se scrie un predicat care transforma o lista intr-o multime, in
%ordinea primei aparitii. Exemplu: [1,2,3,1,2] e transformat in [1,2,3].

%multime(L: Lista, V: lista_vizitate, M: Lista)
%multime(i, o), (i, i)

multime(L, M):-
    multime(L, [], M).

multime([], _, []).

multime([H | T] , V , [H | R]):-
    \+ member(H, V),
    multime(T, [H | V], R),
    !.

multime([H | T], V, R):-
    member(H, V),
    multime(T, V, R).

test_general_a:-
    multime([1, 2, 3, 1, 2], Rez),
    Rez = [1, 2, 3],

    multime([], Rez1),
    Rez1 = [].

%Subpunctul b.)
%Sa se scrie o functie care descompune o lista de numere intr-o lista de
%forma [ lista-de-numere-pare lista-de-numere-impare] (deci lista cu
%doua elemente care sunt liste de intregi), si va intoarce si numarul
%elementelor pare si impare.
%multime_b(i, o, o, o), (i, i)


multime_b([], [[], []], 0, 0).

multime_b([H | T], [[H | P], I], Par, Imp):-
    0 is H mod 2,
    !,
    multime_b(T, [P, I], Par1, Imp),
    Par is Par1 + 1.

multime_b([H | T],[P, [H | I]], Par, Imp):-
    1 is H mod 2,
    multime_b(T, [P, I], Par, Imp1),
    Imp is Imp1 + 1.


test_general_b:-
    multime_b([1,2,3,2,1], Rez, P, I),
    Rez=[[2, 2], [1, 3, 1]],
    P = 2,
    I = 3.
