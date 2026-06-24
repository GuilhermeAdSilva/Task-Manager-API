package pratica.tm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratica.tm.exception.OperacaoNaoPermitidaException;
import pratica.tm.model.entity.Task;
import pratica.tm.model.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public List<Task> buscarTarefas() {
        return repository.findAll();
    }

    public Optional<Task> buscarTarefaPorID(UUID id) {
        return repository.findById(id);
    }

    public Task salvar(Task task) {
        validar();
        return repository.save(task);
    }

    public Task atualizar(Task task) {
        if(task.getId() == null) {
            throw new OperacaoNaoPermitidaException("Tarefa não encontrada na base");
        }
        validar();
        return repository.save(task);
    }

    public void excluirTarefa(UUID id) {
        repository.deleteById(id);
    }

    public void validar() {

    }
}
