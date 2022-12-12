import csv
from glob import glob
#from collections import defaultdict

string = 'test\n'[:-1]

print(string)

'''
with open("movies.tsv", encoding='UTF-8') as fd:
    rd = csv.reader(fd, delimiter="\t", quotechar='"')
    for row in rd:
        print(row)
'''

'''
with open("actors.tsv", encoding='UTF-8') as fd:
    rd = csv.reader(fd, delimiter="\t", quotechar='"')
    teller = 0
    for row in rd:
        teller += 1
    print(teller)
'''

'''
with open("actors.tsv", encoding='UTF-8') as fd1, open("movies.tsv", encoding='UTF-8') as fd2:
    rd1 = csv.reader(fd1, delimiter="\t", quotechar='"')
    rd2 = csv.reader(fd2, delimiter="\t", quotechar='"')
    l1 = list(rd1)
    l2 = list(rd2)
'''

'''
filnavn = 'merge.tsv'

with open(filnavn, 'a', encoding='UTF-8') as single:
    f_tsv = True

    for i in glob('*.tsv'):
        if i == filnavn:
            pass
        else:
            header = True
            for line in open(i, 'r', encoding='UTF-8'):
                if f_tsv and header:
                    single.write(line)
                    f_tsv = False
                    header = False
                elif header:
                    header = False
                else:
                    single.write(line)
    single.close()

with open("merge.tsv", encoding='latin-1') as fd:
    rd = csv.reader(fd, delimiter="\t", quotechar='"')
    for row in rd:
        print(row)
'''
