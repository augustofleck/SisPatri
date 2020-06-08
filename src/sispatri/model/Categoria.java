package sispatri.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe modelo do tipo Categoria
 * 
 * @author augusto
 */
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "perc_depreciacao_mes")
    private double percetualDepreciacaoMes;
    
    @Column(name = "status")
    private String status = "A";
    
    public Categoria() {}

    public Categoria(Integer id, String descricao, double percetualDepreciacaoMes) {
        this.id = id;
        this.descricao = descricao;
        this.percetualDepreciacaoMes = percetualDepreciacaoMes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPercetualDepreciacaoMes() {
        return percetualDepreciacaoMes;
    }

    public void setPercetualDepreciacaoMes(double percetualDepreciacaoMes) {
        this.percetualDepreciacaoMes = percetualDepreciacaoMes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
