package resources;

public class SynWordSet {
	
	private String word;
	private String[] syns;
	
	public SynWordSet(String word, String synsString) {
		
		this.word = word;
		this.syns = synsString.split(",");
	}

	public String getWord() {
		return word;
	}

	public String[] getSyns() {
		return syns;
	}
}
