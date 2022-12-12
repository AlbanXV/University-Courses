class Selection extends Sorter {

    void sort() {
        int i, j, k;

        // Lar i være 0, for løkke gjennom i:
        for (i = 0; i < n - 1; i++) {
            k = i;

            // For-løkke gjennom j der j = i+1:
            for (j = i + 1; j < n; j++) {
                // Deretter sjekker hvis j av A er mindre enn k av A,
                // så blir k <-- j (+ teller sammenligninger):
                if (lt(A[j], A[k])) {
                    k = j;
                }
            }
            // Hvis i ikke stemmer med k:
            if (i != k) {
                // int temp = A[i];
                // A[i] = A[k];
                // A[k] = temp;

                // swapper mellom i og k (og teller antall swaps):
                swap(i, k);
            }
        }
    }

    String algorithmName() {
        return "selection";
    }
}
