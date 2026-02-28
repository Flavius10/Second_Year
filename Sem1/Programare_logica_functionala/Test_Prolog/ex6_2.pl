inloc([], _, _, []).

inloc([Elem | T], Elem, Lista, [Lista | Rez]):-
    !,
    inloc(T, Elem, Lista, Rez).

inloc([H | T], Elem, Lista, [H | Rez]):-
    inloc(T, Elem, Lista, Rez).

