package cn.wildfire.chat.app.login;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.imchat.ezn.R;
import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.app.GameService;
import cn.wildfire.chat.kit.WfcBaseNoToolbarActivity;
import cn.wildfire.chat.kit.net.SimpleCallback;
import com.imchat.ezn.R;

public class RegisterActivity extends WfcBaseNoToolbarActivity {
    @BindView(R.id.phone)
    EditText phoneEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.repeat_password)
    EditText repeatPasswordEditText;
    @BindView(R.id.invite_code)
    EditText inviteCodeEditText;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.register_back)
    TextView backTextView;

    @Override
    protected int contentLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.registerButton)
    void register() {
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();
        String inviteCode = inviteCodeEditText.getText().toString().trim();
        registerButton.setEnabled(false);
        if (phone.length() < 6) {
            toast(getResources().getString(R.string.register_phone_error));
            registerButton.setEnabled(true);
            return;
        }
        if (password.length() < 4) {
            toast(getResources().getString(R.string.register_password_error));
            registerButton.setEnabled(true);
            return;
        }
        if (!password.equals(repeatPassword)) {
            toast(getResources().getString(R.string.register_repeat_password_error));
            registerButton.setEnabled(true);
            return;
        }
        if (inviteCode.length() < 2) {
            toast("邀请码必填");
            registerButton.setEnabled(true);
            return;
        }
        GameService.getInstance().register(phone, password, inviteCode, new SimpleCallback<Void>() {
            @Override
            public void onUiSuccess(Void aVoid) {
                toast(getResources().getString(R.string.register_success));
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                toast(msg);
                registerButton.setEnabled(true);
            }
        });
    }

    @OnClick(R.id.register_back)
    void back() {
        finish();
    }

    void toast(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
    }


}
