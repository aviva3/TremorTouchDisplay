package idoelad.finalproject.tremortouch.display;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends Activity{

	public String LOG_TAG = "Test Activity";

	private TextView timeTextView;
	private TextView shapeNumberTextView;
	private RelativeLayout testZoneLayout;
	private ImageView touchPoint;

	private String fileSelected;
	private String circlesFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		//Get UI components
		shapeNumberTextView = (TextView) findViewById(R.id.shapeNumberTextView);
		shapeNumberTextView.setText("0");
		timeTextView = (TextView) findViewById(R.id.timeTextView);
		timeTextView.setText("0");
		testZoneLayout = (RelativeLayout) findViewById(R.id.testZoneLayout);
		touchPoint = (ImageView) findViewById(R.id.touchPoint);
		
		//Get relevant file path
		fileSelected = getIntent().getStringExtra("fileSelected");
		circlesFilePath = getIntent().getStringExtra("circlesFilePath");
		
		DrawingView dv = new DrawingView(this, fileSelected, circlesFilePath);
	    Canvas canvas = new Canvas();
	    dv.draw(canvas);
	    testZoneLayout.addView(dv);
	}
	
}
