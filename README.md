# FrontEndDataExtraction

The tool is basically used to filter out search results based on certain keywords. It has been written in java following the Page Object Model in Selenium. 
The tool carries out the following functions:-
1) Reads login credentials from a file and passes it to the respective pages.
2) Abstracts the "wait" and "find" functions in Selenium by assimilating those functions in Browser.java
3) Logs into the account.
4) Enters the search parameters.
5) Handles a pop-up block.
6) Naviagtes through 30 pages for filtering.
7) Writes the results into an HTML file.
