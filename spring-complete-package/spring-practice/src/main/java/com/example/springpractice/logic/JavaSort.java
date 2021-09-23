package com.example.springpractice.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JavaSort<T extends Comparable<T>> implements Sort<T> {

	public List<T> sort(final List<T> list) {
		final List<T> output = new ArrayList<>(list);
		Collections.sort(output);

		return output;
	}
}
