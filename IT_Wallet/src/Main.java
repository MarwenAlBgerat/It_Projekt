import java.security.Security;
import java.util.Base64;

public class Main {
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args) throws Exception {
        //Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
//        walletB = new Wallet();
//        //Test public and private keys
//        System.out.println("Private and public keys:");
//        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
//        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
//
//
//        //Create a test transaction from WalletA to walletB
//        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
//        transaction.generateSignature(walletA.privateKey);
//        //Verify the signature works and verify it from the public key
//        System.out.println("Is signature verified");
//        System.out.println(transaction.verifiySignature());
    }




}