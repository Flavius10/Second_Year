def cuvinte_unice_uman(text):
    cuvinte = text.split()
    aparitii = {}

    for cuv in cuvinte:
        if cuv in aparitii:
            aparitii[cuv] += 1
        else:
            aparitii[cuv] = 1

    unice = [x for x in aparitii if aparitii[x] == 1]
    return unice

from collections import Counter

def cuvinte_unice_ai(text):
    numaratoare = Counter(text.split())
    return [cuvant for cuvant, count in numaratoare.items() if count == 1]



text = "ana are ana are mere rosii ana"
print(cuvinte_unice_uman(text))