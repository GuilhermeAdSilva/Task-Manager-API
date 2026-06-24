package pratica.tm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pratica.tm.model.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
