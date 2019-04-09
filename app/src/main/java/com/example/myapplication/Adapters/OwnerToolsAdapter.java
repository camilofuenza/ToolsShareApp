package com.example.myapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Tool;
import com.example.myapplication.MyTools;
import com.example.myapplication.R;

import java.util.List;

public class OwnerToolsAdapter extends BaseAdapter{


        private Context myContext;
        private List<Tool> toolList;

        private MyTools.CallbackInterface deleteCallback;
        public void SetDeleteCallback(MyTools.CallbackInterface callback) {
            this.deleteCallback = callback;
        }


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
            // 2. Have we inflated this view before?
            View v;
            if (convertView != null) {

                // 2a. We have so let's reuse.
                v = convertView;
            }
            else {

                // 2b. We have NOT so let's inflate
                LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.tool_listview_ownerview, parent, false);
            }

            TextView tvtoolName= v.findViewById(R.id.tv_toolName);
            TextView tvcategoryName= v.findViewById(R.id.tv_categoryName);


            tvtoolName.setText(String.valueOf(toolList.get(position).getToolName()));

            tvcategoryName.setText(String.valueOf(toolList.get(position).getCategoryName()));

            //Set up Delete button
            ImageView btnEdit = (ImageView)v.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(view -> {
                //Prompt for confirmation
                //TODO
                //Here we can do some async work, and show a toast message on completion.
                //new DeleteToolTask(this.myContext).execute(toolList.get(position).getIdTool());
                deleteCallback.doTask(toolList.get(position)); //I HATE THIS
            });

            //Set up Approve button
            //TODO: Here we can do some async work, and show a toast message on completion.

            return v;
        }

    private static class DeleteToolTask extends AsyncTask<Integer, Void, Integer> {

        private Context context;
        private com.example.myapplication.Database.DBAccess dbAccess;

        DeleteToolTask(Context context) {
            this.context = context;
            this.dbAccess = DBAccess.getInstance(context);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {

            this.dbAccess.open();
            this.dbAccess.deleteTool(ints[0]);
            this.dbAccess.close();

            return ints[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(this.context, "Deleted " + result, Toast.LENGTH_SHORT).show();
            //TODO: can I just call RefreshListTask here, or should I just delete the list item.
        }
    }
    }


