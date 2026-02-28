def produs_scalar_uman(v1, v2):
    rar1 = []
    for i in range(len(v1)):
        if v1[i] != 0:
            rar1.append((i, v1[i]))

    rar2 = []
    for i in range(len(v2)):
        if v2[i] != 0:
            rar2.append((i, v2[i]))

    suma = 0
    for idx1, val1 in rar1:
        for idx2, val2 in rar2:
            if idx1 == idx2:
                suma += val1 * val2
    return suma


def produs_scalar_ai(v1, v2):
    dict1 = {i: val for i, val in enumerate(v1) if val != 0}

    suma = 0
    for i, val in enumerate(v2):
        if val != 0 and i in dict1:
            suma += val * dict1[i]

    return suma



v1 = [1, 0, 2, 0, 3]
v2 = [1, 2, 0, 3, 1]
print(produs_scalar_uman(v1, v2))