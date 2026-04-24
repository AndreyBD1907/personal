// Clase nodo del árbol binario de búsqueda donde se guardan tarjetas y
// referencias a sus hijos.
package backend;

public class Node {

    Card data;
    Node left;
    Node right;

    public Node(Card data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
