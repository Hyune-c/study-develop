package logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.javaoop.logic.JavaSort;

class JavaSortTest {

	@DisplayName("자바소트 - 리스트를 넣으면 정렬된 결과를 보여준다")
	@Test
	void given_List_WhenExecuting_ThenReturnSortedList() {
		// Given
		final JavaSort<Integer> javaSort = new JavaSort<>();

		// When
		final List<Integer> actual = javaSort.sort(List.of(3, 2, 4, 5, 1));

		// Then
		assertEquals(List.of(1, 2, 3, 4, 5), actual);
	}
}
