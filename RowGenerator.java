public class RowGenerator {
	public static void main(String[] args){
		String str = "";
		int X = 25;
		int Y = 25;
		int NUM_STATES = 3;
		for(int x=0; x<X; x++){
			str+="<cells row = \"";
			for(int y=0; y<Y; y++){
				str+=Math.round(Math.random()*(NUM_STATES-1));
				if(y!=(Y-1))
					str+=" ";
			}
			str+="\"> </cells>\n";
		}
		System.out.println(str);
	}
}