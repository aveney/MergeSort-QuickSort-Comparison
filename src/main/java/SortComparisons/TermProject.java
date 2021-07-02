package SortComparisons;

/*
 The program will determine the number of swaps, comparisons, and time required to complete
 merge sort and quick sort on a random array of 1000000 integers. The output will be displayed in the
 console as a table.
 */
public class TermProject {

    int numComparisons;
    int numSwappings;

    // temporary storage for sorted array
    int[] y = new int[1000000];

    void merge(int[] x, int l, int m, int h) {

        int i = l; // left position variable
        int j = m + 1; // right position variable
        int mergePos = 0;

        // make the comparisons
        while (i <= m && j <= h) {
            if (x[i] < x[j]) {
                y[mergePos] = x[i];
                mergePos++;
                i++;
                numSwappings++;
            } else {
                y[mergePos] = x[j];
                mergePos++;
                j++;
                numSwappings++;
            }
        }
        numComparisons += 1;
        // add remaining elements from left partition
        while (i <= m) {
            y[mergePos] = x[i];
            mergePos++;
            i++;
        }

        // add remaining elements from right partition
        while (j <= h) {
            y[mergePos] = x[j];
            mergePos++;
            j++;
        }

        // phase 3: copy y back to x:
        int k = 0;
        for (; k <= h - l; k++)
            x[k + l] = y[k];
    }

    void mergeSort(int[] x, int l, int h) {
        // initialize middle value
        int m;
        if (l >= h) {
            return;
        }
        // find the mid point
        m = (h + l) / 2;

        // recursively sort left and right partitions
        mergeSort(x, l, m);
        mergeSort(x, m + 1, h);

        // merge partitions in sorted array
        merge(x, l, m, h);

    }

    int partition(int[] x, int low, int high) {
        int m = low + (high - low) / 2;
        int pv = x[m];
        int l = low, h = high;
        boolean done = false;

        while (!done) {
            while (x[l] < pv)
                l++;
            while (x[h] > pv)
                h--;
            numComparisons++;
            if (l >= h)
                done = true;
            else {
                int temp = x[l];
                x[l] = x[h];
                x[h] = temp;
                l++;
                h--;
                numSwappings++;
            }
        }
        return h;
    }

    void quicksort(int[] x, int low, int high) {
        if (low >= high)
            return;
        int h = partition(x, low, high);
        quicksort(x, low, h);
        quicksort(x, h + 1, high);

    }

    public void revisedinsertsort(int[] x) {
        int n = x.length;
        // do n-1 rounds
        for (int i = 1; i < n; i++) {
            int low = 0, high = i - 1, m = 0;
            boolean igm = (x[i] > x[0]);
            while (low < high) {
                m = (low + high) / 2;
                numComparisons++;
                if (x[i] > x[m]) {
                    low = m + 1;
                } else if (x[i] < x[m]) {
                    high = m - 1;
                } else
                    break;
            }

            if (low == high) {
                m = low;
                if (x[i] > x[m])
                    m++; // minor adjustment
            }
            if (i > m) {
                int t = x[i];
                for (int j = i - 1; j >= m; j--) {
                    numSwappings++;
                    x[j + 1] = x[j];
                }
                x[m] = t;
            }
        }
    }

    public static void main(String[] args) {
        TermProject sorts = new TermProject();
        // Generate random array of 100000 elements
        int N = 100000;
        int[] array1 = new int[N];
        int[] array2 = new int[N];
        int[] array3 = new int[N];
        for (int n = 0; n < 100000; n++) {
            array1[n] = array2[n] = array3[n] = (int) (Math.random() * N * 2);
        }

        // Get the runtime, swap count, and comparison count for Quick Sort algorithm
        long quickStart = System.currentTimeMillis();
        sorts.quicksort(array2, 0, array2.length - 1);
        long quickEnd = System.currentTimeMillis() - quickStart;
        int quickSwaps = sorts.numSwappings;
        int quickComparisons = sorts.numComparisons;

        // reset number of comparisons and swaps
        sorts.numSwappings = 0;
        sorts.numComparisons = 0;

        // Get the runtime, swap count, and comparison count for Merge Sort algorithm
        long mergeStart = System.currentTimeMillis();
        sorts.mergeSort(array1, 0, array1.length - 1);
        long mergeEnd = System.currentTimeMillis() - mergeStart;
        int mergeSwaps = sorts.numSwappings;
        int mergeComparisons = sorts.numComparisons;

        // Reset the number of comparisons and swaps
        sorts.numSwappings = 0;
        sorts.numComparisons = 0;

        // Get the runtime, swap count, and comparison count for Binary Insertion Sort
        // algorithm
        long revisedStart = System.currentTimeMillis();
        sorts.revisedinsertsort(array3);
        long revisedEnd = System.currentTimeMillis() - revisedStart;
        int insertSwaps = Math.abs(sorts.numSwappings);
        int insertComparisons = sorts.numComparisons;

        // Present table for random array

        // Head of the table
        System.out.println("Table for Random Array");
        System.out.println("------------------------------------------------------------------------");
        System.out.print("Measure\\Method   |   ");
        System.out.printf("%20s", "Revised-insertion   |");
        System.out.printf("%15s", "Quicksort   |");
        System.out.printf("%15s", "Mergesort   \n");

        System.out.println("------------------------------------------------------------------------");
        System.out.print("#comparisons     |");
        System.out.printf("%11d          |", insertComparisons);
        System.out.printf("%11d     |", quickComparisons);
        System.out.printf("%8d\n", mergeComparisons);

        System.out.println("------------------------------------------------------------------------");
        System.out.print("#swaps           |");
        System.out.printf("%11d          |", insertSwaps);
        System.out.printf("%9d     |", quickSwaps);
        System.out.printf("%9d\n", mergeSwaps);

        System.out.println("------------------------------------------------------------------------");
        System.out.print("#miliseconds     |");
        System.out.printf("%11d          |", revisedEnd);
        System.out.printf("%11d   |", quickEnd);
        System.out.printf("%8d\n", mergeEnd);

        System.out.println("------------------------------------------------------------------------");
    }
}
