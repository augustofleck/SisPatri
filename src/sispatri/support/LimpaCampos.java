package sispatri.support;

import java.awt.*;
import java.math.*;
import javax.swing.*;

/**
 * Classe usada para limpar campos baseado em um Container
 * 
 * @author augusto
 */
public class LimpaCampos {

    public static void LimparCampos(Container pContainer) {
        Component lComponent[] = pContainer.getComponents();
        for (int i = 0; i < lComponent.length; i++) {
            if (lComponent[i] instanceof JFormattedTextField) {
                JFormattedTextField lField = (JFormattedTextField) lComponent[i];
                lField.setValue(null);
            } else if (lComponent[i] instanceof JTextField) {
                JTextField lField = (JTextField) lComponent[i];
                lField.setText("");
//            } else if (lComponent[i] instanceof JScrollPane) {
//                ((JTextArea) ((JScrollPane) lComponent[i]).getViewport().getComponent(0)).setText("");
            } else if (lComponent[i] instanceof JComboBox) {
                JComboBox lField = (JComboBox) lComponent[i];
                if(lField.getItemCount() != 0) {
                    lField.setSelectedIndex(0);
                }
            } else if (lComponent[i] instanceof JCheckBox) {
                JCheckBox lField = (JCheckBox) lComponent[i];
                lField.setSelected(false);
            } else if (lComponent[i] instanceof JNumberFormatField) {
                JNumberFormatField lField = (JNumberFormatField) lComponent[i];
                lField.setValue(BigDecimal.ZERO);
            } else if (lComponent[i] instanceof JSpinner) {
                JSpinner lField = (JSpinner) lComponent[i];
                lField.setValue(0);
            }
        }
    }
}
