package adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import dev.khacdatdo.androidassignments.R;
import model.Ve;

public class VeAdapter extends ArrayAdapter {
    private Context ctx;
    private ArrayList<Ve> veArrayList;

    public VeAdapter(@NonNull Context ctx, @NonNull ArrayList<Ve> veArrayList) {
        super(ctx, R.layout.ve_item, veArrayList);
        this.ctx = ctx;
        this.veArrayList = veArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View veItemView = LayoutInflater.from(this.ctx).inflate(R.layout.ve_item, null, true);
        CheckBox ckbThanhToan = veItemView.findViewById(R.id.checkbox_item_thanhToan);
        TextView txtNoidung = veItemView.findViewById(R.id.txt_item_noidung);

        Ve ve = this.veArrayList.get(position);
        ckbThanhToan.setChecked(ve.isThanhToan());
        txtNoidung.setText(
                ve.getMave() + " - " + ve.getLoaiVe() + " - Ngày bay: " + ve.getNgayBay()
        );

        return veItemView;
    }
}
