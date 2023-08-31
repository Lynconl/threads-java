package br.com.teste.weon.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
public class Chat extends Envio {

    @Column(name = "nome_usuario_origem")
    private String nomeUsuarioOrigem;

    @Column(name = "nome_usuario_destino")
    private String nomeUsuarioDestino;

    public Chat() {

    }

    public Chat(String nomeUsuarioOrigem, String nomeUsuarioDestino, LocalDateTime dataHora) {
        this.nomeUsuarioOrigem = nomeUsuarioOrigem;
        this.nomeUsuarioDestino = nomeUsuarioDestino;
        this.dataHora = dataHora;
    }

    public Chat(String nomeUsuarioOrigem, String nomeUsuarioDestino) {
        var dataHoraAtuais = LocalDateTime.now();

        new Chat(nomeUsuarioOrigem, nomeUsuarioDestino, dataHoraAtuais);
    }
}
