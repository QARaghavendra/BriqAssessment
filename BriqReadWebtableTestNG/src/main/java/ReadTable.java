import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.FileWriter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadTable {
	
	WebDriver driver;
	@BeforeMethod
	public void setup() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		}
	@Test
	public void login() {
		driver.get("http://the-internet.herokuapp.com/challenging_dom");
		driver.manage().window().maximize();

        // Locate the table element (you can use different locators as needed)
        WebElement table = driver.findElement(By.tagName("table")); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = "webtable_" + timestamp + ".csv";
        // Create a CSV file
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            // Iterate through the rows and columns of the table
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                StringBuilder rowText = new StringBuilder();
                for (WebElement cell : cells) {
                    rowText.append(cell.getText()).append(",");
                }
                csvWriter.write(rowText.toString().trim() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	

	@AfterClass
	public void quit() {
		// Close the browser after the test is finished
		driver.quit(); 
	}

}