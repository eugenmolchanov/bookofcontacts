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
    },
    REDIRECT {
        {
            this.command = new RedirectCommand();
        }
    },
    CREATENEWCONTACT {
        {
            this.command = new CreateNewContactCommand();
        }
    },
    SEARCH {
        {
            this.command = new SearchCommand();
        }
    },
    SENDEMAIL {
        {
            this.command = new SendEmailCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new LanguageCommand();
        }
    }
    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
