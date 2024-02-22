package lSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LSystem {
	
	/** The current word in the L-System. */
	private Letter[] word;
	
	/** The set of rules. */
	private final HashMap<Letter, Rule> rules = new HashMap<Letter, Rule>();

	/**
	 * Creates a new L-System.
	 * 
	 * @param axiom The axiom of the L-System.
	 * @param rules The rules of the L-System.
	 */
	public LSystem(Letter[] axiom, Rule[] rules) {
		
		// Stores the given axiom and rules
		word = axiom;
		for (Rule rule : rules)
			this.rules.put(rule.letter, rule);
		
		// Stores rules for any letters for which rules were not given
		for (Letter l : Letter.values())
			if (!this.rules.containsKey(l))
				this.rules.put(l, Rule.defaultRule(l));
	}
	
	/**
	 * @return The word currently stored in the L-System.
	 */
	public Letter[] getWord() {
		return word;
	}
	
	/**
	 * Applies the L-Systems rules to each letter in its word.
	 */
	public void step() {
		
		// Transforms each letter into its new letter sequence
		final ArrayList<Letter> newWord = new ArrayList<Letter>();
		for (Letter l : word) {
			
			// Applies the rule at this letter's relative position in the new word
			final Letter[] transform = rules.get(l).drawTransform();
			for (Letter newLetter : transform)
				newWord.add(newLetter);
		}
		
		// Stores the new word
		word = new Letter[newWord.size()];
		for (int i = 0; i < word.length; i++)
			word[i] = newWord.get(i);
	}
	
	/**
	 * Applies the L-System's rules the given number of times.
	 * 
	 * @param times The number of times to apply the rules.
	 */
	public void steps(int times) {
		for (int i = 0; i < times; i++)
			step();
	}
	
	public String toString() {
		return Arrays.toString(word);
	}
}
