package pratica.tm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pratica.tm.model.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query( value = " select * from task order by finalizada = 'false' desc, data_criacao asc ", nativeQuery = true)
    List<Task> getTasksOrdenado();
}
