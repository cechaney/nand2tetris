package org.cec.nand2tetris;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Assembler {

	private static Logger LOGGER = Logger.getLogger(Assembler.class);

	public static void main(String args[]) {

		String inputFilename = args[0];
		String outputFilename = inputFilename.substring(0, inputFilename.lastIndexOf(".asm")) + ".hack";

		LOGGER.debug("Starting assembler...");

		LOGGER.debug("inputFilename " + inputFilename);
		LOGGER.debug("outputFilename: " + outputFilename);

		LOGGER.debug("Beginning assembly....");

		Parser parser = null;
		BufferedWriter outputFile = null;

		try{

			parser = new Parser(inputFilename);

			if(!parser.hasMoreCommands()){
				LOGGER.debug("Input file is empty.");
				return;
			}

			outputFile = new BufferedWriter(new FileWriter(outputFilename));

			while(parser.hasMoreCommands()){

				parser.advance();

				if(parser.currentCmd() != null){

					String line = null;

					LOGGER.debug("==============================");
					LOGGER.debug("Command Type:" + parser.commandType());
					LOGGER.debug("Command Symbol:" + parser.symbol());
					LOGGER.debug("Command Destination:" + parser.dest());
					LOGGER.debug("Command Comparison:" + parser.comp());
					LOGGER.debug("Command Jump:" + parser.jump());

					if(Parser.CommandType.A_COMMAND.equals(parser.commandType())){

						Integer value = Integer.valueOf(parser.symbol());

						line = String.format("%16s", Integer.toBinaryString(value)).replace(' ', '0');

						LOGGER.debug("A Command: " + line);

					} else if(Parser.CommandType.C_COMMAND.equals(parser.commandType())){

						line = "111" +
								Code.comp(parser.comp()) +
								Code.dest(parser.dest()) +
								Code.jump(parser.jump());

						LOGGER.debug("C Command: " + line);

					}

					outputFile.write(line);
					outputFile.newLine();

				}

			}

			outputFile.close();

		} catch (FileNotFoundException e) {
			LOGGER.error("Unable to open .asm file", e);
		} catch (IOException e) {
			LOGGER.error("IO error during assembly", e);
		} finally{
			if(outputFile != null){
				try{
					outputFile.close();
				} catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
		}

		LOGGER.debug("Finished assembly");
	}


}
