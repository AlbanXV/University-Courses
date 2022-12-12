class Heap extends Sorter {

    void sort() {
        // Kall på max-heap:
        buildMaxHeap(A, n);

        // lar i være n-1 og går gjennom i og senker:
        for (int i = n - 1; i > 0; i--) {
            // swapper mellom i og 0 (og teller antall swaps):
            swap(i, 0);
            bubbleDown(A, 0, i);
        }
    }

    // Metode som gjør et array om til en max-heap:
    void buildMaxHeap(int[] A, int n) {
        for (int i = n / 2; i >= 0; i--) {
            bubbleDown(A, i, n);
        }
    }

    // Metode som henter inn uferdig heap A med n elementer der i er roten:
    void bubbleDown(int[] A, int i, int n) {

        // gir largest, left og right egenskaper:
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Hvis left er mindre enn n og largest av A er mindre enn left av A (+ teller
        // sammenligninger):
        if (lt(left, n) && lt(A[largest], A[left])) {
            // Swapper mellom largest og temp:
            int temp = largest;
            largest = left;
            left = temp;
        }

        // Hvis right er mindre enn n og largest av A er mindre enn right av A (+ teller
        // sammenligninger):
        if (lt(right, n) && lt(A[largest], A[right])) {
            // Swapper mellom largest og right:
            int temp = largest;
            largest = right;
            right = temp;
        }

        // Hvis i er ikke lik som largest:
        if (i != largest) {
            // swapper mellom i og largest (og teller antall swaps):
            swap(i, largest);
            bubbleDown(A, largest, n);
        }
    }

    String algorithmName() {
        return "heap";
    }
}
