package com.github.sylordis.games.codingame.games.puzzles.xorandor;

import java.util.ArrayList;
import java.util.List;

import com.github.sylordis.commons.logical.LogicalComponent;

public class XorandorTranslatorNode {

	/**
	 * First position on the circuit textual representation.
	 */
	private int startPosition;
	/**
	 * Last position on the circuit textual representation.
	 */
	private int endPosition;
	/**
	 * Type of the element.
	 */
	private XorandorElement type;
	/**
	 * Begin and end nodes for cables.
	 */
	private List<XorandorTranslatorNode> cabledPrevious, cabledNext;
	/**
	 * Line number where the component is.
	 */
	private int lineNumber;
	/**
	 * Logical component to be used for linking real objects.
	 */
	private LogicalComponent component;

	/**
	 * Node with a single position (vertical cables).
	 *
	 * @param position
	 *            start and end position.
	 * @param type
	 */
	public XorandorTranslatorNode(int position, XorandorElement type) {
		this(position, position, type);
	}

	/**
	 * Node.
	 *
	 * @param firstPos
	 * @param lastPos
	 * @param type
	 */
	public XorandorTranslatorNode(int firstPos, int lastPos, XorandorElement type) {
		this.startPosition = firstPos;
		this.endPosition = lastPos;
		this.type = type;
		this.cabledPrevious = new ArrayList<>();
		this.cabledNext = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "(" + startPosition + ", " + endPosition + ", " + type + " [" + lineNumber + "])";
	}

	public void addCabledPrevious(XorandorTranslatorNode cabledPrevious) {
		this.cabledPrevious.add(cabledPrevious);
	}

	public List<XorandorTranslatorNode> getCabledPrevious() {
		return cabledPrevious;
	}

	public void setCabledPrevious(List<XorandorTranslatorNode> cabledPrevious) {
		this.cabledPrevious = cabledPrevious;
	}

	public void addCabledNext(XorandorTranslatorNode cabledNext) {
		this.cabledNext.add(cabledNext);
	}

	public List<XorandorTranslatorNode> getCabledNext() {
		return cabledNext;
	}

	public void setCabledTo(List<XorandorTranslatorNode> cabledNext) {
		this.cabledNext = cabledNext;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public XorandorElement getType() {
		return type;
	}

	public int getLinenumber() {
		return lineNumber;
	}

	public void setLinenumber(int linenumber) {
		this.lineNumber = linenumber;
	}

	public LogicalComponent getComponent() {
		return component;
	}

	public void setComponent(LogicalComponent component) {
		this.component = component;
	}

}
