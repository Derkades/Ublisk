package com.robinmc.ublisk.utils;

public class NumberUtils {
	
	public static String getOrdinalNumber(int i){
		String n = "" + i;
		String on = "error";
		String prefix = "error";
		int last = Integer.parseInt(n.substring(n.length() - 1));
		int first = Integer.parseInt(n.substring(0, 1));
		
		if (n.length() == 1){
			return stuff(i);
		}
		
		if (i == 10){
			return "tenth";
		} else if (i == 11){
			return "eleventh";
		} else if (i == 12){
			return "twelfth";
		} else if (i == 13){
			return "thirteenth";
		} else if (i == 14){
			return "fourteenth";
		} else if (i == 15){
			return "fifteenth";
		} else if (i == 16){
			return "sixteenth";
		} else if (i == 17){
			return "seventeenth";
		} else if (i == 18){
			return "eighteenth";
		} else if (i == 19){
			return "nineteenth";
		} else if (i == 20){
			return "twentieth";
		}
		
		if (first == 2){
			prefix = "twenty";
		} else if (first == 3){
			prefix = "thirty";
		} else if (first == 4){
			prefix = "fourty";
		} else if (first == 5){
			prefix = "fifty";
		} else if (first == 6){
			prefix = "sixty";
		} else if (first == 7){
			prefix = "seventy";
		} else if (first == 8){
			prefix = "eighty";
		} else if (first == 9){
			prefix = "ninety";
		}
		
		for (int x = 1; x <= 9; x++) {
		    if (x == last){
		    	on = prefix + "-" + stuff(last);
		    }
		}
		
		return on;
	}
	
	public static String stuff(int i){	
		String on = "error";
		
		if (i == 1){
			on = "first";
		} else if (i == 2){
			on = "second";
		} else if (i == 3){
			on = "third";
		} else if (i == 4){
			on = "fourth";
		} else if (i == 5){
			on = "fifth";
		} else if (i == 6){
			on = "sixth";
		} else if (i == 7){
			on = "seventh";
		} else if (i == 8){
			on = "eigth";
		} else if (i == 9){
			on = "ninth";
		}
		
		return on;
	}

}