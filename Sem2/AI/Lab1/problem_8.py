def genereaza_binar_uman(n):
    rezultate = []
    for i in range(1, n + 1):
        numar = i
        suma = 0
        p = 1
        while numar > 0:
            modul = numar % 2
            suma = (p * modul) + suma
            p *= 10
            numar //= 2
        rezultate.append(str(suma))
    return rezultate

n = 20
print(", ".join(genereaza_binar_uman(n)))