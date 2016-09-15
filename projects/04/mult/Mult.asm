// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

//Loop through R1 - 1 
//Add R1 to R0 each iteration

//Initialize the total
@total
M=0

//Set up the iterator variable
//The loop will be counting down from R1 to 0
@R1
D=M
@i
M=D

(LOOP)
//Check the look condition
@i
D=M
@END
D;JEQ

//Add R0 to the total
@R0
D=M
@total
M=M+D

//Decrement i by 1
@i
M=M-1

//Jump back up to the top
@LOOP
0;JMP 

(END)

@total
D=M
@R2
M=D