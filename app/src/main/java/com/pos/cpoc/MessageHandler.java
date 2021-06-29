package com.pos.cpoc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * @author eric.song
 * @date 2021/5/21 13:46
 */
public final class MessageHandler extends Handler {
    @SuppressLint("SimpleDateFormat")
    private static DateFormat _dateFormat = new SimpleDateFormat(
            "MM/dd HH:mm:ss");
    private StringBuffer _msg = new StringBuffer();
    private TextView _tv;
    private int _lines = 20;

    public MessageHandler(TextView tv) {
        super(Looper.myLooper());
        _tv = tv;
    }

    public MessageHandler(TextView tv, int msgLines) {
        super(Looper.myLooper());
        _tv = tv;
        _lines = msgLines;
    }

    public void sendMessage(String msg) {
        Message message = Message.obtain();
        message.obj = msg;
        sendMessage(message);
    }

    public void handleMessage(Message msg) {
		/*
		Date date = new Date();

		_msg.append(_dateFormat.format(date)).append(":");
		_msg.append(msg.obj);
		String text = _tv.getText().toString();
		if (text != null && !"".equals(text)) {
			String[] str = text.split("\r\n");
			if (str != null) {
				for (int i = 0; i < _lines && i < str.length; i++) {
					_msg.append("\r\n");
					_msg.append(str[i]);
				}
			}
			str = null;
		}

		_tv.setText(_msg.toString());
		_msg.setLength(0);
		*/
        _tv.setText(msg.obj.toString());
    }
}
