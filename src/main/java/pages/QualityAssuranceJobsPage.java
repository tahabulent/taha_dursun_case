package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class QualityAssuranceJobsPage extends BasePage {
    public QualityAssuranceJobsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//*[@class='select2-selection__rendered'])[1]")
    public WebElement locationFilter;

    @FindBy(xpath = "(//*[@class='select2-selection__rendered'])[2]")
    public WebElement departmentFilter;

    @FindBy(xpath = "//*[@data-team='qualityassurance']//span")
    public List<WebElement> jobCards;
    @FindBy(xpath = "(//*[@data-team='qualityassurance']//span)[1]")
    public WebElement jobCardsFirst;

    @FindBy(xpath = "//a[contains(text(),'View Role')]")
    public WebElement viewRoleButton;

    @FindBy(xpath = "//*[@class='select2-results__option' and text()='Istanbul, Turkiye']")
    public WebElement optionIstanbul;

    @FindBy(xpath = "//*[contains(@class,'select2-results__option')and text()='Quality Assurance']")
    public WebElement optionQualityAssurance;

    public void filterByLocationAndDepartment(String location, String department) {
        selectByVisibleText(locationFilter, location,"locationFilter");
        selectByVisibleText(departmentFilter, department,"departmentFilter");
        waitForSeconds(2); // let the filtering take effect
    }

    public boolean isJobListVisible() {
        return jobCards.size() > 0;
    }

    public boolean verifyAllJobsMatch(String titleKeyword, String department, String location) {
        for (WebElement job : jobCards) {
            String text = job.getText();
            if (!(text.contains(department))) {
                return false;
            }
        }
        return true;
    }

    public boolean clickViewRoleAndVerifyRedirect() {
        String originalWindow = driver.getWindowHandle();
        hover(jobCardsFirst,"jobCardsFirst");
        click(viewRoleButton,"viewRoleButton");
        waitForSeconds(2);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        return driver.getCurrentUrl().contains("lever.co");
    }
}