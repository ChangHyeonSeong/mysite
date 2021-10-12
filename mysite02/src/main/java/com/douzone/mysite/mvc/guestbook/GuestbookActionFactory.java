package com.douzone.mysite.mvc.guestbook;




import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {
 
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("list".equals(actionName)) {	
			action = new GuestbookAction();
		} else if("add".equals(actionName)) {
			action = new AddAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else {
			action = new MainAction();
		}
		return action;
	}
	

	
	

}
