def gaseste_duplicat_uman(numere):
    vazute = []
    for număr in numere:
        if număr in vazute:
            return număr
        vazute.append(număr)

def gaseste_duplicat_ai(numere):
    n = len(numere)
    suma_teoretica = (n - 1) * n // 2
    suma_reala = sum(numere)

    return suma_reala - suma_teoretica

sir = [1, 2, 3, 4, 2]
print(gaseste_duplicat_uman(sir))