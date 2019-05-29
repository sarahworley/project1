/**
 * 
 */
package edu.ncsu.csc216.backlog.model.command;

/**
 * The Command class creates objects that encapsulate user actions (or
 * transitions) that cause the state of a TaskItem to update.
 * 
 * @author sarahworley
 *
 */
public class Command {

	/** Note that goes with the task */
	private String noteText;
	/** Author of note that goes with the class */
	private String noteAuthor;

	private CommandValue c;

	/** Lists possible commands */
	public enum CommandValue {	BACKLOG, CLAIM, PROCESS, VERIFY, COMPLETE, REJECT }

	/**
	 * Command constructor. Command constructor.
	 * 
	 * @param c          the commandValue for the constructor
	 * @param noteAuthor Author of the note
	 * @param noteText   Text of the note
	 */
	public Command(CommandValue c, String noteAuthor, String noteText) {
		if (c == null || noteText == null || noteText.equals("") || noteAuthor == null || noteAuthor.equals("")) {
			throw new IllegalArgumentException();
		}

		this.noteText = noteText;
		this.noteAuthor = noteAuthor;
		this.c = c;
		
		
	}

	/**
	 * Gets the command
	 * 
	 * @return the command value for a given command
	 */
	public CommandValue getCommand() {
		return this.c;
	}

	/**
	 * Gets the text of a note that goes with the command
	 * 
	 * @return string for of the note text
	 */
	public String getNoteText() {
		return this.noteText;
	}

	/**
	 * get author of the note
	 * 
	 * @return string of of the author
	 */
	public String getNoteAuthor() {
		return this.noteAuthor;
	}
}
