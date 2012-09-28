package com.dngames.ussd.stop;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.ClipboardManager;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final EditText number = (EditText)findViewById(R.id.number);
        Button callbutton = (Button)findViewById(R.id.callbutton);
        Button cancelbutton = (Button)findViewById(R.id.cancelbutton);
        
        if(getIntent() != null && getIntent().getData() != null)
        {
        	String given = getIntent().getData().toString();
        	String ussd = given.replace("tel:", "");
        	
        	if(ussd.contains("*") || ussd.contains("%23") || ussd.contains("#"))
        	{
	        	number.setText(ussd);
	        	((TextView)findViewById(R.id.infotext)).setText(R.string.infotext);
	        	callbutton.setText(R.string.callbutton_title);
        	}
        	else
        	{
        		Toast.makeText(this, R.string.noussd, Toast.LENGTH_SHORT).show();
        		
				Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(given));
                startActivity(intent);
        	}
        }
        else
        {
        	number.setText("*#06#");
        	((TextView)findViewById(R.id.infotext)).setText(R.string.infotext_empty);
        	callbutton.setText(R.string.callbutton_test);
        }
        
        callbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_DIAL);//"android.intent.action.CALL_PRIVILEGED");
				String callnow = number.getText().toString().trim();
				
				ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				clipboard.setText(callnow);
				
                intent.setData(Uri.parse("tel:" + callnow));
                startActivity(intent);
            }});
        
        cancelbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				String callnow = number.getText().toString().trim();
				ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				clipboard.setText(callnow);
				
				finish();
            }});
    }
}
