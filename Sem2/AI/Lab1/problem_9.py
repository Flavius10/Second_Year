def calculeaza_suma_om(matrice, p, q, r, s):
    suma = 0
    for i in range(p, r + 1):
        for j in range(q, s + 1):
            suma += matrice[i][j]
    return suma

matrice = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]
]

print(calculeaza_suma_om(matrice, 1, 1, 3, 3))
print(calculeaza_suma_om(matrice, 2, 2, 4, 4))

def calculeaza_suma_ai(matrice, p, q, r, s):
    n = len(matrice)
    m = len(matrice[0])
    sums = [[0] * (m + 1) for _ in range(n + 1)]

    for i in range(1, n + 1):
        for j in range(1, m + 1):
            sums[i][j] = matrice[i - 1][j - 1] + sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1]

    return sums[r + 1][s + 1] - sums[p][s + 1] - sums[r + 1][q] + sums[p][q]


matrice = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]
]

print(calculeaza_suma_ai(matrice, 1, 1, 3, 3))
print(calculeaza_suma_ai(matrice, 2, 2, 4, 4))