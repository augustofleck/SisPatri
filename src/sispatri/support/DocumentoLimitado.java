package sispatri.support;

import javax.swing.text.*;

/**
 * Classe usada para delimitar o tamanho de campo no Swing.
 * 
 * @author augusto
 */
public class DocumentoLimitado extends PlainDocument {

    private int FMaxLength = 10;

    public DocumentoLimitado(int pMaxLength) {
        this.FMaxLength = pMaxLength;
    }

    public void insertString(int pOffSet, String pStr, AttributeSet pA) throws BadLocationException {

        if (pStr == null) {
            return;
        }

        String lStrAntiga = getText(0, getLength());
        int lTamanhoNovo = lStrAntiga.length() + pStr.length();

        if (lTamanhoNovo <= FMaxLength) {
            super.insertString(pOffSet, pStr, pA);
        } else {
            super.insertString(pOffSet, "", pA);
        }
    }
}
