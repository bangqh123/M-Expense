package com.example.trip.Expense_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.Database.Expense;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;

import java.util.List;

public class ExpenseAdapter extends BaseAdapter {

    private final Context context;
    private final List<Expense> list;

    public ExpenseAdapter(Context context, List<Expense> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.expense_view, viewGroup, false);

            holder.txtType = view.findViewById(R.id.txt_expense_type);
            holder.btnEdit = view.findViewById(R.id.btn_edit_expense);
            holder.btnDelete = view.findViewById(R.id.btn_delete_expense);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Expense expense = (Expense) getItem(i);

        holder.txtType.setText(expense.getType());

        DatabaseHandler db = new DatabaseHandler(context);

        holder.btnEdit.setOnClickListener(view13 -> {
            Update dialog = new Update(context, expense);
            dialog.setOnDismissListener(dialogInterface -> {
                list.clear();
                list.addAll(db.getAllExpenses());
                notifyDataSetChanged();
            });
            dialog.show();
        });

        holder.btnDelete.setOnClickListener(view12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete expense").setMessage("You want delete expense " + expense.getType() + "?");
            builder.setCancelable(true);
            builder.setNegativeButton("Cancel", (dialogInterface, ii) -> dialogInterface.dismiss());
            builder.setPositiveButton("Confirm", (dialogInterface, ii) -> {
                db.deleteExpense(expense);
                list.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted trip successfully", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            });
            builder.create().show();
        });

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("expense", expense);
            context.startActivity(intent);
        });

        return view;
    }

    private static class ViewHolder{
        TextView txtType;
        ImageButton btnEdit, btnDelete;
    }
}
