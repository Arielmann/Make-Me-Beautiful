package com.example.home.makemebeautiful.contactedusers.events;

import com.example.home.makemebeautiful.contactedusers.model.ContactedUserRow;

import java.util.List;

/**
 * Created by home on 11/4/2016.
 */
public class OnContactedUsersLoadedEvent {

    public List<ContactedUserRow> contactedUsersRows;

    public OnContactedUsersLoadedEvent(List<ContactedUserRow> contactedUsersRows) {
        this.contactedUsersRows = contactedUsersRows;
    }
}
