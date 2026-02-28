import heapq

def al_k_lea_uman(numere, k):
    numere.sort(reverse=True)
    return numere[k-1]


def al_k_lea_ai(numere, k):
    # Creează un min-heap cu primele k elemente
    mini_heap = numere[:k]
    heapq.heapify(mini_heap)

    # Parcurge restul elementelor
    for i in range(k, len(numere)):
        if numere[i] > mini_heap[0]:
            heapq.heapreplace(mini_heap, numere[i])

    return mini_heap[0]

sir = [7, 4, 6, 3, 9, 1]
k = 2
print(al_k_lea_uman(sir, k))