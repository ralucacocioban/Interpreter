package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.interpreter.Config;
import com.android.interpreter.util.GoogleTranslate;
import com.android.interpreter.util.Message;
import com.android.interpreter.util.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This Activity represents the screen with one Chat.
 */
public class ChatActivity extends AbstractActivity {

    public final static String SENDER_ID = "sender";
    public final static String RECEIVER_ID = "receiver";
    public User current_user;


    // Parts needed for the UI, where all the messages are stored in 'messages'.
    private ListView messageList;
    ImageButton imageButton;
    private MessageListAdapter messageListAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

    String receiverID;
    String senderID;

    // This will be the format we will use at the bottom of the message, displaying the date.
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    // Reference in Firebase we start.
    Firebase conversationHereRef;
    Firebase conversationOtherRef;

    Message newMessage;

    GoogleTranslate translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imageButton = (ImageButton)findViewById(R.id.logo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ConversationsActivity.class);
                startActivity(intent);
            }
        });

        Firebase.setAndroidContext(this);

        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
        final String currentUser = settings.getString("CURRENT_USER", null);

        System.out.println("in chatttt " + currentUser);

        Firebase userRef = new Firebase(DBConnector.getPathToUser(currentUser));

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                current_user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        messageList = (ListView) findViewById(R.id.messages);
        messageListAdapter = new MessageListAdapter();
        messageList.setAdapter(messageListAdapter);

        imageButton = (ImageButton)findViewById(R.id.logo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        public boolean onKeyDown(int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                // your code
//                return true;
//            }
//
//            return super.onKeyDown(keyCode, event);
//        }

        // Setting up the correct root reference, giving a Conversation.

        senderID = getIntent().getStringExtra(SENDER_ID);
        receiverID = getIntent().getStringExtra(RECEIVER_ID);
        conversationHereRef = new Firebase (DBConnector.getPathToMessages(senderID, receiverID));
        conversationOtherRef = new Firebase (DBConnector.getPathToMessages(receiverID, senderID));

        conversationHereRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Message current;
                ArrayList<Message> newmessages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    current = messageSnapshot.getValue(Message.class);
                    newmessages.add(current);
                }

                messages = newmessages;
                messageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //create connection to Google Translate API


    }

    public void sendMessage(View view) {

        newMessage = new Message();
        EditText et = (EditText) findViewById(R.id.new_message);
        newMessage.setMessage(String.valueOf(et.getText()));
        newMessage.setDate(new Date());

        //SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
        //final String currentUserID = settings.getString("CURRENT_USER", null);
        newMessage.setSenderID(current_user.getUid());
        newMessage.setOriginalLanguage(current_user.getSendingLanguage());

        conversationHereRef.push().setValue(newMessage);
        conversationOtherRef.push().setValue(newMessage);

        // Delete the sent text from the EditText
        et.setText("");
    }


    /**
     * MessageListAdapter which handles the representation of the messages.
     */
    private class MessageListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);

                // Here we get the currentUserID and then compare it with the sender of the message.
                // SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
                // final String currentUser = settings.getString("CURRENT_USER", null);

                // When the current user is the sender of this message, the message is placed right.
                if (senderID.equals(messages.get(position).getSenderID())) {
                    view = inflater.inflate(R.layout.message_outgoing, null);
                }
                // Otherwise, when the current user is the receiver, the message is placed left
                else {
                    view = inflater.inflate(R.layout.message_incoming, null);

                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            //TODO - create beautifull transition to message details
                            final Intent showDetails = new Intent(ChatActivity.this, MessageDetailsActivity.class);
                            String originallanguage = messages.get(position).getOriginalLanguage();
                            showDetails.putExtra(MessageDetailsActivity.ORIGINAL_LANGUAGE, originallanguage);
                            showDetails.putExtra(MessageDetailsActivity.ORIGINAL_CONTENT, messages.get(position).getMessage());
                            showDetails.putExtra(MessageDetailsActivity.TRANSLATE_LANGUAGE, current_user.getReceivingLanguage());

                            String targetlanguage = current_user.getReceivingLanguage();

//                            new createTranslator().execute();
                            String translatedText = translator.translate(messages.get(position).getMessage(), Config.getLangCode(originallanguage), Config.getLangCode(targetlanguage));


                            showDetails.putExtra(MessageDetailsActivity.TRANSLATE_CONTENT, translatedText);
                            startActivity(showDetails);

                            return true;
                        }
                    });
                }

                holder = new ViewHolder();
                holder.content = (TextView) view.findViewById(R.id.content);
                holder.date = (TextView) view.findViewById(R.id.date);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            Message current = messages.get(position);

            if (senderID.equals(messages.get(position).getSenderID())) {           // NO TRANSLATION
                holder.content.setText(current.getMessage());
            }
            else {
                String targetlanguage = current_user.getReceivingLanguage();
                translator = new GoogleTranslate("AIzaSyCXQPEmG2qw5C5iPCDWi3KieBzM7WtyIQY");
                holder.content.setText(translator.translate(messages.get(position).getMessage(),
                                Config.getLangCode(messages.get(position).getOriginalLanguage()), Config.getLangCode(targetlanguage))
                );
            }
            
            holder.date.setText(df.format(current.getDate()));          // more info on df : top of the class

            return view;
        }

        // This class is used to create the message squares.
        private class ViewHolder {
            public TextView content;
            public TextView date;
        }
    }

//    private class createTranslator extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
////                params(0);
////                params(1);
////                translator = new GoogleTranslate("AIzaSyCXQPEmG2qw5C5iPCDWi3KieBzM7WtyIQY");
////                String result = translator.translate();
////
////
////                return result;
//                Thread.sleep(2000);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return null;
//
//        }
//
//    }


}
