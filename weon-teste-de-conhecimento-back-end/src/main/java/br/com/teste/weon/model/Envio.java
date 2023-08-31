package br.com.teste.weon.model;

import br.com.teste.weon.produtores.TipoProdutor;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "tipo_produtor")
    protected TipoProdutor tipoProdutor;

    @Column(name = "data_hora")
    protected LocalDateTime dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProdutor getTipoProdutor() {
        return tipoProdutor;
    }
}
