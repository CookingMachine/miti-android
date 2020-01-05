package com.shveed.cookmegood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.shveed.wallpapperparser.R;

import butterknife.BindView;

public class LoginDialog extends AppCompatDialogFragment {

    @BindView(R.id.loginEdit) EditText loginEdit;
    @BindView(R.id.passwordEdit) EditText passwordEdit;

    private LoginDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_alert, null);
        builder.setView(view)
                .setPositiveButton("Войти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String login = loginEdit.getText().toString();
                        String password = passwordEdit.getText().toString();
                        listener.loginUser(login, password);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (LoginDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement LoginDialogListener");
        }
    }

    public interface LoginDialogListener{
        void loginUser(String login, String password);
    }
}
