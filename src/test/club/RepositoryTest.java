package com;


import com.model.RifLog;
import com.repository.RifLogsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

	@Autowired
	private RifLogsRepository rifLogsRepository;


	/**
	 * CRUD-тест репозитория/бд на наличие хотя бы одной записи
	 */
	@Test
	public void testGetObjectById_success() {

		RifLog rifLog = rifLogsRepository.getOne(Long.valueOf(1));
		assertNotNull(rifLog);

	}

	@Test
	public void testGetAll_success() {
		List<RifLog> rifLogs = rifLogsRepository.findAll();
		assertEquals(1, rifLogs.size());
	}


	@Test
	public void mainRifRepositoryTest() {
		rifLogsRepository.save(new RifLog("14","Julie", "Ivanova"));
		List<RifLog> target = new ArrayList<>();
		Iterable<RifLog> findAll = rifLogsRepository.findAll();

		findAll.forEach(target::add);
		assertThat(target, hasSize(1));
	//	rifLogsRepository.deleteAll();
	}



}
