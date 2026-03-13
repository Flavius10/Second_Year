def get_last_word_sorted_human(text):
    words = text.split()
    words.sort()
    return words[-1]


def get_last_word_pro(text):
    words = text.split()
    return max(words) if words else ""

text = "Ana are mere rosii si galbene"
print(get_last_word_sorted_human(text))