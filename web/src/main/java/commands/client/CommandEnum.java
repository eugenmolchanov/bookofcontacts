package commands.client;

import commands.*;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public enum CommandEnum {
    LISTOFCONTACTS {
        {
            this.command = new ListOfContactsCommand();
        }
    },
    DELETECONTACTS {
        {
            this.command = new DeleteContacts();
        }
    }

    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
