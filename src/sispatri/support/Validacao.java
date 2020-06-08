package sispatri.support;

import java.awt.Component;
import java.awt.Container;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe usada para validação de dados e campos.
 * @author augusto
 */
public class Validacao {

    /**
     * Valida uma data com base no dia, mês e ano.
     * @param d - Dia
     * @param m - Mês
     * @param a - Ano
     * @return boolean
     */
    public static boolean validarDataDMA(int d, int m, int a) {
        boolean correto = true;
        int[] dias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (a < 0 || m < 1 || m > 12) {
            correto = false;
        } else {
            if (a % 4 == 0 && (a % 100 != 0 || a % 400 == 0)) {
                dias[1] = 29;
            }
            if (d < 1 || d > dias[m - 1]) {
                correto = false;
            }
        }
        return (correto);
    }

    /**
     * Valida uma data com base no dia, mês e ano.
     * @param pDataComFormato - Data no formato dd/MM/yyyy
     * @return boolean
     */
    public static boolean validarDataFormatada(String pDataComFormato) {
        String[] lData = pDataComFormato.split("/");
        if (lData.length == 3) {
            return (validarDataDMA(Integer.parseInt(lData[0]), Integer.parseInt(lData[1]), Integer.parseInt(lData[2])));
        } else {
            return false;
        }
    }

    /**
     * Valida diversos tipos de campos com base no seu pai (container).
     * @param pContainer - Container que abriga os campos de texto a serem validados.
     * @return int - Se retornar maior que zero há campos não preenchidos
     */
    public static int validarCampos(Container pContainer) {
        Component lComponent[] = pContainer.getComponents();
        for (int i = 0; i < lComponent.length; i++) {
            if ((lComponent[i].isVisible())) {
                if (lComponent[i] instanceof JFormattedTextField) {
                    JFormattedTextField lField = (JFormattedTextField) lComponent[i];
                    if (Formatacao.removerFormatacao(lField.getText()).equals("")) {
                        return 1;
                    }
                } else if (lComponent[i] instanceof JTextField) {
                    JTextField lField = (JTextField) lComponent[i];
                    if (lField.getText().equals("")) {
                        if (lField.getName() != null) {
                            if (!lField.getName().equals("tfdComplemento")) {
                                return 1;
                            }
                        } else {
                            return 1;
                        }
                    }
                } else if (lComponent[i] instanceof JNumberFormatField) {
                    JNumberFormatField lField = (JNumberFormatField) lComponent[i];
                    if (lField.getValue().equals(BigDecimal.ZERO)) {
                        return 1;
                    }
                } else if (lComponent[i] instanceof JTextArea) {
                    JTextArea lField = (JTextArea) lComponent[i];
                    if (lField.getText().equals("")) {
                        return 1;
                    }
                } else if (lComponent[i] instanceof JSpinner) {
                    JSpinner lField = (JSpinner) lComponent[i];
                    if ((int) lField.getValue() <= 0) {
                        return 1;
                    }
                } else if (lComponent[i] instanceof JDateChooserComboLayout) {
                    JDateChooserComboLayout lField = (JDateChooserComboLayout) lComponent[i];
                    if (lField.getText().equals("")) {
                        return 1;
                    }
                } else if (lComponent[i] instanceof JComboBox) {
                    JComboBox lField = (JComboBox) lComponent[i];
                    if (lField.getSelectedIndex() == -1 || lField.getSelectedItem().toString().equals("")) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Método que valida se um email é válido.
     * @param email - Email
     * @return boolean
     */
    public static boolean validarEmail(String email) {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Valida se um ou vários objetos estão vazios ou nulos.
     * @param objects - Objetos a serem validados.
     * @return boolean
     */
    public static boolean nullOrEmpty(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
            if (obj instanceof String) {
                String o = (String) obj;
                if (o.equals("")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Valida se Iterable possui item vazio ou nulo.
     * @param iterables - Objeto do tipo Iterable
     * @return boolean
     */
    public static boolean nullOrEmpty(Iterable<?>... iterables) {
        for (Iterable<?> iterable : iterables) {
            if (iterable == null) {
                return true;
            }
            java.util.Iterator<?> iterator = iterable.iterator();
            if (!iterator.hasNext()) {
                return true;
            }
        }
        return false;
    }
}
