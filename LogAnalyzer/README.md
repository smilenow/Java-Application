#Assignment 004: Log Analyzer

===

Web servers typically maintain log files of client accesses to the web pages that they store. Given suitable tools, these logs enable web service managers to extract and analyze useful information such as:

*	which are the most popular pages they provide;
*	which ip took the most pages from the site;
*	whether other sites appear to have broken links to this site’s pages;
*	how much data is being delivered to clients;
*	the busiest periods over the course of a day, or week, or month.


Such information might help managers to determine, for instance, whether they need to upgrade to more powerful server machines, or when the quietest periods are in order to schedule maintenance activities.

The log analyzer project contains an application that performs an analysis of data from such a web server. The server writes a log line to a file each time an access is made. A sample log file called access.log is provided in the Resource division.

===
