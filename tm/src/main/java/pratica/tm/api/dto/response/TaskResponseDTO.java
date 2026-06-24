package pratica.tm.api.dto.response;

import pratica.tm.model.entity.Task;

import java.time.Instant;
import java.util.UUID;

public record TaskResponseDTO(UUID id, String nome, String descricao, Boolean finalizada, Instant dataCriacao, Instant ultimaAtualizacao) {

    public static TaskResponseDTO criar(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getNome(),
                task.getDescricao(),
                task.getFinalizada(),
                task.getDataCriacao(),
                task.getUltimaAtualizacao()
        );
    }
}
