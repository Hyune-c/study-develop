package com.example.springpractice.service;

import com.example.springpractice.logic.Sort;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class SortService {

	private final Sort<String> sort;

	public SortService(@Qualifier("bubbleSort") final Sort<String> sort) {
		this.sort = sort;
		System.out.println("구현체: " + sort.getClass().getName());
	}

	public List<String> doSort(final List<String> list) {
		return sort.sort(list);
	}
}
