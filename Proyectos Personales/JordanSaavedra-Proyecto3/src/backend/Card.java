/**
 * Clase que representa una tarjeta del sistema.
 * Contiene los datos que se almacenarán en el árbol.
 */
package backend;

public class Card {

    private int id;
    private String description;
    private String category;

    /**
     * Constructor de la tarjeta.
     */
    public Card(int id, String description, String category) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}