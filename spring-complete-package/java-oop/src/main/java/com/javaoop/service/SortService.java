package com.javaoop.service;

import java.util.List;

import com.javaoop.logic.Sort;

public class SortService {

	private final Sort<String> sort;

	public SortService(final Sort<String> sort) {
		this.sort = sort;
		System.out.println("구현체: " + sort.getClass().getName());
	}

	public List<String> doSort(final List<String> list) {
		return sort.sort(list);
	}
}
