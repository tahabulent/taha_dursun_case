package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.AllureUtils;
import utils.WaitUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.*;

public class BasePage {
    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element, String elementName) {
        try {
            wait.waitForClickable(element,elementName).click();
            AllureUtils.allureReport(driver, "Clicked successfully: "+elementName, Status.PASSED,true);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Click failed: "+elementName, Status.FAILED, true);
            throw e;
        }
    }

    public void type(WebElement input, String text, String elementName) {
        try {
            wait.waitForVisibility(input,elementName).sendKeys(text);
            AllureUtils.allureReport(driver, "Typed Successfully", Status.PASSED,true);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Typing failed: " , Status.FAILED, true);
            throw e;
        }
    }

    public void scrollToElement(WebElement element, String elementName) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            AllureUtils.allureReport(driver, "Scrolled successfully: "+elementName, Status.PASSED,true);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Scroll failed: "+elementName, Status.FAILED, true);
            throw e;
        }
    }

    public void assertVisible(WebElement element, String elementName) {
        try {
            boolean visible = wait.waitForVisibility(element,elementName).isDisplayed();
            if (visible) {
                AllureUtils.allureReport(driver, "Element visibled successfully: "+elementName, Status.PASSED,true);
            } else {
                AllureUtils.allureReport(driver, "Element not visible: "+elementName , Status.FAILED, true);
            }
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Error during visibility check: "+elementName , Status.FAILED, true);
            throw e;
        }
    }

    public void assertTextContains(WebElement element, String expectedText, String elementName) {
        try {
            String actual = wait.waitForVisibility(element,elementName).getText();
            if (actual.contains(expectedText)) {
                AllureUtils.allureReport(driver, "Expected text is found: "+elementName, Status.PASSED,true);
            } else {
                AllureUtils.allureReport(driver, "Expected text not found: "+elementName, Status.FAILED, true);
            }
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Text check failed: " +elementName, Status.FAILED, true);
            throw e;
        }
    }

    public void assertUrlContains(String partialUrl) {
        try {
            boolean result = wait.waitForUrlToContain(partialUrl);
            if (result) {
                AllureUtils.allureReport(driver, "URL is contained expected: ", Status.PASSED,true);
            } else {
                AllureUtils.allureReport(driver, "URL does not contain expected: " , Status.FAILED, true);
            }
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "URL check failed: ", Status.FAILED, true);
            throw e;
        }
    }

    public boolean waitForElementIfExists(By locator, Duration timeout, String elementName) {
        try {
            boolean found = waitForElementIfExists(locator, timeout,elementName);
            AllureUtils.allureReport(driver, "Element found: "+elementName, Status.PASSED,true);
            return found;
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Element not found: "+elementName, Status.FAILED, true);
            return false;
        }
    }

    public void clickIfExists(By locator, Duration timeout, String elementName) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, timeout);
            WebElement element = customWait.until(ExpectedConditions.presenceOfElementLocated(locator));

            AllureUtils.allureReport(driver, "✅ Element found: " + locator.toString(), Status.PASSED, false);

            wait.waitForClickable(element,elementName).click();
            AllureUtils.allureReport(driver, "🖱️ Clicked on element: " + locator.toString(), Status.PASSED, false);
        } catch (TimeoutException e) {
            AllureUtils.allureReport(driver, "⚠️ Element not found within timeout: " + locator.toString(), Status.SKIPPED, false);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "❌ Failed to click on element: " + locator.toString(), Status.FAILED, true);
            throw e;
        }
    }



    public void selectByVisibleText(WebElement webElement, String visibleText, String elementName) {
        try {
            Select select = new Select(wait.waitForVisibility(webElement,elementName));
            select.selectByVisibleText(visibleText);
            AllureUtils.allureReport(driver, "Selected '" + visibleText + "' from element: "+elementName, Status.PASSED,true);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Failed to select '" + visibleText + "' from element: "+elementName, Status.FAILED, true);
            throw e;
        }
    }

    public void hover(WebElement element, String elementName) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(wait.waitForVisibility(element,elementName)).perform();
            AllureUtils.allureReport(driver, "Hovered over element successfully: "+elementName, Status.PASSED,true);
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "Failed to hover over element: "+elementName, Status.FAILED, true);
            throw e;
        }
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            AllureUtils.allureReport(driver, "Waited for"+seconds, Status.PASSED,true);
        } catch (InterruptedException ignored) {
        }
    }

    public boolean isElementVisible(WebElement element, String elementName) {
        try {
            Allure.step("Check if element is visible: " + elementName);
            return wait.waitForVisibility(element,elementName).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    //@Attachment(value = "Step Failure Screenshot", type = "image/png")
    //public byte[] saveScreenshotPNG(WebDriver driver) {
    //    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    //}



    private static ByteArrayInputStream pngBytesToJpgBytes(ByteArrayInputStream bais) throws IOException, IOException {
        //...
        BufferedImage bufferedImage = ImageIO.read(bais);

        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

        // Create OutputStream to write prepared jpg bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Write image as jpg bytes
        ImageIO.write(newBufferedImage, "JPG", baos);

        // Convert OutputStream to a byte[]
        return new ByteArrayInputStream(baos.toByteArray());
    }
    public void allureReport(String result, String message, boolean ssFlag) {
        try {
            if (ssFlag) {
                Allure.addAttachment("screenshot : " + message,
                        pngBytesToJpgBytes(new ByteArrayInputStream(
                                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES))));
            }

            if (result.equalsIgnoreCase("PASS")) {
                Allure.step(message, Status.PASSED);
            } else if (result.equalsIgnoreCase("INFO")) {
                Allure.step(message, Status.SKIPPED);
            } else {
                Allure.step(message, Status.FAILED);
                Assert.fail(message);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}