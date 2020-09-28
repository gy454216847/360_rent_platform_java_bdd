/*
 * Copyright (c) 2016-2017 Knife, Inc. and other contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.testng.Assert.assertTrue;


/**
 * knife.BrowserEmulator is based on Selenium3 and adds some enhancements
 *
 * @author bugmaster
 */
public class BrowserEmulator {

    public static WebDriver browser;

    int timeout = Integer.parseInt(GlobalSettings.timeout);

    public BrowserEmulator() throws knifeException {

        int browserType = GlobalSettings.browserType;

        if (browserType == 1) {
            browser = new FirefoxDriver();
        } else if (browserType == 2) {
            browser = new ChromeDriver();
        } else if (browserType == 3) {
            browser = new InternetExplorerDriver();
        } else if (browserType == 4) {
            browser = new EdgeDriver();
        } else if (browserType == 5) {
            browser = new OperaDriver();
        } else if (browserType == 6) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            browser = new ChromeDriver(chromeOptions);
        } else {

            throw new knifeException("Positioning syntax errors, lack of '=>'.");
        }

    }

    /**
     * Analyzing targeting elements, and positioning elements
     *
     * @param xpath the element's
     */
    public WebElement getElement(String xpath) throws knifeException {

        if (xpath.contains("=>") == false) {
            throw new knifeException("Positioning syntax errors, lack of '=>'.");
        }

        String by = xpath.split("=>")[0];
        String value = xpath.split("=>")[1];

        if (by.equals("id")) {
            WebElement element = browser.findElement(By.id(value));
            return element;
        } else if (by.equals("name")) {
            WebElement element = browser.findElement(By.name(value));
            return element;
        } else if (by.equals("class")) {
            WebElement element = browser.findElement(By.className(value));
            return element;
        } else if (by.equals("linkText")) {
            WebElement element = browser.findElement(By.linkText(value));
            return element;
        } else if (by.equals("xpath")) {
            WebElement element = browser.findElement(By.xpath(value));
            return element;
        } else if (by.equals("css")) {
            WebElement element = browser.findElement(By.cssSelector(value));
            return element;
        } else {
            throw new knifeException("Please enter the correct targeting elements,'id','name','class','xpath','css'.");
        }

    }

    /**
     * Wait for an element within a certain amount of time
     *
     * @param xpath the element's xpath the second
     */
    public void waitElement(String xpath, int second) throws knifeException {

        if (xpath.contains("=>") == false) {
            throw new knifeException("Positioning syntax errors, lack of '=>'.");
        }

        String by = xpath.split("=>")[0];
        String value = xpath.split("=>")[1];
        By findelement = null;

        if (by.equals("id")) {
            findelement = By.id(value);
        } else if (by.equals("name")) {
            findelement = By.name(value);
        } else if (by.equals("class")) {
            findelement = By.className(value);
        } else if (by.equals("linkText")) {
            findelement = By.linkText(value);
        } else if (by.equals("xpath")) {
            findelement = By.xpath(value);
        } else if (by.equals("css")) {
            findelement = By.cssSelector(value);
        } else {
            throw new knifeException("Please enter the correct targeting elements,'id','name','class','xpath','css'.");
        }
        new WebDriverWait(browser, second).until(ExpectedConditions
                .presenceOfElementLocated(findelement));

    }

    /**
     * Open the URL
     *
     * @param url
     */
    public void open(String url) {
        // pause(stepInterval);
        try {
            browser.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set browser window wide and high.
     *
     * @param wide
     * @param high
     */
    public void setWindow(int wide, int high) {

        browser.manage().window().setSize(new Dimension(wide, high));
    }

    /**
     * Setting browser window is maximized
     */
    public void maxWindow() {

        browser.manage().window().maximize();
    }

    /**
     * close the browser Simulates the user clicking the "close" button in the
     * title bar of a pop up
     */
    public void close() {
        browser.close();
    }

    /**
     * Quit the browser
     */
    public void quit() {
        browser.quit();
    }

    /**
     * Click the page element
     *
     * @param xpath the element's xpath
     */
    public void click(String xpath) throws knifeException {

        waitElement(xpath, timeout);
        WebElement element = getElement(xpath);
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Type text at the page element<br>
     * Before typing, try to clear existed text
     *
     * @param xpath , the element's xpath
     * @param text  , the input text
     */
    public void type(String xpath, String text) throws knifeException {

        waitElement(xpath, timeout);
        WebElement element = getElement(xpath);

        try {
            element.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Right click element.
     *
     * @param xpath the element's xpath
     */
    public void rightClick(String xpath) throws knifeException {
        waitElement(xpath, timeout);

        Actions action = new Actions(browser);
        WebElement element = getElement(xpath);

        action.contextClick(element).perform();
    }

    /**
     * click and hold element.
     *
     * @param xpath the element's xpath
     */
    public void clickAndHold(String xpath) throws knifeException {
        waitElement(xpath, timeout);

        Actions action = new Actions(browser);
        WebElement element = getElement(xpath);

        action.clickAndHold(element).perform();
    }

    /**
     * Drags an element a certain distance and then drops it.
     *
     * @param el_xpath , the element's xpath
     * @param ta_xpath , the element's xpath
     */
    public void dragAndDrop(String el_xpath, String ta_xpath) throws knifeException {
        waitElement(el_xpath, timeout);
        waitElement(ta_xpath, timeout);

        Actions action = new Actions(browser);
        WebElement el = getElement(el_xpath);
        WebElement ta = getElement(ta_xpath);

        action.dragAndDrop(el, ta).perform();
    }

    /**
     * Click the element by the link text.
     *
     * @param text , the element's link text
     */
    public void clickText(String text) {

        WebElement element = browser.findElement(By.partialLinkText(text));
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Select the select tag value
     *
     * @param xpath
     * @param value
     */
    public void selectValue(String xpath, String value) throws knifeException {

        waitElement(xpath, timeout);

        WebElement element = getElement(xpath);
        Select sel = new Select(element);
        sel.selectByValue(value);
    }

    /**
     * Refresh the browser
     */
    public void refresh() {
        browser.navigate().refresh();
    }

    /**
     * Execute JavaScript scripts.
     */
    public void js(String js) {
        ((JavascriptExecutor) browser).executeScript(js);
    }

    /**
     * Enter the iframe
     *
     * @param xpath , the iframe's xpath
     */
    public void enterFrame(String xpath) throws knifeException {
        waitElement(xpath, timeout);
        WebElement element = getElement(xpath);
        browser.switchTo().frame(element);
    }

    /**
     * Leave the iframe
     */
    public void leaveFrame() {
        browser.switchTo().defaultContent();
    }

    /**
     * Open the new window and switch the handle to the newly opened window.
     *
     * @param xpath , the open windows element xpath
     */
    public void openOneWindow(String xpath) throws knifeException {

        waitElement(xpath, timeout);

        String search_handle = browser.getWindowHandle();
        WebElement element = getElement(xpath);
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<String> handles = browser.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(search_handle)) {
                browser.switchTo().window(handle);
            }
        }

    }

    /**
     * Return text from specified web element.
     *
     * @param xpath
     * @return
     */
    public String getText(String xpath) throws knifeException {
        WebElement element = getElement(xpath);
        return element.getText();
    }

    /**
     * Returns the title of the current page
     *
     * @return
     */
    public String getTitle() {
        return browser.getTitle();
    }

    /**
     * Returns the URL of the current page
     *
     * @return
     */
    public String getUrl() {
        return browser.getCurrentUrl();
    }

    /**
     * Gets the value of an element attribute.
     *
     * @return
     */
    public String getAttribute(String xpath, String attribute) throws knifeException {
        WebElement element = getElement(xpath);
        String value = element.getAttribute(attribute);
        return value;
    }

    /**
     * Accept warning box.
     */
    public void acceptAlert() {
        browser.switchTo().alert().accept();
    }

    /**
     * Dismisses the alert available.
     */
    public void dismissAlert() {
        browser.switchTo().alert().dismiss();
    }

    /**
     * Check whether element is displayed.
     */
    public void isElementDisplayed(String xpath) throws knifeException {

        WebElement element = getElement(xpath);
        String name = element.getText();
        if (element.isDisplayed()) {
            System.out.println(name + "--" + "元素存在");
        } else {
            System.out.println(name + "--" + "元素不存在");
            assertTrue(element.isDisplayed());

        }

    }

    public  void sendEmail() throws Exception {
        Yaml yaml = new Yaml();
        File yamlFile = new File("src/main/java/config/email.yaml");
        Map<String, String> data = (Map<String, String>) yaml.load(new FileInputStream(yamlFile));
        String receives = data.get("receives");
        String sender = data.get("sender");
        String smtpserver = data.get("smtpserver");
        final String user = data.get("user");
        final String password = data.get("password");
        String subject = data.get("subject");
        String content = data.get("content");


        Properties properties = new Properties();

        // 设置邮件服务器


        properties.put("mail.smtp.host", smtpserver);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "25");


        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 第二个参数，就是我QQ开启smtp的授权码
                return new PasswordAuthentication(user, password);
            }
        });
        session.setDebug(true);

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(sender,"Garry","UTF-8"));

            // Set To: 头部头字段
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(receives));

            // Set Subject: 头部头字段
            message.setSubject(subject,"UTF-8");

            // 创建消息部分
            BodyPart messageBodyPart1 = new MimeBodyPart();

            // 消息
            messageBodyPart1.setText(content);

            // 创建另外一个MimeBodyPart对象，以便添加其他内容
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // 设置邮件中附件文件的路径
            String filename = "target/cucumber/report.html";

            // 创建一个datasource对象，并传递文件
            DataSource source = new FileDataSource(filename);
            // 设置handler
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // 加载文件
            messageBodyPart2.setFileName(filename);
            // 创建一个MimeMultipart类的实例对象
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            // 发送完整消息
            message.setContent(multipart );

            // 发送消息
            Transport.send(message);
            System.out.println("Sent email successfully");
        }catch (MessagingException e) {
            throw new RuntimeException(e);

        }



    }

    }













