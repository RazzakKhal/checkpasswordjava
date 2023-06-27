
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Main {

    // méthode pour hash trouvée sur https://www.baeldung.com/sha-256-hashing-java
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // pour trouver le mot de passe
// utiliser 6 lettres abcdef... , les Buffer, concatener avec salt buffer les hasher, comparer au hash

        String salt = "02b2c31c64dc40cd863c423ff3478e70";
        String theHash = "cbafb5166696c0e50bb8d0b01bb1773e2528c4796ea3e5a388e40bf44c806069";


        // de buffer à string hexadecimal
        // trouvée ici https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
       var bufferSalt = HexFormat.of().parseHex(salt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");


//boucle avec 6 carac --> Buffer --> concat avec le salt --> hash --> compare
        String alphabete = "abdcefghijklmnopqrstuvwxyz";
        String[] alphabet = alphabete.split("");
        for(int a=0; a < 26; a++){
            for(int b=0; b < 26; b++){
                for(int c=0; c < 26; c++){
                    for(int d=0; d < 26; d++){
                        for(int e=0; e < 26; e++){
                            for(int f=0; f < 26; f++){

                                String password = alphabet[a] + alphabet[b] + alphabet[c] + alphabet[d] + alphabet[e] + alphabet[f];

                                // si le hash du buffer du password + du buffer du salt equals the hash alors on affiche le pass
                                var bufferPass = password.getBytes();
                                byte[] mergeBuffer = new byte[bufferPass.length + bufferSalt.length];
                                System.arraycopy(bufferSalt, 0, mergeBuffer, 0, bufferSalt.length);
                                System.arraycopy(bufferPass, 0, mergeBuffer, bufferSalt.length, bufferPass.length);

                                byte[] hash = digest.digest(mergeBuffer);

                                if(password.equals("rrrrrr")){
                                    System.out.println("yep");
                                }

                                if(bytesToHex(hash).equals(theHash)){
                                    System.out.println(password);



                                ;
                                break;
                            }


                        }
                    }
                }
            }
        }
    }


        }
}