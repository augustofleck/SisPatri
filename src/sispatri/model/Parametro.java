package sispatri.model;

/**
 * Classe responsável por armazenar valores "defaults".
 * 
 * @author augusto
 */
public enum Parametro {
    SENHA_DEFAULT("sispatri");
 
    public String valor;
    private Parametro(String s) {
        this.valor = s;
    }

    public String getValor() {
        return valor;
    }
}
