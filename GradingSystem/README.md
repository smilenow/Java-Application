#Assignment 003: Grading System

===

Write a program that maintains a "database" of names and scores of students.

A student has a name consists of several words and records of scores of varies courses. Each record has a name of the course and the marks the student gets.

User can input or import information and scores for students and retrieve them later. To input a score, the user may key in the name of the student, the name of the course and the marks. Your program then try to find the student in the database and add or replace the score for the course. If the student can not be found in the database, a new student should be created and be put into the database. If the course can not be found in the records of the student, a new record for the course should be created, otherwise, the score should be replaced by the newly inputed one.

The user can provide name of a .CSV file which contains records of students information to import the data into the database. The columns in the .CSV files are:

	name, course name, score

User can input the name of a student to retrieve the scores of all the courses he/she gets, along with the total and average marks. User can input name of a course to retrieve a list of every student who has marks for this course, along with the number of the students and the average marks.
Your program is able to store the database into a file and read them back at the beginning of the execution.

The whole system is a Unix style shell system, which means you are going to develop one program for one function of your system, not a concrete one big program, no menus, no GUIs.

===
