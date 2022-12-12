all: oblig1_tests.c stringsum.c distance_between.c string_between.c stringsum2.c
	gcc $^ -o $@

stringsum: stringsum.c
	gcc $^ -o stringsum

distance: distance_between.c
	gcc $^ -o distance

strb: string_between.c
	gcc $^ -o strb

str2: stringsum2.c
	gcc $^ -o str2

test: oblig1_tests.c
	gcc $^ -o test

run: all
		./all

clean:
	rm -f all

valgrind:
	valgrind ./all
