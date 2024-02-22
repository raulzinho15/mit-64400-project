package lSystem;

public class LSystemMain2 {

	public static void main(String[] args) {
		
		// Sets up the axiom
		final Letter[] axiom = {Letter.BLDG, Letter.MOVE};
		
		// Sets up the BLDG rule
		final Rule bldgRule = new Rule(Letter.BLDG);
		bldgRule.addTransform(new Letter[] {Letter.BLDG, Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG});
		bldgRule.addTransform(new Letter[] {});
		
		// Sets up the BLDG rule
		final Rule moveRule = new Rule(Letter.MOVE);
		moveRule.addTransform(new Letter[] {Letter.BLDG, Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE, Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE});
		
		final Rule[] rules = {bldgRule, moveRule};
		
		for (int i = 0; i < 10; i++) {
			final LSystem lSystem = new LSystem(axiom, rules);
			lSystem.steps(6);
			System.out.println(lSystem);
		}
	}
}
