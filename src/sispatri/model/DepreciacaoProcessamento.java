/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sispatri.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe modelo do tipo DepreciacaoProcessamento
 * 
 * @author augusto
 */
@Entity
@Table(name = "depreciacao_processamento")
public class DepreciacaoProcessamento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "data_processamento")
    private Date dataDepreciacao;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_patrimonio")
    private Patrimonio patrimonio;

    public DepreciacaoProcessamento() {
    }

    public DepreciacaoProcessamento(Date dataDepreciacao, Usuario usuario, Patrimonio patrimonio) {
        this.dataDepreciacao = dataDepreciacao;
        this.usuario = usuario;
        this.patrimonio = patrimonio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDepreciacao() {
        return dataDepreciacao;
    }

    public void setDataDepreciacao(Date dataDepreciacao) {
        this.dataDepreciacao = dataDepreciacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Patrimonio patrimonio) {
        this.patrimonio = patrimonio;
    }
}
