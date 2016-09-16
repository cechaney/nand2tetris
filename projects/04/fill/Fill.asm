// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

// Put your code here.

//Initialize the current position pointer
@8128
D=A
@CURRENT_POS
M=D


//Initilize MAX screen position
@8192
D=A
@MAX_POS
M=D


//Keypress detect loop
(EVENTLOOP)
@KBD
D=M
@BLACK
D;JGT
@WHITE
D;JEQ
@EVENTLOOP
0;JEQ

(BLACK)
@ARG
M=-1
@DRAW
0;JEQ

(WHITE)
@ARG
M=0
@DRAW
0;JEQ

(DRAW)

//Calculate the address to color
@SCREEN
D=A
@CURRENT_POS
A=D + M
D=A
@ADDRESS
M=D

//Set the color using ARG register value
@ARG
D=M
@ADDRESS
A=M
M=D

//Increment CURRENT_POS
@CURRENT_POS
M=M + 1

@MAX_POS
D=M
@CURRENT_POS
D=D - M
@RESET
D;JEQ

//Jump back to event loop
@EVENTLOOP
0;JEQ

(RESET)
@CURRENT_POS
M=0
@EVENTLOOP
0;JEQ

(END)
