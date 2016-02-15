package com.openprice.parser.generic;

/**
 * A simple class that represents a line in chainList
 *
 *
 */
public class ChainLine {

//    * chain id
	private final String chainID;
	public String chainID(){return chainID;}

//    * a field value that can identify the chain, like chain name or slogan
	private final String identityField;
	public String identityField(){return identityField;}

	private final String chainName;
	public String chainName(){return chainName;}

//    * the matching score,
	private final double matchScore;
	public double matchScore(){return matchScore;}

//    * a string of categories separated by comma
	private final String category;
	public String category(){return category;}

//    * chain's parser class name
	private final String parserClassName;
	public String parserClassName(){return parserClassName;}


	public ChainLine(final String cID, final String cName,
			final String identityField,
			final double sc, final String cat, final String parserName){
		this.chainID=cID;
		this.chainName=cName;
		this.identityField=identityField;
		this.matchScore=sc;
		this.category=cat;
		this.parserClassName=parserName;
	}

	@Override
	public String toString(){
		return chainID()
		        +", "
		        + chainName
		        +", "
				+ identityField()
				+", "+matchScore()
				+", "+category+", "
				+parserClassName;
	}


}
