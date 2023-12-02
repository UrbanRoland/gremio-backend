package com.gremio.repository;

import com.gremio.enums.TaskStatus;
import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.Task;
import com.gremio.persistence.specification.TaskSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;
    private Task task1;
    private Task task2;
    
    @BeforeEach
    public void init() {
    task1 = Task.builder()
        .description("Description_Test_01")
        .status(TaskStatus.PASSED)
        .title("Test_01")
        .build();
    taskRepository.save(task1);
    
    task2 = Task.builder()
        .description("Description_Test_02")
        .status(TaskStatus.REVIEW)
        .title("Test_02")
        .build();
    taskRepository.save(task2);
    
    }
    
    @Test
    public void TaskRepository_FindAll_ReturnAllTasksWithTitleFilter() {
        TaskFilter filter = new TaskFilter(task1.getTitle(), null, null);

        TaskSpecification taskSpecification = new TaskSpecification(filter);

        Page<Task> tasks = taskRepository.findAll(taskSpecification,  Pageable.ofSize(10).withPage(0));

        Assertions.assertNotNull(tasks);
        Assertions.assertEquals(tasks.getTotalElements(),1);

    }
    
    @Test
    public void TaskRepository_FindAll_ReturnAllTasksWithDescriptionFilter() {
        TaskFilter filter = new TaskFilter(null, task2.getDescription(), null);

        TaskSpecification taskSpecification = new TaskSpecification(filter);

        Page<Task> tasks = taskRepository.findAll(taskSpecification,  Pageable.ofSize(10).withPage(0));

        Assertions.assertNotNull(tasks);
        Assertions.assertEquals(tasks.getTotalElements(),1);

    }

}