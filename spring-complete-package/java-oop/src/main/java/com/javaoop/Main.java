package com.javaoop;

import java.util.Arrays;

import com.javaoop.logic.JavaSort;
import com.javaoop.logic.Sort;

public class Main {

	public static void main(final String[] args) {
		final Sort<String> sort = new JavaSort<>();

		System.out.println("[result] " + sort.sort(Arrays.asList(args)));
	}
}
