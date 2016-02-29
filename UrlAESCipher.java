import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class UrlAESCipher {

    public final static int IV_LENGTH = 16;

    private final BlockCipher aesCipher = new AESEngine();
    private final PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(new CBCBlockCipher(aesCipher), new PKCS7Padding());

    private byte[] keyBytes;

    private void setKeyBytes(byte[] keyBytes) {

        this.keyBytes = keyBytes;
    }

    public void setBase64Key(String key) {

        setKeyBytes(Base64.getDecoder().decode(key));
    }

    private byte[] process(byte[] input, byte[] iv, boolean forEncryption) {

        CipherParameters cp = new ParametersWithIV(new KeyParameter(keyBytes), iv);
        pbbc.init(forEncryption, cp);
        byte[] out = new byte[pbbc.getOutputSize(input.length)];
        int bytesWrittenOut = pbbc.processBytes(input, 0, input.length, out, 0);
        try {
            int actualLength = pbbc.doFinal(out, bytesWrittenOut) + bytesWrittenOut;
            byte[] result = new byte[actualLength];
            System.arraycopy(out, 0, result, 0, result.length);
            return result;
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }

    }

    public String urlBase64Encrypt(String input) {
        try {
            byte[] inputBytes = input.getBytes("utf-8");
            byte[] iv = generateIv();
            byte[] encrypted = process(inputBytes, iv, true);
            return finalEncryptStep(iv, encrypted);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String urlBase64Decrypt(String input) {
        byte[][] splittedRawData = splitIvAndData(input);
        byte[] iv = splittedRawData[0];
        byte[] encData = splittedRawData[1];
        byte[] decrypted = process(encData, iv, false);
        try {
            return new String(decrypted, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String finalEncryptStep(byte[] iv, byte[] encData) {
        byte[] full = new byte[iv.length + encData.length];
        System.arraycopy(iv, 0, full, 0, iv.length);
        System.arraycopy(encData, 0, full, iv.length, encData.length);
        return Base64.getUrlEncoder().encodeToString(full);
    }

    private static byte[][] splitIvAndData(String enc) {
        byte[] raw = Base64.getUrlDecoder().decode(enc);
        byte[][] result = new byte[2][];
        result[0] = Arrays.copyOf(raw, IV_LENGTH);
        result[1] = Arrays.copyOfRange(raw, IV_LENGTH, raw.length);
        return result;
    }

    private static byte[] generateIv() {
        SecureRandom sr = new SecureRandom();
        byte bytes[] = new byte[IV_LENGTH];
        sr.nextBytes(bytes);
        return bytes;
    }
}
