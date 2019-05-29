/**
 * 
 */
package edu.ncsu.csc216.backlog.model.scrum_backlog;

import java.util.ArrayList;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.task.TaskItem;
import edu.ncsu.csc216.backlog.model.task.TaskItem.Type;

import edu.ncsu.csc216.task.xml.TaskIOException;

import edu.ncsu.csc216.task.xml.TaskReader;
import edu.ncsu.csc216.task.xml.TaskWriter;

/**
 * ScrumBacklogModel controls the creation and modification of TaskItemLists.
 * Provides info to the GUI
 * 
 * @author sarahworley
 *
 */
public class ScrumBacklogModel {

	private static ScrumBacklogModel instance;
	private TaskItemList taskItemList;

	/**
	 * Constructor for ScrumBacklogModel
	 * 
	 */
	private ScrumBacklogModel() {
		this.taskItemList = new TaskItemList();
	}

	/**
	 * single instance of ScrumBacklogModel
	 * 
	 * @return ScrumBacklogModel instance of ScrumBacklogModel
	 */
	public static ScrumBacklogModel getInstance() {
		if (instance == null) {
			instance = new ScrumBacklogModel();
		}
		return instance;

	}

	/**
	 * Saves the current TaskItemList to a file
	 * 
	 * @param filename Name of the file to save TaskItemList to.
	 *
	 */
	public void saveTasksToFile(String filename) {
		try {
			TaskWriter write = new TaskWriter(filename);
			for (int i = 0; i < taskItemList.getTaskItems().size(); i++) {
				write.addItem(taskItemList.getTaskItems().get(i).getXMLTask());
				write.marshal();
			}
		} catch (TaskIOException e) {
			throw new IllegalArgumentException("Unable to save task file.");
		}

	}

	/**
	 * loads a TaskItemList from a file.
	 * 
	 * @param filename that is being read
	 */
	public void loadTasksFromFile(String filename) {
		try {
			TaskReader read = new TaskReader(filename);
			taskItemList.addXMLTasks(read.getTasks());
		} catch (TaskIOException e) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Creates a new TaskItemList.
	 */
	public void createNewTaskItemList() {
		taskItemList = new TaskItemList();
	}

	/**
	 * returning a 2D array of TaskLists
	 * 
	 * @return 2D Object array
	 */
	public Object[][] getTaskItemListAsArray() {
		Object[][] list = new Object[taskItemList.getTaskItems().size()][3];
		for (int i = 0; i < list.length; i++) {
			list[i][0] = taskItemList.getTaskItems().get(i).getTaskItemId();
			list[i][1] = taskItemList.getTaskItems().get(i).getStateName();
			list[i][2] = taskItemList.getTaskItems().get(i).getTitle();
		}
		return list;
	}

	/**
	 * returns a 2d array of tasklists sorted by owner
	 * 
	 * @param owner Name used to sort with
	 * @return 2D Object array
	 */
	public Object[][] getTaskItemListByOwnerAsArray(String owner) {
		if (owner == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<TaskItem> list = (ArrayList<TaskItem>) taskItemList.getTaskItemsByOwner(owner);
		Object[][] ownerList = new Object[list.size()][3];
		for (int i = 0; i < ownerList.length; i++) {
			ownerList[i][0] = list.get(i).getTaskItemId();
			ownerList[i][1] = list.get(i).getStateName();
			ownerList[i][2] = list.get(i).getTitle();
		}
		return ownerList;
	}

	/**
	 * returns a 2d array of tasklists sorted by creator
	 * 
	 * @param creator Name used to sort with
	 * @return 2D Object array
	 */
	public Object[][] getTaskItemListByCreatorAsArray(String creator) {
		if (creator == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<TaskItem> list = (ArrayList<TaskItem>) taskItemList.getTaskItemsByCreator(creator);
		Object[][] creatorList = new Object[list.size()][3];
		for (int i = 0; i < creatorList.length; i++) {
			creatorList[i][0] = list.get(i).getTaskItemId();
			creatorList[i][1] = list.get(i).getStateName();
			creatorList[i][2] = list.get(i).getTitle();
		}
		return creatorList;
	}

	/**
	 * uses id to return specified taskitem
	 * 
	 * @param id ID number of the TaskItem
	 * @return TaskItem with said id
	 */
	public TaskItem getTaskItemById(int id) {
		return taskItemList.getTaskItemById(id);
	}

	/**
	 * preforms Command on TaskItem with specified id
	 * 
	 * @param id      ID number of TaskItem.
	 * @param command Command to execute on TaskItem
	 */
	public void executeCommand(int id, Command command) {
		taskItemList.executeCommand(id, command);
	}

	/**
	 * Deletes the TaskItem with specified id
	 * 
	 * @param id ID number of TaskItem to delete.
	 */
	public void deleteTaskItemById(int id) {
		taskItemList.deleteTaskItemById(id);
	}

	/**
	 * Adds a TaskItem to TaskItemList using the following parameters
	 * 
	 * @param title   Title of TaskItem.
	 * @param type    Type of TaskItem.
	 * @param creator Creator of TaskItem.
	 * @param note    Note of TaskItem.
	 */
	public void addTaskItemToList(String title, Type type, String creator, String note) {
		taskItemList.addTaskItem(title, type, creator, note);
	}

}
