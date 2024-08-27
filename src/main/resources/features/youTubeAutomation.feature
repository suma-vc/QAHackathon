#Author: babu.ar@amadeus.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
@youtubeAutomation 
Feature: Youtube Automation 

@youtube 
Scenario Outline: Youtube Automation for the QA Hackathon 
	Given User launches the browser "<browser>" 
	When user navigates to the website "https://www.youtube.com" 
	And Search for "step-in forum"
	And Open "step-in forum" channel 
	And Navigate to "Videos" tab 
	And Make API call to get video name 
	And Locate the video by scrolling 
	Then Capture the screenshot 
	And  Validate the file upload Click on the video 
	And Change the video quality to "360p" 
	When Get and list names of all upcoming videos 
	And Write the data to JSON file 
	Then Post/upload file to server 
	And Validate the file upload 
	
	
	Examples: 
		| browser     | videoName |
		| Chrome | Panel Discussion - Role of Software in Mobility |
		
		
