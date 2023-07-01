
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

        Kata thread1 = new Kata(salt, theHash, hashObject, 0, 8);
        thread1.start();

        Kata thread2 = new Kata(salt, theHash, hashObject, 8, 16);
        thread2.start();

        Kata thread3 = new Kata(salt, theHash, hashObject, 16, 24);
        thread3.start();

        Kata thread4 = new Kata(salt, theHash, hashObject, 24, 26);
        thread4.start();

        }
}