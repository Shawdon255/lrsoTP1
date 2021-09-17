import java.util.Scanner;

public class Menu {
    private String menu;
    
    public Menu(){
        menu =
        "##--Reserva de Passagens--##\n\n"  +
        "|-----------------------------|\n" + 
        "| Opção 1 - Listar Passagens  |\n" +
        "| Opção 2 - Reservar Passagem |\n" +
        "| Opção 3 - Produtos          |\n" +
        "| Opção 4 - Sair              |\n" +
        "|-----------------------------|\n" +
        "Digite uma opção:";
    }

    public String retornaMenu(){
        return menu;
    }

    public String retornaListaPassagem(){
        return "##--Lista de Passagens--##\n\n";
    }

    public static void main(String[] args){
        Menu teste = new Menu();
    }

} // final Menu


