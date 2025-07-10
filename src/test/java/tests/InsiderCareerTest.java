package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.HomePage;
import pages.QualityAssuranceJobsPage;

import java.time.Duration;


public class InsiderCareerTest extends BaseTest {

    @Test
    @Description("Verify QA job listings in Istanbul, Turkey on Insider careers page")
    public void verifyQualityAssuranceJobsInIstanbul() {
        // 1. Visit homepage and verify
        driver.get("https://useinsider.com/");
        HomePage homePage = new HomePage(driver);
        homePage.clickIfExists(homePage.onlyNecessaryButtonLocator, Duration.ofSeconds(10),"onlyNecessaryButtonLocator");
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page did not load properly");

        // 2. Navigate to Company > Careers and verify blocks
        CareerPage careerPage = homePage.navigateToCareerPage();
        careerPage.scrollToElement(careerPage.teamsWidget,"teamsWidget");
        careerPage.scrollToElement(careerPage.locationsWidget,"locationsWidget");
        careerPage.scrollToElement(careerPage.lifeAtInsiderWidget,"lifeAtInsiderWidget");
        Assert.assertTrue(careerPage.verifyCareerBlocksVisible(), " Locations, Teams, and Life at Insider blocks are visible");

        // 3. Go to QA jobs and apply filters
        driver.get("https://useinsider.com/careers/quality-assurance/,");
        QualityAssuranceJobsPage qaPage = careerPage.goToQAJobs();
        qaPage.waitForSeconds(10);
        qaPage.click(qaPage.locationFilter,"locationFilter");
        qaPage.click(qaPage.optionIstanbul,"optionIstanbul");
        qaPage.click(qaPage.departmentFilter,"departmentFilter");
        qaPage.click(qaPage.optionQualityAssurance,"optionQualityAssurance");
        qaPage.waitForSeconds(15);
        Assert.assertTrue(qaPage.isJobListVisible(), "Job list is visible");

        // 4. Verify job listings
        Assert.assertTrue(qaPage.verifyAllJobsMatch("Quality Assurance", "Quality Assurance", "Istanbul, Turkey"),
                "One or more job listings match the expected values");

        // 5. Click View Role and verify redirect

        Assert.assertTrue(qaPage.clickViewRoleAndVerifyRedirect(), "redirect to Lever Application form page");
    }




}
