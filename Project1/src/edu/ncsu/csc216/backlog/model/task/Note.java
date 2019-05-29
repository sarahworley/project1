/**
 * 
 */
package edu.ncsu.csc216.backlog.model.task;

/**
 * Note encapsulates the author and note text of a user’s interaction with a
 * TaskItem
 * 
 * @author sarahworley
 *
 */
public class Note {

	/** Note that goes with the task */
	private String noteText;
	/** Author of note that goes with the class */
	private String noteAuthor;

	/**
	 * Constructor for note
	 * 
	 * @param noteAuthor author of the note
	 * @param noteText   text of the note
	 */
	public Note(String noteAuthor, String noteText) {
		if (noteAuthor == null || noteText == null || noteText.isEmpty() || noteAuthor.isEmpty()) {
			
			throw new IllegalArgumentException();
			
		}
		this.noteAuthor = noteAuthor;
		this.noteText = noteText;

	}

	/**
	 * gets the note's author
	 * 
	 * @return author string
	 */
	public String getNoteAuthor() {
		return this.noteAuthor;
	}

	

	/**
	 * gets the note's text
	 * 
	 * @return text string
	 */
	public String getNoteText() {
		return this.noteText;
	}

	

	/**
	 * returns an array of length 2 with the author at index 0 and the text at index
	 * 1.
	 * 
	 * @return note array
	 */
	public String[] getNoteArray() {
		return new String[] { noteAuthor, noteText };

	}

}
