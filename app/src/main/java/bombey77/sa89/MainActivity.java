package bombey77.sa89;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    MyTask myTask;

    private static final String LOG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                myTask = new MyTask();
                myTask.execute();
                break;
            case R.id.btnCancel:
                cancel();
                break;
            default:
                break;
        }
    }

    protected class MyTask extends AsyncTask <Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG, "Begin");
            tvResult.setText("Begin");
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 5; i++) {
                //всегда добавляй проверку, чтоб остановить поток
                if (isCancelled()) return null;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Log.d(LOG, "Interrupted");
                    e.printStackTrace();
                }
                Log.d(LOG, "is Canceled - " + isCancelled());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvResult.setText("End");
            Log.d(LOG, "End");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(LOG, "Canceled");
            tvResult.setText("Cancel");
        }
    }

    public void cancel() {
        if (myTask == null) return;
        Log.d(LOG, "Cancel is " + myTask.cancel(true));
    }
}
