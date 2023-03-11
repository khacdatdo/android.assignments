package dev.khacdatdo.androidassignments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import adaper.VeAdapter;
import model.Ve;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Ve> veArrayList = new ArrayList<>();
    private VeAdapter veAdapter;
    private EditText edtMaVe;
    private RadioGroup radioLoaiVe;
    private EditText edtNgayBay;
    private CheckBox ckbThanhToan;
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
        this.listView = this.findViewById(R.id.list_ve);
        this.edtMaVe = this.findViewById(R.id.edit_maVe);
        this.radioLoaiVe = this.findViewById(R.id.radio_loaiVe);
        this.edtNgayBay = this.findViewById(R.id.edit_ngayBay);
        this.ckbThanhToan = this.findViewById(R.id.checkbox_thanhToan);
        this.edtSearch = this.findViewById(R.id.edt_searchTasks);
        this.btnAddTask = this.findViewById(R.id.btn_addTask);
        this.btnUpdateTask = this.findViewById(R.id.btn_editTask);
        this.btnDeleteTask = this.findViewById(R.id.btn_deleteTask);
        this.btnSearch = this.findViewById(R.id.btn_searchTask);

        // Seed list tasks
        this.veArrayList.add(new Ve("123", "VIP", "2022-02-02", true));
        this.veArrayList.add(new Ve("123", "VIP", "2022-02-02", true));
        this.veArrayList.add(new Ve("123", "VIP", "2022-02-02", true));
        this.veArrayList.add(new Ve("123", "VIP", "2022-02-02", true));

        this.initListView();

        // Add events
        edtNgayBay.setOnClickListener(this.onPickDate());
        listView.setOnItemClickListener(this.onClickTaskItem());
        btnAddTask.setOnClickListener(this.onAddTask());
        btnUpdateTask.setOnClickListener(this.onUpdateTask());
        btnDeleteTask.setOnClickListener(this.onDeleteTask());
        btnSearch.setOnClickListener(this.onSearchTask());
    }

    protected void initListView() {
        this.veAdapter = new VeAdapter(MainActivity.this, this.veArrayList);
        this.listView.setAdapter(this.veAdapter);
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected boolean validate() {
        if (this.edtMaVe.getText().toString().isEmpty()
                || this.edtNgayBay.getText().toString().isEmpty()) {
            showToast("Must not empty");
            return false;
        }

        return true;
    }

    protected void fillTaskToForm(int position) {
        Ve ve = this.veArrayList.get(position);
        edtMaVe.setText(ve.getMave());
        edtNgayBay.setText(ve.getNgayBay());
        ckbThanhToan.setChecked(ve.isThanhToan());
        switch (ve.getLoaiVe()) {
            case "VIP": {
                RadioButton radio = (RadioButton) findViewById(R.id.check_vip);
                radio.setChecked(true);
                break;
            }
            case "Phổ thông": {
                RadioButton radio = (RadioButton) findViewById(R.id.check_phothong);
                radio.setChecked(true);
                break;
            }
            case "Giá rẻ": {
                RadioButton radio = (RadioButton) findViewById(R.id.check_giare);
                radio.setChecked(true);
                break;
            }
        }
    }

    protected void clearForm() {
        edtMaVe.setText("");
        edtNgayBay.setText("");
        ckbThanhToan.setChecked(false);
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
                                edtNgayBay.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
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
                System.out.println("CLicked");
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
                    RadioButton radioButton = (RadioButton) findViewById(radioLoaiVe.getCheckedRadioButtonId());
                    veArrayList.add(
                            new Ve(
                                    edtMaVe.getText().toString(),
                                    radioButton.getText().toString().trim(),
                                    edtNgayBay.getText().toString(),
                                    ckbThanhToan.isChecked()
                            )
                    );
                    veAdapter.notifyDataSetChanged();
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
                    RadioButton radioButton = (RadioButton) findViewById(radioLoaiVe.getCheckedRadioButtonId());
                    veArrayList.set(
                            taskItemSelected,
                            new Ve(
                                    edtMaVe.getText().toString(),
                                    radioButton.getText().toString().trim(),
                                    edtNgayBay.getText().toString(),
                                    ckbThanhToan.isChecked()
                            )
                    );
                    veAdapter.notifyDataSetChanged();
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
                veArrayList.remove(taskItemSelected);
                veAdapter.notifyDataSetChanged();
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
                ArrayList<Ve> filteredVe = new ArrayList<>();
                veArrayList.forEach(ve -> {
                    if (ve.getMave().indexOf(edtSearch.getText().toString().trim()) != -1) {
                        filteredVe.add(ve);
                    }
                });
                VeAdapter filteredAdapter = new VeAdapter(MainActivity.this, filteredVe);
                listView.setAdapter(filteredAdapter);
            }
        };
    }
}