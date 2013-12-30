package comp.hp.ij.common.service.pandora.api;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt/Decrypt utilities
 */
public class Security {

	private static final String TRANSFORMATION = "Blowfish";

	private static final int BLOWFISH_BLOCK_SIZE = 8;


	private Cipher encryptCipher;
	private Cipher decryptCipher;

	public Security(String syncTimeKey, String requestKey) throws Exception {
		SecretKeySpec encryptKey = new SecretKeySpec(requestKey.getBytes(), TRANSFORMATION);
		String provider = "CryptixCrypto";
		java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto());
		
		try{
			encryptCipher = Cipher.getInstance(TRANSFORMATION + "/ECB/NoPadding", provider);
		}catch(NoSuchPaddingException e){
			System.out.println("NoSuchPaddingException - " + e.getMessage());
		}catch(NoSuchAlgorithmException e1 ){
			e1.printStackTrace();
			System.out.println("NoSuchAlgorithmException - " + e1.getMessage());
		}
		System.out.println("Cipher getInstance success");
		encryptCipher.init(Cipher.ENCRYPT_MODE, encryptKey);

		SecretKeySpec decryptKey = new SecretKeySpec(syncTimeKey.getBytes(), TRANSFORMATION);
		decryptCipher = Cipher.getInstance(TRANSFORMATION);
		decryptCipher.init(Cipher.DECRYPT_MODE, decryptKey);
	}

	public String encrypt(String data) throws BadPaddingException, IllegalBlockSizeException {
		
		byte[] bytes = encryptCipher.doFinal(fillBlock(data).getBytes());
		return new String(Hex.encodeHex(bytes));
	}

	public String decrypt(String data) throws DecoderException, BadPaddingException, IllegalBlockSizeException, ShortBufferException {

		// decode hex to bytes
		byte[] bytes = Hex.decodeHex(data.toCharArray());
		// decode the syncTime (using the syncTimeKey provided by Pandora)
		String decryptedString = new String(decryptCipher.doFinal(bytes));
		// throw away the first 4 bytes (these are random "salt" bytes added on by the Pandora server)
		String syncTime = decryptedString.substring(4, decryptedString.length());
		// remove the padding
		if (syncTime != null && syncTime.length() > 0 && 
			((byte)syncTime.charAt(syncTime.length()-1)) <= BLOWFISH_BLOCK_SIZE) {
			// the padding is filled with non-ascii values, each byte set to the length of the padding
			syncTime = syncTime.substring(0, syncTime.length() - ((int)syncTime.charAt(syncTime.length()-1)));
		}
		return syncTime;
	}

	private String fillBlock(String data) {
		int paddingLength = 0;
		if ((data.length() % BLOWFISH_BLOCK_SIZE) > 0) {
			paddingLength = BLOWFISH_BLOCK_SIZE - (data.length() % BLOWFISH_BLOCK_SIZE);
		}
		if (paddingLength > 0) {
			String padding = "";
			for (int i = 0; i < paddingLength; i++) {
				padding = padding + ' ';
			}
			return data + padding;
		} else {
			return data;
		}
	}
}
