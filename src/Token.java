class Token{
	/* name stores the value of token
	 * type stores the type of token
	 * 1- LiteralAtom
	 * 2- NumericAtom
	 * 3- OpenParenthesis
	 * 4- ClosedParenthesis
	 * 5- Whitespace
	 * 6- EOF
	 * 7- Error 
	 */
	String name;
	int type;
	Token(String name,int type){
		this.name=name;
		this.type=type; 
	}
}