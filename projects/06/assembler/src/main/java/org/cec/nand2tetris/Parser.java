package org.cec.nand2tetris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Parser {

	private static final Logger LOGGER = Logger.getLogger(Parser.class);

	private BufferedReader br;
	private String curCmd;
	private CommandType curCmdType;
	private String curCmdSymbol;
	private String curCmdDest;
	private String curCmdComp;
	private String curCmdJump;

	public Parser(String inputFilename) throws FileNotFoundException, IOException {

		String outputFilename = inputFilename.substring(0, inputFilename.lastIndexOf(".asm")) + ".hack";

		LOGGER.debug("Starting assembler...");
		LOGGER.debug("inputFilename " + inputFilename);
		LOGGER.debug("outputFilename: " + outputFilename);

		LOGGER.debug("Beginning parse....");

		this.br = new BufferedReader(new FileReader(inputFilename));

	}

	public void shutdown() throws IOException {

		if (br != null) {
			br.close();
		}
	}

	public boolean hasMoreCommands() throws IOException{

		boolean has = false;

		br.mark(1);

		if(br.read() != -1){
			has =  true;
		} else {
			has = false;
		}

		br.reset();

		return has;

	}

	public void advance() throws IOException{

		curCmd = br.readLine();
		curCmd = CmdUtil.cleanCmd(curCmd);
		curCmdType = CmdUtil.typeCmd(curCmd);
		curCmdSymbol = CmdUtil.symbolCmd(curCmd);
		curCmdDest = CmdUtil.destCmd(curCmd);
		curCmdComp = CmdUtil.compCmd(curCmd);
		curCmdJump = CmdUtil.jumpCmd(curCmd);

	}

	public String currentCmd(){
		return curCmd;
	}

	public CommandType commandType(){
		return curCmdType;
	}

	public String symbol(){
		return curCmdSymbol;
	}

	public String dest(){
		return curCmdDest;
	}

	public String comp(){
		return curCmdComp;
	}

	public String jump(){
		return curCmdJump;
	}


}