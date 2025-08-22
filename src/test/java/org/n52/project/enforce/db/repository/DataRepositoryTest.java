package org.n52.project.enforce.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.n52.project.enforce.db.model.Data;
import org.springframework.beans.factory.annotation.Autowired;

public class DataRepositoryTest extends RepositoryTest {

	@Autowired
	private DataRepository dataRepository;

	@Test
	void testFindByServiceRequestId() {

		int userId = 44;
		Data data = new Data(UUID.randomUUID());
		data.setUserId(userId);
		dataRepository.save(data);
		List<Data> dataFromDb = dataRepository.findAll();
		assertEquals(dataFromDb.get(0).getUserId(), userId);

	}

}
