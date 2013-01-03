package jp.co.worksap.examination;

import java.util.*;

/**
 * @author BennyChan
 * @E-mail zqchenbin@gmail.com
 * @PhoneNumber +86-15121025925
 * Examination for Works Applications
 * @param <E>
 */
public class StackForPriorExaminationImpl<E extends Comparable<E>> implements
		IStackForPriorExamination<E> {
	private ArrayList<E> list;
	private ArrayList<E> assistList;
	private Stack max;
	private Stack min;
	private int size;

	public StackForPriorExaminationImpl() {
		list = new ArrayList<E>();
		assistList = new ArrayList<E>();
		max = null;
		min = null;
		max = new Stack<E>();
		min = new Stack<E>();
		size = 0;
	}

	@Override
	public void push(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			list.add(e);
			assistList.add(e);
			max.push(e);
			min.push(e);
			size++;
		} else {
			if (((E) max.peek()).compareTo(e) <= 0) {
				max.push(e);
			}
			if (((E) min.peek()).compareTo(e) >= 0) {
				min.push(e);
			}
			list.add(e);
			assistList.add(e);
			size++;
		}
	}

	@Override
	public E pop() throws EmptyStackException {
		if (size == 0) {
			throw new EmptyStackException();
		}
		E top = list.get(size - 1);
		if (((E) max.peek()).compareTo(top) == 0) {
			max.pop();
		}
		if (((E) min.peek()).compareTo(top) == 0) {
			min.pop();
		}
		list.remove(size - 1);
		assistList.remove(size - 1);
		size--;
		return top;
	}

	@Override
	public E peekMedian() {

		return select(0, size - 1, size / 2);
	}

	@Override
	public E peekMaximum() throws EmptyStackException {
		if (size == 0) {
			throw new EmptyStackException();
		}
		E peekMax = (E) max.peek();
		return peekMax;
	}

	@Override
	public E peekMinimum() throws EmptyStackException {
		if (size == 0) {
			throw new EmptyStackException();
		}
		E peekMin = (E) min.peek();
		return peekMin;
	}

	@Override
	public int size() {
		return size;
	}

	private void swap(int i, int j) {
		E temp1 = assistList.get(i);
		E temp2 = assistList.get(j);
		assistList.remove(i);
		assistList.add(i, temp2);
		assistList.remove(j);
		assistList.add(j, temp1);
	}

	/**
	 * Find out range of the element which in my assistList.
	 * A part of Quick-Sort
	 * @param start
	 * @param end
	 * @return
	 */
	private int partition(int start, int end) {
		int i = start;
		int j = end + 1;
		if (start == end) {
			return start;
		}
		E temp = assistList.get(start);

		while (true) {
			while (assistList.get(++i).compareTo(temp) < 0 && i < end)
				;
			while (assistList.get(--j).compareTo(temp) >= 0 && j > start)
				;
			if (i >= j)
				break;
			swap(j, i);
		}
		E temp2 = assistList.get(j);
		assistList.remove(start);
		assistList.add(start, temp2);

		assistList.remove(j);
		assistList.add(j, temp);
		return j;
	}

	/**
	 * Produce a random number to make partition more efficient.
	 * @param p
	 * @param r
	 * @return
	 */
	private int randomPartition(int p, int r) {
		Random random = new Random();
		int i = p + random.nextInt(r - p);
		E tmp = assistList.get(i);
		assistList.remove(i);
		assistList.add(i, assistList.get(p));

		assistList.remove(p);
		assistList.add(p, tmp);
		return partition(p, r);
	}

	/**
	 * Select the k-th lowest Object.
	 * @param p
	 * @param r
	 * @param k
	 * @return
	 */
	private E select(int p, int r, int k) {

		if (k > r) {
			System.out.println("Out of boudary");
			return null;
		}

		if (p == r) {
			return assistList.get(p);
		}
		int i = randomPartition(p, r);
		if (k == i) {
			return assistList.get(k);
		} else if (k < i) {
			return select(p, i - 1, k);
		} else {
			int j = i - p + 1;
			return select(i + 1, r, k);
		}

	}

}