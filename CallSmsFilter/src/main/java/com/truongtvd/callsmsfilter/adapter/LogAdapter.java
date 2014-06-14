package com.truongtvd.callsmsfilter.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.truongtvd.callsmsfilter.R;
import com.truongtvd.callsmsfilter.database.DataLogHelper;
import com.truongtvd.callsmsfilter.model.LogFilter;
import com.truongtvd.callsmsfilter.util.Util;

import java.util.ArrayList;

/**
 * Created by truongtvd on 6/12/14.
 */
public class LogAdapter  extends ArrayAdapter<LogFilter>{
    private Context context;
    private ArrayList<LogFilter> list=null;
    private LayoutInflater inflater;

    public LogAdapter(Context context, int resource, ArrayList<LogFilter> list) {
        super(context, resource, list);
        this.context=context;
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_log_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.tvPhone=(TextView)convertView.findViewById(R.id.tvPhone);
            viewHolder.tvTime=(TextView)convertView.findViewById(R.id.tvTime);
            viewHolder.btnRemove=(ImageButton)convertView.findViewById(R.id.btnRemove);
            convertView.setTag(viewHolder);
        }else{

            viewHolder=(ViewHolder)convertView.getTag();
        }
        final LogFilter logFilter=getItem(position);

        viewHolder.tvPhone.setText(context.getString(R.string.phone_number,logFilter.getPhone()));
        viewHolder.tvTime.setText(context.getString(R.string.time_call, Util.convertDate(logFilter.getTime())));
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder message=new AlertDialog.Builder(context);
                message.setTitle( context.getString(R.string.alert));
                message.setMessage( context.getString(R.string.content));
                message.setNeutralButton( context.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        }

                );
                message.setPositiveButton( context.getString(R.string.ok), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataLogHelper dataLogHelper=new DataLogHelper(context);
                        dataLogHelper.deleteFilter(logFilter.getTime());
                        remove(getItem(position));
                        //list.remove(position);
                        notifyDataSetChanged();

                    }
                });
                message.show();
            }
        });


        return convertView;
    }

    private class ViewHolder{
        public TextView tvPhone,tvTime;
        public ImageButton btnRemove;
    }
}
