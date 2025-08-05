package basic_practice;
import java.net.*;
import java.io.*;
import java.util.*;

//set port number = 8888
public class MyServer {
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Please give me the port number you'll use.");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port "+ port);

            while(true){
                Socket socket = serverSocket.accept();

                System.out.println("[ " + socket.getInetAddress()+" ] client connected");
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(new Date().toString());

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                System.out.println("message: " + reader.readLine());
            }

        }catch(IOException e){
            System.out.println("Server exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
