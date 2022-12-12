class Insertion extends Sorter {

    void sort() {

        int i, j;

        // Lar i være 1, går gjennom løkke i:
        for (i = 1; i < n; i++) {
            j = i;

            // While-løkke som sjekker når j er større enn 0 og bruker gt
            // som er akkurat samme som > men teller antall sammenlikning for
            // når j-1 i A er større enn j i A
            while (j > 0 && gt(A[j - 1], A[j])) {
                // k = A[j - 1];
                // A[j - 1] = A[j];
                // A[j] = k;

                // swapper mellom j-1 og j (og teller antall swaps):
                swap(j - 1, j);
                j--;
            }
        }

    }

    String algorithmName() {
        return "insertion";
    }
}
