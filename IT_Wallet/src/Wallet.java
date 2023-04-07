import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import Base58.Base58;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public String address;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            // Initialize the security service provider
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            // Generate ECDSA keypair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            
            // Generate RIPEMD-160 hash based on the public key
            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] sha256Bytes = MessageDigest.getInstance("SHA-256").digest(publicKeyBytes);
            byte[] ripeMD160Bytes = MessageDigest.getInstance("RIPEMD160").digest(sha256Bytes);

            // Add version byte in front of RIPEMD-160 hash
            byte[] versionedBytes = new byte[ripeMD160Bytes.length + 1];
            versionedBytes[0] = 0x00; // Main Network version byte
            System.arraycopy(ripeMD160Bytes, 0, versionedBytes, 1, ripeMD160Bytes.length);

            // Generate checksum
            byte[] firstSha256 = MessageDigest.getInstance("SHA-256").digest(versionedBytes);
            byte[] secondSha256 = MessageDigest.getInstance("SHA-256").digest(firstSha256);
            byte[] checksum = Arrays.copyOfRange(secondSha256, 0, 4);

            // Append checksum to versioned RIPEMD-160 hash
            byte[] addressBytes = new byte[versionedBytes.length + checksum.length];
            System.arraycopy(versionedBytes, 0, addressBytes, 0, versionedBytes.length);
            System.arraycopy(checksum, 0, addressBytes, versionedBytes.length, checksum.length);

            // Apply Base58 encoding
            String address = Base58.encode(addressBytes);

            System.out.println("Bitcoin Address: " + address);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}