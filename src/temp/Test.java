package temp;

public class Test {

	public static void main(String[] args) {
		int max = 1073741823 ;
		int min = -1073741824 ;
		
		System.out.println("int range is -2,147,483,648 ~ 2,147,483,647") ;
		System.out.println("overflow : " + (int)((max + 1) * 2)) ;
		System.out.println("right range : " + max * 2) ;
		System.out.println("right range : " + min * 2) ;
		System.out.println("underflow : " + (min - 1) * 2) ;
	}

}
