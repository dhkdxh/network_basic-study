package basic_practice;
import java.net.*;
import java.io.*;

//set hostname = localhost, port = 8888
public class MyClient {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Please give me host name and port number.");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        try(Socket socket = new Socket(hostname,port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output,true);
            writer.println("I'm yewon jeon.");

            //Server가 보내준 정보 받기
            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while((line = reader.readLine())!= null){
                System.out.println(line);
            }

        }catch(UnknownHostException e){
            System.out.println("Server not found: "+ e.getMessage());
        }catch(IOException e){
            System.out.println("I/O Exception: "+e.getMessage());
        }
    }
}
