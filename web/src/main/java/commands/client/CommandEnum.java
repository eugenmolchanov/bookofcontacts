package commands.client;

import commands.*;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public enum CommandEnum {
    LISTOFCONTACTS {
        {
            this.command = new ShowListOfContactsCommand();
        }
    },
    DELETECONTACTS {
        {
            this.command = new DeleteContactsCommand();
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
            this.command = new ChangeLanguageCommand();
        }
    },
    DISPLAYCONTACT {
        {
            this.command = new DisplayContactCommand();
        }
    },
    DISPLAYCONTACTPHOTO {
        {
            this.command = new DisplayContactPhotoCommand();
        }
    },
    DOWNLOADATTACHMENT {
        {
            this.command = new DownloadAttachmentCommand();
        }
    },
    EDITCONTACT {
        {
            this.command = new EditContactCommand();
        }
    },
    SHOWMESSAGES {
        {
            this.command = new ShowMessagesCommand();
        }
    },
    GETMESSAGE {
        {
            this.command = new GetMessageCommand();
        }
    },
    DELETEMESSAGES {
        {
            this.command = new SendMessagesToBucketCommand();
        }
    },
    SHOWDELETEDMESSAGES {
        {
            this.command = new ShowDeletedMessagesCommand();
        }
    },
    DELETEMESSAGESFROMBUCKET {
        {
            this.command = new DeleteMessagesCommand();
        }
    },
    RESTOREMESSAGES {
        {
            this.command = new RestoreMessageCommand();
        }
    }
    ;
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
