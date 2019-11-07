package healthCare;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;


public class Transaction {
	public String transactionId; //Contains a hash of transaction*
	private PublicKey sender; //Senders address/public key.
	public String value; //Contains the informations.
	public byte[] signature; //This is to prevent anybody else from spending funds in our wallet.
	
	
	private static int sequence = 0; //A rough count of how many transactions have been generated 
	
	// Constructor: 
	public Transaction(PublicKey from, String value) {
		this.sender = from;
		this.value = value;
	}
	
	public boolean processTransaction() {
		
		if(verifySignature() == false) {
			
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
				
				
		return true;
	}
	
	
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender)  +value	;
		signature = StringUtil.applyECDSASig(privateKey,data);		
	}
	
	
	
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender)  + value	;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}
	
	public boolean verifySignature(PublicKey p) {
		String data = StringUtil.getStringFromKey(p)  + value	;
		return StringUtil.verifyECDSASig(p, data, signature);
	}
	
	
	private String calulateHash() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				
				value + sequence
				);
	}

}
