% 1. Conditia de oprire:
% Daca lista e goala, rezultatul e o lista goala.
f([], []).

f([H|T], [H|Rez]) :-
    H > 0,
    f(T, Rez),
    !.

% 3. Cazul NEGATIV sau ZERO (Linia care LIPSEA in poza):
% Daca regula de mai sus a esuat (H <= 0), ajungem aici.
% Ignoram capul (folosim _) si continuam recursivitatea doar cu coada T.
f([_|T], Rez) :-
    f(T, Rez).
