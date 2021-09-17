import java.time.LocalDate;

public class Passagem {
    protected String dono;
    protected double preco;
    protected String empresa;
    protected String origem;
    protected String destino;
    protected String data;

    public Passagem(){
        this.dono = "";
        this.preco = 0;
        this.empresa = "";
        this.origem = "";
        this.destino = "";
        this.data = "";
    }

    public Passagem(String dono, double preco, String empresa, String origem, String destino, String data){
        this.dono = dono;
        this.preco = preco;
        this.empresa = empresa;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
    }

    public void copy(Passagem p){
        p.dono = this.dono;
        p.preco = this.preco;
        p.empresa = this.empresa;
        p.origem = this.origem;
        p.destino = this.destino;
        p.data = this.data;
    }
}
