package com.truongtvd.callsmsfilter.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.truongtvd.callsmsfilter.R;
import com.truongtvd.callsmsfilter.database.DataCallFilterHelper;
import com.truongtvd.callsmsfilter.model.CallFilter;

import java.util.ArrayList;

/**
 * Created by truongtvd on 6/11/14.
 */
public class CallAdapter extends ArrayAdapter<CallFilter> {
    private Context context;
    private ArrayList<CallFilter> list=null;
    private LayoutInflater inflater;



    public CallAdapter(Context context, int resource, ArrayList<CallFilter> list) {
        super(context, resource, list);
        this.context=context;
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.tvName);
            viewHolder.tvPhone=(TextView)convertView.findViewById(R.id.tvPhoneNumber);
            viewHolder.btnRemove=(ImageButton)convertView.findViewById(R.id.btnRemove);
            convertView.setTag(viewHolder);
        }else{

            viewHolder=(ViewHolder)convertView.getTag();
        }
        final CallFilter item=getItem(position);
        if(TextUtils.isEmpty(item.getName())){
            viewHolder.tvName.setText(context.getString(R.string.contact_name, "UNKNOW NAME"));
        }else {
            viewHolder.tvName.setText(context.getString(R.string.contact_name, item.getName()));
        }
        viewHolder.tvPhone.setText(context.getString(R.string.phone_number,item.getPhone()));
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
                        DataCallFilterHelper dataCallFilterHelper=new DataCallFilterHelper(context);
                        dataCallFilterHelper.deleteFilter(item.getPhone());
                        remove(getItem(position));
                       // list.remove(position);
                        notifyDataSetChanged();

                    }
                });
                message.show();
            }
        });
        return convertView;
    }

    private class ViewHolder{
        public TextView tvName,tvPhone;
        public ImageButton btnView,btnRemove;

    }
}
