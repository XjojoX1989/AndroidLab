package project.chris.androidLab;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        refresh();
////        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
//        final User user = new User("Chris", 30);
//        binding.setUsers(user);
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(3000);
//                user.setName("Chris 2");
//            }
//        });
//        executorService.shutdown();

    }

    private void refresh() {
        //做一些初始化動作或是根服務器要資料
//
//        TextView textView = findViewById(R.id.textView);
//        textView.setText("OOXXGGYY");
//
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //do something
//            }
//        });
//
//        Button btRefresh = findViewById(R.id.btRefresh);
//        btRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                onCreate(null);
//                refresh();
//            }
//        });

    }

}
