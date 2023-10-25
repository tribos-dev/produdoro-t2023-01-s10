package dev.wakandaacademy.produdoro.tarefa.application.api;

import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
public class TarefaListResponse {
    @Id
    private UUID idTarefa;
    @Indexed
    private UUID idUsuario;

    public static List<TarefaListResponse> converte(List<Tarefa> tarefa) {
        return tarefa.stream()
                .map(TarefaListResponse::new)
                .collect(Collectors.toList());
    }
    public TarefaListResponse (Tarefa tarefa){
        this.idTarefa = tarefa.getIdTarefa();
        this.idUsuario = tarefa.getIdUsuario();
    }
}
