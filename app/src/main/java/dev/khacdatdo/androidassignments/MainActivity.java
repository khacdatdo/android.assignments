package dev.khacdatdo.androidassignments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import adaper.TaskAdapter;
import model.Task;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> taskArrayList = new ArrayList<>();
    private TaskAdapter taskAdapter;

    private EditText edtTaskName;
    private EditText edtTaskDescription;
    private RadioGroup radioGender;
    private EditText edtDueDate;
    private EditText edtSearch;
    private Button btnAddTask;
    private Button btnUpdateTask;
    private Button btnDeleteTask;
    private Button btnSearch;
    private ListView listView;

    private int taskItemSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Resources
        this.listView = this.findViewById(R.id.list_tasks);
        this.edtTaskName = this.findViewById(R.id.edt_taskName);
        this.edtTaskDescription = this.findViewById(R.id.edt_taskDescription);
        this.radioGender = this.findViewById(R.id.radio_taskGender);
        this.edtDueDate = this.findViewById(R.id.edt_taskDueDate);
        this.edtSearch = this.findViewById(R.id.edt_searchTasks);
        this.btnAddTask = this.findViewById(R.id.btn_addTask);
        this.btnUpdateTask = this.findViewById(R.id.btn_editTask);
        this.btnDeleteTask = this.findViewById(R.id.btn_deleteTask);
        this.btnSearch = this.findViewById(R.id.btn_searchTask);

        // Seed list tasks
        this.taskArrayList.add(new Task("Làm bài tập", "Bài tập Android", "male", "2023-03-03"));
        this.taskArrayList.add(new Task("Thực hành", "Thực hành lập trình Android", "female", "2023-03-20"));
        this.initListView();

        // Add events
        edtDueDate.setOnClickListener(this.onPickDate());
        listView.setOnItemClickListener(this.onClickTaskItem());
        btnAddTask.setOnClickListener(this.onAddTask());
        btnUpdateTask.setOnClickListener(this.onUpdateTask());
        btnDeleteTask.setOnClickListener(this.onDeleteTask());
        btnSearch.setOnClickListener(this.onSearchTask());
    }

    protected void initListView() {
        this.taskAdapter = new TaskAdapter(this, taskArrayList);
        this.listView.setAdapter(this.taskAdapter);
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    protected boolean validate() {
        if (this.edtTaskName.getText().toString().isEmpty()
                || this.edtTaskDescription.getText().toString().isEmpty()
                || this.edtDueDate.getText().toString().isEmpty()) {
            showToast("Must not empty");
            return false;
        }

        return true;
    }

    protected void fillTaskToForm(int position) {
        Task task = taskArrayList.get(position);
        edtTaskName.setText(task.getName());
        edtTaskDescription.setText(task.getDescription());
        edtDueDate.setText(task.getDueDate());
        radioGender.check(task.getGender().compareToIgnoreCase("male") == 0 ? R.id.check_genderMale : R.id.check_genderFemale);
    }

    protected void clearForm() {
        edtTaskName.setText("");
        edtTaskDescription.setText("");
        edtDueDate.setText("");
    }

    protected View.OnClickListener onPickDate() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                edtDueDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        };
    }

    protected AdapterView.OnItemClickListener onClickTaskItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskItemSelected = i;
                fillTaskToForm(i);
            }
        };
    }

    protected View.OnClickListener onAddTask() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    RadioButton radioButton = (RadioButton) findViewById(radioGender.getCheckedRadioButtonId());
                    taskArrayList.add(
                            new Task(
                                    edtTaskName.getText().toString(),
                                    edtTaskDescription.getText().toString(),
                                    radioButton.getText().toString().toLowerCase().trim(),
                                    edtDueDate.getText().toString()
                            )
                    );
                    taskAdapter.notifyDataSetChanged();
                    showToast("Added");
                    clearForm();
                }
            }
        };
    }

    protected View.OnClickListener onUpdateTask() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskItemSelected == -1) {
                    showToast("Please select an item");
                    return;
                }
                if (validate()) {
                    RadioButton radioButton = (RadioButton) findViewById(radioGender.getCheckedRadioButtonId());
                    taskArrayList.set(
                            taskItemSelected,
                            new Task(
                                    edtTaskName.getText().toString(),
                                    edtTaskDescription.getText().toString(),
                                    radioButton.getText().toString().toLowerCase(),
                                    edtDueDate.getText().toString()
                            )
                    );
                    taskAdapter.notifyDataSetChanged();
                    taskItemSelected = -1;
                    showToast("Updated");
                    clearForm();
                }
            }
        };
    }

    protected View.OnClickListener onDeleteTask() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskItemSelected == -1) {
                    showToast("Please select an item");
                    return;
                }
                taskArrayList.remove(taskItemSelected);
                taskAdapter.notifyDataSetChanged();
                taskItemSelected = -1;
                showToast("Removed");
                clearForm();
            }
        };
    }

    protected View.OnClickListener onSearchTask() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Task> filteredTask = new ArrayList<>();
                taskArrayList.forEach(task -> {
                    if (task.getName().indexOf(edtSearch.getText().toString().trim()) != -1) {
                        filteredTask.add(task);
                    }
                });
                TaskAdapter filteredAdapter = new TaskAdapter(MainActivity.this, filteredTask);
                listView.setAdapter(filteredAdapter);
            }
        };
    }
}