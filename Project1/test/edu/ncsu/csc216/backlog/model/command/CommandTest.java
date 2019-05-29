/**
 * 
 */
package edu.ncsu.csc216.backlog.model.command;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.backlog.model.command.Command.CommandValue;

/**
 * Tests the command class
 * 
 * @author sarahworley
 *
 */
public class CommandTest {

	private Command c;

	/**
	 * Test method for constructor 
	 * {@link edu.ncsu.csc216.backlog.model.command.Command#Command(edu.ncsu.csc216.backlog.model.command.Command.CommandValue, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCommand() {
		// valid command
		c = new Command(CommandValue.BACKLOG, "sarah", "testing note");
		assertTrue(CommandValue.BACKLOG == c.getCommand());
		assertTrue("testing note".equals(c.getNoteText()));
		assertTrue(c.getNoteAuthor().equals("sarah"));

	}

	/**
	 * Test method for getCommand
	 * {@link edu.ncsu.csc216.backlog.model.command.Command#getCommand()}.
	 */
	@Test
	public void testGetCommand() {
		// valid command
		c = new Command(CommandValue.PROCESS, "sarah", "testing note");
		assertTrue(CommandValue.PROCESS == c.getCommand());

		// invalid Command. makes sure command wasn't changed.
		try {
			c = new Command(null, "sarah", "testing note");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(c.getCommand() == CommandValue.PROCESS);
		}

	}

	/**
	 * Test method for getNoteText
	 * {@link edu.ncsu.csc216.backlog.model.command.Command#getNoteText()}.
	 */
	@Test
	public void testGetNoteText() {
		// vaild note text
		c = new Command(CommandValue.PROCESS, "sarah", "testing note 2");
		assertTrue("testing note 2".equals(c.getNoteText()));

		// invalid note text. makes sure command wasn't changed.
		try {
			c = new Command(CommandValue.PROCESS, "sarah", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(c.getCommand() == CommandValue.PROCESS);
		}

		try {
			c = new Command(CommandValue.PROCESS, "sarah", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(c.getCommand() == CommandValue.PROCESS);
		}

	}

	/**
	 * Test method for getNoteAuthor
	 * {@link edu.ncsu.csc216.backlog.model.command.Command#getNoteAuthor()}.
	 */
	@Test
	public void testGetNoteAuthor() {
		// valid author
		c = new Command(CommandValue.PROCESS, "sarah 2", "testing note");
		assertTrue(c.getNoteAuthor().equals("sarah 2"));

		// invalid note author. makes sure command wasn't changed.
		try {
			c = new Command(CommandValue.PROCESS, "", "testing note");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(c.getCommand() == CommandValue.PROCESS);
		}

		// invalid note author. makes sure command wasn't changed.
		try {
			c = new Command(CommandValue.PROCESS, null, "testing note");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(c.getCommand() == CommandValue.PROCESS);
		}

	}

}
