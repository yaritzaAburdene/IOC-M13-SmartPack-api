package com.smartpack.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe QrCodeService
 */
@Service
public class QrCodeService {

    /**
     * generateQrCodeImage
     * retorna la imatge del QR en memòria
     * 
     * @param text   String
     * @param width  int
     * @param height int
     * @return byte
     * @throws Exception
     */
    public byte[] generateQrCodeImage(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // Especifica el joc de caràcters

        // Genera un array del codi QR a partir del text i les dimensions indicades
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // Crea un flux de sortida
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Escriu el array del codi QR en format PNG dins el flux de sortida
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray(); // Retorna la imatge del codi QR com un array de bytes
    }

}
