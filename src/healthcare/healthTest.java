/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthcare;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;


 class healtTest {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	
	public static int difficulty = 3;
	public static Profile profileA;
	public static Profile profileB;
	public static Transaction genesisTransaction;

	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
	
		
		//Create wallets:
		profileA = new Profile();
		profileB = new Profile();		
		Profile coinbase = new Profile();
		
//		genesisTransaction = new Transaction(coinbase.publicKey, "Hello");
//		genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction	
//		genesisTransaction.transactionId = "0"; //manually set the transaction id
		
		Block genesis = new Block("0");
//		genesis.addTransaction(genesisTransaction);
		genesis.addTransaction(profileA.sendFunds("OH NO!"));
//		genesis.addTransaction(profileA.sendFundsWithFakePublicKey("OH Fake!"));
		
//		genesis.addTransaction(profileB.sendFunds("BUT NO!"));
	//	genesis.addTransaction(profileA.sendFunds("OH NO!"));
		addBlock(genesis);
//		genesis.addTransaction(profileA.sendFunds("OH NO!"));
//		genesis.transactions.get(0).value+=" ";	
//		genesis.nonce++;
	
		

		//testing
		Block block1 = new Block(genesis.hash);

		block1.addTransaction(profileB.sendFunds("OH NO!"));
		addBlock(block1);
		

		
		isChainValid();
		
		//all the medical record for profile A!
		if(isChainValid())
			for(int i=0; i<blockchain.size() ; i++){
				for(int j=0; j<  blockchain.get(i).transactions.size() ; j++){
					Transaction tr=blockchain.get(i).transactions.get(j);
					
					
					if( tr.verifySignature(profileA.publicKey) ){
						System.out.println("hihi" +blockchain.get(i).transactions.get(j).value);
					}else{
					System.out.println("error "+blockchain.size());
					}
					
					 
				}
			}



		
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes not equal");
				return false;
			}
			if(i== 1 ){
				if(!previousBlock.hash.equals(previousBlock.calculateHash()) ){
					System.out.println("Current Hashes not equal");			
					return false;
				}		
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes not equal");
				System.out.println("#Blockchain is NOT valid");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				System.out.println("#Blockchain is NOT valid");
				return false;
			}
			
				
			
		}
		System.out.println("Blockchain is valid");
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}

/*
 * public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		
		//walletA = new Wallet();
		//walletB = new Wallet();
		
		//System.out.println("Private and public keys:");
		//System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		//System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		
		createGenesis();
		
		//Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5);
		//transaction.signature = transaction.generateSignature(walletA.privateKey);
		
		//System.out.println("Is signature verified:");
		//System.out.println(transaction.verifiySignature());
		
	}
 */

//System.out.println("Trying to Mine block 1... ");
//addBlock(new Block("Hi im the first block", "0"));
