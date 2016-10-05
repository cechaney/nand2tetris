package org.cec.nand2tetris;


public class CmdUtil {

	public static String cleanCmd(String cmd){

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

	public static CommandType typeCmd(String cmd){

		CommandType result = null;

		if(cmd != null){

			if(cmd.startsWith("@")){
				result =  CommandType.A_COMMAND;
			} else if(cmd.startsWith("(")){
				result =  CommandType.L_COMMAND;
			} else {
				result =  CommandType.C_COMMAND;
			}

		}

		return result;

	}


	public static String symbolCmd(String cmd){

		String result = cmd;

		CommandType cmdType = typeCmd(cmd);

		if(CommandType.A_COMMAND.equals(cmdType)){
			result = result.substring(1);
		} else if(CommandType.L_COMMAND.equals(cmdType)){
			result = result.replace("(", "");
			result = result.replace(")", "");
		} else {
			result = null;
		}

		return result;

	}


	public static String destCmd(String cmd){

		String result = cmd;

		CommandType cmdType = typeCmd(cmd);

		if(CommandType.C_COMMAND.equals(cmdType)){

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

	public static String compCmd(String cmd){

		String result = cmd;

		CommandType cmdType = typeCmd(cmd);

		if(CommandType.C_COMMAND.equals(cmdType)){

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

	public static String jumpCmd(String cmd){

		String result = cmd;

		CommandType cmdType = typeCmd(cmd);

		if(CommandType.C_COMMAND.equals(cmdType)){

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
