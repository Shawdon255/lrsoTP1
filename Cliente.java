import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.net.UnknownHostException;
import java.net.Socket;
 
public class Cliente {
    public static void main(String[]args) throws UnknownHostException, IOException {
    // dispara cliente
        new Cliente("127.0.0.1", 12345).executa();
    }
 
    private String host;
    private int porta;
 
    public Cliente (String host, int porta) {
        this.host = host;
        this.porta = porta;
    }
 
    public void executa() throws UnknownHostException, IOException {
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("Conexão estabelecida.");
        
        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();
        
        // lê msgs do teclado e manda pro servidor 
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        String palavra;
        while (teclado.hasNextLine()) {
            palavra = teclado.nextLine();
            saida.println(palavra);
            // caso receba a opcao 99 cliente encerra conexão
            if(palavra.compareTo("99") == 0){
                break;
            }
        }
        saida.close();
        teclado.close();
        cliente.close();
    }
}
 
class Recebedor implements Runnable {
    private InputStream servidor;
 
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }
    
    public void run() {
        boolean desconectar = false;
        String palavra;
 
        // recebe msgs do servidor e imprime na tela     
        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine() && !desconectar) {
            palavra = s.nextLine();
            System.out.println(palavra);
            // caso receber desconectar do servidor finaliza o while
            if(palavra.compareTo("desconectar") == 0){
                desconectar = true;
            }
        }
    }   
}