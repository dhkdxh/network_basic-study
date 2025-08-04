package basic_practice;
import java.net.*;
import java.io.*;

public class MyClient {
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Please give me domain name you want");
            return;
        }

        String domainName = args[0];

        String hostname = "whois.internic.net";
        int port = 43; //whois client는 특정 도메인에 대한 정보를 query할 수 있는 인터넷 서비스

        try(Socket socket = new Socket(hostname,port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output,true);
            writer.println(domainName);

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
