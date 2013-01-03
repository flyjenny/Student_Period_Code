package jp.co.worksap.examination;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class Test {
	public static void main(String[] args){
		StackForPriorExaminationImpl<Integer> test=new StackForPriorExaminationImpl<Integer>();
		test.push(7);
		test.push(1);
		test.push(3);
		test.push(3);
		test.push(5);
		test.push(1);
		test.push(2);
		test.push(4);
		test.push(3);
		System.out.println(test.peekMedian());
	}
}
