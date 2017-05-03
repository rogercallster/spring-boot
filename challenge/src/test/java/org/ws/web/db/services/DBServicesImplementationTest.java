package org.ws.web.db.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.ws.web.db.DAO;

@Controller
public class DBServicesImplementationTest {

	String RESULT_DISTANCE = "";
	private static String REAL_DISTANCE_MAP = "i=1 j=2 distance=1    i=1 j=3 distance=2    i=1 j=4 distance=1    i=1 j=5 distance=1    i=1 j=6 distance=2    i=1 j=7 distance=2    i=1 j=8 distance=1    i=1 j=9 distance=2    i=1 j=10 distance=1    i=2 j=2 distance=0    i=2 j=3 distance=2    i=2 j=4 distance=2    i=2 j=5 distance=2    i=2 j=6 distance=1    i=2 j=7 distance=1    i=2 j=8 distance=2    i=2 j=9 distance=2    i=2 j=10 distance=1    i=3 j=2 distance=2    i=3 j=3 distance=0    i=3 j=4 distance=2    i=3 j=5 distance=1    i=3 j=6 distance=1    i=3 j=7 distance=1    i=3 j=8 distance=1    i=3 j=9 distance=1    i=3 j=10 distance=2    i=4 j=2 distance=1    i=4 j=3 distance=1    i=4 j=4 distance=0    i=4 j=5 distance=2    i=4 j=6 distance=1    i=4 j=7 distance=1    i=4 j=8 distance=1    i=4 j=9 distance=1    i=4 j=10 distance=2    i=5 j=2 distance=2    i=5 j=3 distance=1    i=5 j=4 distance=1    i=5 j=5 distance=0    i=5 j=6 distance=2    i=5 j=7 distance=1    i=5 j=8 distance=1    i=5 j=9 distance=2    i=5 j=10 distance=2    i=6 j=2 distance=1    i=6 j=3 distance=2    i=6 j=4 distance=1    i=6 j=5 distance=1    i=6 j=6 distance=0    i=6 j=7 distance=1    i=6 j=8 distance=1    i=6 j=9 distance=1    i=6 j=10 distance=1    i=7 j=2 distance=1    i=7 j=3 distance=1    i=7 j=4 distance=1    i=7 j=5 distance=1    i=7 j=6 distance=1    i=7 j=7 distance=0    i=7 j=8 distance=2    i=7 j=9 distance=1    i=7 j=10 distance=2    i=8 j=2 distance=1    i=8 j=3 distance=2    i=8 j=4 distance=2    i=8 j=5 distance=2    i=8 j=6 distance=2    i=8 j=7 distance=1    i=8 j=8 distance=0    i=8 j=9 distance=2    i=8 j=10 distance=1    i=9 j=2 distance=1    i=9 j=3 distance=1    i=9 j=4 distance=1    i=9 j=5 distance=1    i=9 j=6 distance=1    i=9 j=7 distance=2    i=9 j=8 distance=2    i=9 j=9 distance=0    i=9 j=10 distance=1    i=10 j=2 distance=2    i=10 j=3 distance=2    i=10 j=4 distance=1    i=10 j=5 distance=1    i=10 j=6 distance=2    i=10 j=7 distance=1    i=10 j=8 distance=1    i=10 j=9 distance=2    i=10 j=10 distance=0    ";

	@Test
	public void test() {

		DAO dao = mock(DAO.class);

		DBServicesImplementation dbServices = new DBServicesImplementation(dao);

		List<Integer> list1 = Arrays.asList(new Integer[] { 8, 10, 5, 4, 2 });
		when(dao.getFollowing(1)).thenReturn(list1);
		List<Integer> list2 = Arrays.asList(new Integer[] { 10, 6, 7 });
		when(dao.getFollowing(2)).thenReturn(list2);
		List<Integer> list3 = Arrays.asList(new Integer[] { 8, 6, 9, 1, 5, 7 });
		when(dao.getFollowing(3)).thenReturn(list3);
		List<Integer> list4 = Arrays.asList(new Integer[] { 8, 9, 2, 6, 7, 3 });
		when(dao.getFollowing(4)).thenReturn(list4);
		List<Integer> list5 = Arrays.asList(new Integer[] { 7, 4, 3, 1, 8 });
		when(dao.getFollowing(5)).thenReturn(list5);
		List<Integer> list6 = Arrays.asList(new Integer[] { 2, 5, 9, 4, 8, 1, 7, 10 });//
		when(dao.getFollowing(6)).thenReturn(list6);
		List<Integer> list7 = Arrays.asList(new Integer[] { 3, 9, 4, 6, 2, 5 });
		when(dao.getFollowing(7)).thenReturn(list7);
		List<Integer> list8 = Arrays.asList(new Integer[] { 2, 1, 10, 7 });
		when(dao.getFollowing(8)).thenReturn(list8);
		List<Integer> list9 = Arrays.asList(new Integer[] { 6, 5, 3, 4, 2, 1, 10 });//
		when(dao.getFollowing(9)).thenReturn(list9);
		List<Integer> list10 = Arrays.asList(new Integer[] { 4, 1, 8, 5, 7 });
		when(dao.getFollowing(10)).thenReturn(list10);

		int[] lookup = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		String distance = "";
		for (int i = 0; i < lookup.length; i++) {
			for (int j = 1; j < lookup.length; j++) {
				int src = lookup[i];
				int dst = lookup[j];
				distance += "i=" + (i + 1) + " j=" + (j + 1) + " distance=" + dbServices.getDistance(src, dst) + "    ";
			}
		}
		assertEquals("Expected value to be equal to REAL_DISTANCE_MAP but was : " + distance + "\n", distance.trim(),
				REAL_DISTANCE_MAP.trim());
	}

}
