/**
 * 
 */
package edu.ncsu.csc216.backlog.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the note class
 * 
 * @author sarah worley
 *
 */
public class NoteTest {
	/** note object */
	private Note note;

	/**
	 * Test method for note constructor
	 * {@link edu.ncsu.csc216.backlog.model.task.Note#Note(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testNote() {
		// valid note construction
		note = new Note("sarah", "testing note");
		assertTrue("sarah".equals(note.getNoteAuthor()));
		assertTrue("testing note".equals(note.getNoteText()));
		String[] note2 = note.getNoteArray();
		assertTrue(note2[0].equals("sarah"));
		assertTrue(note2[1].equals("testing note"));
		assertEquals(2, note2.length);

	}

	/**
	 * Test method for noteAuthor
	 * {@link edu.ncsu.csc216.backlog.model.task.Note#getNoteAuthor()}.
	 */
	@Test
	public void testGetNoteAuthor() {
		// valid note author
		note = new Note("sarah", "testing note");
		assertTrue("sarah".equals(note.getNoteAuthor()));
		String[] note2 = note.getNoteArray();
		assertTrue(note2[0].equals("sarah"));
		assertTrue(note2[1].equals("testing note"));
		assertEquals(2, note2.length);

		// invalid notes
		try {
			note = new Note("", "testing note");
			fail();
		} catch (IllegalArgumentException e) {
			// check if anything was added to the array
			assertEquals(2, note2.length);

		}

		try {
			note = new Note(null, "testing note");
			fail();
		} catch (IllegalArgumentException e) {
			// check if anything was added to the array
			assertEquals(2, note2.length);
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.Note#getNoteText()}.
	 */
	@Test
	public void testGetNoteText() {
		// valid note author
		note = new Note("sarah", "testing note");
		assertTrue("testing note".equals(note.getNoteText()));
		String[] note2 = note.getNoteArray();
		assertEquals(2, note2.length);

		// invalid notes
		try {
			note = new Note("Sarah", "");
			fail();
		} catch (IllegalArgumentException e) {
			// check if anything was added to the array
			assertEquals(2, note2.length);

		}

		try {
			note = new Note("sarah", null);
			fail();
		} catch (IllegalArgumentException e) {
			// check if anything was added to the array
			assertEquals(2, note2.length);
		}

	}

	/**
	 * Test method for getNoteArray
	 * {@link edu.ncsu.csc216.backlog.model.task.Note#getNoteArray()}.
	 */
	@Test
	public void testGetNoteArray() {
		//valid array 
		note = new Note("sarah", "testing note");
		String[] note2 = note.getNoteArray();
		assertTrue(note2[0].equals("sarah"));
		assertTrue(note2[1].equals("testing note"));
		assertEquals(2, note2.length);
	}

}
