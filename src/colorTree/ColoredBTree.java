package colorTree;

import static java.util.Map.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ColoredBTree {
	Node root = null;
	int size = 0;
	int index = 0;
	ArrayList<ArrayList<Node>> arrayListOfChains = new ArrayList();

	public static class Node {
		public int value;
		public char color; // R, G, B, Y, …
		public Node parent = null;
		public Node left = null;
		public Node right = null;

		Node(int value, char color, Node parent) {
			this.value = value;
			this.color = color;
			this.parent = parent;
		}
	}

	@SafeVarargs
	public ColoredBTree(Entry<Integer, Character>... valueColorEntries) {
		for (var entry : valueColorEntries) {
			add(entry.getKey(), entry.getValue());
		}
	}

	public boolean add(int value, char color) {

		if (root == null) {
			root = new Node(value, color, null);
			size++;
			return true;
		}

		Node current = root;
		while (true) {
			if (value == current.value) {
				return false; // such element already exists
			}
			if (value < current.value) {
				if (current.left == null) {
					current.left = new Node(value, color, current);
					break;
				}
				current = current.left;
			} else { // value > current.value
				if (current.right == null) {
					current.right = new Node(value, color, current);
					break;
				}
				current = current.right;
			}
		}
		size++;
		return true;
	}

	public void printTree() {
		print(root, 0);
	}

	// recursive part of printTree() function
	private void print(Node node, int depth) {
		if (node != null) {
			print(node.right, depth + 1);
			System.out.printf("%s%02d,%c%n", "    ".repeat(depth), node.value, node.color);
			print(node.left, depth + 1);
		}
	}

	/**
	 * Calculates the longest chain of Node-s having the same color
	 */
	public List<Node> maxChain() {
		ArrayList<Node> currentChain = new ArrayList<>();
		getChains(root, currentChain);
		ArrayList<Node> maxChain = arrayListOfChains.get(0);

		for (ArrayList<Node> chain : arrayListOfChains) {
			if (maxChain.size() < chain.size()) {
				maxChain = chain;
			}
		}
		return maxChain;
	}

	private void getChains(Node root, ArrayList<Node> currentChain) {
		if (root == null) {
			return;
		}
		if (root.parent == null || root.parent.color != root.color) {
			currentChain = new ArrayList<>();
			currentChain.add(root);
			arrayListOfChains.add(currentChain);
		} else if (root.parent.color == root.color) {
			currentChain.add(root);
			;
		}
		if (root.left != null) {
			getChains(root.left, currentChain);
		}
		if (root.right != null) {
			getChains(root.right, currentChain);
		}

	}
}