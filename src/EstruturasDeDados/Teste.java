package EstruturasDeDados;

public class Teste {
    public static void main(String[] args) {
        try {
            Pilha<Integer> pilha = new Pilha<Integer>(10);
            pilha.push(10);
            pilha.push(12);
            pilha.push(6);
            pilha.push(9);
            pilha.push(15);
            pilha.push(2);
            System.out.println(pilha);
            System.out.println(pilha.top());
            System.out.println(pilha.pop());
            System.out.println(pilha.top());

            Pilha<Integer> pilha2 = new Pilha<Integer>(pilha);
            System.out.println(pilha2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
