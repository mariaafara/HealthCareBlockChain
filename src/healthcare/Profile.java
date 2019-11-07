package healthCare;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Profile {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	PublicKey fakeKey;
	
	
	public Profile() {
		generateKeyPair();
	}
		
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	    //    System.out.println("pKey:"+privateKey);
	     //   System.out.println("puKey:"+publicKey);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Transaction sendFunds(String value ) {
		
		Transaction newTransaction = new Transaction(publicKey, value);
		newTransaction.generateSignature(privateKey);
		
		
		return newTransaction;
	}
	
	
	
	
	public Transaction sendFundsWithFakePublicKey(String value ) {
			try {
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
				// Initialize the key generator and generate a KeyPair
				keyGen.initialize(ecSpec, random); //256 
		        KeyPair keyPair = keyGen.generateKeyPair();
		        // Set the public and private keys from the keyPair
		     
		         fakeKey = keyPair.getPublic();
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
	        
			
			Transaction newTransaction = new Transaction(fakeKey, value);
			newTransaction.generateSignature(privateKey);
			
			
			return newTransaction;
		}

	
	

}
