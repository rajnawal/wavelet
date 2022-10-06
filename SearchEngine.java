import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;

class StringHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> storedStrings = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("/add?s=anewstringtoadd to add a new string; /search?s=app to query stored results.");
        } else if (url.getPath().contains("/add")) {
            String stringToAdd = url.getQuery().split("=")[1];
            storedStrings.add(stringToAdd);
            return String.format(stringToAdd + " was added to the stored list!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String result = "";
                String substringToMatch = url.getQuery().split("=")[1];
                for(String s : storedStrings){
                    if(s.indexOf(substringToMatch) >= 0){
                        result += s + " ";
                    }
                }
                return String.format(result);
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new StringHandler());
    }
}
