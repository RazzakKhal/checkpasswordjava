
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;


public class Kata extends Thread{

    String saltt;
    String hashh;

    int debut;

    int fin;

    TheHashObject hashObject;
    public Kata(String saltt, String hashh, TheHashObject hashObject, int debut, int fin){
    this.saltt = saltt;
    this.hashh = hashh;
    this.hashObject = hashObject;
    this.debut = debut;
    this.fin = fin;
    }
    @Override
    public void run() {

        try {
            this.checkpassword();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

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
    public  void checkpassword() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {





        // de string hexa à buffer
        // trouvée ici https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
        var bufferSalt = HexFormat.of().parseHex(this.saltt);
        int endOfLoop = 1;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");


//boucle avec 6 carac --> Buffer --> concat avec le salt --> hash --> compare
        String alphabete = "abdcefghijklmnopqrstuvwxyz";
        String[] alphabet = alphabete.split("");
        for(int a=this.debut; a < this.fin; a++){
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


                                if(bytesToHex(hash).equals(this.hashh)){
                                    System.out.println(password);

                                    HttpClient client2 = HttpClient.newHttpClient();
                                    String url = "https://shallenge.onrender.com/challenges/" + hashObject.getId() + "/answer";

                                    HttpRequest request2 = HttpRequest.newBuilder()
                                            .uri(new URI(url))
                                            .version(HttpClient.Version.HTTP_1_1)
                                            .header("Content-Type", "application/json")
                                            .POST(HttpRequest.BodyPublishers.ofString( "\"" + password + "\""))
                                            .build();

                                    System.out.println(request2.headers());
                                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                                    System.out.println(response2);
                                    System.out.println(response2.body());
                                    endOfLoop = 2;

                                    ;
                                    break;
                                }
                                // fin Boucle 2
                                if(endOfLoop == 2){
                                    break;
                                }


                            }
                            // fin Boucle 3
                            if(endOfLoop == 2){
                                break;
                            }

                        }
                        // fin Boucle 4
                        if(endOfLoop == 2){
                            break;
                        }

                    }
                    // fin Boucle 5
                    if(endOfLoop == 2){
                        break;
                    }

                }
                // fin Boucle 6
                if(endOfLoop == 2){
                    break;
                }

            }
        }


    }
}