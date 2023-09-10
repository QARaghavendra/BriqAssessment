import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Test
public class Download {
	
	WebDriver driver;
	@BeforeMethod
	public void setup() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		}
	public void login() {
		driver.get("https://the-internet.herokuapp.com/download");
		driver.manage().window().maximize();
		 WebElement downloadTxt = driver.findElement(By.linkText("some-file.txt"));
		 downloadTxt.click();
	        
	        WebElement downloadPdf = driver.findElement(By.linkText("download.pdf"));
	        downloadPdf.click();
	        
	        WebElement downloadXml = driver.findElement(By.linkText("5mb script.xml"));
	        downloadXml.click();
	        
	        // Get the default download directory for the browser
	        String downloadDirectory = "C:\\Users\\Last Safezone\\Desktop\\automation docs\\15july\\BriqReadWebtableTestNG"; // Update with your browser's default directory

	        // Assuming the file you want to download has a specific name (e.g., "example.txt")
	        String downloadedFilePathTxt = downloadDirectory + "/some-file.txt"; // Update the filename accordingly
	        String downloadedFilePathPdf = downloadDirectory + "/download.pdf"; 
	        String downloadedFilePathXml = downloadDirectory + "/5mb script.xml"; 
    
	/* @AfterClass
	public void quit() {
		// Close the browser after the test is finished
		driver.quit();
	}*/

}
}
