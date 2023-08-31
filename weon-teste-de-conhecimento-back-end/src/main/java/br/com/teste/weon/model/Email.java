package br.com.teste.weon.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails")
public class Email extends Envio {

    @Column(name = "email_origem")
    private String emailOrigem;

    @Column(name = "email_destino")
    private String emailDestino;

    public Email() {

    }

    public Email(String emailOrigem, String emailDestino, LocalDateTime dataHora) {
        this.emailOrigem = emailOrigem;
        this.emailDestino = emailDestino;
        this.dataHora = dataHora;
    }

    public Email(String emailOrigem, String emailDestino) {
        var dataHoraAtuais = LocalDateTime.now();

        new Email(emailOrigem, emailDestino, dataHoraAtuais);
    }
}
