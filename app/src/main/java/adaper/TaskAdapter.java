package adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import dev.khacdatdo.androidassignments.R;
import model.Task;

public class TaskAdapter extends ArrayAdapter {
    private Context ctx;
    private ArrayList<Task> taskArrayList;

    public TaskAdapter(@NonNull Context ctx, @NonNull ArrayList<Task> taskArrayList) {
        super(ctx, R.layout.task_item, taskArrayList);
        this.ctx = ctx;
        this.taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View taskItemView = LayoutInflater.from(this.ctx).inflate(R.layout.task_item, null, true);
        ImageView imageView = taskItemView.findViewById(R.id.image_task_image);
        TextView taskNameView = taskItemView.findViewById(R.id.txt_task_name);
        TextView taskContentView = taskItemView.findViewById(R.id.txt_task_content);
        TextView taskDueDateView = taskItemView.findViewById(R.id.txt_task_dueDate);

        Task task = this.taskArrayList.get(position);
        imageView.setImageResource(task.getGender().compareToIgnoreCase("male") == 0 ? R.mipmap.icon_boy_foreground : R.mipmap.icon_girl_foreground);
        taskNameView.setText(task.getName());
        taskContentView.setText(task.getDescription());
        taskDueDateView.setText(task.getDueDate());

        return taskItemView;
    }
}
