package resources;


public class KeyWordSet {
	
	private String keyWord;
	private String[] reactions;
	private int reactionCount;
	
	public KeyWordSet(String keyWord, String reactionString){
		
		this.keyWord = keyWord;
		reactions = reactionString.split(",");
		reactionCount = 0;
		
	}

	public String getKeyWord() {
		return keyWord;
	}

	public String[] getReactions() {
		return reactions;
	}

	public int getReactionCount() {
		return reactionCount;
	}

	public void setReactionCount(int reactionCount) {
		this.reactionCount = reactionCount;
	}
	
}
