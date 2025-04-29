package com.smartpack.services;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Classe EtiquetaService
 */
@Service
public class EtiquetaService {

    /**
     * Generate Etiqueta
     * Metode per generar etiqueta de servei amb informacio i QR
     * 
     * @param nombre     String
     * @param adreça     String
     * @param telefon    String
     * @param textPaquet String
     * @param textPes    String
     * @param textMida   String
     * @param detalls    String
     * @param qrBytes    byte[]
     * @return byte[]
     * @throws Exception
     */
    public byte[] generateEtiqueta(String nombre, String adreça, String telefon, String textPaquet,
            String textPes, String textMida, String detalls, byte[] qrBytes) throws Exception {

        // Crear imagen base amb dimensions
        int width = 500;
        int height = 300;
        BufferedImage etiqueta = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        this.crearImatge(etiqueta, width, height, nombre, adreça, telefon, textPaquet, textPes, textMida, detalls,
                qrBytes);

        // Convertir imatge
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(etiqueta, "png", outputStream);

        return outputStream.toByteArray();
    }

    /**
     * Crear Imatge
     * Metode per generar la imatge de l'etiqueta amb estils
     * 
     * @param etiqueta   BufferedImage
     * @param width      int
     * @param height     int
     * @param nombre     String
     * @param adreça     String
     * @param telefon    String
     * @param textPaquet String
     * @param textPes    String
     * @param textMida   String
     * @param detalls    String
     * @param qrBytes    byte[]
     * @throws Exception
     */
    public void crearImatge(BufferedImage etiqueta, int width, int height, String nombre, String adreça,
            String telefon, String textPaquet, String textPes, String textMida, String detalls, byte[] qrBytes)
            throws Exception {

        Graphics2D g2d = etiqueta.createGraphics();

        // fons blanc
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        int y = 50; // posició vertical inicial

        // línia horitzontal
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(20, 20, width - 20, 20);

        // logo
        ClassPathResource imgFile = new ClassPathResource("static/img/logo.png");
        BufferedImage logo = ImageIO.read(imgFile.getInputStream());
        g2d.drawImage(logo, 300, 0, 150, 150, null);

        // Estil de títol
        g2d.setColor(Color.BLACK);
        // Títol Paquet
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(textPaquet, 30, y);
        y += 25;

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(textPes + "   " + textMida, 30, y);
        y += 30;

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Destinatari:", 30, y);
        y += 20;

        // Estil de text més petit
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(nombre, 30, y);
        y += 20;

        // Títol Adreça
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Adreça:", 30, y);
        y += 20;

        // Text Adreça
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(adreça, 30, y);
        y += 20;

        // Títol Telèfon
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Telèfon:", 30, y);
        y += 20;

        // Text Telèfon
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(telefon, 30, y);
        y += 20;

        // Títol Detalls
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Detalls:", 30, y);
        y += 20;

        // Text Detalls
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(detalls, 30, y);
        y += 20;

        // Cargar QR
        ByteArrayInputStream qrInputStream = new ByteArrayInputStream(qrBytes);
        BufferedImage qrImage = ImageIO.read(qrInputStream);
        g2d.drawImage(qrImage, width / 2 + 50, y - 150, 150, 150, null);

        // línia horitzontal
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(20, height - 20, width - 20, height - 20);

        g2d.dispose();
    }
}
