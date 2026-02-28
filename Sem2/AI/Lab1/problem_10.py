def linie_maxim_1_om(matrice):
    n = len(matrice)
    m = len(matrice[0])
    max_1 = -1
    index_linie = -1

    for i in range(n):
        nr_0 = matrice[i].count(0)
        nr_1 = m - nr_0
        if nr_1 > max_1:
            max_1 = nr_1
            index_linie = i

    return index_linie


matrice = [
    [0, 0, 0, 1, 1],
    [0, 1, 1, 1, 1],
    [0, 0, 1, 1, 1]
]


print(linie_maxim_1_om(matrice))


def linie_maxim_1_ai(matrice):
    n = len(matrice)
    m = len(matrice[0])
    index_linie = 0
    j = m - 1

    for i in range(n):
        while j >= 0 and matrice[i][j] == 1:
            j -= 1
            index_linie = i

    return index_linie


matrice = [
    [0, 0, 0, 1, 1],
    [0, 1, 1, 1, 1],
    [0, 0, 1, 1, 1]
]
print(linie_maxim_1_ai(matrice))