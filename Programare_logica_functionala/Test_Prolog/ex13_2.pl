divizorii_numar_principal(Numar, Rez):-
    divizorii_numar(Numar, 2, Rez).

divizorii_numar(N, PosCurent, []):-
    PosCurent >= N,
    !.

divizorii_numar(Numar, PosCurent, [PosCurent | Rez]):-
    0 is Numar mod PosCurent,
    !,
    PosCurent1 is PosCurent + 1,
    divizorii_numar(Numar, PosCurent1, Rez).

divizorii_numar(Numar, PosCurent, Rez):-
    PosCurent1 is PosCurent + 1,
    divizorii_numar(Numar, PosCurent1, Rez).

my_append([], L, L).

my_append([H | T1], L2, [H | RezFinal]):-
    my_append(T1, L2, RezFinal).

sub_a([], []).

sub_a([H | T], RezFinalComplet):-
    divizorii_numar_principal(H, ListaDiv),

    sub_a(T, RezFinal),

    my_append([H], ListaDiv, ListaDivizoriAflata),

    my_append(ListaDivizoriAflata, RezFinal, RezFinalComplet).


e_prim(2):- !.

e_prim(N):-
    N > 2,
    Start is N - 1,
    verifica(N, Start).

verifica(_ , 1):-!.

verifica(N, Iterator):-
    0 =\= N mod Iterator,
    Iterator1 is Iterator - 1,
    verifica(N, Iterator1).


