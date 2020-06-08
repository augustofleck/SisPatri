package sispatri.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * Classe modelo do tipo Patrimonio
 * 
 * @author augusto
 */
@Entity
@Table(name = "patrimonio")
public class Patrimonio implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    
    
    @Column(name = "descricao_curta")
    private String nome;
    
    @Column(name = "descricao_completa")
    private String descricao;
    
    @Column(name = "valor_original")
    private BigDecimal valorOriginal;
    
    @Column(name = "valor_atualizado")
    private BigDecimal valorAtualizado;
    
    @Column(name = "data_compra")
    private Date dataCompra;
    
    private String img;
    
    @Column(name = "status")
    private String status = "A";
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "id_centro_de_custo")
    private CentroDeCusto centroDeCusto;

    public Patrimonio() {
    }
    
    public BigDecimal calcularDepreciacao() {
        BigDecimal valorAtual = valorAtualizado == null ? valorOriginal : valorAtualizado;
        BigDecimal porcentagem = BigDecimal.ONE.subtract(new BigDecimal(categoria.getPercetualDepreciacaoMes() * 0.01));
        valorAtual = valorAtual.multiply(porcentagem).setScale(2, RoundingMode.HALF_EVEN);
        return valorAtual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public BigDecimal getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CentroDeCusto getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }
    
    
}
