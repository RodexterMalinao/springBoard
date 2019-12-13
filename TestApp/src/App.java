
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		methodA("Roy", "Malinao");
		methodA("", 0);
		methodA(0, "");
	}
	
	public static void methodA(String a, String b){
		System.out.println("1");
	}
	
	public static void methodA(String a, int b){
		System.out.println("2");
	}
	
	public static void methodA(int a, String b){
		System.out.println("3");
	}
}
