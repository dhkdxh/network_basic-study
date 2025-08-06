package basic_practice;
import java.io.*;
import java.net.*;

public class HttpClient {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Please enter the hostname and port number");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        try(Socket socket = new Socket(hostname, port)){
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("GET /index.html HTTP/1.1");
            writer.println("Host: "+hostname);
            writer.println("Connection: close");
            writer.println();//요청을 끝낼 때는 empty 줄을 추가해서 보냄

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line).append("\n");
            }

            System.out.println(response.toString());

        }catch(UnknownHostException e){
            System.out.println("UnknownHostException occurs: "+ e.getMessage());
        }catch(IOException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
