class English extends Thread{
	public void run(){
		System.out.print("Java");
		Time.delay();
		System.out.print("SE");
	}
}

class Chinese extends Thread{
	public void run(){
		System.out.print("编程");
		Time.delay();
		System.out.print("艺术");
	}
}


