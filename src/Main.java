import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.TexturePaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Práctica 03");
        DegradadosTexturizados panel = new DegradadosTexturizados();
        ventana.add(panel);
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }
}

class DegradadosTexturizados extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color colorAzulOscuro = new Color(153, 43, 58);
        Color colorNegro = new Color(0, 0, 0);

        int ancho = getWidth();
        int altura = getHeight();

        GradientPaint degradado = new GradientPaint(0, 0, colorAzulOscuro, 0, altura, colorNegro);
        g2d.setPaint(degradado);
        g2d.fillRect(0, 0, ancho, altura);

        g2d.setColor(Color.WHITE);
        Random aleatorio = new Random();
        for (int i = 0; i < 100; i++) {
            int x = aleatorio.nextInt(ancho);
            int y = aleatorio.nextInt(altura);
            int tamañoEstrella = aleatorio.nextInt(3) + 1;
            g2d.fillOval(x, y, tamañoEstrella, tamañoEstrella);
        }

        //Pasto
        int alturaPasto = 100;
        BufferedImage buffer = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D gBuffer = buffer.createGraphics();

        gBuffer.setColor(new Color(89, 108, 89));
        gBuffer.fillRect(0, 0, 20, 20);
        gBuffer.setColor(new Color(108, 75, 88));
        gBuffer.drawLine(0, 10, 20, 10);
        gBuffer.drawLine(10, 0, 10, 20);
        gBuffer.drawLine(5, 5, 15, 15);
        gBuffer.drawLine(15, 5, 5, 15);

        TexturePaint texturaPasto = new TexturePaint(buffer, new Rectangle(20, 20));
        g2d.setPaint(texturaPasto);
        g2d.fillRect(0, altura - alturaPasto, ancho, alturaPasto);

        // Dibujar triángulos por todo el suelo
        int baseTriangulo = 20;
        int alturaTriangulo = 20;
        int numTriangulosHorizontales = ancho / baseTriangulo;
        int numFilasTriangulos = alturaPasto / alturaTriangulo;

        g2d.setColor(Color.gray);
        for (int fila = 0; fila < numFilasTriangulos; fila++) {
            for (int col = 0; col < numTriangulosHorizontales; col++) {
                int[] xPuntos = {col * baseTriangulo, (col + 1) * baseTriangulo, col * baseTriangulo + baseTriangulo / 2};
                int[] yPuntos = {altura - fila * alturaTriangulo, altura - fila * alturaTriangulo, altura - (fila + 1) * alturaTriangulo};
                g2d.fillPolygon(xPuntos, yPuntos, 3);
            }
        }

        g2d.setColor(Color.gray);
        int[] xPuntos1 = {120, 130, 110};
        int[] yPuntos1 = {altura - alturaPasto - 20, altura - alturaPasto, altura - alturaPasto};
        g2d.fillPolygon(xPuntos1, yPuntos1, 3);

        int[] xPuntos2 = {140, 150, 130};
        int[] yPuntos2 = {altura - alturaPasto - 20, altura - alturaPasto, altura - alturaPasto};
        g2d.fillPolygon(xPuntos2, yPuntos2, 3);

        int[] xPuntos3 = {160, 170, 150};
        int[] yPuntos3 = {altura - alturaPasto - 20, altura - alturaPasto, altura - alturaPasto};
        g2d.fillPolygon(xPuntos3, yPuntos3, 3);

        // Casa
        int baseCasaX = 50;
        int baseCasaY = altura - alturaPasto - 100;
        int anchoCasa = 100;
        int alturaCasa = 100;

        // Dibujar base de la casa (rectángulo)
        g2d.setColor(new Color(180, 168, 168));
        g2d.fillRect(baseCasaX, baseCasaY, anchoCasa, alturaCasa);

        // Dibujar techo de la casa (triángulo)
        int[] xTecho = {baseCasaX, baseCasaX + anchoCasa / 2, baseCasaX + anchoCasa};
        int[] yTecho = {baseCasaY, baseCasaY - 50, baseCasaY};
        g2d.setColor(new Color(213, 131, 131));
        g2d.fillPolygon(xTecho, yTecho, 3);

        // Sol en la esquina superior derecha
        int diametroLuna = 100;
        Point2D centro = new Point2D.Float(getWidth() - 75, 75);
        float radio = 50;
        float[] dist = {0.0f, 1.0f};
        Color[] colores = {Color.WHITE, Color.black};
        RadialGradientPaint degradadoLuna = new RadialGradientPaint(centro, radio, dist, colores);
        g2d.setPaint(degradadoLuna);
        g2d.fillOval(getWidth() - 125, 25, diametroLuna, diametroLuna);

        int lunaX = getWidth() - 75;
        int lunaY = 75;
        int radioLuna = 50;

        // Rayos del sol
        g2d.setColor(Color.black);
        float[] patronGuiones = {10, 10};
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, patronGuiones, 0));

        int longitudRayo = 100;
        for (int i = 0; i < 360; i += 30) {
            double angulo = Math.toRadians(i);
            int x1 = (int) (lunaX + radioLuna * Math.cos(angulo));
            int y1 = (int) (lunaY + radioLuna * Math.sin(angulo));
            int x2 = (int) (lunaX + (radioLuna + longitudRayo) * Math.cos(angulo));
            int y2 = (int) (lunaY + (radioLuna + longitudRayo) * Math.sin(angulo));
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Arcos desde el centro de la pantalla
        int anchoArco = 100;
        int alturaArco = 100;
        int centroX = ancho / 2;
        int arcoY = altura - alturaPasto - alturaArco + 50;

        g2d.setStroke(new BasicStroke());
        g2d.setColor(Color.WHITE);

        // Dibujar arcos hacia la derecha
        for (int i = 0; i < 3; i++) {
            int arcoX = centroX + i * anchoArco;
            g2d.drawArc(arcoX, arcoY, anchoArco, alturaArco, 0, 180);
        }

        // Dibujar arcos hacia la izquierda
        for (int i = 1; i <= 2; i++) {
            int arcoX = centroX - i * anchoArco;
            g2d.drawArc(arcoX, arcoY, anchoArco, alturaArco, 0, 180);
        }

        // Nubes
        g2d.setColor(Color.WHITE);
        g2d.fillOval(100, 50, 60, 40);
        g2d.fillOval(130, 30, 80, 60);
        g2d.fillOval(170, 50, 60, 40);
        g2d.fillOval(140, 60, 80, 50);

        g2d.fillOval(230, 50, 60, 40);
        g2d.fillOval(260, 30, 80, 60);
        g2d.fillOval(300, 50, 60, 40);
        g2d.fillOval(270, 60, 80, 50);

        // Additional clouds
        g2d.fillOval(400, 70, 60, 40);
        g2d.fillOval(430, 50, 80, 60);

        // Imagen de Yoshi en el centro de la pantalla
        ImageIcon iconoImagen = new ImageIcon(getClass().getResource("/monita.png"));
        int anchoImagen = 150;
        int alturaImagen = 300;
        int x = (ancho - anchoImagen) / 2;
        int y = altura - alturaPasto - alturaImagen;
        g2d.drawImage(iconoImagen.getImage(), x, y, anchoImagen, alturaImagen, this);
    }
}