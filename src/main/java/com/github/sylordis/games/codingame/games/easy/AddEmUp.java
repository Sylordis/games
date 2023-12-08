package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AddEmUp {

	private final Deque<Long> numbers;

	public AddEmUp(List<Long> numbers) {
		this.numbers = new LinkedList<>(numbers);
	}

	public Long compute() {
		long cost = 0L;
		LinkedList<Long> numbers = new LinkedList<>(this.numbers);
		while (numbers.size() > 1) {
			numbers.sort(Long::compare);
			long sum = numbers.pop() + numbers.pop();
			cost += sum;
			numbers.offerFirst(sum);
		}
		return cost;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		List<Long> list = new ArrayList<>();
		for (int i = 0; i < n; i++)
			list.add(in.nextLong());
		in.close();
		System.out.println(new AddEmUp(list).compute());
	}
}
