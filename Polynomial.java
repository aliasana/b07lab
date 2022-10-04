import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Polynomial {

    //Attributes
    double[] coefficients;
    int[] exponents;

    public Polynomial (String contents ) {

        create_polynomial(contents);

     }

     public Polynomial (File file) {
       try {
        Scanner myReader = new Scanner(file);
          String contents = myReader.nextLine();
          create_polynomial(contents);
          myReader.close();
      } catch (FileNotFoundException e) {
        //System.out.println("An error occurred.");
        e.printStackTrace();
      }
     }

     public Polynomial () {
       String contents = "0";
       create_polynomial(contents);
     }

     private void create_polynomial(String contents) {
       String reg = "((?<=[\\+|\\-])|(?=[\\+|\\-]))";
       String[] expressions = contents.split(reg);
       String terms[] = get_terms(expressions);

       //if(true) return; //Placeholder exit
       for (int i = 0; i < terms.length ; i++ ) {
           double coefficient;
           int exponent;

           //find the location of x
               //if none found, exponent = 0, get the coefficient
               //if found, get int for exponent from the string indexed after the x
                   //get double for coefficient from string indexed before the x
           int xLocation = terms[i].indexOf('x');

           if (xLocation < 0) {
               // There is no exponent on x
               coefficient = Double.parseDouble(terms[i]);
               exponent = 0;
           }
           else {
               coefficient = Double.parseDouble(terms[i].substring(0,xLocation));
               if (xLocation == (terms[i].length() - 1)) {
                 //There is only a base, without exponent e.g. 5x
                 exponent = 1;
               }
               else {
                 exponent = Integer.parseInt(terms[i].substring(xLocation+1));
               }
           }

       //Add stuff to arrays
           exponents = add_to_exponents(exponent);
           coefficients = add_to_coefficients(coefficient);
       }
     }

    private String[] get_terms (String[] expressions) {
        // ["-" "5" "+" "6x2"] => ["-5" "6x2"]
        boolean isNegative = false; //flag set to 1 if the term is negative
        String[] terms = null;
        for (int i = 0; i < expressions.length; i++) {
            if(expressions[i].equals("-")) {
                //term is negative
                isNegative = true;
                i++; //move to expression
            }
            else if(expressions[i].equals("+")) {
                //term is positive
                isNegative = false;
                i++; //move to expression
            }
            //System.out.println("Expression, should not be +-: " + expressions[i]);
            if(isNegative)
                terms = add_to_terms(terms, "-" + expressions[i]);
            else
                terms = add_to_terms(terms, expressions[i]);
        }
        return terms;
    }

    private String[] add_to_terms(String[] terms, String newExpression) {
        //System.out.println("Expression: " + newExpression);
        int length;
        if(terms != null)
            length = terms.length;
        else
            length = 0;
        String[] newArray = new String [length + 1];

        for (int i = 0; i < length; i++ ) {
            newArray[i] = terms[i];
        }
        newArray[length] = newExpression;
        return newArray;
    }

    private int[] add_to_exponents(int newExponent) {
        int length;
        if(exponents != null)
            length = exponents.length;
        else
            length = 0;
        int[] newArray = new int [length + 1];

        for (int i = 0; i < length; i++ ) {
            newArray[i] = exponents[i];
        }
        newArray[length] = newExponent;
        return newArray;
    }

    private double[] add_to_coefficients(double newCoefficient) {
        int length;
        if(coefficients != null)
            length = coefficients.length;
        else
            length = 0;
        double[] newArray = new double [length + 1];

        for (int i = 0; i < length; i++ ) {
            newArray[i] = coefficients[i];
        }
        newArray[length] = newCoefficient;
        return newArray;
    }

    //private void print_polynomial(Polynomial polynomial) {
    //  System.out.println("=====Printing=====");
    //  for (int i = 0; i < polynomial.exponents.length; i++) {
    //   System.out.println("CoEff: " + polynomial.coefficients[i] + " " + "Exp: " + polynomial.exponents[i]);
    //  }
    //  System.out.println("====End==========");
    //}

    public Polynomial add (Polynomial expression) {
      double[] poly1 = convert_to_single_array(expression);
      double[] poly2 = convert_to_single_array(this);
      return _add(poly1, poly2);
    }

    private double[] convert_to_single_array(Polynomial polynomial) {
      int exponentIndex = polynomial.exponents.length;



      if(exponentIndex == 0 && polynomial.coefficients[0] == 0.0) {
        //empty polynomial with only a zero
        return new double[1]; //return an array with 0
      }

      int length = polynomial.exponents[exponentIndex-1] + 1; //returns the highest value exponent

      double [] returnArray = new double [length]; //declare an array indexed to highest exponent

      for (int i = 0; i < polynomial.exponents.length; i++) {
        int index = polynomial.exponents[i];
        returnArray[index] = polynomial.coefficients[i];
      }

      return returnArray;
    }

    private Polynomial _add (double[] poly1, double[]  poly2) {
      int longest = Math.max(poly1.length, poly2.length); //length of longest array
      int shortest = Math.min(poly1.length, poly2.length); //length of shortest array

      double[] coeffs = new double [longest]; //create an array to hold all coefficients after addition

      int i;
      for (i = 0; i < shortest; i++) {
        coeffs[i] = poly1[i] + poly2[i];
      }

      if(poly1.length <= poly2.length) {
      	//exp is longer or equal
          for (; i<longest; i++) {
          	coeffs[i] = poly2[i];
          }
      }
      else {
      	//array is longer
        for (; i<longest; i++) {
          coeffs[i] = poly1[i];
        }
      }

      return new Polynomial(convert_to_string(coeffs));
      //print_polynomial(returnPoly);
      //return new Polynomial(coeffs);
    }

    private String convert_to_string(double[] array) {
      String returnString = "";
      for (int i = 0; i < array.length; i++) {
        if(array[i] != 0.0) {
          if(i == 0) {
            returnString = returnString + array[i];
          }
          else if(i == 1) {
            if(array[i] < 0.0) {
              // is negative, add - sign
              returnString = returnString + array[i] + "x";
            }
            else {
              returnString = returnString + "+" + array[i] + "x";
            }
          }
          else {
            if(array[i] < 0.0) {
              // is negative, add - sign
              returnString = returnString + array[i] + "x" + i;
            }
            else {
              returnString = returnString + "+" + array[i] + "x" + i;
            }
          }
        }
      }

      return returnString;
    }


    public Polynomial multiply(Polynomial polynomial) {
    	//convert current polynomial to a single array

    	double[] poly_1 = convert_to_single_array(polynomial);

    	//convert the arg polynomial to a single arr
    	double[] poly_2 = convert_to_single_array(this);

    	//create another array(resultant)of length n.m

    	double[] resultant_arr = new double[(poly_1.length-1) * (poly_2.length-1)];

    	//System.out.println("==Printing==");
    	//System.out.println("poly_1 " + (poly_1.length-1));
    	//System.out.println("poly_2 " + (poly_2.length-1));
    	//System.out.println("resultant_arr " + (resultant_arr.length));
    	//System.out.println("==End==");


    	//multiply coeffs across two arrays
        //check if the exp is set in the resultant arr
        //if yes, add the coeffs
    	//if not, set coeffs to arr


    	for(int i=0; i < poly_1.length; i++)
    	{
    		for(int j=0; j < poly_2.length; j++)
    		{
    			resultant_arr[i+j] = resultant_arr[i+j] + (poly_1[i] * poly_2[j]);

    		}
    	}

    	//for(int a=0; a< resultant_arr.length; a++) {

    	//	System.out.println(resultant_arr[a]);

    	//}

    	//convert polynomial from arr


    	return new Polynomial(convert_to_string(resultant_arr));


    }

    public void saveToFile (String filename) {
      try {
        File file = new File(filename);
        file.createNewFile();
        //if (file.createNewFile()) {
         // System.out.println("File created: " + file.getName());
        //} else {
        //  System.out.println("File already exists.");
        //}
      } catch (IOException e) {
        //System.out.println("An error occurred.");
        e.printStackTrace();
      }

      double[] coeffs = convert_to_single_array(this);


      try {
          FileWriter myWriter = new FileWriter(filename);
          myWriter.write(convert_to_string(coeffs));
          myWriter.close();
          //System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
          //System.out.println("An error occurred.");
          e.printStackTrace();
      }
    }

    //public static void main(String args[]) {
    // Polynomial test1 = new Polynomial("5-1x+7x8");
     //Polynomial test2 = new Polynomial("10x2");
     //test1.saveToFile("test.txt");
     //Polynomial test3 = new Polynomial(new File("test.txt"));
     //test3.print_polynomial(test3);
    }




}



     



	