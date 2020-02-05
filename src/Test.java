package sdaTracking;

import java.util.Scanner;

public class Test {
	
	public static void main(String args[]) throws Exception {
		Scanner in = new Scanner(System.in);
		String code = in.next();
		SdaTracking track = new SdaTracking(code);
		System.out.println(track.getCode()+"\n"+track.getStatus()+"\n"+track.getFirma());
		in.close();
	}
}
