package org.cec.nand2tetris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Parser {

	public  enum CommandType{
		A_COMMAND,
		C_COMMAND,
		L_COMMAND;
	}

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

		LOGGER.debug("Beginning assembly....");

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
		curCmd = cleanCmd(curCmd);
		curCmdType = typeCmd(curCmd);
		curCmdSymbol = symbolCmd(curCmd);
		curCmdDest = destCmd(curCmd);
		curCmdComp = compCmd(curCmd);
		curCmdJump = jumpCmd(curCmd);

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

	private String cleanCmd(String cmd){

		String result = null;

		result = cmd.replace(" ", "");

		if (result.indexOf("//") > -1) {
			result = result.substring(0, result.indexOf("//"));
		}

		if (result.length() == 0) {
			result = null;
		}

		return result;

	}

	private CommandType typeCmd(String cmd){

		CommandType result = null;

		if(cmd != null){

			if(cmd.startsWith("@")){
				result =  CommandType.A_COMMAND;
			} else if(curCmd.startsWith("(")){
				result =  CommandType.L_COMMAND;
			} else {
				result =  CommandType.C_COMMAND;
			}

		}

		return result;

	}


	private String symbolCmd(String cmd){

		String result = cmd;

		if(CommandType.A_COMMAND.equals(curCmdType)){
			result = result.substring(1);
		} else if(CommandType.L_COMMAND.equals(curCmdType)){
			result = result.replace("(", "");
			result = result.replace(")", "");
		} else {
			result = null;
		}

		return result;

	}


	private String destCmd(String cmd){

		String result = cmd;

		if(CommandType.C_COMMAND.equals(curCmdType)){

			if(cmd.indexOf("=") > 0){
				result = cmd.substring(0,  cmd.indexOf("="));
			} else {
				result = null;
			}

		} else {
			return null;
		}

		return result;

	}

	private String compCmd(String cmd){

		String result = cmd;

		if(CommandType.C_COMMAND.equals(curCmdType)){

			if(cmd.indexOf(";") > 0){
				result = cmd.substring(cmd.indexOf("=") + 1,  cmd.indexOf(";"));
			} else {
				result = cmd.substring(cmd.indexOf("=") + 1);
			}

		} else {
			return null;
		}

		return result;
	}

	private String jumpCmd(String cmd){

		String result = cmd;

		if(CommandType.C_COMMAND.equals(curCmdType)){

			if(cmd.indexOf(";") > 0){
				result = cmd.substring(cmd.indexOf(";") + 1);
			} else {
				result = null;
			}

		} else {
			return null;
		}

		return result;

	}

}