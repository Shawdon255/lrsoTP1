import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
        public static void main(String[] args) throws IOException {
            // inicia o servidor
            new Servidor(12345).executa();
        }

        private int porta;
        private List<PrintStream> clientes;
        private Controlador controlador;
        
        public Servidor (int porta) {
            this.porta = porta;
            this.clientes = new ArrayList<PrintStream>();
            this.controlador = new Controlador();
        }

        public void executa () throws IOException {
            ServerSocket servidor = new ServerSocket(this.porta);
            System.out.println("Porta 12345 aberta!");
            while (true) {
                // aceita um cliente
                Socket cliente = servidor.accept();
        
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
                // adiciona saida do cliente à lista
                PrintStream ps = new PrintStream(cliente.getOutputStream());
                this.clientes.add(ps);
                // cria tratador de cliente numa nova thread
                TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
                new Thread(tc).start();
            }
        }

        public void distribuiMensagem(String msg) {
            // envia msg para todo mundo
            for (PrintStream cliente : this.clientes) {
                cliente.println(msg);
            }
        }

        public void enviaMenu(){
            for (PrintStream cliente : this.clientes) {
                cliente.println(controlador.mostrar());
            }
        }
    }
  
    class TrataCliente implements Runnable {
        private InputStream cliente;
        private Servidor servidor;

        public TrataCliente(InputStream cliente, Servidor servidor) {
            this.cliente = cliente;
            this.servidor = servidor;
        }
        
        public void run() { 
            // quando chegar uma msg, distribui pra todos
            Scanner s = new Scanner(this.cliente);
            while (s.hasNextLine()) {
                System.out.println("Enviado menu para o usuário");
                servidor.enviaMenu();
                //servidor.distribuiMensagem(s.nextLine());
            }
            s.close();
        }
    }