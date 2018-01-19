package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Message;
import kr.sofac.jangsisters.views.adapters.MessagesAdapter;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.messages) RecyclerView messagesList;
    @BindView(R.id.new_message) EditText newMessage;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<Message> messages;
    private MessagesAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backward_start, R.anim.backward_finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initToolbar();
        loadMessages();
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.help_chat);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadMessages() {
        messages = new ArrayList<>();
        messages.add(new Message(0, getString(R.string.hello_how_can_i_help_you), getString(R.string.time_chat)));
        updateList();
    }

    private void updateList() {
        adapter = new MessagesAdapter(messages, appPreference.getUser().getId());
        messagesList.setLayoutManager(new LinearLayoutManager(this));
        messagesList.setAdapter(adapter);
        messagesList.scrollToPosition(messages.size() - 1);
    }

    @OnClick(R.id.message_add)
    public void addNewMessage(){
        if(!newMessage.getText().toString().isEmpty()){
            messages.add(new Message(appPreference.getUser().getId(), newMessage.getText().toString(),
                    getString(R.string.time_chat)));
            newMessage.setText(null);
            updateList();
        }
    }
}
