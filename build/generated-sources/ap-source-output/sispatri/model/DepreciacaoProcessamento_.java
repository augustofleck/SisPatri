package sispatri.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DepreciacaoProcessamento.class)
public abstract class DepreciacaoProcessamento_ {

	public static volatile SingularAttribute<DepreciacaoProcessamento, Patrimonio> patrimonio;
	public static volatile SingularAttribute<DepreciacaoProcessamento, Date> dataDepreciacao;
	public static volatile SingularAttribute<DepreciacaoProcessamento, Usuario> usuario;
	public static volatile SingularAttribute<DepreciacaoProcessamento, Integer> id;

}

