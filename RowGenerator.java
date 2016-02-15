public class RowGenerator {
	public static void main(String[] args){
		String str = "";
		int numCells = 1200;
		int NUM_STATES = 3;
		str+="<cells list = \"";
		for(int x=0; x<numCells; x++){
			
			str+=Math.round(Math.random()*(NUM_STATES-1));
			if(x!=(numCells-1))
				str+=" ";
			
		}
		str+="\"> </cells>\n";
		System.out.println(str);
	}
}