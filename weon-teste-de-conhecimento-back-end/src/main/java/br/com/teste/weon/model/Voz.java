package br.com.teste.weon.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vozes")
public class Voz extends Envio {

    @Column(name = "telefone_origem")
    private String telefoneOrigem;

    @Column(name = "telefone_destino")
    private String telefoneDestino;

    public Voz() {

    }

    public Voz(String telefoneOrigem, String telefoneDestino, LocalDateTime dataHora) {
        this.telefoneOrigem = telefoneOrigem;
        this.telefoneDestino = telefoneDestino;
        this.dataHora = dataHora;
    }

    public Voz(String telefoneOrigem, String telefoneDestino) {
        var dataHoraAtuais = LocalDateTime.now();

        new Voz(telefoneOrigem, telefoneDestino, dataHoraAtuais);
    }
}
