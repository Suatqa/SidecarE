import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class Sidecare {

        public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver= new ChromeDriver();
        String url= "http://automationpractice.com/index.php";
        driver.get(url);

        String ExpectedResult ="My Store";
        String a= driver.getTitle();
        //Verify Home page
        Assert.assertEquals(ExpectedResult, a);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //Verify Quantity (should be `1`)

        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\'homefeatured\']/li[1]/div/div[2]/h5/a"));
        act.doubleClick(ele).perform();


        String Quantity ="1";
        String actually_q = driver.findElement(By.xpath("//input[@id='quantity_wanted']")).getAttribute("value");
        System.out.println(actually_q);
        Assert.assertEquals(Quantity, actually_q);
        String item_price =driver.findElement(By.id("our_price_display")).getText();
        System.out.println(item_price);
        String item_name =driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
        System.out.println(item_name);

        driver.findElement(By.cssSelector("#add_to_cart > button > span")).click();
        driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[2]/div[4]/a/span/i")).click();

        // In the shopping cart summary, validate that item name, quantity and price against the values stored in step - 4.

        String sum_item_name = driver.findElement(By.xpath("//*[@id=\"product_1_1_0_0\"]/td[2]/p/a")).getText();
        Assert.assertEquals(item_name, sum_item_name);

        String sum_item_price =          driver.findElement(By.xpath("//*[@id=\"product_price_1_1_0\"]/span")).getText();
        Assert.assertEquals(item_price, sum_item_price);

        String sum_quantity = driver.findElement(By.xpath("//*[@id=\"product_1_1_0_0\"]/td[5]/input[2]")).getAttribute("value");
        Assert.assertEquals(actually_q, sum_quantity);

        // On the same page, validate that "TOTAL = Total products + Total shipping + Tax"
        String total = driver.findElement(By.xpath("//*[@id=\"total_price\"]")).getText();
        String total1 = total.substring(1,total.length());
        double total2 = Double.parseDouble(total1);


        String tax = driver.findElement(By.id("total_tax")).getText();
        String tax1= tax.substring(1,tax.length());
        double tax2 = Double.parseDouble(tax1);

        String  Total_product = driver.findElement(By.id("total_product")).getText();
        String Total_product1 =Total_product.substring(1,Total_product.length());
        double Total_product2 = Double.parseDouble(Total_product1);


        String Total_shipping = driver.findElement(By.id("total_shipping")).getText();
        String Total_shipping1 =Total_shipping.substring(1,Total_shipping.length());
        double Total_shipping2 = Double.parseDouble(Total_shipping1);

        if (total2 == (tax2+Total_product2+Total_shipping2)){
            System.out.println("Correct");
        }else{
            System.out.println("Wront");
        }





        driver.close();
        driver.quit();

    }

}
