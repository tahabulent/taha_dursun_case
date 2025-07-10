package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(css = "#password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(@type,'submit')]")
    private WebElement loginButton;

    public CareerPage loginAndNavigateToCareer(String username, String password) {
        type(usernameInput, username,"usernameInput");
        type(passwordInput, password,"passwordInput");
        click(loginButton,"loginButton");
        return new CareerPage(driver);
    }
}