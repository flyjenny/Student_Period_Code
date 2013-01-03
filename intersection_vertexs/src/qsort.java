

public class qsort {
	public static void quicksort(line[] a, int left, int right) {
        if (right <= left) return;
        int i = partition(a, left, right);
        quicksort(a, left, i-1);
        quicksort(a, i+1, right);
    }

    private static int partition(line[] a, int left, int right) {
        int i = left - 1;
        int j = right;
        while (true) {
            while (a[++i].x1<a[right].x1)
                ;
            while (a[right].x1<a[--j].x1)
                if (j == left) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, i, right);
        return i;
    }

    private static void exch(line[] a, int i, int j) {
        line swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
