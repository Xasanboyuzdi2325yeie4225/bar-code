package org.example.barcode;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class BarCode {

    public String createBarCode(String text){
        try {
            generateBarcodeImage(text, 300, 150, BARCODE_IMAGE_PATH);
            return "yaratildi";
        } catch (Exception e) {
            return ("Shtrix-kodni yaratib bo‘lmadi, IOException :: " + e.getMessage());
        }
    }

    private static final String BARCODE_IMAGE_PATH = "./MyBarcode.png";
    private static void generateBarcodeImage(String text, int width, int height, String filePath)
            {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
                try {
                    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }




    public String readBarCode(){
        String res="";
        try {
            File file = new File("./MyBarcode.png");
            BufferedImage bufferedImage = ImageIO.read(file);

            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            res=("Barcode data: " + result.getText());
        } catch (IOException e) {
            res=("Could not read the image file, IOException :: " + e.getMessage());
        } catch (NotFoundException e) {
            res=("There is no barcode in the image, NotFoundException :: " + e.getMessage());
        }
        return res;
    }

}
