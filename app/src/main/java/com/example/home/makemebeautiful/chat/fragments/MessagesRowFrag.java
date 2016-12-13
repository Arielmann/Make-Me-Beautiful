package com.example.home.makemebeautiful.chat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.OnTextTransferred;
import com.example.home.makemebeautiful.chat.controllers.AddChatImageItemController;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;

/**
 * Created by home on 6/21/2016.
 */
public class MessagesRowFrag extends Fragment implements View.OnClickListener{

    private OnTextTransferred onTextTransferred;
    private ImageButton sendMessage;
    private TextView textTypeField;
    private ImageButton openImagesPickerButton;
    private AddChatImageItemController addImagesModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View messageComponentLayout = inflater.inflate(R.layout.component_message_row, null);
        textTypeField = (TextView) messageComponentLayout.findViewById(R.id.textTypeField); //editext inside the layout
        sendMessage = (ImageButton) messageComponentLayout.findViewById(R.id.sendButton); //button inside layout
        sendMessage.setOnClickListener(this);
        addImagesModel = new AddChatImageItemController(getContext());
        openImagesPickerButton = (ImageButton) messageComponentLayout.findViewById(R.id.cameraImageButton);
        openImagesPickerButton.setOnClickListener(addImagesModel);
        return messageComponentLayout;

        /*
        * Send images flow:
        * 1 The user clicks on openImagePickerButton
        * 2. button's model activates a dialog for choosing images
        * 3. user choose to pick from gallery or take photo from camera
        * 4. ChatScreen's onImageProvider chosen interface builds the required image frag
        * 5. photo is being chosen
        * 6. photo is scaled with imageUtils
        * 7. ChatScreen's presentImagesOnScreen interface is provoked.
        * 8. The ChatImagesModel notifies to adapter to add the new Image
        * */
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            onTextTransferred = (OnTextTransferred) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString()
                    + " must implement OnTextTransferred");
        }
    }

    @Override
    public void onClick(View v) {
        /*TODO: when this buttom clicked mulitple processes have to start:
        - transfer text for saving
        TODO: this should be done with events that listen to this click event
        */
        String message = textTypeField.getText().toString();
        onTextTransferred.onTextSentFromUser(SharedPrefManager.getInstance(getContext()).getUserName(), message);
        textTypeField.setText("");
    }
}
