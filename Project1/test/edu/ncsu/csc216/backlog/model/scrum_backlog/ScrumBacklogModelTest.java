/**
 * 
 */
package edu.ncsu.csc216.backlog.model.scrum_backlog;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.backlog.model.task.TaskItem;

/**
 * Tests the ScrumBacklogModel class
 * 
 * @author sarahworley
 *
 */
public class ScrumBacklogModelTest {
	/** Instance for scrumbacklog model */
	ScrumBacklogModel instance = ScrumBacklogModel.getInstance();

	/**
	 * Sets up the new Instance of backlog everytime
	 * 
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		instance.createNewTaskItemList();
	}

	/**
	 * Test method for LoadTasksFromFile
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#loadTasksFromFile(java.lang.String)}.
	 */
	@Test
	public void testLoadTasksFromFile() {
		instance.loadTasksFromFile("test-files/tasks_valid.xml");
		assertEquals(6, instance.getTaskItemListAsArray().length);
		assertEquals(5, instance.getTaskItemListAsArray()[4][0]);
		assertEquals(TaskItem.DONE_NAME, instance.getTaskItemListAsArray()[4][1]);
		assertEquals("Special Carts", instance.getTaskItemListAsArray()[4][2]);
	}

	/**
	 * Test method for SaveTasksToFile
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#saveTasksToFile(java.lang.String)}.
	 */
	@Test
	public void testSaveTasksToFile() {
		instance.loadTasksFromFile("test-files/tasks_valid.xml");
		instance.saveTasksToFile("test-files/act_tasks_valid.xml");
		instance.createNewTaskItemList();
		assertEquals(0, instance.getTaskItemListAsArray().length);
	}

	/**
	 * Test method for GetTaskItemListByOwnerAsArray
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#getTaskItemListByOwnerAsArray(java.lang.String)}.
	 */
	@Test
	public void testGetTaskItemListByOwnerAsArray() {
		Command commandClaim = new Command(CommandValue.CLAIM, "anyone", "i am claiming");
		instance.createNewTaskItemList();
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		instance.executeCommand(1, commandClaim);
		instance.addTaskItemToList("task2", TaskItem.Type.BUG, "sarah", "note2");
		instance.addTaskItemToList("task3", TaskItem.Type.BUG, "sarah", "note3");
		instance.addTaskItemToList("task4", TaskItem.Type.BUG, "anyone", "note3");
		//assertEquals(3, instance.getTaskItemListByOwnerAsArray("sarah").length);
		assertEquals(1, instance.getTaskItemListByOwnerAsArray("anyone").length);
	}

	/**
	 * Test method for GetTaskItemListByCreatorAsArray
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#getTaskItemListByCreatorAsArray(java.lang.String)}.
	 */
	@Test
	public void testGetTaskItemListByCreatorAsArray() {
		instance.createNewTaskItemList();
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		instance.addTaskItemToList("task2", TaskItem.Type.BUG, "sarah", "note2");
		instance.addTaskItemToList("task3", TaskItem.Type.BUG, "sarah", "note3");
		instance.addTaskItemToList("task4", TaskItem.Type.BUG, "anyone", "note3");
		assertEquals(3, instance.getTaskItemListByCreatorAsArray("sarah").length);
	}

	/**
	 * Test method for GetTaskItemById
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#getTaskItemById(int)}.
	 */
	@Test
	public void testGetTaskItemById() {
		instance.createNewTaskItemList();
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		instance.addTaskItemToList("task2", TaskItem.Type.BUG, "sarah", "note2");
		instance.addTaskItemToList("task3", TaskItem.Type.BUG, "sarah", "note3");
		instance.addTaskItemToList("task4", TaskItem.Type.BUG, "anyone", "note3");
		assertEquals("task1", instance.getTaskItemById(1).getTitle());
	}

	/**
	 * Test method for ExecuteCommand
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#executeCommand(int, edu.ncsu.csc216.backlog.model.command.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		instance.createNewTaskItemList();
		Command commandClaim = new Command(CommandValue.CLAIM, "anyone", "i am claiming");
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		instance.executeCommand(1, commandClaim);
		assertEquals(TaskItem.OWNED_NAME, instance.getTaskItemListAsArray()[0][1]);
		Command commandProcessing = new Command(CommandValue.PROCESS, "anyone1", "i am processing");
		instance.executeCommand(1, commandProcessing);
		assertEquals(TaskItem.PROCESSING_NAME, instance.getTaskItemListAsArray()[0][1]);
		
	}

	/**
	 * Test method for DeleteTaskItemById
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#deleteTaskItemById(int)}.
	 */
	@Test
	public void testDeleteTaskItemById() {
		instance.createNewTaskItemList();
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		instance.addTaskItemToList("task2", TaskItem.Type.BUG, "sarah", "note2");
		instance.addTaskItemToList("task3", TaskItem.Type.BUG, "sarah", "note3");
		instance.addTaskItemToList("task4", TaskItem.Type.BUG, "anyone", "note3");
		instance.deleteTaskItemById(3);

		assertEquals(3, instance.getTaskItemListAsArray().length);
	}

	/**
	 * Test method for AddTaskItemToList
	 * {@link edu.ncsu.csc216.backlog.model.scrum_backlog.ScrumBacklogModel#addTaskItemToList(java.lang.String, edu.ncsu.csc216.backlog.model.task.TaskItem.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddTaskItemToList() {
		instance.createNewTaskItemList();
		assertEquals(0, instance.getTaskItemListAsArray().length);
		instance.addTaskItemToList("task1", TaskItem.Type.BUG, "sarah", "note1");
		assertEquals(1, instance.getTaskItemListAsArray().length);
	}

}
