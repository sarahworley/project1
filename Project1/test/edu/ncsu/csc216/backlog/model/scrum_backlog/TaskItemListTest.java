/**
 * 
 */
package edu.ncsu.csc216.backlog.model.scrum_backlog;

import static org.junit.Assert.*;

import java.util.ArrayList;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.backlog.model.task.TaskItem;
import edu.ncsu.csc216.task.xml.NoteItem;
import edu.ncsu.csc216.task.xml.NoteList;
import edu.ncsu.csc216.task.xml.Task;

import org.junit.Test;

/**
 * Tests the TaskItemList class
 * 
 * @author sarahworley
 *
 */
public class TaskItemListTest {

	/** TaskItemList to hold TaskItem Objects */
	TaskItemList list;

	/** TaskItem object */
	TaskItem item;

	/**
	 * Test method for TaskItemList
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#TaskItemList()}.
	 */
	@Test
	public void testTaskItemList() {
		list = new TaskItemList();
		assertEquals(0, list.getTaskItems().size());
	}

	/**
	 * Test method for AddTaskItem
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#addTaskItem(java.lang.String, edu.ncsu.csc216.backlog.model.task.TaskItem.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddTaskItem() {
		list = new TaskItemList();
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "task1 note");
		assertEquals(1, list.getTaskItems().size());

	}

	/**
	 * Test method for AddXMLTask
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#addXMLTasks(java.util.List)}.
	 */
	@Test
	public void testAddXMLTasks() {
		// make a xml task
		Task task = new Task();
		task.setId(1);
		task.setTitle("task1");
		task.setCreator("sarah");
		task.setOwner("sarah");
		task.setState(TaskItem.BACKLOG_NAME);
		task.setType("F");
		task.setVerified(false);

		// adds the note
		NoteItem note = new NoteItem();
		note.setNoteAuthor("anyone");
		note.setNoteText("note for task1");

		// adds note to list
		NoteList noteList = new NoteList();
		noteList.getNotes().add(note);
		task.setNoteList(noteList);
		// all the tasks
		ArrayList<Task> tasks = new ArrayList<Task>();
		// add task to the list
		tasks.add(task);
		list = new TaskItemList();
		list.addXMLTasks(tasks);
		assertEquals(1, list.getTaskItems().size());
	}

	/**
	 * Test method for GetTaskItems
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#getTaskItems()}.
	 */
	@Test
	public void testGetTaskItems() {
		// makes a list. adds item to list. checks size of list
		list = new TaskItemList();
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "task1 note");
		assertEquals(1, list.getTaskItems().size());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#getTaskItemsByOwner(java.lang.String)}.
	 */
	@Test
	public void testGetTaskItemsByOwner() {
		list = new TaskItemList();
		// adds tasks with different owners
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task2", TaskItem.Type.FEATURE, "sarah", "task2 note");
		list.addTaskItem("task3", TaskItem.Type.FEATURE, "anyone", "task3 note");
		list.addTaskItem("task4", TaskItem.Type.FEATURE, "sarah", "info");
		// makes sure there are 3 tasks owned by sarah
		Command commandClaim = new Command(CommandValue.CLAIM, "anyone", "i am claiming");
		list.getTaskItemById(1).update(commandClaim);
		assertEquals(1, list.getTaskItemsByOwner("anyone").size());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#getTaskItemsByCreator(java.lang.String)}.
	 */
	@Test
	public void testGetTaskItemsByCreator() {
		list = new TaskItemList();
		// adds tasks with different creators
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task2", TaskItem.Type.FEATURE, "sarah", "task2 note");
		list.addTaskItem("task4", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task3", TaskItem.Type.FEATURE, "anyone", "task3 note");
		
		
		// makes sure there are 3 tasks created by sarah
		assertEquals(3, list.getTaskItemsByCreator("sarah").size());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#getTaskItemById(int)}.
	 */
	@Test
	public void testGetTaskItemById() {
		list = new TaskItemList();
		// adds tasks
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task2", TaskItem.Type.FEATURE, "sarah", "task2 note");
		list.addTaskItem("task4", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task3", TaskItem.Type.FEATURE, "anyone", "task3 note");

		assertTrue(list.getTaskItemById(1).getTitle().equals("task1"));

		assertTrue(list.getTaskItemById(3).getTitle().equals("task4"));

	}

	/**
	 * Test method for ExecuteCommand
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#executeCommand(int, edu.ncsu.csc216.backlog.model.command.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		Command commandReject = new Command(CommandValue.REJECT, "anyone", "i am rejecting");
		Command commandClaim = new Command(CommandValue.CLAIM, "anyone", "i am claiming");
		list = new TaskItemList();
		// adds tasks
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task2", TaskItem.Type.FEATURE, "sarah", "task2 note");
		list.addTaskItem("task4", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task3", TaskItem.Type.FEATURE, "anyone", "task3 note");

		assertTrue(TaskItem.BACKLOG_NAME == list.getTaskItemById(1).getStateName());
		list.getTaskItemById(1).update(commandReject);
		assertTrue(TaskItem.REJECTED_NAME == list.getTaskItemById(1).getStateName());

		assertTrue(TaskItem.BACKLOG_NAME == list.getTaskItemById(4).getStateName());
		list.getTaskItemById(4).update(commandClaim);
		assertTrue(TaskItem.OWNED_NAME == list.getTaskItemById(4).getStateName());
	}

	/**
	 * Test method for DeleteTaskItemById
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.TaskItemList#deleteTaskItemById(int)}.
	 */
	@Test
	public void testDeleteTaskItemById() {

		list = new TaskItemList();

		// adds tasks
		list.addTaskItem("task1", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task2", TaskItem.Type.FEATURE, "sarah", "task2 note");
		list.addTaskItem("task4", TaskItem.Type.FEATURE, "sarah", "info");
		list.addTaskItem("task3", TaskItem.Type.FEATURE, "anyone", "task3 note");
		list.deleteTaskItemById(4);
		assertEquals(3, list.getTaskItems().size());
	}

}
