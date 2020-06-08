package sispatri.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patrimonio.class)
public abstract class Patrimonio_ {

	public static volatile SingularAttribute<Patrimonio, String> img;
	public static volatile SingularAttribute<Patrimonio, Categoria> categoria;
	public static volatile SingularAttribute<Patrimonio, CentroDeCusto> centroDeCusto;
	public static volatile SingularAttribute<Patrimonio, BigDecimal> valorAtualizado;
	public static volatile SingularAttribute<Patrimonio, String> nome;
	public static volatile SingularAttribute<Patrimonio, Integer> id;
	public static volatile SingularAttribute<Patrimonio, BigDecimal> valorOriginal;
	public static volatile SingularAttribute<Patrimonio, String> descricao;
	public static volatile SingularAttribute<Patrimonio, Date> dataCompra;
	public static volatile SingularAttribute<Patrimonio, String> status;

}

