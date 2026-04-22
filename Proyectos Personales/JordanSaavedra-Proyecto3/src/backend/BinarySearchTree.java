// Implementación de un Árbol Binario de Búsqueda (BST)
// Maneja inserción, búsqueda, eliminación, recorridos y consultas
package backend;

public class BinarySearchTree {
    // Nodo raiz del árbol
    private Node root;

    // Método utilizado para obtener el nodo raiz del árbol
    public Node getRoot() {
        return root;
    }
    
    // Busca una tarjeta por ID
    public Card search(int id) {
        // Busca el id de forma recursiva desde la raiz
        Node result = searchRec(root, id);
        // Verifica que el resultado obtenido no sea nulo
        if (result != null)
            return result.data;
        else
            return null;
    }

    // Búsqueda recursiva en el árbol
    private Node searchRec(Node current, int id) {
        // Si encuentra el id o ya no hay más nodos, debe retornar
        if (current == null || current.data.getId() == id)
            return current;
        // Si el id es menor al del nodo actualmente evaluado, va a la izquierda
        if (id < current.data.getId())
            return searchRec(current.left, id);
        // Si no, va a la derecha
        else
            return searchRec(current.right, id);
    }
    
    // Inserción recursiva respetando las reglas del BST
    private Node insertRec(Node current, Card card) {
        // Caso base: insertar nodo si el árbol está vacío
        if (current == null)
            return new Node(card);
        // En caso de que el árbol no esté lleno, evalúa los id's presentes
        // Si el id es menor al del nodo atualmente evaluado, va a la izquierda
        if (card.getId() < current.data.getId())
            current.left = insertRec(current.left, card);
        // Si no, va a la derecha
        else
            current.right = insertRec(current.right, card);
        return current;
    }
    
    // Inserta una nueva tarjeta en el árbol y valida que el ID sea positivo y
    // no esté repetido
    public boolean insert(Card card) {
        // Validación de ID positivo
        if (card.getId() <= 0)
            return false;
        // Validación de ID único
        if (search(card.getId()) != null)
            return false;
        // Comienza el proceso de inserción desde la raiz
        root = insertRec(root, card);
        return true;
    }
    
    // Elimina un nodo según las reglas del enunciado
    public boolean delete(int id) {
        // Busca si el nodo con el id a buscar existe
        if (search(id) == null)
            return false;
        // Si lo encuentra, comienza con el proceso desde la raiz
        root = deleteRec(root, id);
        return true;
    }

    // Método recursivo para la eliminación de un nodo dentro del árbol
    private Node deleteRec(Node current, int id) {
        // Verifica si el nodo actual a evaluar no está vacío
        if (current == null)
            return null;
        // Si el id es menor al del nodo atualmente evaluado, va a la izquierda
        if (id < current.data.getId())
            current.left = deleteRec(current.left, id);
        // Si el id es mayor al del nodo atualmente evaluado, va a la derecha
        else if (id > current.data.getId())
            current.right = deleteRec(current.right, id);
        else
            // 🔴 Regla: no eliminar si es "Civiles"
            if (current.data.getCategory().equals("Civiles"))
                return current;
            // Caso 1: nodo hoja
            if (current.left == null && current.right == null)
                return null;
            // Caso 2: solo subárbol derecho → NO eliminar
            if (current.left == null && current.right != null)
                return current;
            // Caso 3: solo subárbol izquierdo → subirlo
            if (current.left != null && current.right == null)
                return current.left;
            // Caso 4: dos hijos → NO eliminar
            if (current.left != null && current.right != null)
                return current;
        return current;
    }
    
    // Recorrido preorden (Root - Left - Right)
    public String preOrder() {
        StringBuilder result = new StringBuilder();
        preOrderRec(root, result);
        return result.toString();
    }

    // Método recursivo para el recorrido preorden
    private void preOrderRec(Node current, StringBuilder result) {
        if (current != null) {
            result.append(current.data.getId()).append("-");
            preOrderRec(current.left, result);
            preOrderRec(current.right, result);
        }
    }
    
    // Recorrido inorden (Left - Root - Right)
    public String inOrder() {
        StringBuilder result = new StringBuilder();
        inOrderRec(root, result);
        return result.toString();
    }

    // Método recursivo para el recorrido inorden
    private void inOrderRec(Node current, StringBuilder result) {
        if (current != null) {
            inOrderRec(current.left, result);
            result.append(current.data.getId()).append("-");
            inOrderRec(current.right, result);
        }
    }
    
    // Recorrido postorden (Left - Right - Root)
    public String postOrder() {
        StringBuilder result = new StringBuilder();
        postOrderRec(root, result);
        return result.toString();
    }

    // Método recursivo para el recorrido postorden
    private void postOrderRec(Node current, StringBuilder result) {
        if (current != null) {
            postOrderRec(current.left, result);
            postOrderRec(current.right, result);
            result.append(current.data.getId()).append("-");
        }
    }
    
    // Cuenta tarjetas de categoría "Súper héroes" y "Súper villanos"
    public int countHeroesAndVillains() {
        return countRec(root);
    }

    // Método recursivo para contar las tarjetas "Súper héroes" y "Súper
    // villanos"
    private int countRec(Node current) {
        if (current == null)
            return 0;
        int count = 0;
        String category = current.data.getCategory();
        if (category.equals("Súper héroes") ||
            category.equals("Súper villanos"))
            count = 1;
        return count + countRec(current.left) + countRec(current.right);
    }
    
    // Lista descripciones de tarjetas que son hoja y pertenecen a la categoría
    // "Frases icónicas"
    public String getLeafIconicPhrases() {
        StringBuilder result = new StringBuilder();
        leafRec(root, result);
        return result.toString();
    }

    // Método recursivo para enlistar las descripciones de tarjetas que son hoja
    // y pertenecen a la categoría "Frases icónicas"
    private void leafRec(Node current, StringBuilder result) {
        if (current == null)
            return;
        if (current.left == null && current.right == null &&
            current.data.getCategory().equals("Frases icónicas"))
            result.append(current.data.getDescription()).append("\n");
        leafRec(current.left, result);
        leafRec(current.right, result);
    }
    
    // Obtiene la tarjeta con menor ID
    public Card findMin() {
        Node current = root;
        while (current != null && current.left != null) {
            current = current.left;
        }
        if (current != null)
            return current.data;
        else
            return null;
    }

    // Obtiene la tarjeta con mayor ID
    public Card findMax() {
        Node current = root;
        while (current != null && current.right != null) {
            current = current.right;
        }
        if (current != null)
            return current.data;
        else
            return null;
    }
}
