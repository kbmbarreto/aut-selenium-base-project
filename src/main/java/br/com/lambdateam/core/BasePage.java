package br.com.lambdateam.core;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static br.com.lambdateam.core.DriverFactory.getDriver;

public class BasePage {

    /********* TextField and TextArea ************/
    public void fill(By by, String text) {
        getDriver().findElement(by).clear();
        getDriver().findElement(by).sendKeys(text);
    }

    public void fill(String idField, String text) {
        fill(By.id(idField), text);
    }

    public String getFieldValue(String idField) {
        return getDriver().findElement(By.id(idField)).getAttribute("value");
    }

    /********* Radio and Check ************/
    public void clickRadio(By by) {
        getDriver().findElement(by).click();
    }

    public void clickRadio(String id) {
        clickRadio(By.id(id));
    }

    public boolean isActiveRadio(String id) {
        return getDriver().findElement(By.id(id)).isSelected();
    }

    public void clickCheck(String id) {
        getDriver().findElement(By.id(id)).click();
    }

    public boolean isChecked(String id) {
        return getDriver().findElement(By.id(id)).isSelected();
    }

    /********* Combo ************/
    public void selectCombo(String id, String value) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.selectByVisibleText(value);
    }

    public void deselectCombo(String id, String value) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.deselectByVisibleText(value);
    }

    public String getValueCombo(String id) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    public List<String> getValuesCombo(String id) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> values = new ArrayList<String>();

        for (WebElement option: allSelectedOptions) {
            values.add(option.getText());
        }
        return values;
    }

    public int getQuantityOptionsCombo(String id) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    public boolean verifyOptionCombo(String id, String _option) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();

        for (WebElement option: options) {
            if (option.getText().equals(_option)) {
                return true;
            }
        }
        return false;
    }

    public void selectComboPrime(String radical, String value) {
        clickRadio(By.xpath("//*[@id='" + radical + "_input']/../..//span"));
        clickRadio(By.xpath("//*[@id='" + radical + "_items']//li[.='" + value + "']"));
    }

    /********* Button ************/
    public void clickButton(String id) {
        getDriver().findElement(By.id(id)).click();
    }

    public String getValueElement(String id) {
        return getDriver().findElement(By.id(id)).getAttribute("value");
    }

    /********* Link ************/
    public void clickLink(String link) {
        getDriver().findElement(By.linkText(link)).click();
    }

    /********* Textos ************/
    public String getText(By by) {
        return getDriver().findElement(by).getText();
    }

    public String getText(String id) {
        return getText(By.id(id));
    }

    /********* Alerts ************/
    public String getAlertText() {
        Alert alert = getDriver().switchTo().alert();
        return alert.getText();
    }

    public String getAlertTextAndAccept() {
        Alert alert = getDriver().switchTo().alert();
        String value = alert.getText();
        alert.accept();
        return value;
    }

    public String getAlertTextAndDismiss() {
        Alert alert = getDriver().switchTo().alert();
        String value = alert.getText();
        alert.dismiss();
        return value;
    }

    public void fillAlert(String value) {
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(value);
        alert.accept();
    }

    /********* Frames and Windows ************/
    public void toEnterFrame(String id) {
        getDriver().switchTo().frame(id);
    }

    public void exitFrame() {
        getDriver().switchTo().defaultContent();
    }

    public void switchWindow(String id) {
        getDriver().switchTo().window(id);
    }

    /************** JS *********************/
    public Object executeJs(String cmd, Object... param) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return js.executeScript(cmd, param);
    }

    /************** Table *********************/
    public void clickTableButton(String columnSearch, String value, String columnButton, String idTable) {
        //encontrar a linha do registro
        WebElement table = getDriver().findElement(By.xpath(idTable));
        int idColumn = getColumnIndex(columnSearch, table);

        //encontrar a linha do registro
        int idRow = getRowIndex(value, table, idColumn);

        //procurar coluna do botao
        int idColumnButton = getColumnIndex(columnButton, table);

        //clicar no botao da celula encontrada
        WebElement cell = table.findElement(By.xpath(".//tr["+idRow+"]/td["+idColumnButton+"]"));
        cell.findElement(By.xpath(".//input")).click();
    }

    protected int getRowIndex(String value, WebElement table, int idColumn) {
        List<WebElement> rows = table.findElements(By.xpath("./tbody/tr/td["+idColumn+"]"));
        int idRow = -1;
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i).getText().equals(value)) {
                idRow = i+1;
                break;
            }
        }
        return idRow;
    }

    protected int getColumnIndex(String column, WebElement table) {
        List<WebElement> columns = table.findElements(By.xpath(".//th"));
        int idColumn = -1;
        for(int i = 0; i < columns.size(); i++) {
            if(columns.get(i).getText().equals(column)) {
                idColumn = i+1;
                break;
            }
        }
        return idColumn;
    }
}