package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@alt='insider_logo']")
    public WebElement logo;

    @FindBy(xpath = "//*[contains(text(),'Company') and @data-toggle='dropdown']")
    public WebElement companyMenu;

    @FindBy(xpath = "//a[contains(text(),'Careers')]")
    public WebElement careersLink;

    public By onlyNecessaryButtonLocator = By.xpath("//*[text()='Only Necessary']");

    @FindBy(xpath = "//*[text()='Only Necessary']")
    public WebElement onlyNecessaryButton;

    public boolean isHomePageLoaded() {
        return isElementVisible(logo,"logo");
    }

    public CareerPage navigateToCareerPage() {
        hover(companyMenu,"companyMenu");
        click(careersLink,"careersLink");
        return new CareerPage(driver);
    }
}