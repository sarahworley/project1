/**
 * 
 */
package edu.ncsu.csc216.backlog.model.scrum_backlog;

import java.util.ArrayList;

import java.util.List;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.task.TaskItem;
import edu.ncsu.csc216.backlog.model.task.TaskItem.Type;
import edu.ncsu.csc216.task.xml.Task;

/**
 * TaskItemList maintains a List of TaskItems
 * 
 * @author sarahworley
 *
 */
public class TaskItemList {

	List<TaskItem> tasks = new ArrayList<TaskItem>();

	static final int INITIAL_COUNTER_VALUE = 1;

	/**
	 * Constructor for the TaskItemList .
	 */
	public TaskItemList() {
		this.tasks = emptyList();
	}

	/**
	 * empties the contents of the current TaskItemList.
	 */
	private List<TaskItem> emptyList() {
		TaskItem.setCounter(TaskItemList.INITIAL_COUNTER_VALUE);
		return new ArrayList<TaskItem>();
	}

	/**
	 * Adds a TaskItem to the TaskItemList using the parameters:
	 * 
	 * @param title   Title of TaskItem
	 * @param type    Type of TaskItem
	 * @param creator Creator of TaskItem
	 * @param note    Note for the TaskItem.
	 * @return The unique ID number of TaskItem.
	 */
	public int addTaskItem(String title, Type type, String creator, String note) {
		TaskItem task = new TaskItem(title, type, creator, note);
		tasks.add(task);
		return task.getTaskItemId();
	}

	/**
	 * Add's a list of Tasks to TaskItemList.
	 * 
	 * @param tasklist List of Task's to added to TaskItemList
	 */
	public void addXMLTasks(List<Task> tasklist) {
		int maxId = 0;
		for(int i = 0; i < tasklist.size(); i++) {
			tasks.add(new TaskItem(tasklist.get(i)));
			if( tasklist.get(i).getId() > maxId) {
				maxId = tasklist.get(i).getId();
			}
		}
		TaskItem.setCounter(maxId + 1);
	}

	/**
	 * returns the current list of TaskItems.
	 * 
	 * @return The list of TaskItems.
	 */
	public List<TaskItem> getTaskItems() {
		
		return this.tasks;
	}

	/**
	 * returns a list of TaskItems. sorted by the owner
	 * 
	 * @param owner Owner of the TaskItem.
	 * @return List of TaskItems
	 */
	public List<TaskItem> getTaskItemsByOwner(String owner) {
		if (owner == null || owner.equals("")) {
			throw new IllegalArgumentException();
		}

		List<TaskItem> ownerList = new ArrayList<TaskItem>();
		TaskItem task = null;
		
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getOwner() != null && tasks.get(i).getOwner().equals(owner)) {
				task = tasks.get(i);
				ownerList.add(task);
				//return ownerList;
			}
		}
		return ownerList;

	}

	/**
	 * returns a list of TaskItems. Sorted by creator
	 * 
	 * @param creator Creator of the TaskItem.
	 * @return List of TaskItems
	 */
	public List<TaskItem> getTaskItemsByCreator(String creator) {
		if (creator == null || creator.equals("")) {
			throw new IllegalArgumentException();
		}

		List<TaskItem> creatorList = new ArrayList<TaskItem>();
		TaskItem task = null;
		
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getCreator().equals(creator)) {
				task = tasks.get(i);
				creatorList.add(task);
				//return creatorList;
			}
		}
		return creatorList;
	}

	/**
	 * returns a taskItem of a specified id
	 * 
	 * @param id of the TaskItem
	 * @return TaskItem with the id
	 */
	public TaskItem getTaskItemById(int id) {
		TaskItem task = null;
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskItemId() == id) {
				task = tasks.get(i);
				return task;
			}

		}
		return null;

	}

	/**
	 * Changed the Taskitem with specified command
	 * 
	 * @param id      ID of the TaskItem.
	 * @param command to perform on the TaskItem.
	 */
	public void executeCommand(int id, Command command) {
		TaskItem a = getTaskItemById(id);
		if (a != null ) {
			getTaskItemById(id).update(command);
		}
	}

	/**
	 * Deletes the TaskItem using the id
	 * 
	 * @param id ID of the TaskItem
	 */
	public void deleteTaskItemById(int id) {
		List<TaskItem> list = this.getTaskItems();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTaskItemId() == id) {
				list.remove(list.get(i));
				this.tasks = list;
			}
		}

	}

}
