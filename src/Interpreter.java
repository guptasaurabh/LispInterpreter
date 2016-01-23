import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Interpreter {

	public static void main(String[] args) throws IOException{
		InputStreamReader in= new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(in);
		ArrayList<Token> tokenList = new ArrayList<>();
		Token result;   					// Stores the result returned from GetNextToken
		do{
			result = getNextToken(input);
			if(result.type!=5){
				if(result.type==7){
					break;
				}
				tokenList.add(result);
			}
		}while(result.type != 6); 			// Run till EOF is not encountered
		input.close();  // close the input stream
		if(result.type==7){   				// Check for invalid token
			System.out.println("ERROR: Invalid token " + result.name );
		}else{
			/* Prepares the output to be printed
			 * literalAtoms contains list of LiteralAtoms
			 * numericAtoms contains list of Numeric Atoms
			 * openParenthesis contains the count of open Parenthesis encountered
			 * closeParenthesis contains the count of close Parenthesis encountered
			 */
			ArrayList<String> literalAtoms = new ArrayList<>();
			ArrayList<Long> numericAtoms = new ArrayList<>();
			Long openParenthesis =(long) 0;
			Long closedParenthesis = (long)0;
			for( Token t : tokenList){
				switch(t.type){
					case 1: literalAtoms.add(t.name);
							break;
					case 2: numericAtoms.add(Long.parseLong(t.name));
							break;
					case 3: openParenthesis++;
							break;
					case 4: closedParenthesis++;
							break;
				}
			}
			StringBuilder sb = new StringBuilder();
			if( literalAtoms.size()!=0){
				sb.append(""+literalAtoms.size());
				for (String s : literalAtoms){
				    sb.append(", "+s);
				}
			}else{
				sb.append("0");
			}
			Long sum = (long) 0;
			StringBuilder integerString = new StringBuilder();
			if( numericAtoms.size()!=0){
				for(Long l: numericAtoms){
					sum += l; 
				}
				integerString.append(", "+ sum);
			}
			System.out.println("LITERAL ATOMS: " + sb );
			System.out.println("NUMERIC ATOMS: " + numericAtoms.size() + integerString );
			System.out.println("OPEN PARENTHESES: " + openParenthesis );
			System.out.println("CLOSING PARENTHESES: " + closedParenthesis );
		}
	}
	
	public static Token getNextToken(BufferedReader input) {
		int c = 0;	
		try{	
			if(!input.ready()){
				return new Token("EOF",6);
			}
			input.mark(0);
			c = input.read();
			if(c == 10 || c==32 || c==13 ){		
				do{
					input.mark(0);
					if(input.ready()){
						c = input.read();
					}else{
						c = 11;
					}
				}while(c == 10 || c==32 || c==13 );
				input.reset();
				return new Token(" ",5);
			}else if (c==40){
				return new Token("(",3);
			}else if(c==41){
				return new Token(")",4);
			}else if(c>=65&&c<=90){
				String res = "";
				do{
					res += (char)c;
					input.mark(0);
					if(input.ready()){
						c = input.read(); 
					}else{
						c=11;
					}
				}while((c>=65&&c<=90)||(c>=48&c<=57));
				input.reset();
				return new Token(res,1);
			}else if(c>=48&&c<=57){
				String res = "";
				do{
					res += (char)c;
					input.mark(0);
					if(input.ready()){
						c = input.read(); 
					}else{
						c=11;
					}
				}while(c>=48&c<=57);
				input.reset();
				input.mark(0);
				if(input.ready()){
					c = input.read();
					if(c>=65&&c<=90){
						do{
							res += (char)c;
							input.mark(0);
							if(input.ready()){
								c = input.read(); 
							}else{
								c=11;
							}
						}while((c>=65&&c<=90)||(c>=48&c<=57));
						input.reset();
						return new Token(res,7);
					}else{
						input.reset();
					}
				}
				return new Token(res,2);
			}
		}catch (Exception e){
			System.out.println("error in program");
		}
		return new Token(" ",8);
	}
}

