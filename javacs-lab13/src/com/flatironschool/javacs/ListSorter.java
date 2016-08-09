/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // TODO FILL THIS IN!
        if (list.size() <= 1) {
            return list;
        }
		int splitIndex = list.size() / 2;
        List<T> first = mergeSort(new LinkedList<T>(list.subList(0, splitIndex)), comparator);
        List<T> second = mergeSort(new LinkedList<T>(list.subList(splitIndex, list.size())), comparator);

        return merge(first, second, comparator);
	}

    private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
        List<T> merged = new LinkedList<T>();
        while (first.size() != 0 && second.size() != 0) {
            T firstHead = first.get(0);
            T secondHead = second.get(0);
            if (comparator.compare(firstHead, secondHead) <= 0) {
                merged.add(first.remove(0));
            } else {
                merged.add(second.remove(0));
            }
        }
        if (first.size() > 0) {
            merged.addAll(first);
        } else if (second.size() > 0) {
            merged.addAll(second);
        }
        return merged;
    }

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // TODO FILL THIS IN!
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
        heap.addAll(list);
        list.clear();
        while (!heap.isEmpty()) {
            list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // TODO FILL THIS IN!
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
        for (T element: list) {
            if (heap.size() < k) {
                heap.offer(element);
                continue;
            }
            int cmp = comparator.compare(element, heap.peek());
            if (cmp > 0) {
                heap.poll();
                heap.offer(element);
            }
        }
        List<T> tops = new ArrayList<T>();
        while (!heap.isEmpty()) {
            tops.add(heap.poll());
        }
        return tops;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
