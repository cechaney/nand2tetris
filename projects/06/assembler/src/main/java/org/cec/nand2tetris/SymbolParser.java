package org.cec.nand2tetris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class SymbolParser {

	private static final Logger LOGGER = Logger.getLogger(Parser.class);

	public static SymbolTable parse(String inputFilename){

		BufferedReader br = null;
		String curCmd = null;
		CommandType curCmdType = null;
		String curCmdSymbol = null;
		int instrNumber = 0;
		int memoryLoc = 16;

		SymbolTable symbolTable = new SymbolTable();

		LOGGER.debug("Starting symbol parser...");
		LOGGER.debug("inputFilename " + inputFilename);

		LOGGER.debug("Beginning parse....");

		try {

			br = new BufferedReader(new FileReader(inputFilename));

			String line = null;

			while((line = br.readLine()) != null){

				curCmd = CmdUtil.cleanCmd(line);
				curCmdType = CmdUtil.typeCmd(curCmd);
				curCmdSymbol = CmdUtil.symbolCmd(curCmd);

				if(CommandType.L_COMMAND.equals(curCmdType)){

					symbolTable.addEntry(curCmdSymbol,instrNumber);

				} else if(curCmdType != null){

					instrNumber++;
				}

			}

			try{
				if(br != null){
					br.close();
				}
			} catch(IOException ioe){
				ioe.printStackTrace();
			}

			instrNumber = 0;

			br = new BufferedReader(new FileReader(inputFilename));

			while((line = br.readLine()) != null){

				curCmd = CmdUtil.cleanCmd(line);
				curCmdType = CmdUtil.typeCmd(curCmd);
				curCmdSymbol = CmdUtil.symbolCmd(curCmd);

				if(CommandType.A_COMMAND.equals(curCmdType)){

					if(!Character.isDigit(curCmdSymbol.charAt(0))){

						if(!symbolTable.contains(curCmdSymbol)){
							symbolTable.addEntry(curCmdSymbol,memoryLoc);
							memoryLoc++;
						}

					}

					instrNumber++;

				} else if(CommandType.C_COMMAND.equals(curCmdType)){
					instrNumber++;
				}

			}

			try{
				if(br != null){
					br.close();
				}
			} catch(IOException ioe){
				ioe.printStackTrace();
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

			return null;

		} catch(IOException ioe){

			ioe.printStackTrace();

			return null;

		} finally{

			try{
				if(br != null){
					br.close();
				}
			} catch(IOException ioe){
				ioe.printStackTrace();
			}
		}

		return symbolTable;

	}


}