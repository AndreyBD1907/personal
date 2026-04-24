// Panel personalizado para dibujar un Árbol Binario de Búsqueda, en el cual se
// utiliza recursividad para representar los nodos y sus conexiones.
package backend;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {

    private BinarySearchTree tree;

    // Espaciado vertical entre niveles
    private final int verticalGap = 60;

    // Radio de los nodos
    private final int nodeRadius = 20;

    // Método constructor
    public TreePanel(BinarySearchTree tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    // Método que permite actualizar el árbol y redibujarlo.
    public void setTree(BinarySearchTree tree) {
        this.tree = tree;
        repaint();
    }

    // Método principal de dibujo del panel.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree != null && tree.getRoot() != null) {
            int width = getWidth();
            // Se inicia el dibujo desde la raíz centrada
            drawTree(g, tree.getRoot(), width / 2, 40, width / 4);
        }
    }

    // Método recursivo que dibuja cada nodo del árbol.
    private void drawTree(Graphics g, Node node, int x, int y,
            int horizontalGap) {

        if (node == null) return;
        // Dibuja conexiones primero (para que queden debajo de los nodos)
        if (node.left != null) {
            int childX = x - horizontalGap;
            int childY = y + verticalGap;
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.left, childX, childY, horizontalGap / 2);
        }
        if (node.right != null) {
            int childX = x + horizontalGap;
            int childY = y + verticalGap;
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.right, childX, childY, horizontalGap / 2);
        }

        // Dibuja un nodo (círculo)
        g.setColor(Color.WHITE);
        g.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        // Dibuja el ID centrado
        String text = String.valueOf(node.data.getId());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, x - textWidth / 2, y + textHeight / 4);
    }
}
