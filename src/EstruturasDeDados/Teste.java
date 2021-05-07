package EstruturasDeDados;

public class Teste {
    public static void main(String[] args) {
        try {
            Coordenada coordenada = new Coordenada(5, 10);
            Coordenada co = new Coordenada(6, 10);
            System.out.println(coordenada);
            System.out.println(co);
            System.out.println(coordenada.equals(co));
            System.out.println(coordenada.hashCode());
            System.out.println(co.hashCode());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
