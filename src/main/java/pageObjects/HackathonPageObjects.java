package pageObjects;

import org.openqa.selenium.By;

public class HackathonPageObjects {
public static  By videoTab=By.xpath("//div[text()=\"Videos\"]");
public static  By searchBox=By.name("search_query");
public static  By channelLink=By.partialLinkText("STeP-IN Forum");
public static  By videoTitles=By.id("video-title-link");
public static  By settingsbtn=By.xpath("//button[@title='Settings']");
public static  By qualityMenu=By.xpath("//div[contains(text(), 'Quality')]");
public static  By quality360p=By.xpath("//span[text()='360p']");
public static  By upComingVideoTitles=By.id("video-title");
public static By menuList(String menuLowerCase) {
	return By.xpath("//div[@class='menu_popover menu_" + menuLowerCase + " hover']//span[@class='sub sub_reports1']");
}
}
