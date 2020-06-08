package sispatri.support;

import java.awt.event.*; 
import java.math.*; 
import java.text.*; 
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
     
public class JNumberFormatField extends JTextField { 
    private static final NumberFormat MONETARY = new DecimalFormat("R$ #,##0.00"); 
    private NumberFormat FNumberFormat; 
    private int FLimit = -1; 
     
    public JNumberFormatField(int pCasasDecimais) { 
        this(new DecimalFormat((pCasasDecimais == 0 ? "#,##0" : "#,##0.") + makeZeros(pCasasDecimais))); 
    } 
     
    public JNumberFormatField() { 
        this(MONETARY); 
    } 
     
    public JNumberFormatField(NumberFormat pNumberFormat) { 
        // Define o formato do número         
        FNumberFormat = pNumberFormat; 
        // Alinhamento horizontal para o texto 
        setHorizontalAlignment(RIGHT); 

        // Documento responsável pela formatação do campo 
        setDocument(new PlainDocument() { 
            @Override 
            public void insertString(int pOffSet, String pStr, AttributeSet pA) throws BadLocationException { 
                String lText = new StringBuilder(JNumberFormatField.this.getText().replaceAll("[^0-9]", "")).append(pStr.replaceAll("[^0-9]", "")).toString(); 
                super.remove(0, getLength()); 
                if (lText.isEmpty()) { 
                    lText = "0"; 
                } else { 
                    lText = new BigInteger(lText).toString(); 
                } 
                super.insertString(0, FNumberFormat.format(new BigDecimal(getLimit() > 0 == lText.length() > getLimit() ? lText.substring(0, getLimit()) : lText).divide(new BigDecimal(Math.pow(10, FNumberFormat.getMaximumFractionDigits())))), pA); 
            } 

            @Override 
            public void remove(int pOffSet, int pLength) throws BadLocationException { 
                super.remove(pOffSet, pLength); 
                if (pLength != getLength()) { 
                    insertString(0, "", null); 
                } 
            } 
        });

        // Mantém o cursor no final do campo 
        addCaretListener(new CaretListener() { 
            boolean lUpdate = false; 

            @Override 
            public void caretUpdate(CaretEvent e) { 
                if (!lUpdate) { 
                    lUpdate = true; 
                    setCaretPosition(getText().length()); 
                    lUpdate = false; 
                } 
            } 
        });

        // Limpa o campo se apertar DELETE 
        addKeyListener(new KeyAdapter() { 
            @Override 
            public void keyPressed(KeyEvent e) { 
                if (e.getKeyCode() == KeyEvent.VK_DELETE) { 
                    setText(""); 
                } 
            } 
        });
        
        // Formato inicial 
        setText("0"); 
        setCaretPosition(getText().length()); 
    } 
     
    public void setValue(BigDecimal pValue) { 
        super.setText(FNumberFormat.format(pValue)); 
    } 

    public final BigDecimal getValue() { 
        return new BigDecimal(getText().replaceAll("[^0-9]", "")).divide(new BigDecimal(Math.pow(10, FNumberFormat.getMaximumFractionDigits()))); 
    } 

    public NumberFormat getNumberFormat() { 
        return FNumberFormat; 
    } 

    public void setNumberFormat(NumberFormat pNumberFormat) { 
        this.FNumberFormat = pNumberFormat; 
    } 

    private static final String makeZeros(int pZeros) { 
        if (pZeros >= 0) { 
            StringBuilder lStrBuilder = new StringBuilder(); 
            for (int i = 0; i < pZeros; i++) { 
                lStrBuilder.append('0'); 
            } 
            return lStrBuilder.toString(); 
        } else { 
            throw new RuntimeException("Número de casas decimais inválida (" + pZeros + ")"); 
        } 
    } 

    public int getLimit() { 
        return FLimit; 
    } 

    public void setLimit(int pLimit) { 
        this.FLimit = pLimit; 
    } 
}