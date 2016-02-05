package src.controller;

public class RowGenerator {
	public static void main(String[] args){
		String str = "";
		int X = 50;
		int Y = 50;
		int NUM_STATES = 3;
		for(int x=0; x<X; x++){
			str+="<cells row = \"";
			for(int y=0; y<Y; y++){
				str+=Math.round(Math.random()*(NUM_STATES-1));
				if(y!=(Y-1))
					str+=" ";
			}
			str+="\">\n";
		}
		System.out.println(str);
	}
}
