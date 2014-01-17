*******************************************
Title:  Prime Number Generator

Author: [Matthew Boyette](mailto:Dyndrilliac@gmail.com)

Date:   2/21/2012
*******************************************

This code makes use of my [Custom Java API](https://github.com/Dyndrilliac/java-custom-api). In order to build this source, you should clone the repository for the API using your Git client, then import the project into your IDE of choice (I prefer Eclipse), and finally modify the build path to include the API project. For more detailed instructions, see the README for the API project.
	
This application generates the first 'n' prime numbers and displays them to the user. The method used to test for primality is fairly efficient but will naturally pale in comparison to industry standard libraries optimized for this task. This is merely proof of concept code. It will also approximate the amount of time in seconds that it took to generate the list.