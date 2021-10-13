package mesproduits;
import magasin.iClient;

public class Client implements iClient {
    private static int idClients = 0;
    private int id;

    public Client() {
        this.id = idClients++;
    }

    @Override
    public String id() {
        return ""+id;
    }

    @Override
    public int compareTo(iClient o) {
        return -1;
    }
}
