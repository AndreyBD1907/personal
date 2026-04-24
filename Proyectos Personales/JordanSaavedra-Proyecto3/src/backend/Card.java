// Clase que representa una tarjeta del sistema y en la que se contiene los
// datos que se almacenarán en el árbol.
package backend;

public class Card {

    private int id;
    private String description;
    private String category;

    // Método constructor
    public Card(int id, String description, String category) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    // Método utilizado para obtener el ID de la tarjeta
    public int getId() {
        return id;
    }

    // Método utilizado para obtener la descripción de la tarjeta
    public String getDescription() {
        return description;
    }

    // Método utilizado para obtener la categoría de la tarjeta
    public String getCategory() {
        return category;
    }
}