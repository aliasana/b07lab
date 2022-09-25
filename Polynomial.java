public class Polynomial {
  double[] array;

  public Polynomial ( double [] coeffs ) {
    array = coeffs.clone();
  }

  public Polynomial () {
   array = new double [] {0};
  }

  public Polynomial add (Polynomial exp) {
    int longest = Math.max(array.length, exp.array.length); //length of longest array
    int shortest = Math.min(array.length, exp.array.length); //length of shortest array

    double[] coeffs = new double [longest]; //create an array to hold all coefficients after addition

    int i;
    for (i = 0; i < shortest; i++) {
      coeffs[i] = array[i] + exp.array[i];
    }

    if(array.length <= exp.array.length) {
    	//exp is longer or equal
        for (; i<longest; i++) {
        	coeffs[i] = exp.array[i];
        }
    }
    else {
    	//array is longer
      for (; i<longest; i++) {
        coeffs[i] = array[i];
      }
    }

    return new Polynomial(coeffs);
  }

  public double evaluate (double x) {
    double result = 0;
    for (int i = 0; i < array.length; i++) {
      result = result + (array[i] * Math.pow(x, i));
    }
    return result;
  }

  public boolean hasRoot (double x) {
    return evaluate(x) == 0;
  }
}
	