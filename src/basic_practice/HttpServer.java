package basic_practice;
import java.net.*;
import java.io.*;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            System.out.println("Please give me the port number.");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("서버 실행 중: http://localhost:"+port);

        while(true){
            try (Socket socket = serverSocket.accept()) {
                System.out.println("클라이언트 접속: "+socket.getInetAddress());

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();

                String requestline = reader.readLine();
                System.out.println(requestline);

                String line;
                while((line = reader.readLine())!= null && !line.isEmpty()){
                    System.out.println(line);
                }

                if(requestline == null || requestline.isEmpty()) continue;

                String[] tokens = requestline.split(" ");
                String filePath = tokens[1];

                if(filePath.equals("/")) filePath="/index.html";

                File file = new File("."+ filePath);

                if(file.exists() && !file.isDirectory()){//!file.isDirectory 하는 이유: directory가 아닌 file형태 골라내기
                    BufferedReader fileReader = new BufferedReader(new FileReader(file));
                    StringBuilder content = new StringBuilder();//StringBuffer은 쓰면 안됨? 단일 thread에서 문자열 조작하니까
                    //multithread에서 공유 문자열을 조작할 때는 StringBuffer가 안전하긴 한데
                    //요즘은 multithread를 처리하는 경우에도 StringBuilder+외부 동기화 처리 선호

                    String templine;

                    while((templine = fileReader.readLine()) != null){
                        content.append(templine).append("\n");
                    }
                    fileReader.close();

                    String response = "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: text/html; charset=UTF-8\r\n" +
                                    "Content-Length: " + content.toString().getBytes().length + "\r\n" +
                                    "Connection: close\r\n"+ "\r\n"+ content.toString();

                    output.write(response.getBytes());
                }else{
                    String response = "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "404 File Not Found";
                    output.write(response.getBytes());
                }
                output.flush();

            }catch(IOException e){
                System.out.println("client error: "+e.getMessage());
            }
        }
    }
}
