package com.android.interpreter.interpreter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * This Activity represents the screen with one Chat.
 */
public class ChatActivity extends Activity {

    public final static String SENDER_ID = "sender";
    public final static String RECEIVER_ID = "receiver";
    public User current_user;

    protected String translatedText;


    // Parts needed for the UI, where all the messages are stored in 'messages'.
    private ListView messageList;
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
                        new createTranslator().execute(current);
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

//            if (convertView == null) {
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
                            new createTranslator().execute(messages.get(position));
                            showDetails.putExtra(MessageDetailsActivity.TRANSLATE_CONTENT, messages.get(position).getTranslatedMessage());
                            startActivity(showDetails);

                            return true;
                        }
                    });
                }

                holder = new ViewHolder();
                holder.content = (TextView) view.findViewById(R.id.content);
                holder.date = (TextView) view.findViewById(R.id.date);
                view.setTag(holder);

//            } else {
//                view = convertView;
//                holder = (ViewHolder) view.getTag();
//            }

            Message current = messages.get(position);

            String messagesText = current.getMessage();
            if (senderID.equals(messages.get(position).getSenderID())) {           // NO TRANSLATION
                holder.content.setText(messagesText);
            } else {
                String translatedMessage = current.getTranslatedMessage();
                if (translatedMessage != null) {
                    holder.content.setText(translatedMessage);
                } else {
                    holder.content.setText(messagesText);
                }
            }

            holder.date.setText(df.format(current.getDate()));          // more info on df : top of the class

            //System.out.println("TRANSLATED MESSAGE : " + messages.get(position).getTranslatedMessage());

            //holder.content.setText(messages.get(position).getTranslatedMessage());
            return view;
        }

        // This class is used to create the message squares.
        private class ViewHolder {
            public TextView content;
            public TextView date;
        }
    }

    private class createTranslator extends AsyncTask<Message, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Message... params) {


            StringBuilder result = new StringBuilder();


            try {
                String encodedText = URLEncoder.encode(params[0].getMessage(), "UTF-8");
                String originalLanguage = Config.getLangCode(params[0].getOriginalLanguage());
                String targetLanguage = Config.getLangCode(current_user.getReceivingLanguage());
                String urlStr = "https://www.googleapis.com/language/translate/v2?key=" + Config.BOSS_API_KEY +
                        "&q=" + encodedText +
                        "&target=" + targetLanguage +
                        "&source=" + originalLanguage;

                URL url = new URL(urlStr);

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                InputStream stream;
                if (conn.getResponseCode() == 200) //success
                {
                    stream = conn.getInputStream();
                } else
                    stream = conn.getErrorStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JsonParser parser = new JsonParser();

                JsonElement element = parser.parse(result.toString());

                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    if (obj.get("error") == null) {
                        String translatedMsg = obj.get("data").getAsJsonObject().
                                get("translations").getAsJsonArray().
                                get(0).getAsJsonObject().
                                get("translatedText").getAsString();
                        params[0].setTranslatedMessage(translatedMsg);
                        translatedText = translatedMsg;
                        System.out.println("original message  " + params[0].getMessage());
                        System.out.println("Result from api  " + translatedMsg);
                        return true;
                    }
                }

                if (conn.getResponseCode() != 200) {
                    System.err.println(result);
                }

            } catch (IOException | JsonSyntaxException ex) {
                System.err.println(ex.getMessage());
            }

            System.out.println("Did not translate  " + params[0].getMessage());

            return false;
        }

        @Override
        protected void onPostExecute(Boolean translated) {
            super.onPostExecute(translated);
            if (translated) {
                messageListAdapter.notifyDataSetChanged();
            }


        }
    }


}
