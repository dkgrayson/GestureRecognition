package edu.pitt.cs1635.dkg8.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.widget.*;
import android.view.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.content.Intent;
import java.util.List;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpResponse;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GestureView extends Activity {

DrawingView dv ;   
private Paint paint;
public static Color color;
TextView tv;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LinearLayout layout = new LinearLayout(this);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    layout.setLayoutParams(params);
    layout.setOrientation(LinearLayout.VERTICAL);
    dv = new DrawingView(this);
    params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    dv.setLayoutParams(params);
    dv.setDrawingCacheEnabled(true);
    
    Button clearButton = new Button(this);

    params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    clearButton.setLayoutParams(params);
    clearButton.setGravity(Gravity.CENTER);
    clearButton.setText("Clear Canvas");
    
    Button colorButton = new Button(this);
    
    colorButton.setLayoutParams(params);
    colorButton.setGravity(Gravity.CENTER);
    colorButton.setText("Change Brush Color");
    
    Button submit = new Button(this);
    
    submit.setLayoutParams(params);
    submit.setGravity(Gravity.CENTER);
    submit.setText("Submit Gesture");
    
    tv = new TextView(this);
    tv.setLayoutParams(params);
    tv.setText("Translated Text: ");
    layout.addView(dv);
    layout.addView(clearButton);
    layout.addView(colorButton);
    layout.addView(submit);
    layout.addView(tv);
    setContentView(layout);
    
    
   clearButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            dv.clear();
        }
    });
   
   colorButton.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
    	   changeActivity();

       }
   });
   
   //On click listener submits points to server, puts result in textview and clears path
   submit.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
    	    String strokes = "[";
    	    for (int f : dv.points){
    	    	strokes += Integer.toString(f) + ", ";
    	    }
    	    strokes += "255, 255]";
    	   	List<NameValuePair> nvp = new ArrayList<NameValuePair>();
    	   	nvp.add(new BasicNameValuePair("key", "11773edfd643f813c18d82f56a8104ed"));
    	   	nvp.add(new BasicNameValuePair("q", strokes));
    	   	HttpClient httpclient = new DefaultHttpClient();
    	    HttpPost httppost = new HttpPost("http://cwritepad.appspot.com/reco/usen");
    	    
    	    try {
    	        httppost.setEntity(new UrlEncodedFormEntity(nvp));
    	        HttpResponse response = httpclient.execute(httppost);
    	      
    	        BufferedReader download = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String temp = "";

                while ((temp = download.readLine()) != null) {
                    sb.append(temp);
                }

                tv.setText("Translated Text: " + sb.toString());
                dv.points.clear();
                dv.clear();
    	    } catch (ClientProtocolException e) {
    	    	tv.setText(e.toString());
    	    } catch (IOException e) {
    	    	tv.setText(e.toString());
    	    }
       }
   });
 
   	//Set up paint attributes
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeWidth(12);  
    paint.setColor(Color.parseColor("#06799F"));
    paint.setStyle(Paint.Style.STROKE);
  
}
	//Call the second activity
	public void changeActivity(){
		   Intent i = new Intent(this, ColorChange.class);
		   startActivityForResult(i, 1);
	}
	
	//Handle the result sent back from the second activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		String newColor = data.getStringExtra("COLOR");
		if(newColor.equals("blue")){
			paint.setColor(Color.parseColor("#06799F"));
		}
		else if(newColor.equals("green")){
			paint.setColor(Color.parseColor("#00C12B"));
		}
		else if(newColor.equals("purple")){
			paint.setColor(Color.parseColor("#CF5FD3"));
		}
	}
	
	//Class for drawing area
	 public class DrawingView extends View {
	        public int width;
	        public  double height;
	        private Path    path;
	        private Paint   bitmapPaint;
	        private Bitmap  bitmap;
	        private Canvas  canvas;
	        
	        public List<Integer> points = new ArrayList();
	        
	        //Constructor, also sets drawing area size
	        public DrawingView(Context c) {
	        super(c);
	        setMinimumWidth(500);
	        setMinimumHeight(500);
	        path = new Path();
	        bitmapPaint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
	   }
	
	    //Create bitmap
	   @Override
	   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        super.onSizeChanged(w, h, oldw, oldh);
		    bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		    canvas = new Canvas(bitmap);
	    }
	        
	   //Get the size of the paint area
        @Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension(
			getSuggestedMinimumWidth(),
			getSuggestedMinimumHeight());
		}
        
        //override onDraw
        protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        canvas.drawColor(Color.parseColor("#FF8300"));
	        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
	        canvas.drawPath(path,  paint);
        }

        private float X, Y;
        private int max = 255;
        private int min = 0;

        //touch down event
        private void touch_start(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        X = x;
        Y = y;
        points.add(Math.round(X % 254));
        points.add(Math.round(Y % 254));
        }
        
        //touch move event, check to see if user drew far enough to draw new line
        private void touch_move(float x, float y) {
	        float dx = Math.abs(x - X);
	        float dy = Math.abs(y - Y);
	        points.add(Math.round(x % 254));
            points.add(Math.round(y % 254));
	        if (dx >= 4 || dy >= 4) {
	            path.quadTo(X, Y, (x + X)/2, (y + Y)/2);
	            X = x;
	            Y = y;
	        }
        }
        
        //touch up event
        private void touch_up() {
	        path.lineTo(X, Y);
	        canvas.drawPath(path, paint);
	        path.reset();
	        points.add(max);
	        points.add(min);
        }

        //Figure out what touch event we're using, call its method, then invalidate
        @Override
        public boolean onTouchEvent(MotionEvent event) {
	        float x = event.getX();
	        float y = event.getY();
	        
	        switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                touch_start(x, y);
	                invalidate();
	                break;
	            case MotionEvent.ACTION_MOVE:
	                touch_move(x, y);
	                invalidate();
	                break;
	            case MotionEvent.ACTION_UP:
	                touch_up();
	                invalidate();
	                break;
	        }
	        return true;
        }
        
        //Clear drawing area
        public void clear(){
    		canvas.drawColor(Color.parseColor("#FF8300"));
    		invalidate();
    		points.clear();
    	}
     }
  }
