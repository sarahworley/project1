/**
 * 
 */
package edu.ncsu.csc216.backlog.model.task;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.command.Command.CommandValue;

import edu.ncsu.csc216.task.xml.NoteItem;
import edu.ncsu.csc216.task.xml.NoteList;
import edu.ncsu.csc216.task.xml.Task;

/**
 * Tests the task item class
 * 
 * @author sarahworley
 *
 */
public class TaskItemTest {

	/** TaskItem object */
	TaskItem item;
	
	


	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#TaskItem(java.lang.String, edu.ncsu.csc216.backlog.model.task.TaskItem.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testTaskItemStringTypeStringString() {
		// valid task item
		TaskItem.setCounter(1);
		TaskItem item0 = new TaskItem("task1", TaskItem.Type.FEATURE, "sarah", "note");
		assertEquals(1, item0.getTaskItemId());
		assertEquals("task1", item0.getTitle());
		assertEquals(TaskItem.Type.FEATURE , item0.getType());
		assertEquals("sarah" , item0.getCreator());
		assertEquals("note", item0.getNotesArray()[0][1]);
		assertEquals("sarah" , item0.getNotesArray()[0][0]);
		
		item = new TaskItem("task2", TaskItem.Type.FEATURE, "sarah2", "note2");
		assertEquals(2, item.getTaskItemId());
		assertEquals("task2", item.getTitle());
		assertEquals(TaskItem.Type.FEATURE , item.getType());
		assertEquals("sarah2" , item.getCreator());
		assertEquals("note2", item.getNotesArray()[0][1]);
		assertEquals("sarah2" , item.getNotesArray()[0][0]);
 
		// invalid task item
		try {
			item = new TaskItem("", TaskItem.Type.FEATURE, "sarah", "info");
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}
		try {
			item = new TaskItem(null, TaskItem.Type.FEATURE, "sarah", "info");
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}

		try {
			item = new TaskItem("task1", TaskItem.Type.FEATURE, "", "info");
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}

		try {
			item = new TaskItem("task1", TaskItem.Type.TECHNICAL_WORK, null, "info");
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}

		try {
			item = new TaskItem("task1", TaskItem.Type.KNOWLEDGE_ACQUISITION, "sarah", "");
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}

		try {
			item = new TaskItem("task1", TaskItem.Type.BUG, "sarah", null);
			fail();
		} catch (IllegalArgumentException e) {
			// makes sure nothing was added in the notes array, no new task was created
			assertEquals(1, item.getNotesArray().length);

		}

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#TaskItem(edu.ncsu.csc216.task.xml.Task)}.
	 */
	@Test
	public void testTaskItemTask() {
		// valid task
		Task task = new Task();
		task.setId(2);
		task.setTitle("task2");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.REJECTED_NAME);
		task.setType("F");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);

		// checks if everything was added correctly
		assertEquals("task2", item.getTitle());
		assertEquals("sarah", item.getCreator());
		assertEquals("F", item.getTypeString());
		assertEquals(2, item.getTaskItemId());
		assertEquals("Feature", item.getTypeFullString());
		assertEquals("someone", item.getNotesArray()[0][0]);
		assertEquals("rejected", item.getNotesArray()[0][1]);
		assertEquals("Rejected", item.getStateName());
		assertEquals(1, item.getNotes().size());
		task.setState(TaskItem.OWNED_NAME);
		assertEquals("sarah", item.getOwner());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getTaskItemId()}.
	 */
	@Test
	public void testGetTaskItemId() {

		Task task = new Task();
		task.setId(7);
		task.setTitle("task2");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.REJECTED_NAME);
		task.setType("F");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);

		assertEquals(7, item.getTaskItemId());
	}

	/**
	 * Test method for GetStateName
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getStateName()}.
	 */
	@Test
	public void testGetStateName() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task2");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("F");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("Backlog", item.getStateName());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getType()}.
	 */
	@Test
	public void testGetType() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task2");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("B");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("Bug", item.getTypeFullString());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getOwner()}.
	 */
	@Test
	public void testGetOwner() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task2");
		task.setCreator("sarah");
		task.setOwner("someone else");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("KA");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);

		assertEquals("someone else", item.getOwner());
		assertEquals("KA", item.getTypeString());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getTitle()}.
	 */
	@Test
	public void testGetTitle() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task400");
		task.setCreator("sarah");
		task.setOwner("someone else");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("TW");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("task400", item.getTitle());
		assertEquals("TW", item.getTypeString());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getCreator()}.
	 */
	@Test
	public void testGetCreator() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task400");
		task.setCreator("sarah");
		task.setOwner("someone else");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("B");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("sarah", item.getCreator());
		assertEquals("B", item.getTypeString());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getNotes()}.
	 */
	@Test
	public void testGetNotes() {
		Task task = new Task();
		task.setId(2);
		task.setTitle("task400");
		task.setCreator("sarah");
		task.setOwner("someone else");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("B");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");

		// adds the note to the list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("rejected", item.getNotesArray()[0][1]);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#update(edu.ncsu.csc216.backlog.model.command.Command)}.
	 */
	@Test
	public void testUpdate() {
		// vaild movements through update
		// make new item check state of item
		item = new TaskItem("a task", TaskItem.Type.FEATURE, "sarah", "this is a note");
		assertEquals(TaskItem.BACKLOG_NAME, item.getStateName());
		// make a command. update the item with command check if item was claimed and is
		// owned with new note
		Command commandClaim = new Command(CommandValue.CLAIM, "anyone", "i am claiming");
		item.update(commandClaim);
		assertEquals(TaskItem.OWNED_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am claiming", item.getNotesArray()[1][1]);
		// move task to processing. verify state, owner and note added
		Command commandProcessing = new Command(CommandValue.PROCESS, "anyone1", "i am processing");
		item.update(commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am processing", item.getNotesArray()[2][1]);
		// move task to verify. verify state, owner and note added
		Command commandVerify = new Command(CommandValue.VERIFY, "anyone2", "i am verifying");
		item.update(commandVerify);
		assertEquals(TaskItem.VERIFYING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am verifying", item.getNotesArray()[3][1]);
		// move task to done. verify state, owner and note added
		Command commandDone = new Command(CommandValue.COMPLETE, "anyone3", "i am done");
		item.update(commandDone);
		assertEquals(TaskItem.DONE_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am done", item.getNotesArray()[4][1]);

		// moves form done back to processing
		item.update(commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am processing", item.getNotesArray()[5][1]);

		// moves form processing to processing 
		item.update(commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am processing", item.getNotesArray()[6][1]);
		//processing to verify 
		item.update(commandVerify);
		assertEquals(TaskItem.VERIFYING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		//verify back to processing 
		item.update(commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		
		//Processing to back log 
		Command commandBacklog = new Command(CommandValue.BACKLOG, "anyone1", "i am backlogging");
		item.update(commandBacklog);
		assertEquals(TaskItem.BACKLOG_NAME , item.getStateName());
		// no owner
		assertEquals(null, item.getOwner());
		// new note was added
		assertEquals("i am backlogging", item.getNotesArray()[9][1]);
		
		

		// new task
		item = new TaskItem("a task", TaskItem.Type.BUG, "sarah", "this is a note");
		assertEquals(TaskItem.BACKLOG_NAME, item.getStateName());
		Command commandReject = new Command(CommandValue.REJECT, "anyone", "i am rejecting");
		item.update(commandReject);
		assertEquals(TaskItem.REJECTED_NAME , item.getStateName());
		// null owner since rejected
		assertEquals(null, item.getOwner());
		// new note was added
		assertEquals("i am rejecting", item.getNotesArray()[1][1]);
		// sends task back to backlog
		item.update(commandBacklog);
		assertEquals(TaskItem.BACKLOG_NAME , item.getStateName());
		// no owner
		assertEquals(null, item.getOwner());
		// new note was added
		assertEquals("i am backlogging", item.getNotesArray()[2][1]);
		
		//new task 
		item = new TaskItem("task4", TaskItem.Type.BUG, "sarah", "this is a note");
		assertEquals(TaskItem.BACKLOG_NAME, item.getStateName());
		item.update(commandClaim);
		assertEquals(TaskItem.OWNED_NAME , item.getStateName());
		assertEquals("i am claiming", item.getNotesArray()[1][1]);
		//move to processing
		item.update(commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		// new note was added
		assertEquals("i am processing", item.getNotesArray()[2][1]);
		//move to verify
		item.update(commandVerify);
		assertEquals(TaskItem.VERIFYING_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		//verify to done
		item.update(commandDone);
		assertEquals(TaskItem.DONE_NAME , item.getStateName());
		assertEquals("anyone", item.getOwner());
		//done back to back log 
		item.update(commandBacklog);
		assertEquals(TaskItem.BACKLOG_NAME , item.getStateName());
		// no owner
		assertEquals(null, item.getOwner());
		// new note was added
		assertEquals("i am backlogging", item.getNotesArray()[5][1]);
		//to owned
		item.update(commandClaim);
		assertEquals(TaskItem.OWNED_NAME , item.getStateName());
		assertEquals("i am claiming", item.getNotesArray()[6][1]);
		assertEquals("anyone", item.getOwner());
		//back to back log
		item.update(commandBacklog);
		assertEquals(TaskItem.BACKLOG_NAME , item.getStateName());
		// no owner
		assertEquals(null, item.getOwner());
		//back to claim 
		item.update(commandClaim);
		assertEquals(TaskItem.OWNED_NAME , item.getStateName());
		assertEquals("i am claiming", item.getNotesArray()[8][1]);
		assertEquals("anyone", item.getOwner());
		//reject from owned
		item.update(commandReject);
		assertEquals(TaskItem.REJECTED_NAME , item.getStateName());
		// null owner since rejected
		assertEquals(null, item.getOwner());
		// new note was added
		assertEquals("i am rejecting", item.getNotesArray()[9][1]);
		
		
	}

	/**
	 * Test method for GetXMLTask
	 * {@link edu.ncsu.csc216.backlog.model.task.TaskItem#getXMLTask()}.
	 */
	@Test
	public void testGetXMLTask() {
		Task task = new Task();
		task.setId(5);
		task.setTitle("task5");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.REJECTED_NAME);
		task.setType("F");
		task.setVerified(false);

		// task note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("someone");
		note.setNoteText("rejected");
		// adds note to list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		item = new TaskItem(task);
		assertEquals("task5", item.getXMLTask().getTitle());

	}

}
