import java.time.LocalDate;

public class Passagem {
    private String dono;
    private double preco;
    private String empresa;
    private String origem;
    private String destino;
    private LocalDate data;

    public Passagem(){
        this.dono = "";
        this.preco = 0;
        this.empresa = "";
        this.origem = "";
        this.destino = "";
        this.data = null;
    }

    public Passagem(String dono, int preco, String empresa, String origem, String destino, LocalDate data){
        this.dono = dono;
        this.preco = preco;
        this.empresa = empresa;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
    }
}
