
package edu.ncsu.csc216.backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.backlog.model.command.Command;
import edu.ncsu.csc216.backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.task.xml.NoteItem;
import edu.ncsu.csc216.task.xml.NoteList;
import edu.ncsu.csc216.task.xml.Task;

/**
 * The TaskItem class is the implementation of the Task FSM
 * 
 * @author sarahworley
 *
 */
public class TaskItem {

	/** id for the task */
	private int taskId;
	/** state of the task item */
	private TaskItemState state;
	/** title for the task */
	private String title;
	/** creator for the task */
	private String creator;
	/** owner for the task */
	private String owner;
	/** if task is verified or not */
	private boolean isVerified;

	/** Backlog State Object */
	private final TaskItemState backlogState = new BacklogState();
	/** Owned State Object */
	private final TaskItemState ownedState = new OwnedState();
	/** Processing State Object */
	private final TaskItemState processingState = new ProcessingState();
	/** Verifying State Object f */
	private final TaskItemState verifyingState = new VerifyingState();
	/** Done State Object */
	private final TaskItemState doneState = new DoneState();
	/** Rejected State Object */
	private final TaskItemState rejectedState = new RejectedState();

	/** Name of Backlog State */
	public static final String BACKLOG_NAME = "Backlog";
	/** Name of Owned State */
	public static final String OWNED_NAME = "Owned";
	/** Name of Backlog State */
	public static final String PROCESSING_NAME = "Processing";
	/** Name of Processing State */
	public static final String VERIFYING_NAME = "Verifying";
	/** Name of Done State */
	public static final String DONE_NAME = "Done";
	/** Name of Rejected State */
	public static final String REJECTED_NAME = "Rejected";
	/** Feature Type of TaskItem */
	static final String T_FEATURE = "F";
	/** Bug Type of TaskItem */
	static final String T_BUG = "B";
	/** Technical Work Type of TaskItem */
	static final String T_TECHNICAL_WORK = "TW";
	/** Knowledge Acquisition Type of TaskItem */
	static final String T_KNOWLEDGE_ACQUISITION = "KA";
	/** Counter for unique ID number assignment */
	static int counter = 1;

	/** Lists possible task types */
	public enum Type { FEATURE, BUG, TECHNICAL_WORK, KNOWLEDGE_ACQUISITION }

	/** the type of task */
	private Type type;
	/** Array list of notes */
	private ArrayList<Note> notes = new ArrayList<Note>();

	/**
	 * Task Item constructor. Constructs a new task item with the following
	 * parameters:
	 * 
	 * @param title   title of task
	 * @param type    type of task
	 * @param creator creator of task
	 * @param note    not to go along with task
	 */
	public TaskItem(String title, Type type, String creator, String note) {
		if (title == null || title.equals("") || creator == null || creator.equals("") || note == null || note.equals("")) {
			throw new IllegalArgumentException();
		}
		this.title = title;

		if (type == Type.FEATURE) {
			setType(T_FEATURE);
		} else if (type == Type.BUG) {
			setType(T_BUG);
		} else if (type == Type.KNOWLEDGE_ACQUISITION) {
			setType(T_KNOWLEDGE_ACQUISITION);
		} else if (type == Type.TECHNICAL_WORK) {
			setType(T_TECHNICAL_WORK);
		} else {
			throw new IllegalArgumentException();
		}

		this.creator = creator;
		this.notes.add(new Note(creator, note));
		this.state = backlogState;

		this.taskId = counter;
		TaskItem.setCounter(counter);
		TaskItem.incrementCounter();
	}

	/**
	 * Creates a taskItem from a task
	 * 
	 * @param task task thats is used to create the task item
	 */
	public TaskItem(Task task) {
		String title1 = task.getTitle();
		String string1 = task.getType();
		String creator1 = task.getCreator();
		String state1 = task.getState();
		String owner1 = task.getOwner();
		taskId = task.getId();

		ArrayList<NoteItem> notelist = (ArrayList<NoteItem>) task.getNoteList().getNotes();
		for (int i = 0; i < notelist.size(); i++) {
			notes.add(new Note(notelist.get(i).getNoteAuthor(), notelist.get(i).getNoteText()));
		}

		Type type1 = null;
		if (string1.equals(T_KNOWLEDGE_ACQUISITION)) {
			type1 = Type.KNOWLEDGE_ACQUISITION;
		}
		if (string1.equals(T_TECHNICAL_WORK)) {
			type1 = Type.TECHNICAL_WORK;
		}
		if (string1.equals(T_BUG)) {
			type1 = Type.BUG;
		}
		if (string1.equals(T_FEATURE)) {
			type1 = Type.FEATURE;
		}
		setState(state1);
		this.title = title1;
		this.type = type1;
		this.creator = creator1;
		this.owner = owner1;

	}

	/**
	 * Increments the counter by one each time a new TaskItem is constructed.
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * returns the task items id
	 * 
	 * @return id
	 */
	public int getTaskItemId() {
		return this.taskId;
	}

	/**
	 * returns the task items state name
	 * 
	 * @return state name
	 */
	public String getStateName() {
		return state.getStateName();
	}

	/**
	 * sets the task items state
	 * 
	 */
	private void setState(String stateValue) {
		if (stateValue == null || stateValue.equals("")) {
			throw new IllegalArgumentException();
		}
		// uses string representation of state to set state
		switch (stateValue) {
		case BACKLOG_NAME:
			this.state = backlogState;
			break;

		case OWNED_NAME:
			this.state = ownedState;
			break;

		case PROCESSING_NAME:
			this.state = processingState;
			break;

		case VERIFYING_NAME:
			this.state = verifyingState;
			break;

		case DONE_NAME:
			this.state = doneState;
			break;

		case REJECTED_NAME:
			this.state = rejectedState;
			break;

		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * sets the task items Type
	 * 
	 */
	private void setType(String typeValue) {
		if (typeValue == null || typeValue.equals("")) {
			throw new IllegalArgumentException();
		}
		// changes short hand used in the XML into full input
		switch (typeValue) {
		case T_KNOWLEDGE_ACQUISITION:
			this.type = Type.KNOWLEDGE_ACQUISITION;
			break;
		case T_TECHNICAL_WORK:
			this.type = Type.TECHNICAL_WORK;
			break;
		case T_BUG:
			this.type = Type.BUG;
			break;
		case T_FEATURE:
			this.type = Type.FEATURE;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * returns the task items type
	 * 
	 * @return type
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * returns the task items type in a string format
	 * 
	 * @return type in string format
	 */
	public String getTypeString() {
		// changes full input into short hand used in the XML.
		switch (getType()) {

		case KNOWLEDGE_ACQUISITION:
			return T_KNOWLEDGE_ACQUISITION;

		case TECHNICAL_WORK:
			return T_TECHNICAL_WORK;

		case BUG:
			return T_BUG;

		case FEATURE:
			return T_FEATURE;

		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * returns the task items full type in a string format
	 * 
	 * @return full type in string format
	 */
	public String getTypeFullString() {
		// gets string representation of the type
		switch (getType()) {

		case KNOWLEDGE_ACQUISITION:
			return "Knowledge Acquisition";

		case TECHNICAL_WORK:
			return "Technicial Work";

		case BUG:
			return "Bug";

		case FEATURE:
			return "Feature";

		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * returns the task items owner in string format
	 * 
	 * @return owner
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * returns the task items title in string format
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * returns the task items creator in string format
	 * 
	 * @return creator
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * returns an arraylist of task items
	 * 
	 * @return notes
	 */
	public ArrayList<Note> getNotes() {
		return this.notes;

	}

	/**
	 * updates the command for the taskItem
	 * 
	 * @param command command that will be executed on the task item
	 */
	public void update(Command command) {
		state.updateState(command);
	}

	/**
	 * updates the command for the taskItem
	 * 
	 * @return task
	 */
	public Task getXMLTask() {
		Task task = new Task();
		task.setTitle(getTitle());
		task.setOwner(getOwner());
		task.setId(this.getTaskItemId());
		task.setCreator(getCreator());
		task.setState(state.getStateName());
		task.setVerified(isVerified);
		task.setType(getTypeString());

		NoteList noteList = new NoteList();

		for (int i = 0; i < notes.size(); i++) {
			NoteItem noteitem = new NoteItem();
			noteitem.setNoteAuthor(notes.get(i).getNoteAuthor());
			noteitem.setNoteText(notes.get(i).getNoteText());
			noteList.getNotes().add(noteitem);

		}
		task.setNoteList(noteList);

		return task;

	}

	/**
	 * sets the counter
	 * 
	 * @param counter and incrementer that will count the number of tasks
	 */
	public static void setCounter(int counter) {
		if (counter <= 0)
			throw new IllegalArgumentException();
		TaskItem.counter = counter;

	}

	/**
	 * Returns an array of notes
	 * 
	 * @return array of notes
	 */
	public String[][] getNotesArray() {
		String[][] note = new String[notes.size()][2];
		for (int i = 0; i < notes.size(); i++) {
			note[i][0] = notes.get(i).getNoteAuthor();
			note[i][1] = notes.get(i).getNoteText();
		}
		return note;
	}

	/**
	 * Interface for states in the Task State Pattern. All concrete task states must
	 * implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface TaskItemState {

		/**
		 * Update the {@link TaskItem} based on the given {@link Command}. An
		 * {@link UnsupportedOperationException} is throw if the {@link CommandValue} is
		 * not a valid action for the given state.
		 * 
		 * @param c {@link Command} describing the action that will update the
		 *          {@link TaskItem}'s state.
		 * @throws UnsupportedOperationException if the {@link CommandValue} is not a
		 *                                       valid action for the given state.
		 */
		void updateState(Command c);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * private class that represents the backlog state
	 * 
	 * @author sarahworley
	 *
	 */
	private class BacklogState implements TaskItemState {

		/**
		 * Constructor for back log state
		 * 
		 */
		private BacklogState() {
			isVerified = false;
			owner = null;
		}

		/*
		 * A user can claim the task as an owner [S1], reject the task [S2], or cancel
		 * the edit action [S3]. The user must provide a note. The owner is the note’s
		 * author
		 * 
		 * @param c the command
		 */
		public void updateState(Command c) {

			if (CommandValue.CLAIM == c.getCommand()) {
				owner = c.getNoteAuthor();
				setState(TaskItem.OWNED_NAME);
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));

			} else if (CommandValue.REJECT == c.getCommand()) {
				owner = null;
				setState(TaskItem.REJECTED_NAME);
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));

			} else {
				throw new UnsupportedOperationException();
			}

		}

		/*
		 * Returns a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return BACKLOG_NAME;
		}

	}

	/**
	 * private class that represents the owned state
	 * 
	 * @author sarahworley
	 *
	 */
	private class OwnedState implements TaskItemState {

		/**
		 * Constructor for back log state
		 * 
		 */
		private OwnedState() {

			isVerified = false;
			state = ownedState;
		
		
		}

		/*
		 * An owner can start processing the task [S1], reject the task as out of scope
		 * [S2], backlog the task for another developer to work on [S3], or cancel the
		 * edit action [S4]. The user must provide a note. The note’s author is the
		 * owner.
		 * 
		 * @param c the command
		 */
		public void updateState(Command c) {
			if (CommandValue.PROCESS == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.PROCESSING_NAME);
				//owner = c.getNoteAuthor();

			} else if (CommandValue.REJECT == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				owner = null;
				setState(TaskItem.REJECTED_NAME);

			} else if (CommandValue.BACKLOG == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				owner = null;
				setState(TaskItem.BACKLOG_NAME);

			} else {
				throw new UnsupportedOperationException();
			}

		}

		/*
		 * Returns a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return TaskItem.OWNED_NAME;
		}

	}

	/**
	 * private class that represents the processing state
	 * 
	 * @author sarahworley
	 *
	 */
	private class ProcessingState implements TaskItemState {

		/**
		 * Constructor for back log state
		 * 
		 */
		private ProcessingState() {

			isVerified = false;
			state = processingState;

		}

		/*
		 * A user can add a note to the task with progress on completing the task [S1],
		 * request verification of a feature, bug, or technical work task [S2], complete
		 * a knowledge acquisition task [S3], return the task to the backlog [S4], or
		 * cancel the edit action [S5]. The user must provide a note, which is authored
		 * by the owner.
		 * 
		 * @param c the command
		 */
		public void updateState(Command c) {
			if (CommandValue.VERIFY == c.getCommand() && !(getType() == Type.KNOWLEDGE_ACQUISITION)) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.VERIFYING_NAME);

			} else if (CommandValue.PROCESS == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));

			} else if (CommandValue.COMPLETE == c.getCommand() && (getType() == Type.KNOWLEDGE_ACQUISITION)) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.DONE_NAME);

			} else if (CommandValue.BACKLOG == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				owner = null;
				setState(TaskItem.BACKLOG_NAME);

			} else {
				throw new UnsupportedOperationException();
			}

		}

		/*
		 * Returns a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return TaskItem.PROCESSING_NAME;
		}

	}

	/**
	 * private class that represents the verifying state
	 * 
	 * @author sarahworley
	 *
	 */
	private class VerifyingState implements TaskItemState {

		/**
		 * Constructor for VerifyingState
		 * 
		 */
		private VerifyingState() {
			isVerified = false;
			state = verifyingState;
		}

		/*
		 * A user can return the task to the owner as needing additional work [S1],
		 * verify the change for a feature, bug, or technical work task as correct [S2],
		 * or cancel the edit action [S3]. The user must provide a note. A note includes
		 * the note’s author (as the verifying team member) and text.
		 * 
		 * @param c the command
		 */
		public void updateState(Command c) {
			if (CommandValue.PROCESS == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.PROCESSING_NAME);
				
			} else if (CommandValue.COMPLETE == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				isVerified = true; 
				setState(TaskItem.DONE_NAME);
				
			} else {
				throw new UnsupportedOperationException();
			}

		}

		/* string representation of the state name
		 * @return a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return TaskItem.VERIFYING_NAME;
		}
	}

	/**
	 * private class that represents the DoneState
	 * 
	 * @author sarahworley
	 *
	 */
	private class DoneState implements TaskItemState {

		/**
		 * Constructor for VerifyingState
		 * 
		 */
		private DoneState() {
			state = doneState;
		}

		/*
		 *  A user can return the task to the owner for additional work [S1],
		 *  return the task to the backlog for additional work by another developer [S2],
		 *  or cancel the edit action [S3]. The user must provide a note, which is authored by the owner.
		 *  
		 * 
		 */
		public void updateState(Command c) {
			if(CommandValue.BACKLOG == c.getCommand()) {
				owner = null;
				setState(TaskItem.BACKLOG_NAME);
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				 
			} else if(CommandValue.PROCESS == c.getCommand()) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.PROCESSING_NAME);
			} else {
				throw new UnsupportedOperationException();
			}
		
		}

		/*
		 * Returns a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return TaskItem.DONE_NAME;
		}

	}

	/**
	 * private class that represents the RejectedState
	 * 
	 * @author sarahworley
	 *
	 */
	private class RejectedState implements TaskItemState {

		/**
		 * Constructor for VerifyingState
		 * 
		 */
		private RejectedState() {
			isVerified = false;
			owner = null;

		}

		/*
		 * A user can return the task to the backlog for additional work by another developer [S1]
		 *  or cancel the edit action [S2]. The user must provide a note. A note includes the note’s author and text.
		 * 
		 */
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.BACKLOG) {
				notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
				setState(TaskItem.BACKLOG_NAME);
			} else {
				throw new UnsupportedOperationException();
			}
			
		}

		/*
		 * Returns a string representation of the state name
		 * 
		 */
		public String getStateName() {

			return TaskItem.REJECTED_NAME;
		}

	}

}
