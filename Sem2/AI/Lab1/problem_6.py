def element_majoritar_uman(numere):
    n = len(numere)
    frecventa = {}

    for x in numere:
        if x in frecventa:
            frecventa[x] += 1
        else:
            frecventa[x] = 1

        if frecventa[x] > n / 2:
            return x

    return None


def element_majoritar_ai(numere):
    candidat = None
    voturi = 0

    for x in numere:
        if voturi == 0:
            candidat = x
            voturi = 1
        elif x == candidat:
            voturi += 1
        else:
            voturi -= 1

    return candidat


sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
print(element_majoritar_ai(sir))

sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
print(element_majoritar_uman(sir))