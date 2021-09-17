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
            this.controlador = new Controlador();
            controlador.criaListaPassagem();
        }

        public void executa () throws IOException {
            ServerSocket servidor = new ServerSocket(this.porta);
            System.out.println("Porta 12345 aberta!");
            while (true) {
                // aceita um cliente
                Socket cliente = servidor.accept();
        
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
                // adiciona saida do cliente à lista
                //PrintStream ps = new PrintStream(cliente.getOutputStream());
                //this.clientes.add(ps);
                // cria tratador de cliente numa nova thread
                TrataCliente tc = new TrataCliente(cliente, this, controlador);
                new Thread(tc).start();
            }
        }

        public void distribuiMensagem(String msg) {
            // envia msg para todo mundo
            for (PrintStream cliente : this.clientes) {
                cliente.println(msg);
            }
        }

        public void enviaMensagem(Socket cliente, String msg) throws IOException{
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            ps.println(msg);
        }
    }
  
    class TrataCliente implements Runnable {
        private Socket cliente;
        private Servidor servidor;
        private Controlador controlador;

        public TrataCliente(Socket cliente, Servidor servidor, Controlador controlador) {
            this.cliente = cliente;
            this.servidor = servidor;
            this.controlador = controlador;
        }

        public String enviaMenu(){
            return controlador.mostrar();
        }
        
        public void run() { 
            // quando chegar uma msg, distribui
            try{
                Scanner s = new Scanner(this.cliente.getInputStream());
                System.out.println("Enviado menu para o usuário");
                servidor.enviaMensagem(this.cliente, this.enviaMenu());
                int opcao;

                while (s.hasNextLine()){
                    System.out.println("Enviando mensagem para o usuário: " + cliente.getInetAddress().getHostAddress());
                    opcao = s.nextInt();
                    servidor.enviaMensagem(this.cliente, controlador.selecionaOpcao(opcao, 0, "")+"Digite uma opção: ");
                    
                    switch(opcao){
                        case 2:                     
                            servidor.enviaMensagem(this.cliente, "Digite o ID da passagem: ");
                            int idPassagem = s.nextInt();
                            servidor.enviaMensagem(this.cliente, "Digite o Nome do comprador: ");
                            s.nextLine();
                            String nomeCliente = s.nextLine();
                            System.out.println(nomeCliente);
                            controlador.selecionaOpcao(2, idPassagem, nomeCliente);
                            break;
                        default:
                            System.out.println("");
                            break;
                    }
                }
                s.close();
            
        } catch(IOException ioe){
                System.out.println("Deu RUIM");
        }
    }
} // final TrataCliente