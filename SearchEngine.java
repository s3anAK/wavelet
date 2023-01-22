import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class NumberHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        String returnString = "";
        if (url.getPath().equals("/")) {
            if (list.size() != 1) {
                for (int i = 0; i <list.size(); i++) {
                    returnString = returnString + list.get(i) + ", ";
                }
            } else {
                returnString = list.get(0);
            }
            return String.format("Sean's list: %s", returnString);
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                list.add(parameters[1]);
            }
            return String.format("element added!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).contains(parameters[1])) {
                            returnString = returnString + list.get(i) + ", ";
                        }
                    }
                    
                }
                return returnString;
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

        Server.start(port, new NumberHandler());
    }
}
