package com.github.sylordis.games.codingame.games.puzzles.xorandor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sylordis.commons.logical.LogicalComponent;
import com.github.sylordis.commons.logical.exceptions.MaximumIOReachedException;

public class XorandorTranslator {

	/**
	 * Creates a temporary mapping from a textual representation.
	 *
	 * @param lines
	 *            Textual representation
	 * @return a list of translator nodes
	 * @throws XorandorException
	 *             if something goes wrong
	 */
	private List<XorandorTranslatorNode> createXorandorTranslatorMapping(String[] lines) {
		List<XorandorTranslatorNode> current = new ArrayList<>(), remaining = new ArrayList<>(), saved = new ArrayList<>();
		int linen = 0;
		for (String line : lines) {
			System.err.println(line);
			current = parseLine(line, linen);
			List<XorandorTranslatorNode> linked = link(remaining, current);
			saved.addAll(linked.stream().filter(n -> !n.getType().isReplaceable()).distinct().collect(Collectors.toList()));
			remaining.removeAll(linked);
			remaining.addAll(current);
			linen++;
		}
		// Add all remaining components except cables
		saved.addAll(remaining.stream().filter(n -> !n.getType().isReplaceable()).distinct().collect(Collectors.toList()));
		return saved;
	}

	/**
	 * Links nodes from a list of current nodes to a list of yet unlinked nodes.
	 *
	 * @param unlinkedList
	 *            List of nodes to link against
	 * @param currentList
	 *            List of nodes to link
	 * @return a list containing all nodes which have been just linked
	 */
	private List<XorandorTranslatorNode> link(List<XorandorTranslatorNode> unlinkedList,
			List<XorandorTranslatorNode> currentList) {
		List<XorandorTranslatorNode> linked = new ArrayList<>();
		// Skip if there's nothing to link the nodes to
		if (!unlinkedList.isEmpty()) {
			for (XorandorTranslatorNode currentNode : currentList) {
				List<XorandorTranslatorNode> previousesList = matchPreviousNode(currentNode, unlinkedList);
				if (!previousesList.isEmpty()) {
					for (XorandorTranslatorNode previousNode : previousesList) {
						if (previousNode.getType().isReplaceable()) {
							// Remove replaceable by new entities
							List<XorandorTranslatorNode> prevPrev = previousNode.getCabledNext();
							// Get cabled previous and replace the previous replaceable with this one
							for (XorandorTranslatorNode toFilter : prevPrev) {
								toFilter.getCabledPrevious().remove(previousNode);
								toFilter.addCabledPrevious(currentNode);
								currentNode.addCabledNext(toFilter);
							}
						} else {
							// Link
							currentNode.addCabledNext(previousNode);
							previousNode.addCabledPrevious(currentNode);
						}
						linked.add(previousNode);
					}
				}
			}
		}
		return linked;
	}

	/**
	 * Takes a list of translator nodes and converts it to a logical circuit.
	 *
	 * @param mapping
	 *            A list of translator nodes
	 * @return a list of logical components representing the logical circuit.
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws MaximumIOReachedException
	 */
	private List<LogicalComponent> mapToCircuit(List<XorandorTranslatorNode> mapping)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, MaximumIOReachedException {
		List<LogicalComponent> components = new ArrayList<>();
		// Create components
		for (XorandorTranslatorNode tnode : mapping) {
			LogicalComponent component = createComponent(tnode);
			long count = components.stream().filter(c -> c.getClass().equals(tnode.getType().getTargetClass())).count() + 1;
			component.setName(tnode.getType().toString() + "-" + count);
			tnode.setComponent(component);
			components.add(component);
		}
		// Link components together
		for (XorandorTranslatorNode tnode : mapping) {
			// Inputs
			final List<LogicalComponent> inputs = tnode.getCabledPrevious().stream().map(n -> n.getComponent())
					.collect(Collectors.toList());
			tnode.getComponent().setInputs(inputs);
			// Outputs
			final List<LogicalComponent> outputs = tnode.getCabledNext().stream().map(n -> n.getComponent())
					.collect(Collectors.toList());
			tnode.getComponent().setOutputs(outputs);
		}
		return components;
	}

	/**
	 * Creates a component from a translator node.
	 *
	 * @param tnode
	 *            Translator node
	 * @return a logical component
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private LogicalComponent createComponent(XorandorTranslatorNode tnode) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends LogicalComponent> constr = tnode.getType().getTargetClass().getConstructor();
		LogicalComponent component = constr.newInstance();
		return component;
	}

	/**
	 * Searches for all nodes from a list of nodes which could be linked according to their position.
	 *
	 * @param current
	 *            The node to search a previous node for
	 * @param unlinkedNodes
	 *            All nodes not yet linked and that could be a potential previous node
	 * @return a list of matching nodes
	 */
	private List<XorandorTranslatorNode> matchPreviousNode(XorandorTranslatorNode current,
			List<XorandorTranslatorNode> unlinkedNodes) {
		List<XorandorTranslatorNode> previouses = new ArrayList<>();
		for (XorandorTranslatorNode node : unlinkedNodes) {
			switch (current.getType()) {
				case CABLE:
					if (node.getStartPosition() <= current.getStartPosition()
							&& node.getEndPosition() >= current.getEndPosition())
						previouses.add(node);
					break;
				default:
					// Check if it surrounds current node's positions
					if (node.getStartPosition() >= current.getStartPosition()
							&& node.getEndPosition() <= current.getEndPosition())
						previouses.add(node);
					break;
			}
		}
		return previouses;
	}

	/**
	 * Parses a line and extract nodes from it.
	 *
	 * @param line
	 *            Line to be parsed
	 * @param linen
	 *            Line number
	 * @return a list of nodes extracted from the line
	 */
	private List<XorandorTranslatorNode> parseLine(String line, int linen) {
		List<XorandorTranslatorNode> nodes = new ArrayList<>();
		boolean isBiggerElement = false;
		int index = -1;
		XorandorElement type = null;
		char[] carray = line.toCharArray();
		// Parse each character
		for (int i = 0; i < carray.length; i++) {
			// Determine the constructs
			switch (carray[i]) {
				case ' ': // Space, do nothing.
				case '-': // Mid-part of chunk of cable, do nothing.
					break;
				case '0':
					nodes.add(new XorandorTranslatorNode(i, XorandorElement.SWITCH));
					break;
				case '[': // Beginning of gate
					isBiggerElement = true;
					index = i;
					break;
				case ']': // End of gate
					isBiggerElement = false;
					nodes.add(new XorandorTranslatorNode(index, i, type));
					break;
				case '+': // Beginning or end of a chunk of horizontal cable
					if (isBiggerElement) {
						// Forks
						if ((carray.length > i + 1 && carray[i + 1] != '-') || carray.length <= i + 1) {
							// End of an horizontal chunk of cable
							isBiggerElement = false;
							nodes.add(new XorandorTranslatorNode(index, i, XorandorElement.CABLE_CHUNK));
						}
					} else {
						// Beginning of an horizontal chunk of cable
						isBiggerElement = true;
						index = i;
					}
					break;
				case '|': // Vertical cable (or gate)
					if (!isBiggerElement) {
						nodes.add(new XorandorTranslatorNode(i, XorandorElement.CABLE));
						break;
					}
					// If it's a bigger element (gate), fall through switch
				default:
					type = XorandorElement.matchBySymbol(carray[i]);
					break;
			}
		}
		nodes.forEach(n -> n.setLinenumber(linen));
		return nodes;
	}

	/**
	 * Main method of the translator, parses a given entry and constructs the Xorandor circuit.
	 *
	 * @param lines
	 *            A textual representation of the circuit.
	 * @return a list of logical component.
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws MaximumIOReachedException
	 */
	public List<LogicalComponent> translate(String[] lines)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, MaximumIOReachedException {
		List<XorandorTranslatorNode> mapping = createXorandorTranslatorMapping(lines);
		List<LogicalComponent> result = mapToCircuit(mapping);
		return result;
	}
}
