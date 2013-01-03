package jp.co.worksap.examination;

public interface IStackForPriorExamination<E extends Comparable<E>> {
	public void push(E e);
	public E pop();
	public E peekMedian();
	public E peekMaximum();
	public E peekMinimum();
	public int size();
}
