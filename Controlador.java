import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private List<Passagem> listaPassagem;
    private Menu menu;

    public Controlador(){
        listaPassagem = new ArrayList<Passagem>();
        menu = new Menu();
    }

    public String mostrar(){
        return this.menu.retornaMenu();
    }
    
    public void criaListaPassagem(){

    }


    public static void main(String[] args){
        System.out.println("executando codigo");
    }

} // final Controlador
