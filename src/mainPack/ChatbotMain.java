package mainPack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import resources.KeyWordSet;
import resources.KonjPair;
import resources.SynWordSet;

public class ChatbotMain {

	public static void main(String[] args) throws SQLException {
		Scanner s = new Scanner (System.in);
		SQLiteDb database = new SQLiteDb();
		
		ResultSet reacSet = database.getŔeactions();
		ResultSet konjSet = database.getkonjs();
		ResultSet synSet = database.getSyns();
		
		ArrayList<KeyWordSet> reactions = new ArrayList<KeyWordSet>();
		ArrayList<KonjPair> konjugations = new ArrayList<KonjPair>();
		ArrayList<SynWordSet> synonyms = new ArrayList<SynWordSet>();
		
		
		while(reacSet.next()) {
			String keyWord = reacSet.getString(2);
			String reactionString = reacSet.getString(3);
			reactions.add(new KeyWordSet(keyWord, reactionString));
		}
		
		while(konjSet.next()) {
			String firstWord = konjSet.getString(2);
			String secondWord = konjSet.getString(3);
			konjugations.add(new KonjPair(firstWord, secondWord));
		}
		
		while (synSet.next()) {
			String word = synSet.getString(2);
			String synString = synSet.getString(3);
			synonyms.add(new SynWordSet(word, synString));
		}
		
		ChatBotProgramm programm = new ChatBotProgramm(reactions, konjugations, synonyms);
		programm.run(s);
		s.close();
		database.closeConn();
		

	}

}
