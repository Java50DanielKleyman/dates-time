package colorTree;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;



class ColoredBTreeTest {

	@Test
	void test1() {
		var tree = new ColoredBTree(
				entry(22, 'G'), 
				entry(15, 'Y'),
				entry(6, 'Y'), 
				entry(5, 'B'), 
				entry(10, 'Y'), 
				entry(18, 'B'),
				entry(35, 'B'), 
				entry(79, 'B'));
		assertAndPrint(tree, "Length: 3, Color: Y, Nodes: [10, 6, 15]");
	}

	@Test
	void test2() {
		var tree = new ColoredBTree(
				entry(22, 'R'), 
				entry(15, 'G'), 
				entry(6, 'G'), 
				entry(5, 'G'), 
				entry(2, 'G'), 
				entry(10, 'Y'),
				entry(18, 'G'),
				entry(19, 'V'), 
				entry(35, 'B'), 
				entry(79, 'B'), 
				entry(85, 'B'), 
				entry(99, 'B'), 
				entry(2, 'G'));
		assertAndPrint(tree, "Length: 5, Color: G, Nodes: [2, 5, 6, 15, 18]");
	}

	@Test
	void test3() {
		var tree = new ColoredBTree(
				entry(35, 'G'), 
				entry(22, 'G'), 
				entry(15, 'G'), 
				entry(6, 'G'), 
				entry(5, 'G'), 
				entry(2, 'G'), 
				entry(1, 'Y'), 
				entry(10, 'Y'),
				entry(18, 'G'),
				entry(19, 'V'), 
				entry(79, 'B'), 
				entry(85, 'B'), 
				entry(99, 'B'), 
				entry(40, 'G'));
		assertAndPrint(tree, "Length: 6, Color: G, Nodes: [2, 5, 6, 15, 22, 35]");
	}
	
	private String chainToString(List<ColoredBTree.Node> chain) {
		char color = (chain.size() > 0) ? chain.get(0).color : '\0';
		List<Integer> values = new ArrayList<>(chain.size());
		chain.forEach(node -> values.add(node.value));
		return String.format("Length: %d, Color: %c, Nodes: %s", chain.size(), color, values.toString());
	}
	
	private void assertAndPrint(ColoredBTree tree, String expectedResult) {
		System.out.println("=".repeat(30));
		tree.printTree();
		String result = chainToString(tree.maxChain());
		System.out.println("-".repeat(30));
		System.out.println("Expected: " + expectedResult);
		assertEquals(expectedResult, result);
	}
}