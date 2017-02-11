#Code Smells
1. There is duplicated code that is contained both in the fireModel file and the lifeModel file. There are parts of a for loop which can be refactored and hopefully put into the Model super class which would lead to better design and less duplicated code.
2. Additionally, in the watorModel class there is duplicated code for finding the different neighbors. There are two different methods which basically look for two different types of neighbors but use the exact same code on the inside of if statements. So these two methods can potentially be combined by using a boolean value to set which neighbors the code is looking for.   


#Checklist Refactoring
1. There are a lot of magic values that can be refactored and made more readable and proper by the use of public final variables. For example, in the game of life files, the code uses the numbers 2 and 3 when testing for the number of neighbors of a cell but these can be reduced into named constants. 
2. Another checklist refactoring goal was that there was a lot of static instance variables in the XMLGenerator class. Static variables by definition are not great to have under object oriented so we worked to remove them and make the overall class more object oriented. The static instance variables were successfully removed. 