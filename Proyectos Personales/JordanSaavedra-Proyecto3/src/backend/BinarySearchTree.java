// Implementación de un Árbol Binario de Búsqueda (BST)
// Maneja inserción, búsqueda, eliminación, recorridos y consultas
package backend;

public class BinarySearchTree {
    // Constantes definidas para identificar cuando un nodo no puede ser
    // eliminado debido a restricciones
    public static final int NOT_FOUND = 1;
    public static final int DELETED = 2;
    public static final int BLOCKED_CIVIL = 3;
    public static final int BLOCKED_RIGHT_CHILD = 4;
    public static final int BLOCKED_TWO_CHILDREN = 5;

    // Nodo raiz del árbol
    private Node root;

    // Método constructor
    public BinarySearchTree() {
        this.root = null;
    }
    
    // Método utilizado para obtener el nodo raiz del árbol
    public Node getRoot() {
        return root;
    }
    
    // Busca una tarjeta por ID
    public Card search(int id) {
        // Busca el id de forma recursiva desde la raiz
        Node result = searchRec(root, id);
        // Verifica que el resultado obtenido no sea nulo
        if (result != null) {
            return result.data;
        } else {
            return null;
        }
    }

    // Búsqueda recursiva en el árbol
    private Node searchRec(Node current, int id) {
        // Si encuentra el id o ya no hay más nodos, debe retornar
        if (current == null || current.data.getId() == id) {
            return current;
        }
        // Si el id es menor al del nodo actualmente evaluado, va a la izquierda
        if (id < current.data.getId()) {
            return searchRec(current.left, id);
        // Si no, va a la derecha
        } else {
            return searchRec(current.right, id);
        }
    }
    
    // Inserción recursiva respetando las reglas del BST
    private Node insertRec(Node current, Card card) {
        // Caso base: insertar nodo si el árbol está vacío
        if (current == null) {
            return new Node(card);
        }
        // En caso de que el árbol no esté lleno, evalúa los id's presentes
        // Si el id es menor al del nodo atualmente evaluado, va a la izquierda
        if (card.getId() < current.data.getId()) {
            current.left = insertRec(current.left, card);
        // Si no, va a la derecha
        } else {
            current.right = insertRec(current.right, card);
        }
        return current;
    }
    
    // Inserta una nueva tarjeta en el árbol y valida que el ID sea positivo y
    // no esté repetido
    public boolean insert(Card card) {
        // Validación de ID positivo
        if (card.getId() <= 0) {
            return false;
        }
        // Validación de ID único
        if (search(card.getId()) != null) {
            return false;
        }
        // Comienza el proceso de inserción desde la raiz
        root = insertRec(root, card);
        return true;
    }
    
    // Elimina un nodo según las reglas del enunciado
    public int delete(int id) {
        // Busca la tarjeta solicitada entre los nodos registrados
        Node node = searchRec(root, id);
        // Si el nodo retornado tiene valor nulo, la tarjeta no existe
        if (node == null) {
            return NOT_FOUND;
        }
        // Caso en el que la tarjeta es de tipo civiles
        if (node.data.getCategory().equals("Civiles")) {
            return BLOCKED_CIVIL;
        }
        // Caso en el que un nodo solo tiene nodo derecho
        if (node.left == null && node.right != null) {
            return BLOCKED_RIGHT_CHILD;
        }
        // Caso en el que un nodo tiene 2 hijos
        if (node.left != null && node.right != null) {
            return BLOCKED_TWO_CHILDREN;
        }
        // Si pasa todas las validaciones, se elimina
        root = deleteRec(root, id);
        return DELETED;
    }

    // Eliminación recursiva respetando las reglas del BST y las restricciones
    private Node deleteRec(Node current, int id) {
        // Si el nodo actual es nulo, retorna nulo
        if (current == null) {
            return null;
        }
        // Si el id es menor al del nodo actualmente evaluado, va a la izquierda
        if (id < current.data.getId()) {
            current.left = deleteRec(current.left, id);
        // Si el id es mayor al del nodo actualmente evaluado, va a la derecha
        } else if (id > current.data.getId()) {
            current.right = deleteRec(current.right, id);
        } else {
            // Caso hoja
            if (current.left == null && current.right == null) {
                return null;
            }
            // Solo subárbol izquierdo
            if (current.left != null && current.right == null) {
                return current.left;
            }
        }
        return current;
    }
    
    // Recorrido preorden (Root - Left - Right)
    public String preOrder() {
        // Variable que nos permite modificar un String
        StringBuilder result = new StringBuilder();
        // Método recursivo
        preOrderRec(root, result);
        // Retorna el valor en formato String
        return result.toString();
    }

    // Método recursivo para el recorrido preorden
    private void preOrderRec(Node current, StringBuilder result) {
        // En caso de que haya una tarjeta, guarda su id
        if (current != null) {
            // Lo acumula en la variable y lo separa con un guión
            result.append(current.data.getId()).append("-");
            // Sigue el resto de llamados recursivos hasta llegar a las hojas
            preOrderRec(current.left, result);
            preOrderRec(current.right, result);
        }
    }
    
    // Recorrido inorden (Left - Root - Right)
    public String inOrder() {
        // Variable que nos permite modificar un String
        StringBuilder result = new StringBuilder();
        // Método recursivo
        inOrderRec(root, result);
        // Retorna el valor en formato String
        return result.toString();
    }

    // Método recursivo para el recorrido inorden
    private void inOrderRec(Node current, StringBuilder result) {
        // En caso de que haya una tarjeta, guarda su id
        if (current != null) {
            // Continúa su recorrido por el lado izquierdo
            inOrderRec(current.left, result);
            // Lo acumula en la variable y lo separa con un guión
            result.append(current.data.getId()).append("-");
            // Continúa su recorrido por el lado derecho
            inOrderRec(current.right, result);
        }
    }
    
    // Recorrido postorden (Left - Right - Root)
    public String postOrder() {
        // Variable que nos permite modificar un String
        StringBuilder result = new StringBuilder();
        // Método recursivo
        postOrderRec(root, result);
        // Retorna el valor en formato String
        return result.toString();
    }

    // Método recursivo para el recorrido postorden
    private void postOrderRec(Node current, StringBuilder result) {
        // En caso de que haya una tarjeta, guarda su id
        if (current != null) {
            // Sigue el resto de llamados recursivos hasta llegar a las hojas
            postOrderRec(current.left, result);
            postOrderRec(current.right, result);
            // Lo acumula en la variable y lo separa con un guión
            result.append(current.data.getId()).append("-");
        }
    }
    
    // Método que cuenta tarjetas según su categoría
    public int countHeroesAndVillains(String category) {
        // Método recursivo
        return countRec(root, category);
    }

    // Método recursivo para contar las tarjetas según su categoría
    private int countRec(Node current, String category) {
        // En caso de que el nodo actual sea nulo, devuelve 0
        if (current == null)
            return 0;
        // Inicializa un contador local
        int count = 0;
        // Toma la categoría de la tarjeta actual para ser comparada
        String currentCategory = current.data.getCategory();
        // En caso de coincidir con la categoría a comparar, incrementa el
        // contador
        if (currentCategory.equals(category))
            // El contador local ahora tiene valor 1
            count = 1;
        // Devuelve lo que el contador local tiene más lo que las otras
        // llamadas recursivas retornen
        return count + countRec(current.left, category) +
                countRec(current.right, category);
    }
    
    // Método utilizado para obtener todas las frases icónicas registradas
    public String getLeafIconicPhrases() {
        // Variable que nos permite modificar un String
        StringBuilder result = new StringBuilder();
        // Método recursivo
        leafRec(root, result);
        // Retorna el valor en formato String
        return result.toString();
    }

    // Método recursivo para enlistar las descripciones de tarjetas que son hoja
    // y pertenecen a la categoría "Frases icónicas"
    private void leafRec(Node current, StringBuilder result) {
        // En caso de que el nodo actual sea nulo, debe retornar
        if (current == null) {
            return;
        }
        // Si la tarjeta posee categoría Frases Icónicas, se guarda su
        // descripción
        if (current.data.getCategory().equals("Frases icónicas")) {
            result.append(current.data.getDescription()).append("\n");
        }
        // Sigue el resto de llamados recursivos hasta llegar a las hojas
        leafRec(current.left, result);
        leafRec(current.right, result);
    }
    
    // Método utilizado para obtener la carta con el id más pequeño
    public Card findMin() {
        // Se inicia buscando desde la raiz
        Node current = root;
        // Mediante un ciclo, se comienza a buscar en el lado izquierdo del
        // árbol
        while (current != null && current.left != null) {
            current = current.left;
        }
        // Devuelve la carta con sus datos
        if (current != null) {
            return current.data;
        // Si el árbol está vacío, retorna nulo
        } else {
            return null;
        }
    }

    // Método utilizado para obtener la carta con el id más grande
    public Card findMax() {
        // Se inicia buscando desde la raiz
        Node current = root;
        // Mediante un ciclo, se comienza a buscar en el lado derecho del árbol
        while (current != null && current.right != null) {
            current = current.right;
        }
        // Devuelve la carta con sus datos
        if (current != null) {
            return current.data;
        // Si el árbol está vacío, retorna nulo
        } else {
            return null;
        }
    }
}
