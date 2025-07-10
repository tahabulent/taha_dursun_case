package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class AllureUtils {

    public static void allureReport(WebDriver driver, String message, Status status, boolean attachScreenshot) {
        try {
            if (attachScreenshot && driver != null) {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                ByteArrayInputStream jpgStream = pngBytesToJpgBytes(new ByteArrayInputStream(screenshotBytes));
                Allure.addAttachment("Screenshot: " + message, jpgStream);
            }

            Allure.step(message, status);

            if (status == Status.FAILED) {
                throw new AssertionError(message);
            }

        } catch (Exception e) {
            System.out.println("Allure report error: " + e.getMessage());
        }
    }

    private static ByteArrayInputStream pngBytesToJpgBytes(ByteArrayInputStream bais) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(bais);
        BufferedImage newBufferedImage = new BufferedImage(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBufferedImage, "jpg", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
