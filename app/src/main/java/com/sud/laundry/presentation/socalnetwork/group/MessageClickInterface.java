package com.sud.laundry.presentation.socalnetwork.group;

import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageDetailsItem;

public interface MessageClickInterface {

    public  void  messageClick( int position, MessageDetailsItem messageDetailsItem);

    public  void  deleteMessageClick( int position, MessageDetailsItem messageDetailsItem);

}
