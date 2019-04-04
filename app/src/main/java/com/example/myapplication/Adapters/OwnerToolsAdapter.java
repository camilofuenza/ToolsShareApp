package com.example.myapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Model.Tool;
import com.example.myapplication.R;

import java.util.List;

public class OwnerToolsAdapter extends BaseAdapter{


        private Context myContext;
        private List<Tool> toolList;

        public OwnerToolsAdapter(Context myContext, List<Tool> toolList) {
            this.myContext = myContext;
            this.toolList= toolList;
        }

        @Override
        public int getCount() {
            return toolList.size();
        }

        @Override
        public Object getItem(int position) {
            return toolList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return toolList.get(position).getIdTool();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v= View.inflate(myContext  , R.layout.tool_listview_ownerview, null);
            TextView tvtoolName= (TextView)v.findViewById(R.id.tv_toolName);
            TextView tvtoolDescription= (TextView)v.findViewById(R.id.tv_toolDescription);
            TextView tvisAvailable= (TextView)v.findViewById(R.id.tv_isAvailable);
            TextView tvcategoryName= (TextView)v.findViewById(R.id.tv_categoryName);
            TextView tvcostumerName= (TextView)v.findViewById(R.id.tv_costumerName);


            tvtoolName.setText(String.valueOf(toolList.get(position).getToolName()));
            tvtoolDescription.setText(String.valueOf(toolList.get(position).getToolDescription()));
            tvisAvailable.setText(String.valueOf(
                    toolList.get(position).getIsAvailable() == 1 ?
                    "Available for borrowing" : "Currently in use"));
            tvcategoryName.setText("Category: " + String.valueOf(toolList.get(position).getCategoryName()));
            tvcostumerName.setText("Owner: " + String.valueOf(toolList.get(position).getCostumerName()));


            //Set up Delete button
            Button btnDelete = v.findViewById(R.id.btnDelete);
          /*  btnDelete.setOnClickListener(view -> {
                //Prompt for confirmation
                
                //TODO: Here we can do some async work, and show a toast message on completion.
            });
*/
            return v;
        }
    }


