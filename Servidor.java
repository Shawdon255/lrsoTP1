import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
 
public class Servidor {
        public static void main(String[] args) throws IOException {
            // inicia o servidor na porta 12345
            new Servidor(12345).executa();
        }
 
        private int porta;
        private Controlador controlador;
        
        public Servidor (int porta) {
            this.porta = porta;
            this.controlador = new Controlador();
            controlador.criaListaPassagem();
        }
 
        public void executa () throws IOException {
            // cria um socker do servidor com a porta selecionada
            ServerSocket servidor = new ServerSocket(this.porta);
            System.out.println("Porta 12345 aberta!");
            while (true) {
                // aceita um cliente
                Socket cliente = servidor.accept();
        
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
                // cria tratador de cliente numa nova thread
                TrataCliente tc = new TrataCliente(cliente, this, controlador);
                new Thread(tc).start();
            }
        }
 
        public void enviaMensagem(Socket cliente, String msg) throws IOException{
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            ps.println(msg);
        }
    }
  
    // Classe responsável para lidar com o socket do Cliente em um thread separada.
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
            // Comunicação entre o servidor e o cliente.
            try{
                // Leitura do que o cliente enviou para o servidor.
                Scanner s = new Scanner(this.cliente.getInputStream());
                System.out.println("Enviado menu para o usuário");
                servidor.enviaMensagem(this.cliente, this.enviaMenu());
                int opcao;
                boolean desconectar = false;
 
                // Manter comunicação entre o servidor e este cliente enquanto existir a conexão.
                while (s.hasNextLine() && !desconectar){
                    // Chama um controlador que ira ativar alguma funcionalidade escolhida pelo cliente.
                    System.out.println("Enviando mensagem para o usuário: " + cliente.getInetAddress().getHostAddress());
                    opcao = s.nextInt();
                    servidor.enviaMensagem(this.cliente, controlador.selecionaOpcao(opcao, 0, ""));
                    
                    // Lógica de négocio do servidor para controle de telas e opção com o cliente.
                    switch(opcao){
                        // Voltar ao menu inicial.
                        case 1:
                            servidor.enviaMensagem(this.cliente, "Voltar ao Menu? (y/n)");
                            String volta = s.next();
                            while(volta.charAt(0) != 'y'){
                                servidor.enviaMensagem(this.cliente, "Voltar ao Menu? (y/n)");
                                volta = s.next();
                            }
                            volta.toLowerCase();
                            if(volta.charAt(0) == 'y'){
                                System.out.println("Enviado menu para o usuário");
                                servidor.enviaMensagem(this.cliente, this.enviaMenu());
                                break; 
                            }
                        // Reservar passagem.        
                        case 2:                     
                            servidor.enviaMensagem(this.cliente, "Digite o ID da passagem que deseja reservar: ");
                            int idPassagem = s.nextInt();
                            
                            while(idPassagem > 6){
                                servidor.enviaMensagem(this.cliente, "ID Inválido, digíte novamente: ");
                                idPassagem = s.nextInt();
                            }
                            servidor.enviaMensagem(this.cliente, "Digite o Nome do comprador: ");
                            s.nextLine(); // Limpeza do buffer para leitura sem ruídos do nome.
                            
                            String nomeCliente = s.nextLine();
                            System.out.println("Cliente para reservar: "+nomeCliente);
                            controlador.selecionaOpcao(2, idPassagem, nomeCliente);
                            
                            servidor.enviaMensagem(this.cliente, "Reserva efetuada obrigado "+cliente.getInetAddress().getHostAddress());
                            servidor.enviaMensagem(this.cliente, this.enviaMenu());
                            break;
                        // Cancelar reserva de passagem.
                        case 3:
                            servidor.enviaMensagem(this.cliente, "Digite o ID da passagem que deseja cancelar: ");
                            idPassagem = s.nextInt();                     
                            servidor.enviaMensagem(this.cliente, "Digite o Nome do comprador: ");
                            
                            s.nextLine(); // Limpeza do buffer para leitura sem ruídos do nome.
                            nomeCliente = s.nextLine();
                            System.out.println("Cliente para cancelar: "+nomeCliente);
                            controlador.selecionaOpcao(3, idPassagem, nomeCliente);
                            
                            servidor.enviaMensagem(this.cliente, "Cancelamento efetuado obrigado "+cliente.getInetAddress().getHostAddress());
                            servidor.enviaMensagem(this.cliente, this.enviaMenu());
                            break;
                        // Desconectar o cliente.
                        case 99:
                            servidor.enviaMensagem(this.cliente, "desconectar");
                            System.out.println("Cliente desconectado: "+cliente.getInetAddress().getHostAddress());
                            desconectar = true;
                            break;
                        default:
                            System.out.println("");
                            break;
                    }
                }
                s.close();
            
        } catch(IOException ioe){ // Caso der ruim imprime "Deu RUIM".
                System.out.println("Deu RUIM");
        }
    }
} // final TrataCliente