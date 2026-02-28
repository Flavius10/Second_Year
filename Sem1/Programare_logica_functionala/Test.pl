% model de flux(i, o)
% sterg(Lista: Lista care se da, Rez: O lista care vine ca si raspuns la
% cerinta)


sterge_pana_la_final(Lista, RezStergere9):-
    sterg(Lista, RezStergere),
    sterg(RezStergere, RezStergere1),
    sterg(RezStergere1, RezStergere2),
    sterg(RezStergere2, RezStergere3),
    sterg(RezStergere3, RezStergere4),
    sterg(RezStergere4, RezStergere5),
    sterg(RezStergere5, RezStergere6),
    sterg(RezStergere6, RezStergere7),
    sterg(RezStergere7, RezStergere8),
    sterg(RezStergere8, RezStergere9).
    %sterge_pana_la_final(RezStergere, Rez).


sterg([], []):- !.

sterg([X], [X]):- !.

sterg([A, B | T], Rez):-
    B =:= A + 1,
    !,
    sari_peste(B, T, Rez).

sterg([A , B | T], [A | Rez]):-
    sterg([B | T], Rez).


%model de flux(i, i, o)
% sari_peste(Last: Numarul care se da ca sa poti sa vezi care a fost
% elementul precedent, Lista: lista care e in continuarea elementului,
% Rez: Lista dupa ce s-a "Sarit" peste cele consecutive).
sari_peste(Last, [H | T], Rez):-
    H =:= Last + 1,
    !,
    sari_peste(H, T, Rez).

sari_peste(_, [H | T], Rez):-
    sterg([H | T], Rez).

sari_peste(_, [], []):- !.

%model de flux(i, i)
contine(E, [E | _]):- !.

contine(E, [_ | T]):-
    contine(E, T).

%model de flux(i, i)
egalitate_liste([], _):- !.

egalitate_liste([H | T], L2):-
    contine(H, L2),
    egalitate_liste(T, L2).

egalitate(L1, L2):-
    egalitate_liste(L1, L2),
    egalitate_liste(L2, L1).























