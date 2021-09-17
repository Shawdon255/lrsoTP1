import java.util.Scanner;

public class Menu {
    private String menu;
    
    public Menu(){
        menu =
        "\n##--Reserva de Passagens--##\n\n"  +
        "|------------------------------|\n" + 
        "| Opção 1  - Listar Passagens  |\n" +
        "| Opção 2  - Reservar Passagem |\n" +
        "| Opção 3  - Cancelar Reserva  |\n" +
        "| Opção 99 - Sair              |\n" +
        "|------------------------------|\n" +
        "Digite uma opção:";
    }

    public String retornaMenu(){
        return menu;
    }

    public String retornaListaPassagem(){
        return "##--Lista de Passagens--##\n\n";
    }

} // final Menu


