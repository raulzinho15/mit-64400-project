package lSystem;

import java.util.ArrayList;

public class Rule {
	
	/** The letter to be transformed by this rule. */
	public final Letter letter;
	
	/** The letter sequence to which this rule's letter will transform. */
	public final ArrayList<Letter[]> transforms = new ArrayList<Letter[]>();

	/**
	 * Creates a new rule.
	 * 
	 * @param letter The letter to be transformed.
	 */
	public Rule(Letter letter) {
		this.letter = letter;
	}

	/**
	 * Creates a new rule with a pre-existing transform.
	 * 
	 * @param letter The letter to be transformed.
	 * @param transform The letter sequence to transform into.
	 */
	public Rule(Letter letter, Letter[] transform) {
		this(letter);
		addTransform(transform);
	}
	
	/**
	 * @param transform The letter sequence to transform into.
	 */
	public void addTransform(Letter[] transform) {
		transforms.add(transform);
	}
	
	/**
	 * @return A transform drawn from a uniform probability distribution of
	 * 			all the transforms in this rule.
	 */
	public Letter[] drawTransform() {
		return transforms.get((int)(transforms.size() * Math.random()));
	}
	
	/**
	 * Creates a rule that is the identity transform for the given letter.
	 * 
	 * @param letter The letter for the rule.
	 * 
	 * @return A rule that is the identity transform.
	 */
	public static Rule defaultRule(Letter letter) {
		return new Rule(letter, new Letter[] {letter});
	}
}
