package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;

public class CareerPage extends BasePage {
    public CareerPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "career-our-location")
    public WebElement locationsWidget;

    @FindBy(id = "career-find-our-calling")
    public WebElement teamsWidget;

    @FindBy(xpath = "//*[@data-element_type='section']//*[contains(text(),'Life at Insider')]")
    public WebElement lifeAtInsiderWidget;

    @FindBy(xpath = "//a[contains(@href,'qualityassurance') and contains(text(),'See all QA jobs')]")
    public WebElement seeAllJobsButton;

    public boolean verifyCareerBlocksVisible() {
        return isElementVisible(locationsWidget,"locationsWidget")
                && isElementVisible(teamsWidget,"teamsWidget")
                && isElementVisible(lifeAtInsiderWidget,"lifeAtInsiderWidget");
    }

    public QualityAssuranceJobsPage goToQAJobs() {
        click(seeAllJobsButton,"seeAllJobsButton");
        return new QualityAssuranceJobsPage(driver);
    }
}