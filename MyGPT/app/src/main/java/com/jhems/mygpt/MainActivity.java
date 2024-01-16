package com.jhems.mygpt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtWelcome;
    EditText edtMessage;
    ImageButton btnSend;
    List<Message> messageList;

    MessageAdapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.chat_rv);
        txtWelcome = findViewById(R.id.txtWelcome);
        edtMessage = findViewById(R.id.message_edit_text);
        btnSend = findViewById(R.id.send_btn);

        messageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(messageList, this);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = edtMessage.getText().toString().trim();
                addToChat(question, Message.SENT_BY_ME);
                edtMessage.setText("");
                CallAPI(question);
                txtWelcome.setVisibility(View.GONE);
            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void CallAPI(String question) {
        // Chunk the conversation into parts if it's too long
        List<String> conversationChunks = chunkConversation(question);

        // Send each chunk to the API
        for (String chunk : conversationChunks) {
            sendAPIRequest(chunk);
        }
    }

    List<String> chunkConversation(String conversation) {
        int chunkSize = 50; // Adjust the chunk size as needed
        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < conversation.length(); i += chunkSize) {
            int end = Math.min(i + chunkSize, conversation.length());
            chunks.add(conversation.substring(i, end));
        }

        return chunks;
    }

    void sendAPIRequest(String chunk) {
        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray messageArr = new JSONArray();

            JSONObject obj = new JSONObject();
            obj.put("role", "user");
            obj.put("content", chunk);
            messageArr.put(obj);

            jsonObject.put("messages", messageArr);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Fail to Load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    addResponse("Failed to response due to " + response.body().string());
                }
            }
        });
    }
}
