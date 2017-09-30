package commands.client;

import commands.*;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public enum CommandEnum {
    LISTOFCONTACTS {
        {
            this.command = new ShowListOfContacts();
        }
    },
    DELETECONTACTS {
        {
            this.command = new DeleteContacts();
        }
    },
    REDIRECT {
        {
            this.command = new Redirect();
        }
    },
    CREATENEWCONTACT {
        {
            this.command = new CreateNewContact();
        }
    },
    SEARCH {
        {
            this.command = new Search();
        }
    },
    SENDEMAIL {
        {
            this.command = new SendEmail();
        }
    },
    LANGUAGE {
        {
            this.command = new ChangeLanguage();
        }
    },
    DISPLAYCONTACT {
        {
            this.command = new DisplayContact();
        }
    },
    DISPLAYCONTACTPHOTO {
        {
            this.command = new DisplayContactPhoto();
        }
    },
    DOWNLOADATTACHMENT {
        {
            this.command = new DownloadAttachment();
        }
    }
    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
