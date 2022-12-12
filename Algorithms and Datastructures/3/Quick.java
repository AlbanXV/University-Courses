class Quick extends Sorter {

    void sort() {
        quickSort(0, n - 1);
    }

    // Metode som har low og high som indekser og gir ut et sortert array:
    int[] quickSort(int low, int high) {

        // Returnerer A hvis low er større eller lik high:
        if (low >= high) {
            return A;
        }

        // Sorterer:
        int p = partition(low, high);
        quickSort(low, p - 1);
        quickSort(p + 1, high);
        return A;
    }

    // Metode som bruker low og high som indekser og flytter elementer:
    int partition(int low, int high) {

        int p = choosePivot(low, high);

        // int temp = A[high];
        // A[high] = A[p];
        // A[p] = temp;

        // swapper mellom high og p (og teller antall swaps):
        swap(high, p);

        int pivot = A[high];
        int left = low;
        int right = high - 1;

        // Sjekker når right er større eller lik left
        while (left <= right) {
            // så skal verdi i left økes når right er større eller lik left og
            // pivot er større eller lik left i A (+ teller sammenligninger):
            while (left <= right && leq(A[left], pivot)) {
                left++;
            }
            // Synker ned verdi i right når right er større eller lik left og
            // pivot er større eller lik right i A (+ teller sammenligninger):
            while (right >= left && geq(A[right], pivot)) {
                right--;
            }
            // Hvis right er større enn left:
            if (left < right) {
                // temp = A[left];
                // A[left] = A[right];
                // A[right] = temp;

                // swapper mellom left og right (og teller antall swaps):
                swap(left, right);
            }
        }
        // temp = A[left];
        // A[left] = A[high];
        // A[high] = temp;

        // Utenfor while-løkken, swapper mellom left og high (og teller antall swaps):
        swap(left, high);
        return left;
    }

    // Metode som velger medianverdien av første (a), midterste (b) og siste tallet
    // (c) i A:
    int choosePivot(int low, int high) {
        // gir a, b og c egenskaper:
        int a = A[low];
        int b = A[Math.round(high / 2)];
        int c = A[high];

        // Hvis a er større enn b og b er større enn c:
        if (a > b && b > c) {
            // Returnerer midterste tall
            return Math.round(high / 2);
            // Hvis b er større enn a og a er større enn c:
        } else if (b > a && a > c) {
            // Returnerer første / low tall:
            return low;
        }
        // Returnerer siste / high tallet:
        return high;
    }

    String algorithmName() {
        return "quick";
    }
}
