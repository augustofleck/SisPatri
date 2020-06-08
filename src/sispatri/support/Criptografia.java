package sispatri.support;

import java.security.*;

/**
 * Classe para criptografar senha
 * 
 * @author augusto
 */
public class Criptografia {

    public static String criptografar(String pSenha) {
        try {
            MessageDigest lMD = MessageDigest.getInstance("SHA-256");
            byte lHash[] = lMD.digest(pSenha.getBytes());

            StringBuilder lHexStr = new StringBuilder();
            for (byte b : lHash) {
                lHexStr.append(String.format("%02X", 0xFF & b));
            }

            return lHexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            return pSenha;
        }
    }

}
