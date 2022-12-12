CFLAGS = -std=gnu99

all: clean ruterdrift run

ruterdrift: ruterdrift.c
	gcc $(FLAGS) $^ -g -o ruterdrift

run:
	./ruterdrift topology.dat commands.txt

clean:
	rm -f ruterdrift

valgrind:
	valgrind --leak-check=full --show-leak-kinds=all ./ruterdrift topology.dat commands.txt
