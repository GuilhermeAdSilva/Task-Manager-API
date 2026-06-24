package pratica.tm.api.dto.request;

import pratica.tm.model.entity.Task;

import java.time.Instant;

public record TaskRequestDTO(String nome, String descricao, Boolean finalizada) {

    public Task mapearParaTask() {
        Task task = new Task();
        task.setNome(this.nome);
        task.setDescricao(this.descricao);
        task.setFinalizada(this.finalizada);
        return task;
    }
}
