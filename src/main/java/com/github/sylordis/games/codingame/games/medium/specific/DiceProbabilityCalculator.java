package com.github.sylordis.games.codingame.games.medium.specific;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.Map;

import com.github.sylordis.commons.math.probability.Die;
import com.github.sylordis.commons.math.probability.tree.AddProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.DieProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.GreaterThanProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.MultiplyProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.NumberProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.OperationProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.ProbabilityNode;
import com.github.sylordis.commons.math.probability.tree.SubtractProbabilityNode;

/**
 * Class in charge of building the probability tree with {@link #parseExpression(String)} and resolving it.
 */
public class DiceProbabilityCalculator {

	private static final String SYMBOL_ADD = "+";
	private static final String SYMBOL_GREATER_THAN = ">";
	private static final String SYMBOL_SUBTRACT = "-";
	private static final String SYMBOL_MULTIPLY = "*";
	private static final int PARENTHESIS_PRIORITY = 4;
	private static final int BASE_HIGHEST_PRIORITY = 10000;

	/**
	 * Root element of the expression.
	 */
	private ProbabilityNode<Integer> root;

	/**
	 * Builds the probability tree.
	 *
	 * @param expression
	 *            Arithmetical expression to analyse.
	 */
	public void parseExpression(String expression) {
		root = createBranchFromExpression(expression);
		debug(root);
	}

	/**
	 * Builds the branch of the tree represented by the expression.
	 *
	 * @param expr
	 *            Expression to parse.
	 * @return The root node of the branch.
	 */
	public ProbabilityNode<Integer> createBranchFromExpression(String expr) {
		ProbabilityNode<Integer> node = null;
		// Get lowest priority sign if any
		int index = getLowestPrioritySign(expr);
		if (index >= 0) {
			debug("symbol[{}]: {}", index, expr.charAt(index));
			// Create corresponding element, left hand expression and right hand expression recursively.
			OperationProbabilityNode<Integer> nodeOp = (OperationProbabilityNode<Integer>) createNodeFrom(
					Character.toString(expr.charAt(index)));
			final String left = filterGlobbingParenthesis(expr.substring(0, index));
			final String right = filterGlobbingParenthesis(expr.substring(index + 1));
			debug("left: {}", left);
			nodeOp.setLeft(createBranchFromExpression(left));
			debug("right: {}", right);
			nodeOp.setRight(createBranchFromExpression(right));
			node = nodeOp;
		} else // No operation, then leaf
			node = createNodeFrom(expr);
		return node;
	}

	/**
	 * Removes globbing parenthesis from the expression.
	 *
	 * @param expr
	 * @return
	 */
	private String filterGlobbingParenthesis(String expr) {
		String result = expr;
		if (expr.startsWith("(") && expr.endsWith(")"))
			result = expr.substring(1, expr.length() - 1);
		return result;
	}

	/**
	 * Creates a node from given expression.
	 *
	 * @param symbol
	 * @return
	 */
	private ProbabilityNode<Integer> createNodeFrom(String symbol) {
		ProbabilityNode<Integer> node = null;
		switch (symbol) {
			case SYMBOL_MULTIPLY:
				node = MultiplyProbabilityNode.forInteger();
				break;
			case SYMBOL_SUBTRACT:
				node = SubtractProbabilityNode.forInteger();
				break;
			case SYMBOL_ADD:
				node = AddProbabilityNode.forInteger();
				break;
			case SYMBOL_GREATER_THAN:
				node = GreaterThanProbabilityNode.forInteger();
				break;
			default:
				if (symbol.matches("d[0-9]+")) // Die
					node = new DieProbabilityNode(new Die(Integer.parseInt(symbol.substring(1))));
				else if (symbol.matches("[0-9]+")) // Number
					node = new NumberProbabilityNode<>(Integer.parseInt(symbol));
				break;
		}
		debug("node={}", node.getClass().getSimpleName());
		return node;
	}

	/**
	 * Gets the index of the lowest priority operation sign, considering parenthesis.
	 *
	 * @param expr
	 * @return -1 if there's no operation sign, its index otherwise.
	 */
	public int getLowestPrioritySign(String expr) {
		int index = -1;
		int minPriority = BASE_HIGHEST_PRIORITY;
		int currPriority = 0;
		int ambientPriority = 0;
		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if (c == '(')
				ambientPriority += PARENTHESIS_PRIORITY;
			else if (c == ')')
				ambientPriority -= PARENTHESIS_PRIORITY;
			else {
				currPriority = getSymbolPriority(Character.toString(c)) + ambientPriority;
				if (currPriority < minPriority) {
					index = i;
					minPriority = currPriority;
				}
			}
		}
		return index;
	}

	/**
	 * Gets the priority for a given symbol. The higher the priority, the more importantly this symbol should be
	 * processed.
	 *
	 * @param symbol
	 * @return
	 */
	private int getSymbolPriority(String symbol) {
		int priority = BASE_HIGHEST_PRIORITY;
		switch (symbol) {
			case SYMBOL_MULTIPLY:
				priority = 3;
				break;
			case SYMBOL_SUBTRACT:
			case SYMBOL_ADD:
				priority = 2;
				break;
			case SYMBOL_GREATER_THAN:
				priority = 1;
				break;
		}
		return priority;
	}

	/**
	 * Gets the outcomes of the tree.
	 *
	 * @return
	 */
	public Map<Integer, Float> getOutcomes() {
		return root.getOutcomes();
	}

}
