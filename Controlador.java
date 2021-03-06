import java.util.ArrayList;
import java.util.List;
 
 
public class Controlador {
    private List<Passagem> listaRotas;
    private List<Passagem> listaPassagem;
    private int[] quantidadePassagem;
    private Menu menu;
 
    public Controlador(){
        listaRotas = new ArrayList<Passagem>();
        listaPassagem = new ArrayList<Passagem>();
        quantidadePassagem = new int[100];
        menu = new Menu();
    }
 
    /**
     * Mostra menu.
     * @return o menu em formato de String para o servidor.
     */
    public String mostrar(){
        return this.menu.retornaMenu();
    }
 
    /**
     * Lista as rotas disponíveis para reserva.
     * @return um menu contendo a lista das rotas.
     */
    public String listarPassagem(){
        String novoMenu = this.menu.retornaListaPassagem();
        int i = 0;
 
        for(Passagem p : this.listaRotas){
            if (quantidadePassagem[i] != 0){
                novoMenu += ""+(i+1)+"- " + p.toString() + " " + "(Quantidade disponível: "+quantidadePassagem[i] + ")\n";
            }
            i++;
        }
        return novoMenu;
    }
 
    /**
     * Reserva uma passagem em nome de um cliente.
     * @param idPassagem - id da passagem que deseja reservar.
     * @param nome - nome do cliente dono da passagem.
     */
    public void reservarPassagem(int idPassagem, String nome){
        Passagem p = listaRotas.get(idPassagem-1);
        Passagem cliente = new Passagem();
        p.copy(cliente);
        cliente.dono = nome;
        
        listaPassagem.add(cliente);
        quantidadePassagem[idPassagem-1]--;
    }
 
    /**
     * Cancela uma passagem.
     * @param idPassagem - id da passagem que deseja cancelar.
     * @param nome - nome do cliente dono da passagem.
     */
    public void cancelarPassagem(int idPassagem, String nome){
        for(Passagem p : listaPassagem){
            if (p.dono.compareTo(nome) == 0){
                listaPassagem.remove(listaPassagem.indexOf(p));                   
                quantidadePassagem[idPassagem-1]++;
                break;
            }
        }
    }
    
    /**
     * Cria lista de passagem para teste de reserva e cancelamento.
     */
    public void criaListaPassagem(){
        listaRotas.add(new Passagem("", 123.15, "GOL", "BHZ", "CGH", "2021-09-16"));
        quantidadePassagem[0] = 1;
        listaRotas.add(new Passagem("", 123.15, "GOL", "CGH", "BHZ", "2021-09-16"));
        quantidadePassagem[1] = 100;
        listaRotas.add(new Passagem("", 123.15, "LATAM", "CNF", "SDU", "2021-10-30"));
        quantidadePassagem[2] = 11;
        listaRotas.add(new Passagem("", 123.15, "LATAM", "SDU", "CNF", "2021-10-30"));
        quantidadePassagem[3] = 23;
        listaRotas.add(new Passagem("", 123.15, "AZUL", "CNF", "MAO", "2021-11-18"));
        quantidadePassagem[4] = 25;
        listaRotas.add(new Passagem("", 123.15, "AZUL", "MAO", "CNF", "2021-11-18"));
        quantidadePassagem[5] = 25;
    }
 
    /**
     * Lógica de négocio do controlador para tratamento das solicitações feitas pelo servidor.
     * @param opcao - Opção selecionada pelo cliente e passada pelo servidor.
     * @param idPassagem - ID da passagem para uso na reserva e cancelamento.
     * @param nome - Nome do dono da passagem (chave de busca).
     * @return - Retorna status da solicitação.
     */
    public String selecionaOpcao(int opcao, int idPassagem, String nome){
        switch(opcao){
            case 1:
               return listarPassagem();
            case 2:
                if (idPassagem != 0){       
                    reservarPassagem(idPassagem, nome);
                    return new String("Incluido com sucesso!");
                }else{
                    return "";
                }
            case 3:
                if (idPassagem != 0){       
                    cancelarPassagem(idPassagem, nome);
                    return new String("Cancelada com sucesso!");
                }else{
                    return "";
                }
            case 99:
                return new String("Iniciando desconexão...");
            default:
                System.out.println("Opção não encontrada!");
                return "";
        }
    }
 
} // final Controlador