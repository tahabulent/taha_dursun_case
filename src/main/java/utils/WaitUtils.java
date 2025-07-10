package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public WebElement waitForVisibility(WebElement element, String elementName) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            return visibleElement;
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "❌ Failed to wait for visibility: " + describeElement(element,elementName), Status.FAILED, true);
            throw new RuntimeException("Element not visible: " + describeElement(element,elementName), e);
        }
    }

    public WebElement waitForClickable(WebElement element, String elementName) {
        try {
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            return clickableElement;
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "❌ Failed to wait for clickable: " + describeElement(element,elementName), Status.FAILED, true);
            throw new RuntimeException("Element not clickable: " + describeElement(element,elementName), e);
        }
    }

    public boolean waitForUrlToContain(String partialUrl) {
        try {
            boolean result = wait.until(ExpectedConditions.urlContains(partialUrl));
            return result;
        } catch (Exception e) {
            AllureUtils.allureReport(driver, "❌ Failed to wait for URL to contain: " + partialUrl, Status.FAILED, true);
            throw new RuntimeException("URL did not contain expected: " + partialUrl, e);
        }
    }


    private String describeElement(WebElement element, String elementName) {
        try {
            String tag = element.getTagName();
            String id = element.getAttribute("id");
            String text = element.getText();
            return String.format("[tag=%s, id=%s, text=%s]", tag, id, text);
        } catch (Exception e) {
            return elementName;
        }
    }
}
