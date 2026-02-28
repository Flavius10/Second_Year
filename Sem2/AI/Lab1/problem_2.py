import math


def distanta_euclidiana(p1, p2):
    x1, y1 = p1
    x2, y2 = p2

    distanta = math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)
    return distanta

def patratul_diferentei(a, b):
    return (a - b) ** 2

def calculeaza_distanta(p1, p2):
    suma_patratelor = patratul_diferentei(p1[0], p2[0]) + patratul_diferentei(p1[1], p2[1])
    return math.sqrt(suma_patratelor)


punct_a = (1, 5)
punct_b = (4, 1)
print(distanta_euclidiana(punct_a, punct_b))