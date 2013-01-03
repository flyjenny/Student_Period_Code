package ex5_2;

public class qsort {
	public static void quicksort(line[] a, int left, int right) {
		if (right <= left)
			return;
		int i = partition(a, left, right);
		quicksort(a, left, i - 1);
		quicksort(a, i + 1, right);
	}

	private static int partition(line[] a, int left, int right) {
		line pivot = a[left];
		int l = left;
		int r = right;
		while (l < r) {
			while ((l <= r) && (campare(a[l], pivot) == -1)) {
				l++;
			}
			while ((r >= l) && (campare(a[r], pivot) == 1)) {
				r--;
			}
			if (l < r) {
				line tmp = a[l];
				a[l] = a[r];
				a[r] = tmp;
			}
		}
		int mid = r;
		line temp = a[left];
		a[left] = a[mid];
		a[mid] = temp;
		/*
		 * int i = left - 1; int j = right; while (true) { while
		 * (campare(a[++i],a[right])==-1); //a[++i].x1<a[right].x1 while
		 * (campare(a[right],a[--j])==-1) //a[right].x1<a[--j].x1
		 * campare(a[right],a[--j])==-1) if (j == left) break; if (i >= j)
		 * break; exch(a, i, j); } exch(a, i, right);
		 */return mid;
	}

	private static void exch(line[] a, int i, int j) {
		line swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	public static int campare(line a, line b) {
		int mark = -1; // a<=b 为-1；a>b 为1
		if (a.x1 < b.x1) {
			mark = -1;
		} else if (a.x1 > b.x1) {
			mark = 1;
		} else {
			if (a.x1 == b.x1) {
				if ((a.x1 == a.x2) && (b.x1 == b.x2)) { // 竖直线 竖直线
					mark = -1;
				}
				if ((a.x1 == a.x2) && (b.x1 != b.x2)) { // 竖直线 水平线
					if (b.x1 < b.x2) {
						mark = 1;
					} else {
						mark = -1;
					}
				}
				if ((a.x1 != a.x2) && (b.x1 == b.x2)) { // 水平线 竖直线
					if (a.x1 < a.x2) {
						mark = -1;
					} else {
						mark = 1;
					}
				}
				if ((a.x1 != a.x2) && (b.x1 != b.x2)) { // 水平线 水平线
					if ((a.x1 > a.x2) && (b.x1 < b.x2)) {
						mark = 1;
					} else if ((a.x1 < a.x2) && (b.x1 > b.x2)) {
						mark = -1;
					} else {
						mark = -1;
					}
				}
			}
		}

		return mark;
	}
}
