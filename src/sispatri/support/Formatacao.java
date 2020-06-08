package sispatri.support;

import java.text.*;
import java.util.*;

/**
 * Classe com métodos úteis para formatação de textos, datas, etc...
 * 
 * @author augusto
 */
public class Formatacao {
    
    /**
     * Formata uma data para String (dd/MM/yyyy).
     * @param pData - Data
     * @return String
     */
    public static String ajustaDataDMA(Date pData) {
        if (pData != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(pData);
        }

        return "";
    }

    /**
     * Remove formatação de textos como pontos, dashs, etc...
     * @param pDado - Texto a ser formatado
     * @return String
     */
    public static String removerFormatacao(String pDado) {
        String lRetorno = "";
        for (int i = 0; i < pDado.length(); i++) {
            if (pDado.charAt(i) != '.' && pDado.charAt(i) != '/' && pDado.charAt(i) != '-'
                    && pDado.charAt(i) != '(' && pDado.charAt(i) != ')' && pDado.charAt(i) != ' ') {
                lRetorno += pDado.charAt(i);
            }
        }
        return lRetorno;
    }
}
