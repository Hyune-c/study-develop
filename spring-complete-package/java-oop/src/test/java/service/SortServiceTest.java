package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.javaoop.logic.JavaSort;
import com.javaoop.service.SortService;

class SortServiceTest {

	private final SortService sut = new SortService(new JavaSort<>());

	@Test
	void test() {
		// Given

		// When
		final List<String> actual = sut.doSort(List.of("3", "2", "1"));

		// Then
		assertEquals(List.of("1", "2", "3"), actual);
	}
}
