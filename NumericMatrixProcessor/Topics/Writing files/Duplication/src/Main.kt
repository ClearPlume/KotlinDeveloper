// Write your code here. Do not import any libraries
val myFile = File("MyFile.txt")
val str = readLine()!!
myFile.writeText(str.repeat(2))