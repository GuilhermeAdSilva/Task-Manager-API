package pratica.tm.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pratica.tm.api.dto.request.TaskRequestDTO;
import pratica.tm.api.dto.response.TaskResponseDTO;
import pratica.tm.exception.OperacaoNaoPermitidaException;
import pratica.tm.model.entity.Task;
import pratica.tm.service.TaskService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> buscarTarefas()  {
        List<Task> tasks = service.buscarTarefas();
        return ResponseEntity.ok(tasks.stream().map(TaskResponseDTO::criar).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskResponseDTO> buscarTarefaPorId(@PathVariable("id") UUID id) {
        Optional<Task> taskOptional = service.buscarTarefaPorID(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskResponseDTO dto = new TaskResponseDTO(task.getId(), task.getNome(), task.getDescricao(), task.getFinalizada(), task.getDataCriacao(), task.getUltimaAtualizacao());
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> criarTarefa(@RequestBody TaskRequestDTO dto) {
        try {
            Task task = dto.mapearParaTask();
            service.salvar(task);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(task.getId())
                    .toUri();

            TaskResponseDTO response = new TaskResponseDTO(task.getId(), task.getNome(), task.getDescricao(), task.getFinalizada(), task.getDataCriacao(), task.getUltimaAtualizacao());
            return ResponseEntity.created(location).body(response);
        } catch (OperacaoNaoPermitidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarTarefa(@RequestBody TaskRequestDTO dto, @PathVariable("id") UUID id) {
        Optional<Task> taskOptional = service.buscarTarefaPorID(id);
        if (taskOptional.isPresent()) {
            try {
                Task task = dto.mapearParaTask();
                task.setId(id);
                task.setDataCriacao(taskOptional.get().getDataCriacao());
                service.atualizar(task);

                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(task.getId())
                        .toUri();

                TaskResponseDTO response = new TaskResponseDTO(task.getId(), task.getNome(), task.getDescricao(), task.getFinalizada(), task.getDataCriacao(), task.getUltimaAtualizacao());
                return ResponseEntity.created(location).body(response);
            } catch (OperacaoNaoPermitidaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirTarefa(@PathVariable("id") UUID id) {
        Optional<Task> taskOptional = service.buscarTarefaPorID(id);
        if (taskOptional.isPresent()) {
            service.excluirTarefa(id);
            return ResponseEntity.ok("Tarefa excluida");
        }
        return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
    }
}
