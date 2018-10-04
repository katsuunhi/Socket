public class RunnableBasicApp{
	public static void main(String [] arge){
		Thread letter = new Thread(new EnglishWord());
		Thread word = new Thread(new ChineseWord());
		letter.start();
		word.start();
	}
}