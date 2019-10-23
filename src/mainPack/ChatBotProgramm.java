package mainPack;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import resources.KeyWordSet;
import resources.KonjPair;
import resources.SynWordSet;

public class ChatBotProgramm {
	
	private ArrayList<KeyWordSet> reactions;
	private ArrayList<KonjPair> konjugations;
	private ArrayList<SynWordSet> synonyms;
	int keyWordCount;
	private boolean isBye;
	private String lastInput;
	private int invalidCounter;
	
	public ChatBotProgramm(ArrayList<KeyWordSet> reactions, ArrayList<KonjPair> konjugations,
			ArrayList<SynWordSet> synonyms) {
		this.reactions = reactions;
		this.konjugations = konjugations;
		this.synonyms = synonyms;
		keyWordCount = reactions.size();
		isBye = false;
		lastInput = null;
		invalidCounter = 0;
	}
	
	private boolean isUpperCase(char a) {
		return ((a>='A') && (a<='Z'));
	}
	
	private String trim(String trimmString) {
		int i=0;
		int j=trimmString.length()-1;
		while ((i<trimmString.length()) && !isUpperCase(trimmString.charAt(i))) {
			i++;
		}
		while ((j>=i) && !isUpperCase(trimmString.charAt(j))) {
			j--;
		}
		
		return(trimmString.substring(i, j+1));
	}	
	
	private String konjugation(String konjString) {
		int firstBoundry = 0;
		int secondBoundry = 0;
		int length = konjString.length();
		String result = "";
		String workingResult = null;
		while (secondBoundry<length) {
			
			while ((secondBoundry <length) && isUpperCase(konjString.charAt(secondBoundry))) {
				secondBoundry++;
			}
			workingResult = konjString.substring(firstBoundry, secondBoundry);
			for (int i=0; i<konjugations.size(); i++) {
					
				if (workingResult.equals(konjugations.get(i).getFirstWord())){
					workingResult = konjugations.get(i).getSecondWord();
					break;
						
				} else if (workingResult.equals(konjugations.get(i).getSecondWord())) {
					workingResult = konjugations.get(i).getFirstWord();
					break;
				}
			}
				
			result = result + workingResult + " ";
			while ((secondBoundry<length) && !isUpperCase(konjString.charAt(secondBoundry))) {
				secondBoundry++;
			}
			
			firstBoundry = secondBoundry;
		}
		return trim(result);
	}
	
	private String synReplacement(String synString) {
		
		int firstBoundry = 0;
		int secondBoundry = 0;
		int length = synString.length();
		String result = "";
		String workingResult = null;
		boolean found = false;
		
		while (secondBoundry<length) {
			while ((secondBoundry <length) && isUpperCase(synString.charAt(secondBoundry))) {
				secondBoundry++;
			}
			workingResult = synString.substring(firstBoundry, secondBoundry);
			for (int i=0; (i<synonyms.size()) && !found; i++) {
				for(int i2 = 0; (i2<synonyms.get(i).getSyns().length) && !found; i2++) {
					if (workingResult.equals(synonyms.get(i).getSyns()[i2])) {
						workingResult = synonyms.get(i).getWord();
						found=true;
					}
				}
			}
			result = result+workingResult + " ";
			while ((secondBoundry<length) && !isUpperCase(synString.charAt(secondBoundry))) {
				secondBoundry++;
			}
			
			firstBoundry = secondBoundry;
		}
		
		return trim(result);
	}
	
	private String react(String reactString) {
		
		int keyPosition = 0;
		int starPosition = 0;
		String result = "";
		
		if (reactString.equals("CIAO")) {
			isBye = true;
			return ("SCHU MAL WIEDR VORBEI");
		}
		
		for (int i = 0; i<keyWordCount; i++) {
			keyPosition = reactString.indexOf(reactions.get(i).getKeyWord());
			if (keyPosition != -1) {
				
				int reactionCount = reactions.get(i).getReactionCount();
				result = reactions.get(i).getReactions()[reactionCount];
				reactions.get(i).setReactionCount(reactionCount +1);
				//System.out.println(reactions.get(i).getReactionCount());
				
				if (reactions.get(i).getReactionCount() == reactions.get(i).getReactions().length) {
					reactions.get(i).setReactionCount(0);
				}
				
				starPosition = result.indexOf('*');
				if (starPosition != -1) {
					String konjRest = konjugation(reactString.substring(keyPosition + reactions.get(i).getKeyWord().length(), reactString.length() ));
					result = result.substring(0, starPosition) + konjRest + result.substring(starPosition + 1 , result.length() );
				}
				
				break;
				
			}
		}
		
		if (result.equals("")){
			return (react("!!!NOMATCH!!!"));
		} else {
			return result;
		}
	}

	public void handle( String inputString) throws SQLException {
		if (invalidCounter < 3) {
			
			if (inputString.equals(lastInput)) {
				System.out.println(trim(react("!!!WDH!!!")));
				invalidCounter++;
			} else if (inputString.isEmpty()) {
				System.out.println(trim(react("!!!LEER!!!")));
				invalidCounter++;
			} else {
				System.out.println(trim(react(inputString)));
				lastInput = inputString;
				invalidCounter = 0;
			}
			
		} else if (invalidCounter < 5) {
			
			if (inputString.equals(lastInput) || inputString.isEmpty()) {
				System.out.println(trim(react("!!!ALT!!!")));
				invalidCounter ++;
			} else {
				System.out.println(trim(react(inputString)));
				lastInput = inputString;
				invalidCounter = 0;
			}
			
		} else {
			System.out.println(trim(react("CIAO")));
		}
		System.out.println(invalidCounter);
	}
	public void run(Scanner s) throws SQLException {
		
		System.out.println("Hallo zu unsrem kleinen Psychiater");
		Thread listen = new Thread(()->{
			String userInput = "";
			userInput = s.nextLine();
			try {
				handle(synReplacement(userInput.toUpperCase()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		while (!isBye) {
			String userInput = "";
			userInput = s.nextLine();
			handle(synReplacement(userInput.toUpperCase()));
				
		}
		
		return;
	}
	
}
