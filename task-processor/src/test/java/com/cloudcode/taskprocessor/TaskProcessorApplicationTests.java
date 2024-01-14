package com.cloudcode.taskprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.controller.TaskController;
import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class TaskProcessorApplicationTests {

	@Autowired
	private TaskController restController;

	@MockBean
	private TaskRepo taskRepo;

	static TaskInfo taskCreated = new TaskInfo();
	static TaskInfo taskCompleted = new TaskInfo();

	@BeforeAll
	public static void beforeAll() {
		taskCreated.setTaskId(1L);
		taskCreated.setTaskName("Task001");
		taskCreated.setTaskDesc("Task001 for testing");
		taskCreated.setTaskStatus(TASK_STATUS.CREATED.name());
		taskCreated.setUpdateTime("2024-01-14 12:00:10");

		taskCompleted.setTaskId(2L);
		taskCompleted.setTaskName("Task002");
		taskCompleted.setTaskDesc("Task002 for testing");
		taskCompleted.setTaskStatus(TASK_STATUS.COMPLETED.name());
		taskCompleted.setUpdateTime("2024-01-14 12:00:00");
	}

	@Test
	public void test_getTaskStatus() {
		when(taskRepo.findAll()).thenReturn(Flux.just(taskCreated, taskCompleted));
		StepVerifier.create(restController.getTaskStatus(null, null))
				.expectSubscription()
				.expectNextCount(2)
				.expectComplete()
				.verify();

		StepVerifier.create(restController.getTaskStatus(null, null))
				.expectSubscription()
				.expectNext(taskCreated)
				.expectNext(taskCompleted)
				.expectNextCount(0)
				.expectComplete()
				.verify();

		StepVerifier.create(restController.getTaskStatus(null, null))
				.expectSubscription()
				.assertNext(task -> {
					assertEquals(taskCreated.getTaskName(), task.getTaskName());
					assertEquals(taskCreated.getTaskStatus(), task.getTaskStatus());
				})
				.assertNext(task -> {
					assertEquals("Task002", task.getTaskName());
					assertEquals(TASK_STATUS.COMPLETED.name(), task.getTaskStatus());
				})
				.expectNextCount(0)
				.expectComplete()
				.verify();
		verify(taskRepo, VerificationModeFactory.atMost(3)).findAll();
	}

	@Test
	public void test_saveTask() {
		when(taskRepo.save(taskCreated)).thenReturn(Mono.just(taskCreated));
		StepVerifier.create(restController.saveTask(taskCreated))
				.expectSubscription()
				.assertNext(task -> {
					assertNotNull(task);
					assertEquals(1L, task.getTaskId());
					assertEquals("Task001", task.getTaskName());
					assertEquals(TASK_STATUS.CREATED.name(), task.getTaskStatus());
				})
				.expectComplete()
				.verify();
		verify(taskRepo).save(taskCreated);
	}

}
