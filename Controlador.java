import java.time.LocalDate;
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

    public String mostrar(){
        return this.menu.retornaMenu();
    }

    public String listarPassagem(){
        String novoMenu = this.menu.retornaListaPassagem();
        int i = 0;

        for(Passagem p : this.listaRotas){
            if (quantidadePassagem[i] != 0){
                novoMenu += ""+(i+1)+"- " + p.toString() + " " + quantidadePassagem[i] + "\n";
            }
            i++;
        }
        return novoMenu;
    }

    public void reservarPassagem(int idPassagem, String nome){
        Passagem p = listaRotas.get(idPassagem-1);
        Passagem cliente = new Passagem();
        p.copy(cliente);
        cliente.dono = nome;
        
        listaPassagem.add(cliente);
        quantidadePassagem[idPassagem-1]--;
    }
    
    public void criaListaPassagem(){
        listaRotas.add(new Passagem("", 123.15, "GOL", "BHZ", "CGH", "2021-09-16"));
        quantidadePassagem[0] = 1;
        listaRotas.add(new Passagem("", 123.15, "GOL", "CGH", "BHZ", "2021-09-16"));
        quantidadePassagem[1] = 100;
    }

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
            default:
                System.out.println("Opção não encontrada!");
                return "";
        }
    }

    public static void main(String[] args){
        System.out.println("executando codigo");
    }

} // final Controlador
