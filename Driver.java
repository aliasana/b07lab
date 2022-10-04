 public class Driver { 
 public static void main(String [] args) { 
  Polynomial p = new Polynomial(); 
  System.out.println(p.evaluate(3)); 
  double [] c1 = {6,0,0,5}; 
  Polynomial p1 = new Polynomial(c1); 
  double [] c2 = {0,-2,0,0,-9}; 
  Polynomial p2 = new Polynomial(c2); 
  Polynomial s = p1.add(p2); 
  System.out.println("s(0.1) = " + s.evaluate(0.1)); 
  if(s.hasRoot(1)) 
   System.out.println("1 is a root of s"); 
  else 
   System.out.println("1 is not a root of s"); 
 } 
} 


/* public class Driver {

	public static void main(String[] args) {
		double c1[]= {4,2,3}, c2[] = {1,-5};
		int e1[]= {2,1,0}, e2[] = {-1,0};
	
		double c3[] = {6,2,3,5};
		int e3[] = {3,2,1,4};
		
		double c4[] = {1,2,3};
		int e4[] = {5,3,1};
		
		Polynomial p1=new Polynomial(c1, e1);
		Polynomial p2=new Polynomial(c2, e2);
		
		
		Polynomial p3=p1.add(p2);
		
		Polynomial p4=new Polynomial(c3, e3);
		Polynomial p5=new Polynomial(c4, e4);
		
		Polynomial p6=p4.add(p5);
		
		//System.out.println(p3.evaluate(0));
		for(int i=0; i<p6.coefficients.length; i++) {
			
			System.out.print(p6.coefficients[i] + " ");		
		}
		
		System.out.println();
		
		for(int i=0; i<p6.coefficients.length; i++) {
			
			System.out.print(p6.exponents[i] + " ");			
		}
		
		
		
	}

} */
