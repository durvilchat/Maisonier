package com.mahya.maisonier;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;

import jp.bassaer.chatmessageview.Message;
import jp.bassaer.chatmessageview.MessageView;

public class MessageActivity extends Activity  {

    private MessageView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        messageView = (MessageView) findViewById(R.id.message_view);
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), jp.bassaer.chatmessageview.R.drawable.face_1);

        Message message1 = new Message();
        message1.setUserIcon(icon1);
        message1.setUserName("Michael");
        message1.setMessageText("hey! how are you?");
        message1.setRightMessage(true);

        ArrayList<Message> messages = new ArrayList<>();

        messages.add(message1);

        MessageView messageView  = (MessageView) findViewById(R.id.message_view);

        messageView.init(messages);
    }

}
