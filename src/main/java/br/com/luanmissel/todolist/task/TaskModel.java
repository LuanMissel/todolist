package br.com.luanmissel.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity (name = "tb_task")
public class TaskModel {
    @Id
    @GeneratedValue (generator = "UUID") // Vai usar a chave primária como um UUID e o própio spring vai fazer o gerenciamento.
    private UUID id;
    private String description;

    @Column(length = 50) // o tamanho do nome da coluna só pode chegar a 50 caracteres
    private String title; // define a variável title
    private String priority;
    private UUID userId;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
