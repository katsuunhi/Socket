class Letter{
	public void delay(){
		long n = 500000;
		for(int i = 0; i < n; i ++){
			System.out.print("");
		}
	}

	public void display(String letters){
		System.out.print(letters + " ");
	}
}

class EnglishWord extends Letter implements Runnable{
	public void run(){
		display("Java ");
		delay();
		display("SE ");
	}
}

class ChineseWord extends Letter implements Runnable{
	public void run(){
		display("bian cheng");
		delay();
		display("yi shu");
	}
}