
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public static void main(String[] args) throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {


// mise en place de l'equivalent de mon fetch de manière synchrone --> https://www.baeldung.com/java-9-http-client

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://shallenge.onrender.com/challenges"))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // transformer la reponse en objet pour pouvoir lire les propriétés

        ;
String futurObject = response.body();
        System.out.println(futurObject);
TheHashObject hashObject = new TheHashObject(futurObject.substring(53,117),futurObject.substring(127,159) , futurObject.substring(7,43));

        System.out.println(hashObject.getHash());
        System.out.println(hashObject.getId());
        System.out.println(hashObject.getSalt());
        String theHash = hashObject.getHash();
        String salt = hashObject.getSalt();



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


                                if(bytesToHex(hash).equals(theHash)){
                                    System.out.println(password);

                                    HttpClient client2 = HttpClient.newHttpClient();
                                    String url = "https://shallenge.onrender.com/challenges/" + hashObject.getId() + "/answer";
                                    HttpRequest request2 = HttpRequest.newBuilder()
                                            .uri(new URI(url))
                                            .version(HttpClient.Version.HTTP_2)
                                            .header("Content-Type", "application/json")
                                            .POST(HttpRequest.BodyPublishers.ofString(password))
                                            .build();

                                    System.out.println(request2.headers());
                                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                                    System.out.println(response2.headers());
                                    System.out.println(response2.body());


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